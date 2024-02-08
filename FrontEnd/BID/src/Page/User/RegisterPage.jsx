import React, { useState } from "react";
import styled from "./RegisterPage.module.css";
import { Link } from "react-router-dom";
import useModal from "../../hooks/useModal";
import Logo from "../../Component/Common/Logo";
import { addUserApi, duplicateIdApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";

function RegisterPage() {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setconfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [tel, setTel] = useState("");
  const [verifyNo, setVerifyNo] = useState("");
  const [schoolNo, setSchoolNo] = useState("");
  const { openModal } = useModal();

  /** 아이디 중복 검사 */
  const duplicateIdQuery = useMutation({
    mutationKey: ["sendCode"],
    mutationFn: (userData) => duplicateIdApi(userData),
    onSuccess: (data) => {
      console.log(data);
      // Move to the next step upon successful submission
      // setStep(2);
    },
    onError: (error) => {
      console.log(error);
    },
  });

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

  const handleDuplicateId = (e) => {
    // 코드 발송 동작 구현
    e.preventDefault();
    let userData = {
      id,
    };
    duplicateIdQuery.mutate(userData);
  };
  

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원가입" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput}>
        <div className={styled.registerInput}>
          <input
            type="text"
            placeholder="아이디"
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
          <button
              className={styled.duplicateIdBtn}
              onClick={handleDuplicateId}
          >
          중복 검사
          </button>
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
          <button
              className={styled.sendCodeBtn}
              // onClick={handleSendCodeBtn}
          >
          코드 발송
          </button>
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
          <button
            className={styled.searchSchool}
            onClick={(e) => {
              e.preventDefault(); // Prevent default form submission behavior
              openModal({
                type: "searchSchool",
                props: ["학교 검색"]
              });
            }}
          >
          학교 검색
          </button>
          <Link
            to="/login"
            className={styled.joinLink}
            onClick={handleRegisterEvent}
          >
            <p>JOIN</p>
          </Link>
          </div>
        </form>
      </div>
    </section>
  );
}

export default RegisterPage;
