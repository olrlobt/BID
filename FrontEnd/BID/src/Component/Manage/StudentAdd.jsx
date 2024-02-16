import React, { useState } from "react";
import Modal from '../Common/Modal';
import { addStudentApi } from "../../Apis/UserApis";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";
import styled from "./StudentAdd.module.css";
import { useMutation } from "@tanstack/react-query";
import { mainSelector } from "../../Store/mainSlice";

const StudentAdd = ({ onClose, ...props }) => {
  const teacherInfo = useSelector(userSelector);
  const schoolNo = teacherInfo.adminInfo.schoolNo
  const [number, setNumber] = useState('');
  const [name, setName] = useState('');
  const [birth, setBirth] = useState(''); // birth로 이름 변경
  const mainClass = useSelector(mainSelector);
  console.log(props)
  const gradeNo = mainClass.no;
  console.log(gradeNo)
  /** 학생 추가 쿼리 */
  const addStudentQuery = useMutation({
    mutationKey: ['addStudent'],
    mutationFn: (userCredentials) => addStudentApi(userCredentials),
    onSuccess: (res) => {
      console.log(res.data);
      onClose();
    },
    onError: (error) => {
      console.log(error);
      console.log("이미 있는 번호 입니다!")
    },
  });


  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear().toString().slice(-2); // 마지막 2자리만 가져옴
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // 0을 붙여 2자리로 만듦
    const day = ('0' + date.getDate()).slice(-2); // 0을 붙여 2자리로 만듦
    return year + month + day;
  }

  /** 학생 추가 버튼 이벤트 핸들러 */
  const addNewStudent = (e) => {
    e.preventDefault();
    
    // 필수 입력 필드 확인
    if (!number || !name || !birth) {
      console.error("모든 필드를 입력해주세요.");
      return;
    }
  
    const userCredentials = {
      schoolNo,
      number,
      name,
      password: formatDate(birth),
      gradeNo    
    };
    console.log(userCredentials);
    addStudentQuery.mutate(userCredentials);
  }

  return (
    <Modal onClose={onClose} {...props}>
        <div className={styled.logo}>
          <div className={styled.title}>{props[0]}</div>
        </div>
        <div className={styled.content}>
          <form className={styled.contentInput} onSubmit={addNewStudent}>
            <div className={styled.contenttitle} >
              번호
            <input
              type="text"
              value={number}
              onChange={(e) => setNumber(e.target.value)}
            />
            </div>
            <div className={styled.contenttitle} >              
            이름
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            </div>
            <div className={styled.contenttitle} >              생년월일
            <input
              type="date"
              value={birth}
              onChange={(e) => setBirth(e.target.value)}
            />
            </div>
            <button type="submit"
            className={styled.button}>
              등록
            </button>
          </form>
        </div>
    </Modal>
  );
};

export default StudentAdd;
