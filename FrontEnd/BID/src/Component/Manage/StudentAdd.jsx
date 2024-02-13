import React, { useState } from "react";
import useStudents from "../../hooks/useStudents";
import Modal from '../Common/Modal';
import { addStudentApi } from "../../Apis/UserApis";
import SubmitButton from '../Common/SubmitButton';
import styled from "./StudentAdd.module.css";
import { useMutation } from "@tanstack/react-query";

const StudentAdd = ({ onClose, ...props }) => {
  const { addStudent } = useStudents();

  const [number, setNumber] = useState('');
  const [name, setName] = useState('');
  const [birth, setBirth] = useState(''); // birth로 이름 변경

  /** 학생 추가 쿼리 */
  const addStudentQuery = useMutation({
    mutationKey: ['addStudent'],
    mutationFn: (userCredentials) => addStudentApi(userCredentials),
    onSuccess: (res) => {
      console.log(res.data);
      addStudent({
        newStudent: res.data // 새로운 학생 데이터 추가
      });
      onClose();
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 학생 추가 버튼 이벤트 핸들러 */
  const addNewStudent = (e) => {
    e.preventDefault();
    const userCredentials = {
      schoolNo: 1,
      number,
      name,
      birth,
      gradeNo: 1
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
            <div className={styled.contenttitle} >              이름
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
          </form>
            <button type="submit"
            className={styled.button}>
              등록
            </button>
        </div>
    </Modal>
  );
};

export default StudentAdd;
