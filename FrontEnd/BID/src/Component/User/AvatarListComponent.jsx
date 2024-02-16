import React from "react";
import styled from "./AvatarListComponent.module.css";

export default function AvatarListComponent(props){
  const { url, name, onClick } = props;

  return(
    <div className={styled.avatarContainer} onClick={onClick}>
      <div className={styled.imageArea}>
        <img src={url} alt='avatar image' onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
      </div>
      <h3>{name}</h3>
    </div>
  );
}