import React from "react";
import styled from "./Product.module.css";

export default function Product(props) {
  const {
    onClick,
    title,
    displayPrice,
    goodsImgUrl,
    userName,
    boardStatus
  } = props;

  return (
    <div className={styled.productWrapper} onClick={onClick}>
      <div className={styled.imgWrapper}>
        <img src={goodsImgUrl} alt="이미지" onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
      </div>
      <div className={styled.productHeader}>
        <span className={styled.title}>{title}</span>
        <span className={styled.userName}>{userName}</span>
      </div>
      <div
        className={styled.displayPrice}
        style={boardStatus==='PROGRESS'? {}: {color: 'gray'}}
      >
        {displayPrice}비드
      </div>
    </div>
  );
}
