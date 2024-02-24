import React from 'react';
import styled from './PwdRemoveBtn.module.css';
import PropTypes from 'prop-types';

function PwdRemoveBtn(props) {
  const { onClick } = props;

  return (
    <button className={styled.logoContainer} onClick={onClick}>
      <p>비밀번호</p>
      <p>초기화</p>
    </button>
  );
}

PwdRemoveBtn.propTypes = {
  text: PropTypes.string.isRequired,
};

export default PwdRemoveBtn;
