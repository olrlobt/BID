import React, { useState, useEffect } from "react";
import styled from './Button2.module.css';
import PropTypes from "prop-types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGear } from "@fortawesome/free-solid-svg-icons"; // Import the needed icon

function Button2({ text, onClick, active }) {
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
        backgroundColor: "#5FA1C4",
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
        cursor: 'pointer' 

      }}>
        <FontAwesomeIcon icon={faGear} />
        {text}</p>
    </button>
  );
}

Button2.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  active: PropTypes.bool,
};

export default Button2;
