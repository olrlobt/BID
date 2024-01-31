import React, { useState, useEffect } from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import useStudents from "../../hooks/useStudents";

const PwdRemoveModal = ({ onClose, ...props }) => {

  const { editStudent } = useStudents();

  const [form, setForm] = useState({
    number: '',
    name: "",
    asset: "",
  });

  // Assuming you have student details as props
  useEffect(() => {
    setForm({
      number: props[1].number,
      name: props[1].name,
      asset: props[1].asset,
    });
  }, [props]);


  const editExistingStudent = (e) => {
    e.preventDefault();

    // Assuming you have an editStudent function in useStudents hook
    editStudent({
      editedStudent: form,
    });

    onClose();
  }

  return (
    <Modal onClose={onClose} {...props}>
      <div style={{ fontSize: '24px', textAlign: 'center' }}>{props[0]}</div>
      <div>
        {props[1].name}님의 비밀번호를 초기화 하시겠습니까?
      </div>
      <div>
      번호 : {props[1].number}
      이름 : {props[1].name}
      생년월일 : {props[1].birth}
      </div>
      <form onSubmit={editExistingStudent}>
        {/* Your form input fields here */}
        <SubmitButton
          type="submit"
          text="초기화"
          width="80%"
          height="7vh"
        />
      </form>
    </Modal>
  );
};

export default PwdRemoveModal;
