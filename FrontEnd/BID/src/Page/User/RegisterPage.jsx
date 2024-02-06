import React, { useState } from "react";
import styled from "./RegisterPage.module.css";
import { Link } from "react-router-dom";
import Logo from "../../Component/Common/Logo";
import { addUserApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";

function RegisterPage() {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setconfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [tel, setTel] = useState("");
  const [verifyNo, setVerifyNo] = useState("");
  const [schoolNo, setSchoolNo] = useState("");


  /** 새 회원 추가 쿼리 */
  const addUserQuery = useMutation({
    mutationKey: ["addNewUser"],
    mutationFn: (newUserForm) => addUserApi(newUserForm),
    onSuccess: (data) => {
      // Dispatch an action to update the Redux store with the registered user data
     console.log(data)
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleRegisterEvent = (e) => {
    e.preventDefault();
    let userData = {
      id,
      password,
      confirmPassword,
      name,
      tel,
      schoolNo,
    };
    // console.log(userData)
    addUserQuery.mutate(userData);
  };


  

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원가입" />
      </div>
      <div className={styled.content}>
        <div className={styled.contentInput}>
          <input
            type="text"
            placeholder="아이디"
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <input
            type="password"
            placeholder="비밀번호 확인"
            value={confirmPassword}
            onChange={(e) => setconfirmPassword(e.target.value)}
          />
          <input
            type="text"
            placeholder="이름"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            placeholder="휴대전화번호"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
          />
          <input
            type="text"
            placeholder="인증코드"
            value={verifyNo}
            onChange={(e) => setVerifyNo(e.target.value)}
          />
          <input
            type="text"
            placeholder="학교코드"
            value={schoolNo}
            onChange={(e) => setSchoolNo(e.target.value)}
          />
          <Link
            to="/login"
            className={styled.joinLink}
            onClick={handleRegisterEvent}
          >
            <p>JOIN</p>
          </Link>
        </div>
      </div>
    </section>
  );
}

export default RegisterPage;
