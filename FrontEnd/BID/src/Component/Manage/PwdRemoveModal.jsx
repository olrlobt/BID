import React from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import styled from './PwdRemoveModal.module.css'

const PwdRemoveModal = ({ onClose, ...props }) => {

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
      </div>
      <SubmitButton
        text="초기화"
        width="100%"
        height="7vh"
      />
    </Modal>
  );
};

export default PwdRemoveModal;