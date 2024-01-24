import React from "react";
import styled from "./DeletePage.module.css";
import { Link } from "react-router-dom";
import Logo from "../../Component/Common/Logo";


function RegisterPage() {
  return (
    <section className={styled.back}>
      <div className={styled.logo}>
      <Logo text="회원 탈퇴" />
      </div>
      <div className={styled.content}>
        <div className={styled.contentInput}>
          정말 탈퇴할 거니..?
          <input type="password" placeholder="비밀번호" />
          <Link to="/login" className={styled.IdLink}>
                <p>회원 탈퇴</p>
              </Link>
        </div>
      </div>
    </section>
  );
}

export default RegisterPage;
