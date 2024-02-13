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

export default function Teacher() {
  const location = useLocation();
  const teacherInfo = useSelector(userSelector);
  const { userNo, schoolName, adminName } = teacherInfo.data.adminInfo;

  return (
    <section className={styled.teacherArea}>
      <img src={Logo} alt="로고" />
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
              to={`/classlist/${userNo}/modify`}
              className={`${styled.classBtn} ${
                location.pathname !== `/classlist/${userNo}`
                  ? styled.disabled
                  : ''
              }`}
            >
              비밀번호 변경
            </Link>
          )}
          {location.pathname === `/classlist/${userNo}` ? (
            <button className={styled.logoutBtn}>
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
