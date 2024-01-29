import React from "react";
import styled from "./LoginPage.module.css";
import { Link } from "react-router-dom";
import Logo from "../../Component/Common/Logo";


function RegisterPage() {
  return (
    <section className={styled.back}>
      <div className={styled.logo}>
      <Logo text="아이디 찾기" />
      </div>
      <div className={styled.content}>
        <div className={styled.contentInput}>
          <input type="password" placeholder="이름" />
          <input type="password" placeholder="휴대전화번호" />
          <Link to="/login" className={styled.joinLink}>
                <p>아이디 찾기</p>
              </Link>
        </div>
      </div>
    </section>
  );
}

export default RegisterPage;
