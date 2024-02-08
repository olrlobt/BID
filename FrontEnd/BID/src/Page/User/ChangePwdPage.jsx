import React, { useState } from "react";
import styled from "./ChangePwdPage.module.css";
import Logo from "../../Component/Common/Logo";
import { useMutation } from "@tanstack/react-query";
import { sendCodeApi, authenticateApi, changePwdApi } from "../../Apis/UserApis";

function ChangePwdPage() {
  const [step, setStep] = useState(1); // Default step is 1
  const [id, setId] = useState("");
  const [tel, setTel] = useState("");
  const [verifyNo, setVerifyNo] = useState("");
  const [error, setError] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordCheck, setNewPasswordCheck] = useState("");


  const sendCodeQuery = useMutation({
    mutationKey: ["sendCode"],
    mutationFn: (userData) => sendCodeApi(userData),
    onSuccess: (data) => {
      console.log(data);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const authenticateQuery = useMutation({
    mutationKey: ["authenticateCode"],
    mutationFn: (userData) => authenticateApi(userData),
    onSuccess: (data) => {
      console.log(data);
      setStep(2);
    },
    onError: (error) => {
      console.log(error);
      setError("인증 코드가 일치하지 않습니다");
    },
  });

  const changePwdQuery = useMutation({
    mutationKey: ["changePwd"],
    mutationFn: (newPwd) => changePwdApi(newPwd), // corrected typo here
    onSuccess: (data) => {
      console.log(data);

    },
    onError: (error) => {
      console.log(error);
      setError("비밀번호 확인 값이 일치하지 않습니다");
    }
  })


  const handleChangePwd = (e) => {
    e.preventDefault();
    if (step === 1) 
    {
      let userData = {
        verifyNo
      }
      authenticateQuery.mutate(userData);
    }
    if (step === 2) 
    {
      let newPwd = {
        id,
        tel,
        verifyNo,
      };
      changePwdQuery.mutate(newPwd);
  };
    }
    
  const handleSendCode = (e) => {
    // 코드 발송 동작 구현
    e.preventDefault();
    let userData = {
      id,
      tel,
    };
    sendCodeQuery.mutate(userData);
  };


  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="비밀번호 찾기" />
      </div>
      <div className={styled.content}>
        {step === 1 && ( // Render step 1 UI
          <form className={styled.contentInput}
           onSubmit={handleChangePwd}>
            <div className={styled.telInput}>
              <input
                type="id"
                placeholder="아이디"
                value={id}
                onChange={(e) => setId(e.target.value)}
              />
              <input
                type="tel"
                placeholder="휴대전화번호"
                value={tel}
                onChange={(e) => setTel(e.target.value)}
              />
              <button
                className={styled.sendCodeBtn}
                onClick={handleSendCode}
              >
                코드 발송
              </button>
              <input
                type="verifyNo"
                placeholder="인증코드"
                value={verifyNo}
                onChange={(e) => setVerifyNo(e.target.value)}
              />
            </div>
            {error && <p className={styled.error}>{error}</p>}
            <button className={styled.changePwdBtn} type="submit">
              비밀번호 재설정
            </button>
          </form>
        )}
        {step === 2 && ( // Render step 2 UI
          <form className={styled.contentInput}
          onSubmit={handleChangePwd}>
           <div className={styled.telInput}>
             <input
               type="newpassword"
               placeholder="새 비밀번호"
               value={newPassword}
               onChange={(e) => setNewPassword(e.target.value)}
             />
             <input
               type="confirmNewPassword"
               placeholder="새 비밀번호 확인"
               value={newPasswordCheck}
               onChange={(e) => setNewPasswordCheck(e.target.value)}
             />

           </div>
           {error && <p className={styled.error}>{error}</p>}
           <button className={styled.changePwdBtn} type="submit">
             비밀번호 재설정
           </button>
         </form>
        )}
      </div>
    </section>
  );
}

export default ChangePwdPage;
