import React, { useState, useEffect } from "react";
import styled from './Button.module.css';
import PropTypes from "prop-types";

function Button({ text, onClick, active }) {
  const [isClicked, setIsClicked] = useState(active);

  useEffect(() => {
    setIsClicked(active);
  }, [active]);

  const handleClick = () => {
    setIsClicked(!isClicked);
    onClick();
  };

  return (
    <button
      className={styled.logoContainer}
      style={{
        backgroundColor: isClicked ? "#FFD43A" : "#FFFFFF",
        border: isClicked ? "none" : "0.1vw solid #FFD43A",
        boxSizing: 'border-box',
        padding: '8px', // Optional: Add padding for better appearance
        minWidth: '3vw', // Optional: Set a minimum width to prevent being too small
      }}
      onClick={handleClick}
    >
      <p style={{
        color: isClicked ? "#FFFFFF" : "#FFD43A",
        fontWeight: 'bold',
        margin: 0,
        fontSize: 16,
        boxSizing: 'border-box',
        overflow: 'hidden', // Optional: Hide overflow if text is too long
        textOverflow: 'ellipsis', // Optional: Add ellipsis (...) for long text
      }}>{text}</p>
    </button>
  );
}

Button.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  active: PropTypes.bool,
};

export default Button;
