import React, { useState } from 'react';
import styled from './LoginPage.module.css';
import { Link, useNavigate } from 'react-router-dom';
import Logo from '../../Asset/Image/LoginLogo.png';
import useModels from '../../hooks/useModels';
import { studentLoginApi } from '../../Apis/ModelApis';
import { useMutation } from '@tanstack/react-query';
import { SvgIcon } from '@material-ui/core';
import SearchIcon from '@mui/icons-material/Search';
import useModal from '../../hooks/useModal';
import { setCookie } from '../../cookie';

function LoginPage() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const { loginStudent, initModels } = useModels();
  const navigate = useNavigate();
  const { openModal } = useModal();
  const [schoolCode, setSchoolCode] = useState('');

  /** 로그인 쿼리 */
  const studentLoginQuery = useMutation({
    mutationKey: ['studentLogin'],
    mutationFn: (userCredentials) => studentLoginApi(userCredentials),
    onSuccess: (res) => {
      loginStudent({ model: res.data.myInfo });
      initModels({ models: res.data.studentList });
      setCookie('accessToken', res.data.tokenResponse.accessToken);
      navigate('/studentmain');
      console.log(res);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 로그인 버튼 */
  const handleLoginEvent = (e) => {
    e.preventDefault();
    let userCredentials = {
      id,
      password,
    };
    console.log(userCredentials);
    studentLoginQuery.mutate(userCredentials);
  };

  const handlerModalClose = (schoolCode) => {
    console.log(schoolCode);
    if (schoolCode) {
      setSchoolCode(schoolCode);
    }
  };

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <SvgIcon
          component={SearchIcon}
          style={{ fill: '#4D4D4D', height: '2.5vh' }}
          className={styled.searchSchoolIcon}
          onClick={(e) => {
            e.preventDefault(); // Prevent default form submission behavior
            openModal({
              type: 'studentSchool',
              props: ['학교 검색', handlerModalClose],
            });
          }}
        />
        <input
          className={styled.searchSchool}
          type="text"
          value={schoolCode}
          placeholder="학교 코드 검색"
          readOnly
        />
        <form className={styled.contentInput} onSubmit={handleLoginEvent}>
          <div className={styled.inputWithIcon}>
            <input
              type="id"
              value={id}
              onChange={(e) => setId(e.target.value)}
              placeholder="아이디"
            />
          </div>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호"
          />
          <button type="submit">LOGIN</button>
          <div className={styled.ContentOption}>
            <div className={styled.ContentOptions}>
              <Link to="/managelogin" className={styled.managelogin}>
                <p>선생님으로 로그인하기</p>
              </Link>
            </div>
            <div className={styled.info}>아이디: 학교 코드 + 학년 반 번호</div>
            <div className={styled.info}>초기 비밀번호: 생년월일</div>
          </div>
        </form>
      </div>
    </section>
  );
}

export default LoginPage;
