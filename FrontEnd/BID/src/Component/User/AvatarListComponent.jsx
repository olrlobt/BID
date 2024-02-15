import React from "react";
import styled from "./AvatarListComponent.module.css";

export default function AvatarListComponent(props){
  const { url, name, onClick } = props;

  return(
    <div className={styled.avatarContainer} onClick={onClick}>
      <div className={styled.imageArea}>
        <img src={url} alt='avatar image' />
      </div>
      <h3>{name}</h3>
    </div>
  );
}