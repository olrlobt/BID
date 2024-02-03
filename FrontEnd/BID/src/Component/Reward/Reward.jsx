import React from "react";
import styled from "./Reward.module.css";
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { SvgIcon } from "@material-ui/core";
import DoDisturbOnIcon from '@mui/icons-material/DoDisturbOn';
import axios from "axios";

export default function Reward(props){
  const { rNo, rName, rPrice, isSetting, onClick, isActivated } = props;

  const deleteCoupon = () => {
    axios.delete('http://i10a306.p.ssafy.io:8081/rewards/' + rNo)
    .then(response => {
      console.log('deleted');
    })
    .catch(error => {
      console.log(error);
    });
  }

  return(
    <div
      className={ styled.reward }
      onClick={ isSetting?null :onClick }
      style={ !isSetting && isActivated? {backgroundColor: '#FFD43A'}: null }
    >
      <span
        className={ styled.title }
        style={ !isSetting && isActivated? {color: 'white', fontWeight: '800', textShadow: '2px 4px 12px rgba(0,0,0,0.1)'}: null }
      >
        { rName }
      </span>
      <div className={ styled.rewardRight }>
        <RoundedInfoButton
          value={ rPrice }
          unit='비드'
          textColor='white'
          borderColor='#BBBD32'
          backgroundColor='#BBBD32'
          padding='0 0.5vw'
        />
        {
          isSetting?
          <SvgIcon
            component={ DoDisturbOnIcon }
            style={ {
              fill: '#F23F3F',
              fontSize: '1.9vw',
              marginLeft: '0.7vw'
            } }
            onClick={ deleteCoupon }
          />
          :
          null
        }
      </div>
    </div>
  );
}