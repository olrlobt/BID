import React from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import styled from './PwdRemoveModal.module.css'
import { resetPwdApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";

const PwdRemoveModal = ({ onClose, item, ...props }) => {

  const userNo = props[1].no
  /** 학생 비밀번호 초기화 쿼리 */
  const resetPwdQuery = useMutation({
    mutationKey: ['resetPwd'],
    mutationFn: () => resetPwdApi(userNo),
    onSuccess: (res) => {
      console.log(res);
      onClose(); // Close the modal on success
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const onResetPwd = (e) => {
    e.preventDefault();
    console.log('눌러짐')
    resetPwdQuery.mutate(userNo); // Trigger the mutation when the button is clicked
  
  };

  
  return (
    <Modal onClose={onClose} {...props}>
      <div className={styled.title}>
        {props[0]}
      </div>
      <div className={styled.content}>
        <strong>{props[1].name}</strong>님의 비밀번호를 초기화 하시겠습니까?
      </div>
      <div className={styled.secondContent}>
        비밀번호는 주민번호 앞자리 6자리로 설정됩니다.
      <div className={styled.button} onClick={onResetPwd} >
        <SubmitButton
          text="초기화"
          width="100%"
          height="7vh"
          fontSize= "1rem"
        />
        </div>
      </div>
    </Modal>
  );
};

export default PwdRemoveModal;
