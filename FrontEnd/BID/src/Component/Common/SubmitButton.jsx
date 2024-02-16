import React from "react";
import styled from "./SubmitButton.module.css";

export default function SubmitButton(props){
  const { text, width, height, fontSize } = props;

  const buttonSize = {
    width: width,
    height: height,
    fontSize: fontSize
  }

  return(
    <button
      className={ styled.submitBtn }
      type='submit'
      style={ buttonSize }
    >
      {text}
    </button>
  );
}