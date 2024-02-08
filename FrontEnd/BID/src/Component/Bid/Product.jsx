import React from "react";
import styled from "./Product.module.css";

export default function Product(props){
  const {no, title, contents, imgUrl, goodsType, goodsName, userName, startPrice, avgPrice, endTime, createdAt} = props;

  return(
    <div className = {styled.productWrapper}>
      <div className = {styled.imgWrapper}>
        <img src = {imgUrl}/>
      </div>
      <div className = {styled.productHeader}>
        <span className = {styled.title}>{title}</span>
        <span className = {styled.userName}>{userName}</span>
      </div>
      <div className = {styled.avgPrice}>{avgPrice}비드</div>
    </div>
  );
}