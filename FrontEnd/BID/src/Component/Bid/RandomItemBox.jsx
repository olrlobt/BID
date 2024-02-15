import React from "react";
import styled from "./RandomItemBox.module.css"

export default function RandomItemBox(){

  return(
    <div className={styled.boxContainer}>
      <div className={styled.imageArea}>
        <img
          src='https://ssafya306.s3.ap-northeast-2.amazonaws.com/6b5595a5-efb0-46de-bb90-847235eb4458'
          alt='아이템 상자'
        />
      </div>
    </div>
  );
}

