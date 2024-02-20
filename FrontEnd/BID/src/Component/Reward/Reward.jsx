import React from "react";
import styled from "./Reward.module.css";
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { SvgIcon } from "@material-ui/core";
import DoDisturbOnIcon from "@mui/icons-material/DoDisturbOn";
import { deleteRewardApi } from "../../Apis/RewardApis";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useSelector } from "react-redux";
import { mainSelector } from "../../Store/mainSlice";
import confirmBtn from "../Common/Confirm";

export default function Reward(props){
  const { rNo, rName, rPrice, isSetting, onClick, isActivated } = props;
  const queryClient = useQueryClient();
  const gradeNo = useSelector(mainSelector).no;

  /** 리워드 삭제 쿼리 */
  const deleteRewardQuery = useMutation({
    mutationKey: ['deleteReward'],
    mutationFn: () => deleteRewardApi(gradeNo, rNo),
    onSuccess: () => {
      queryClient.invalidateQueries('rewardList');
    },
    onError: (error) => { console.log(error); }
  })

    /** 리워드 삭제 함수 */
  const deleteReward = () => {
    confirmBtn({
			icon: "warning",
			html: "삭제하신 리워드는 다시 복구할 수 없습니다.<br/>그래도 삭제하시겠습니까?",
			confirmTxt: "삭제",
			confirmColor: "#F23F3F",
			cancelTxt: "취소",
			cancelColor: "#a6a6a6",
			confirmFunc: () => deleteRewardQuery.mutate(),
      cancelFunc: () => {}
		})
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
            onClick={ deleteReward }
          />
          :
          null
        }
      </div>
    </div>
  );
}