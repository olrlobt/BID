import React from "react";
import styled from "./Product.module.css";

export default function Product(props) {
  const {
    onClick,
    title,
    displayPrice,
    goodsImgUrl,
    userName,
  } = props;

  return (
    <div className={styled.productWrapper} onClick={onClick}>
      <div className={styled.imgWrapper}>
        <img src={goodsImgUrl} alt="이미지" />
      </div>
      <div className={styled.productHeader}>
        <span className={styled.title}>{title}</span>
        <span className={styled.userName}>{userName}</span>
      </div>
      <div className={styled.displayPrice}>{displayPrice}비드</div>
    </div>
  );
}
