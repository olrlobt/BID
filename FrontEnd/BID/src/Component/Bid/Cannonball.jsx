import React from "react";
import styled from "./Cannonball.module.css"

export default function CannonBall(){
  return(
    <div className={styled.ballContainer}>
      <div className={styled.imageArea}>
        <img
          src='https://ssafya306.s3.ap-northeast-2.amazonaws.com/6bc8ba03-ff7f-443f-aba2-91f3332f9bb2'
          alt='자리구슬'
          onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}
        />
      </div>
      <div className={styled.priceArea}>
      </div>
    </div>
  );
}