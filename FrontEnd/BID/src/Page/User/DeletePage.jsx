import React, { useState } from 'react';
import styled from './DeletePage.module.css';
import Logo from '../../Component/Common/Logo';
import { deleteUserApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";
import { useNavigate } from "react-router-dom";
import useUser from "../../hooks/useUser";
import { removeCookie } from "../../cookie";

export default function DeletePage() {
  const { logoutUser } = useUser();
  const teacherInfo = useSelector(userSelector);
  const [password, setPassword] = useState('');
  const gradeNo = 1;
  const userNo = teacherInfo.adminInfo.userNo;
  const navigate = useNavigate();

  /** 회원 탈퇴 쿼리 */
  const deleteUserQuery = useMutation({
    mutationKey: ['deleteUser'],
    mutationFn: (userData) => deleteUserApi(gradeNo, userNo, userData), 
    onSuccess: (res) => {
      console.log(res);
      logoutUser();
      removeCookie('accessToken');
    },
    onError: (error) => {
      console.log(error);
    },
    
  });

  /** 회원 탈퇴 버튼 핸들러 */
  const handleDeleteUser = (e) => {
    e.preventDefault();
    const userData = {
      password // 사용자 비밀번호만 필요
    };
    deleteUserQuery.mutate(userData);
    navigate('/managelogin');

  };

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <Logo text="회원 탈퇴" />
      </div>
      <div className={styled.contentInput}>
        <span>
          정말 탈퇴하실 건가요? <br /> 회원 탈퇴를 하시면 데이터를 복구할 수
          없어요
        </span>
        <input
          className={styled.enterPW}
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button onClick={handleDeleteUser} className={styled.IdLink}>
          <p>회원 탈퇴</p>
        </button>
      </div>
    </section>
  );
}
