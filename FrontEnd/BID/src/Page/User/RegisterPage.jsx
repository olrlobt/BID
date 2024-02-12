import React, { useState, useEffect } from "react";
import styled from "./RegisterPage.module.css";
import { Link } from "react-router-dom";
import useModal from "../../hooks/useModal";
import { useSelector } from "react-redux";
import Logo from "../../Component/Common/Logo";
import { addUserApi, duplicateIdApi, registerCodeApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";
import { SvgIcon } from "@material-ui/core";
import PersonIcon from '@mui/icons-material/Person';
import LockIcon from '@mui/icons-material/Lock';
import PhoneIphoneIcon from '@mui/icons-material/PhoneIphone';
import SchoolIcon from '@mui/icons-material/School';

function RegisterPage() {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setconfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [tel, setTel] = useState("");
  const [verifyNo, setVerifyNo] = useState("");
  const [schoolNo, setSchoolNo] = useState("");
  const { openModal } = useModal();
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  /** 아이디 중복 검사 */
  const duplicateIdQuery = useMutation({
    mutationKey: ["duplicateId"],
    mutationFn: (userData) => duplicateIdApi(userData),
    onSuccess: (res) => {
      console.log(res);
      if (res.data === true) {
        setErrorMessage("이미 존재하는 아이디입니다.");
      } else {
        setSuccessMessage("사용 가능한 아이디입니다.");
      }
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 전화번호 코드 전송 */
  const registerCodeQuery = useMutation({
    mutationKey: ["sendCode"],
    mutationFn: (userTel) => registerCodeApi(userTel),
    onSuccess: (res) => {
      console.log(res);
      if (res.data === true) {
      } else {
      }
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
    e.preventDefault();
    if (!id) {
      setErrorMessage("아이디를 입력하세요.");
    } else {
      setErrorMessage(""); // 새로운 검색을 시작하기 전에 기존 메시지 초기화
      let userData = id;
      duplicateIdQuery.mutate(userData);
    }
  };

  const handleSendCodeBtn = (e) => {
    e.preventDefault()
    if (!tel) {
      setErrorMessage("휴대전화 번호를 입력하세요.")
    }
    else {
      setErrorMessage("")
      let userTel = {
        tel
      }
      registerCodeQuery.mutate(userTel);
    }
  }

  const handlerModalClose = (schoolNo) => {
    console.log(schoolNo)
    if (schoolNo) {
      setSchoolNo(schoolNo)
    }
  }



  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원가입" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput}>
        {errorMessage && <p className={styled.errorMessage}>{errorMessage}</p>}
        {successMessage && <p className={styled.successMessage}>{successMessage}</p>}
        <div className={styled.registerInput}>
          <div className={styled.inputWithIcon}>
            <SvgIcon 
            component={PersonIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            />
            <input
              type="text"
              placeholder="아이디"
              value={id}
              onChange={(e) => setId(e.target.value)}
            />
            </div>
          <button
              className={styled.duplicateIdBtn}
              onClick={handleDuplicateId}
          >
          중복 검사
          </button>
          <div className={styled.inputWithIcon}>
          <SvgIcon 
            component={LockIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            />
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
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
            placeholder="비밀번호 확인"
            value={confirmPassword}
            onChange={(e) => setconfirmPassword(e.target.value)}
          />
          </div>
          <div className={styled.inputWithIcon}>
          <SvgIcon 
            component={PersonIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            />
          <input
            type="text"
            placeholder="이름"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          </div>
          <div className={styled.inputWithIcon}>
          <SvgIcon 
            component={PhoneIphoneIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            />
          <input
            type="text"
            placeholder="휴대전화번호"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
          />
          <button
              className={styled.sendCodeBtn}
              onClick={handleSendCodeBtn}
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
            type="text"
            placeholder="인증코드"
            value={verifyNo}
            onChange={(e) => setVerifyNo(e.target.value)}
          />
          </div>
          <div className={styled.inputWithIcon}>
          <SvgIcon 
            component={SchoolIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            />
          <input
            type="text"
            placeholder="학교코드"
            value={schoolNo} // Display selected school code
          />
          <button
            className={styled.searchSchool}
            onClick={(e) => {
              e.preventDefault(); // Prevent default form submission behavior
              openModal({
                type: "searchSchool",
                props: ["학교 검색", handlerModalClose]
              });
            }}
          >
            학교 검색
          </button>
          </div>
          <Link
            to="/login"
            className={styled.joinLink}
            onClick={handleRegisterEvent}
          >
            JOIN
          </Link>
          </div>
        </form>
      </div>
    </section>
  );
}

export default RegisterPage;
