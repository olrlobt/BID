import React, { useState, useEffect } from 'react';
import Modal from '../Common/Modal';
import { editStudentApi } from "../../Apis/UserApis";
import styled from "./StudentAdd.module.css";
import { useMutation } from "@tanstack/react-query";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";
import { mainSelector } from "../../Store/mainSlice";

const StudentEditModal = ({ onClose, ...props }) => {
  const teacherInfo = useSelector(userSelector);
  const schoolNo = teacherInfo.adminInfo.schoolNo;

  const [number, setNumber] = useState('');
  const [name, setName] = useState('');
  const [birth, setBirth] = useState('');

  console.log(props)
  useEffect(() => {
    // props로 받은 데이터를 설정
    if (props[1] && props[1].birthDate) {
      const originalDate = props[1].birthDate;
      let formattedDate = '';
  
      // 앞 두 글자가 '0', '1', '2'로 시작하면 '20'을 붙이고 '-' 추가
      if (['0', '1', '2'].includes(originalDate[0])) {
        formattedDate += '20';
      } else {
        formattedDate += '19';
      }
  
      // 날짜 형식으로 변환 (yy-mm-dd)
      formattedDate += `${originalDate[0]}${originalDate[1]}-${originalDate[2]}${originalDate[3]}-${originalDate[4]}${originalDate[5]}`;
      
      setName(props[1].name || '');
      setNumber(props[1].number || '');
      setBirth(formattedDate);
    }
  }, [props]);

  const mainClass = useSelector(mainSelector);
  const gradeNo = mainClass.no;

  /** 학생 수정 쿼리 */
  const editStudentQuery = useMutation({
    mutationKey: ['editStudent'],
    mutationFn: (userCredentials) => editStudentApi(props[1].no,userCredentials),
    onSuccess: (res) => {
      console.log(res.data);
      onClose();
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear().toString().slice(-2); // 마지막 2자리만 가져옴
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // 0을 붙여 2자리로 만듦
    const day = ('0' + date.getDate()).slice(-2); // 0을 붙여 2자리로 만듦
    return year  + month +  day;
  }

  /** 학생 수정 버튼 이벤트 핸들러 */
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!number || !name || !birth) {
      console.error("모든 필드를 입력해주세요.");
      return;
    }

    const userCredentials = {
      schoolNo,
      number : e.target.studentNo.value,
      name : e.target.studentName.value,
      birthDate: formatDate(e.target.studentBirth.value), // 날짜 포맷 변환
      gradeNo
    };
    console.log(userCredentials);
    editStudentQuery.mutate(userCredentials);
  }

  return (
    <Modal onClose={onClose} {...props}>
      <div className={styled.logo}>
        <div className={styled.title}>{props[0]}</div>
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} onSubmit={handleSubmit}>
          <div className={styled.contenttitle} >
            번호
            <input
              name='studentNo'
              type="text"
              defaultValue={number}
              onChange={(e) => setNumber(e.target.value)}
            />
          </div>
          <div className={styled.contenttitle} >
            이름
            <input
              name='studentName'
              type="text"
              defaultValue={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className={styled.contenttitle} >
            생년월일
            <input
              name='studentBirth'
              type="date"
              defaultValue={birth}
              onChange={(e) => setBirth(e.target.value)}
            />
          </div>
          <button type="submit" className={styled.button}>
            등록
          </button>
        </form>
      </div>
    </Modal>
  );
};

export default StudentEditModal;
