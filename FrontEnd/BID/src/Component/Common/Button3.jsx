// Button3.jsx
import React, { useState, useEffect } from "react";
import styled from './Button3.module.css';
import PropTypes from "prop-types";

function Button3({ text, onClick, active }) {
  const [isClicked, setIsClicked] = useState(active);

  useEffect(() => {
    setIsClicked(active);
  }, [active]);

  const handleClick = () => {
    setIsClicked(!isClicked);
    onClick(); // 이 부분에서 onClick을 호출합니다.
  };

  return (
    <button
      className={styled.logoContainer}
      style={{
        backgroundColor: "#BBBD32",
        border: "none", 
        boxSizing: 'border-box',
        alignItems: 'center',
        justifyContent: 'center',
        cursor: 'pointer'
      }}
      onClick={handleClick}
    >
      <p style={{
        color: "#FFFFFF",
        fontWeight: 'bold',
        cursor: 'pointer',
        display: 'flex', // Center the icon and text horizontally
        alignItems: 'center', // Center the icon and text vertically
        margin: 0,
      }}>
        {text}
      </p>
    </button>
  );
}

Button3.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired, // onClick props 추가
  active: PropTypes.bool,
};

export default Button3;

