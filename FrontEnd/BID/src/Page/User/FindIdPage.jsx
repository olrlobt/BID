import React, { useState } from "react";
import styled from "./LoginPage.module.css";
import Logo from "../../Component/Common/Logo";
import { useMutation } from "@tanstack/react-query";
import { findIdApi } from "../../Apis/UserApis";

function FindIdPage() {
  const [name, setName] = useState("");
  const [tel, setTel] = useState("");

  const findIdQuery = useMutation({
    mutationKey: ["findId"],
    mutationFn: (userData) => findIdApi(userData),
    onSuccess: (data) => {
      // Dispatch an action to update the Redux store with the registered user data
      console.log(data);
    },
    onError: (error) => {
      console.log(error);
    },
  });


  const handleFindId = (e) => {
    e.preventDefault();
    let userData = {
      name,
      tel,
    };
    console.log(userData)
    findIdQuery.mutate(userData);
  };


  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="아이디 찾기" />
      </div>
      <div className={styled.content}>
        <div className={styled.contentInput}>
          <input
            type="name"
            placeholder="이름"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="password"
            placeholder="휴대전화번호"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
          />
          <button
            className={styled.findIdBtn}
            onClick={handleFindId} 
          >
            아이디 찾기
          </button>
        </div>
      </div>
    </section>
  );
}

export default FindIdPage;
