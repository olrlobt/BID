import React from "react";
import styled from "./Reward.module.css";
import RoundedInfoButton from "../Common/RoundedInfoButton";

export default function Reward(props){
  const { rName, rPrice } = props;

  return(
    <div className={ styled.reward }>
      <span className={ styled.title }>{ rName }</span>
        <RoundedInfoButton
          value = { rPrice }
          unit = '비드'
          textColor = 'white'
          borderColor = '#BBBD32'
          backgroundColor = '#BBBD32'
          padding = '0 0.5vw'
        />
    </div>
  );
}