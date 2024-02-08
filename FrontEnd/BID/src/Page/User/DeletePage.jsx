import React from 'react';
import styled from './DeletePage.module.css';
import { Link } from 'react-router-dom';
import Logo from '../../Component/Common/Logo';

export default function DeletePage() {
  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원 탈퇴" />
      </div>
      <div className={styled.contentInput}>
        <span>
          정말 탈퇴하실 건가요? <br /> 회원 탈퇴를 하시면 데이터를 복구할 수
          없어요
        </span>
        <input
          className={styled.enterPW}
          type="password"
          placeholder="비밀번호"
        />

        <Link to="/login" className={styled.IdLink}>
          <p>회원 탈퇴</p>
        </Link>
      </div>
    </section>
  );
}
