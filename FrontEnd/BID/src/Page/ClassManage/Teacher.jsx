import React from 'react';
import Logo from '../../Asset/Image/logo.png';
import styled from './Teacher.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faGear,
  faArrowRightFromBracket,
} from '@fortawesome/free-solid-svg-icons';
import { Link, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { userSelector } from '../../Store/userSlice';
import { logoutUserApi } from '../../Apis/UserApis';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import useUser from '../../hooks/useUser';
import { removeCookie } from '../../cookie';
import useMain from '../../hooks/useMain';

export default function Teacher() {
  const location = useLocation();
  const teacherInfo = useSelector(userSelector);
  const { userNo, schoolName, adminName } = teacherInfo.adminInfo;
  const navigate = useNavigate();
  const { changeMainClass } = useMain();
  const { logoutUser } = useUser();

  /** 로그아웃 쿼리 */
  const logoutUserQuery = useMutation({
    mutationKey: ['logoutUser'],
    mutationFn: () => logoutUserApi(),
    onSuccess: (res) => {
      console.log(res);
      // 여기서 토큰 폐기 작업 수행
      // 예를 들어, 로컬 스토리지에서 토큰을 삭제하는 방법:
      changeMainClass([]);
      logoutUser();
      removeCookie('accessToken');
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 로그아웃 버튼 handle */
  const handleLogoutUser = (e) => {
    e.preventDefault();
    logoutUserQuery.mutate();
    navigate('/managelogin');
  };

  return (
    <section className={styled.teacherArea}>
      <img
        src={Logo}
        alt="로고"
        onError={(e) =>
          (e.target.src =
            'https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg')
        }
      />
      <section>
        <div className={styled.eleInfo}>{schoolName}</div>
        <div className={styled.nameInfo}>
          {adminName}
          <span>선생님</span>
        </div>
        <section className={styled.teacherBtn}>
          {/* 현재 url에 따라 어떤 정보 보여줄지 표현 */}
          {location.pathname === `/classlist/${userNo}` ||
          location.pathname === `/classlist/${userNo}/no-class` ? (
            <Link
              to={`/classlist/${userNo}/modify`}
              className={styled.classBtn}
            >
              <FontAwesomeIcon className={styled.icon} icon={faGear} />
              계정/학급관리
            </Link>
          ) : (
            <Link
              to={`/classlist/${userNo}/changepass`}
              className={`${styled.classBtn} ${
                location.pathname !== `/classlist/${userNo}`
                  ? styled.disabled
                  : ''
              }`}
            >
              비밀번호 변경
            </Link>
          )}
          {location.pathname === `/classlist/${userNo}` ||
          location.pathname === `/classlist/${userNo}/no-class` ? (
            <button className={styled.logoutBtn} onClick={handleLogoutUser}>
              <FontAwesomeIcon
                className={styled.icon}
                icon={faArrowRightFromBracket}
              />
              로그아웃
            </button>
          ) : (
            <Link to={`/delete/${userNo}`} className={styled.logoutBtn}>
              회원탈퇴
            </Link>
          )}
        </section>
      </section>
    </section>
  );
}
