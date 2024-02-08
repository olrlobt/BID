import React from "react";
import styled from './Button3.module.css';
import PropTypes from "prop-types";

function Button3(props) {

  const {onClick, text} = props;


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
        {text}
      </p>
    </button>
  );
}

Button3.propTypes = {
  text: PropTypes.string.isRequired,
};

export default Button3;

