import React from "react";
import styled from "./NoContent.module.css";

export default function NoContent(props){
  const { text } = props;

  return(
    <div className = {styled.noContents}>
      {text}
    </div>
  );
}