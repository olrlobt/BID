import React from 'react';
// import { useState,useEffect } from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
// import useStudents from "../../hooks/useStudents";

const PwdRemoveModal = ({ onClose, ...props }) => {

  // const { editStudent } = useStudents();

  // const [form, setForm] = useState({
  //   number:'',
  //   name: "",
  //   asset: "",
  // });

  // useEffect(() => {
  //   setForm({
  //     number: props[1].number,
  //     name: props[1].name,
  //     asset: props[1].asset,
  //   });
  // }, [props]);


  // const addNewStudent = (e) => {
  //   e.preventDefault();
  //   if (form.id === '') {
  //     console.log('학생 번호를 입력하세요');
  //   } else if (form.name === '') {
  //     console.log('학생 이름을 입력하세요');
  //   } else if (form.birth === 0) {
  //     console.log('학생 생년월일을 입력하세요');
  //   } else{
  //     // 데이터 저장

  //     addStudent({
  //       newStudent: form
  //     });
  //     onClose();
  //   }
  // }
  return (
    <Modal
     onClose={onClose} {...props}>
      <div style={{ fontSize: '24px', textAlign: 'center' }}>{props[0]}</div>
        <div>
          {props[1].name}님의 비밀번호를 초기화 하시겠습니까?
          </div>
          <div>
            비밀번호는 주민번호 앞자리 6자리로 설정됩니다.
          </div>
        <SubmitButton
          text = '초기화'
          width = '80%'
          height = '7vh'
        />
    </Modal>
  );
};

export default PwdRemoveModal;
