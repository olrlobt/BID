import React from "react";
import styled from "./NoContent.module.css";

export default function NoContent(){
  return(
    <div className = {styled.noContents}>
      현재 진행 중인 경매가 없어요
    </div>
  );
}