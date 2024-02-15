import React from "react";
import styled from "./Cannonball.module.css"

export default function CannonBall(){
  return(
    <div className={styled.ballContainer}>
      <div className={styled.imageArea}>
        <img
          src='https://ssafya306.s3.ap-northeast-2.amazonaws.com/6bc8ba03-ff7f-443f-aba2-91f3332f9bb2'
          alt='자리구슬'
        />
      </div>
      <div className={styled.priceArea}>
      </div>
    </div>
  );
}