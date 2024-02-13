import React from 'react';
import Logo from '../../Asset/Image/logo.png';
import styled from './Teacher.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faGear,
  faArrowRightFromBracket,
} from '@fortawesome/free-solid-svg-icons';
import { Link, useLocation } from 'react-router-dom';

export default function Teacher() {
  const location = useLocation();

  return (
    <section className={styled.teacherArea}>
      <img src={Logo} alt="로고" />
      <section>
        <div className={styled.eleInfo}>해오름초등학교</div>
        <div className={styled.nameInfo}>
          김비드<span>선생님</span>
        </div>
        <section className={styled.teacherBtn}>
          {/* 현재 url에 따라 어떤 정보 보여줄지 표현 */}
          {location.pathname === '/classlist/:teacherId/' ||
          location.pathname === '/classlist/:teacherId/no-class/' ? (
            <Link to="/classlist/:teacherId/modify" className={styled.classBtn}>
              <FontAwesomeIcon className={styled.icon} icon={faGear} />
              계정/학급관리
            </Link>
          ) : (
            <Link
              to="/classlist/:teacherId/modify"
              className={`${styled.classBtn} ${
                location.pathname !== '/classlist/:teacherId/'
                  ? styled.disabled
                  : ''
              }`}
            >
              비밀번호 변경
            </Link>
          )}
          {location.pathname === '/classlist/:teacherId/' ? (
            <button className={styled.logoutBtn}>
              <FontAwesomeIcon
                className={styled.icon}
                icon={faArrowRightFromBracket}
              />
              로그아웃
            </button>
          ) : (
            <Link to="/delete/:id/" className={styled.logoutBtn}>
              회원탈퇴
            </Link>
          )}
        </section>
      </section>
    </section>
  );
}
