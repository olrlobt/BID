import React, { useState } from "react";
import styled from "./RegisterPage.module.css";
import { Link } from "react-router-dom";
import Logo from "../../Component/Common/Logo";
import { useDispatch, useSelector } from "react-redux";
import { registerUser } from "../../Store/UserSlice";

function RegisterPage() {

  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [verifyPwd, setverifyPwd] = useState('')
  const [name, setName] = useState('')
  const [phone, setPhone] = useState('')
  const [verifyCode, setVerifyCode] = useState('')
  const [schoolCode, setSchoolCode] = useState('')

  const { loading, error } = useSelector((state) => state.user);
  const dispatch = useDispatch();
  

  const handleRegisterEvent = (e) => {
    e.preventDefault();
    let userData = {
        id,
        password,
        name,
        phone,
        verifyCode,
        schoolCode,
    };
    dispatch(registerUser(userData)).then((result) => {
        // 회원가입 성공 후 처리
    });
}

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원가입" />
      </div>
      <div className={styled.content}>
        <div className={styled.contentInput}>
          <input type="text" placeholder="아이디" value={id} onChange={e => setId(e.target.value)}/>
          <input type="password" placeholder="비밀번호" value={password} onChange={e => setPassword(e.target.value)}/>
          <input type="password" placeholder="비밀번호 확인" value={verifyPwd} onChange={e => setverifyPwd(e.target.value)}/>
          <input type="text" placeholder="이름" value={name} onChange={e => setName(e.target.value)}/>
          <input type="text" placeholder="휴대전화번호" value={phone} onChange={e => setPhone(e.target.value)}/>
          <input type="text" placeholder="인증코드" value={verifyCode} onChange={e => setVerifyCode(e.target.value)}/>
          <input type="text" placeholder="학교코드" value={schoolCode} onChange={e => setSchoolCode(e.target.value)}/>
            <Link to="/login" className={styled.joinLink} onClick={handleRegisterEvent}>
              <p>JOIN</p>
            </Link>
        </div>
      </div>
    </section>
  );
}

export default RegisterPage;
