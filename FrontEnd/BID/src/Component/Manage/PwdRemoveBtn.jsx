import React from "react";
import styled from './PwdRemoveBtn.module.css';
import PropTypes from "prop-types";

function PwdRemoveBtn(props) {

  const {onClick} = props;


  return (
    <button
      className={styled.logoContainer}
      style={{
        backgroundColor: "#A6A6A6",
        justifyContent: 'center',
        cursor: 'pointer'
      }}
      onClick={onClick}
    >
      <p style={{
        color: "#FFFFFF",
        fontWeight: 'bold',
        cursor: 'pointer',
        display: 'flex', // Center the icon and text horizontally
        alignItems: 'center', // Center the icon and text vertically
        margin: 0,
      }}>
        비밀번호 초기화
      </p>
    </button>
  );
}

PwdRemoveBtn.propTypes = {
  text: PropTypes.string.isRequired,
};

export default PwdRemoveBtn;

