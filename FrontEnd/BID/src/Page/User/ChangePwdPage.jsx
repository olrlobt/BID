import React, { useState, useEffect } from "react";
import styled from "./ChangePwdPage.module.css";
import Logo from "../../Component/Common/Logo";
import { useMutation } from "@tanstack/react-query";
import { SvgIcon } from "@material-ui/core";
import PersonIcon from '@mui/icons-material/Person';
import LockIcon from '@mui/icons-material/Lock';
import PhoneIphoneIcon from '@mui/icons-material/PhoneIphone';
import { sendCodeApi, authenticateApi, changePwdApi } from "../../Apis/UserApis";
import { useNavigate } from "react-router-dom";

function ChangePwdPage() {
  const [step, setStep] = useState(1); // Default step is 1
  const [id, setId] = useState("");
  const [tel, setTel] = useState("");
  const [code, setCode] = useState("");
  const [error, setError] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordCheck, setNewPasswordCheck] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // 에러가 있을 때 5초 후에 에러 메시지를 초기화합니다.
    if (error) {
      const timer = setTimeout(() => {
        setError("");
      }, 5000); // 5초 후에 에러 메시지 초기화
      return () => clearTimeout(timer);
    }
  }, [error]);

  useEffect(() => {
    // step이 변경될 때 에러 메시지를 초기화합니다.
    setError("");
  }, [step]);

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
      navigate('/managelogin');
    },
    onError: (error) => {
      console.log(error);
      setError("비밀번호 확인 값이 일치하지 않습니다");
    }
  });

  const handleChangePwd = (e) => {
    e.preventDefault();
    if (step === 1) {
      let userData = {
        tel,
        code
      }
      authenticateQuery.mutate(userData);
    }
    if (step === 2) {
      if (newPassword !== newPasswordCheck) {
        setError("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
        return;
      }
      let newPwd = {
        id,
        newPassword,
        newPasswordCheck,
      };
      console.log(newPwd)
      changePwdQuery.mutate(newPwd);
    }
  };

  const handlePasswordChange = (e) => {
    setNewPassword(e.target.value);
    // 비밀번호가 변경될 때마다 confirmPassword와 비교하여 에러 메시지 설정
    if (e.target.value !== newPasswordCheck) {
      setError("비밀번호가 일치하지 않습니다.");
    } else {
      setError("");
    }
  };

  const handleConfirmPasswordChange = (e) => {
    setNewPasswordCheck(e.target.value);
    // confirmPassword가 변경될 때마다 password와 비교하여 에러 메시지 설정
    if (e.target.value !== newPassword) {
      setError("비밀번호가 일치하지 않습니다.");
    } else {
      setError("");
    }
  };


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
        {step === 1 && (
          <form className={styled.contentInput} onSubmit={handleChangePwd}>
            <div className={styled.telInput}>
              <div className={styled.inputWithIcon}>
                <SvgIcon
                  component={PersonIcon}
                  style={{ fill: "#4D4D4D", height: "2.5vh" }}
                  className={styled.icon}
                />
                <input
                  type="id"
                  placeholder="아이디"
                  value={id}
                  onChange={(e) => setId(e.target.value)}
                />
              </div>
              <div className={styled.inputWithIcon}>
                <SvgIcon
                  component={PhoneIphoneIcon}
                  style={{ fill: "#4D4D4D", height: "2.5vh" }}
                  className={styled.icon}
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
              </div>
              <div className={styled.inputWithIcon}>
                <SvgIcon
                  component={PhoneIphoneIcon}
                  style={{ fill: "#4D4D4D", height: "2.5vh" }}
                  className={styled.icon}
                />
                <input
                  type="code"
                  placeholder="인증코드"
                  value={code}
                  onChange={(e) => setCode(e.target.value)}
                />
              </div>
            </div>
            {error && <p className={styled.error}>{error}</p>}
            <button className={styled.changePwdBtn} type="submit">
              비밀번호 재설정
            </button>
          </form>
        )}
        {step === 2 && (
          <form className={styled.contentInput} onSubmit={handleChangePwd}>
            <div className={styled.telInput}>
              <div className={styled.inputWithIcon}>
                <SvgIcon
                  component={LockIcon}
                  style={{ fill: "#4D4D4D", height: "2.5vh" }}
                  className={styled.icon}
                />
                <input
                  type="password"
                  placeholder="새 비밀번호"
                  value={newPassword}
                  onChange={handlePasswordChange}
                  />
              </div>
              <div className={styled.inputWithIcon}>
                <SvgIcon
                  component={LockIcon}
                  style={{ fill: "#4D4D4D", height: "2.5vh" }}
                  className={styled.icon}
                />
                <input
                  type="password"
                  placeholder="새 비밀번호 확인"
                  value={newPasswordCheck}
                  onChange={handleConfirmPasswordChange}
                  />
              </div>
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
