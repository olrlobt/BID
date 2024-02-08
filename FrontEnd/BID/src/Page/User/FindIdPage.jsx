import React, { useState } from "react";
import styled from "./FindIdPage.module.css";
import Logo from "../../Component/Common/Logo";
import { useMutation } from "@tanstack/react-query";
import { findIdApi } from "../../Apis/UserApis";
import { Link } from "react-router-dom"; // Import Link from React Router

function FindIdPage() {
  const [name, setName] = useState("");
  const [tel, setTel] = useState("");
  const [error, setError] = useState("");
  const [id, setId] = useState("");

  const findIdQuery = useMutation({
    mutationKey: ["findId"],
    mutationFn: (userData) => findIdApi(userData),
    onSuccess: (data) => {
      console.log(data);
      setId(data.data);
      setError(""); // Clear error upon success
      // Clear input fields upon successful ID retrieval
      setName("");
      setTel("");
    },
    onError: (error) => {
      console.log(error);
      setError("이름과 전화번호가 일치하지 않습니다");
    },
  });

  const handleFindId = (e) => {
    e.preventDefault();
    let userData = {
      name,
      tel,
    };
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
            type="tel"
            placeholder="휴대전화번호"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
          />
          {error && <p className={styled.error}>{error}</p>}
          {id ? (
            <Link to="/managelogin">
              <button className={styled.findIdBtn}>로그인 하기</button>
            </Link>
          ) : (
            <button className={styled.findIdBtn} onClick={handleFindId}>
              아이디 찾기
            </button>
          )}
          {id && (
            <p className={styled.success}>
              고객님의 아이디는 <span className={styled.id}>{id}</span> 입니다
            </p>
          )}
        </div>
      </div>
    </section>
  );
}

export default FindIdPage;
