import React, { useState } from "react";
// import styled from "./StudentAdd.module.css";
import useStudents from "../../hooks/useStudents";
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';


const StudentAdd = ({ onClose, ...props }) => {

  const { addStudent } = useStudents(); // Assuming useStudents returns the list of students

  const [form, setForm] = useState({
    id: '',
    name: "",
    birth: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const addNewStudent = (e) => {
    e.preventDefault();
    if (form.id === '') {
      console.log('학생 번호를 입력하세요');
    } else if (form.name === '') {
      console.log('학생 이름을 입력하세요');
    } else if (form.birth === 0) {
      console.log('학생 생년월일을 입력하세요');
    } else{
      // 데이터 저장

      addStudent({
        newStudent: form
      });
      onClose();
    }
  }


  
  return (
    <Modal
     onClose={onClose} {...props}>
      <div style={{ fontSize: '24px', textAlign: 'center' }}>{props[0]}</div>
      <form onSubmit={addNewStudent} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '20px' }}>
        <label htmlFor="id">
          번호
          <input
            type="text"
            name="id"
            placeholder="번호를 입력하세요"
            onChange={handleChange}
            style={{ width: '100%', padding: '10px' }}
          />
        </label>
        <label htmlFor="name">
          이름
          <input
            type="text"
            name="name"
            placeholder="이름을 입력하세요"
            onChange={handleChange}
            style={{ width: '100%', padding: '10px' }}
          />
        </label>
        <label htmlFor="birth">
          생년월일
          <input
            type="text"
            name="birth"
            placeholder="생년월일을 입력하세요"
            onChange={handleChange}
                style={{ width: '100%', padding: '10px' }}

          />
        </label>
        <SubmitButton
          text = '저장'
          width = '100%'
          height = '7vh'
        />
      </form>
    </Modal>
  );
};

export default StudentAdd;
