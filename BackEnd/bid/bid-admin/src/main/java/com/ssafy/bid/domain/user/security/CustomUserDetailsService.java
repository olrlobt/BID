package com.ssafy.bid.domain.user.security;

import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserById(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));
        CustomUserInfo customUserInfo = new CustomUserInfo(user);

        return new CustomUserDetails(customUserInfo);
    }
}
