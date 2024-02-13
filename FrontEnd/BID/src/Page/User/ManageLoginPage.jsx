import React, { useState } from 'react';
import styled from './LoginPage.module.css';
import { Link, useNavigate } from 'react-router-dom';
import Logo from '../../Asset/Image/LoginLogo.png';
import useUser from '../../hooks/useUser';
import { loginUserApi } from '../../Apis/UserApis';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { setCookie } from '../../cookie';
import { getGrades } from '../../Apis/ClassManageApis';

function ManageLoginPage() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const { loginUser } = useUser();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  /** 로그인 쿼리 */
  const loginUserQuery = useMutation({
    mutationKey: ['loginUser'],
    mutationFn: (userCredentials) => loginUserApi(userCredentials),
    onSuccess: (data) => {
      loginUser(data);
      setCookie('accessToken', data.data.tokenResponse.accessToken);

      // 학급 리스트 추가
      queryClient.setQueryData([
        'classList',
        `classList_${data.data.adminInfo.userNo}`,
      ]);

      const mainClass = classList?.find((item) => item.main);
      if (mainClass !== undefined) {
        // main학급 정보 넣어서 보여줄 것
        navigate('/', { state: mainClass });
      } else {
        navigate(`/classlist/${data.data.adminInfo.userNo}/no-class`, {
          state: {
            teacherId: data.data.adminInfo.userNo,
          },
        });
      }
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const { data: classList } = useQuery({
    queryKey: ['ClassList'],
    queryFn: () =>
      getGrades().then((res) => {
        console.log(res.data);
        return res.data;
      }),
  });

  /** 로그인 버튼 */
  const handleLoginEvent = (e) => {
    e.preventDefault();
    let userCredentials = {
      id,
      password,
    };
    console.log(userCredentials);
    loginUserQuery.mutate(userCredentials);
  };

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} onSubmit={handleLoginEvent}>
          <input
            type="id"
            value={id}
            onChange={(e) => setId(e.target.value)}
            placeholder="아이디"
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호"
          />
          <button type="submit">LOGIN</button>
          <div className={styled.ContentOption}>
            <div className={styled.ContentOptions}>
              <Link to="/find_id" className={styled.findIdLink}>
                <p>아이디 찾기</p>
              </Link>
              <Link to="/change_pwd" className={styled.changePwdLink}>
                <p>비밀번호 찾기</p>
              </Link>
              <Link to="/register" className={styled.registerLink}>
                <p>회원가입</p>
              </Link>
            </div>
          </div>
        </form>
      </div>
    </section>
  );
}

export default ManageLoginPage;
