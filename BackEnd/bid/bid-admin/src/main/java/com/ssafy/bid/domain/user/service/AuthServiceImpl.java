package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.TokenBlacklist;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.TokenBlacklistDTO;
import com.ssafy.bid.domain.user.repository.TokenBlacklistRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.domain.user.security.CustomUserInfo;
import com.ssafy.bid.domain.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    @Transactional
    public String login(LoginRequest request) {
        String id = request.getId();
        String password = request.getPassword();
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfo info = new CustomUserInfo(user);

        return jwtUtil.createAccessToken(info);
    }

    @Override
    @Transactional
    public void logout(String token) {
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        TokenBlacklistDTO tokenBlacklistDTO = new TokenBlacklistDTO(actualToken);
        TokenBlacklist blacklistedToken = tokenBlacklistDTO.toEntity();
        tokenBlacklistRepository.save(blacklistedToken);
    }
}
