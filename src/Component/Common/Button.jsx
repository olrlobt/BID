import React,  { useState }  from "react";
import styled from './Button.module.css'
import PropTypes from "prop-types";

function Button({ text, onClick }) {

    const [isClicked, setIsClicked] = useState(false);

    const handleClick = () => {
      setIsClicked(!isClicked);
      onClick(); 
    };

    return (
    <div
      className={styled.logoContainer}
      style={{
        backgroundColor: isClicked ? "#FFD43A" : "#FFFFFF",
        border: isClicked ? "none" : "3px solid #FFD43A",
        height: '4vh', 
        width: '8vw',
        boxSizing: 'border-box'
      }}
      onClick={handleClick}
    >
      <div
        className={styled.border}
        style={{
            height: '100%', 
            boxSizing: 'border-box', 
          }}
      ></div>
      <p style={{ 
        color: isClicked ? "#FFFFFF" : "#FFD43A",
        fontWeight: 'bold',
        margin: 0,
        
    }}>{text}</p>
    </div>
  );
}

Button.propTypes = {
    text: PropTypes.string.isRequired,
    onClick: PropTypes.func.isRequired,
  };
  
  export default Button;
