import React, { useState } from "react";
import styled from "./RewardPage.module.css";
import SubmitButton from "../../Component/Common/SubmitButton";
import Reward from "../../Component/Reward/Reward";
import SettingButton from "../../Component/Common/SettingButton";
import SettingsIcon from '@mui/icons-material/Settings';

export default function RewardPage() {
  const [isSetting, setIsSetting] = useState(false);

  return (
    <div className = {styled.contentSection}>
      <div className = {styled.rewardArea}>
        <div className = {[styled.studentCol, styled.col].join(" ")}>
          <h3 className = {styled.title}> 학생 목록 </h3>
          <div className = {styled.body}>
            <table>
              <thead>
                <tr>
                  <th><input type='checkbox' /></th>
                  <th>번호</th>
                  <th>이름</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><input type='checkbox' /></td>
                  <td>1</td>
                  <td>봇봇봇</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div className = {[styled.rewardCol, styled.col].join(" ")}>
          <div className = {styled.header}>
            <h3 className = {styled.title}> 리워드 목록 </h3>
            <SettingButton
              onClick = {() =>
                setIsSetting(!isSetting)
              }
              svg = { SettingsIcon }
              text = '리워드 편집' 
              height = '2vw'
            />
          </div>
          <div className = {styled.rewardContainer}>
            <div className = {styled.body}>
              <Reward
                rName='일이삼사오륙칠팔구십'
                rPrice={ 200 }
              />
              <Reward
                rName='두 번째 리워드'
                rPrice={ 900 }
              />
            </div>
            {
              isSetting?
              <div className = {styled.footer}>
                <form onSubmit={() => console.log('aa')}>
                  <input
                    type='text'
                    placeholder='리워드 이름'
                  />
                  <input
                    type='number'
                    placeholder='금액'
                  />
                  <SubmitButton
                    text = '추가'
                    width = '4vw'
                    height = '4vw'
                    fontSize = '1.3vw'
                  />
                </form>
              </div>
              :
              null
            }
            
          </div>
        </div>
        <div className = {[styled.submitCol, styled.col].join(" ")}>
          <div className = {styled.result}>
            <div className = {styled.student}>
              <h3 className = {styled.subTitle}> 리워드 대상 </h3>
              <div className = {styled.body}>
                {/* <div>가가가</div>
                <div>나나나</div>
                <div>다다다</div>
                <div>라라라</div>
                <div>가가가</div>
                <div>나나나</div>
                <div>다다다</div>
                <div>라라라</div>
                <div>가가가</div>
                <div>나나나</div>
                <div>다다다</div>
                <div>라라라</div>
                <div>라라라</div>
                <div>가가가</div>
                <div>나나나</div>
                <div>다다다</div>
                <div>라라라</div> */}
              </div>
            </div>
            <div className = {styled.reward}>
              <h3 className = {styled.subTitle}> 지급 리워드 </h3>
              <div className = {styled.body}>

              </div>
            </div>
          </div>
          <div className = {styled.comment}>
              <h3 className = {styled.title}> 코멘트 </h3>
              <textarea 
                placeholder='리워드와 함께 전달할 코멘트를 입력해주세요!'
              />
          </div>
          <SubmitButton
            text = '리워드 지급'
            width = '100%'
            height = '4vw'
            fontSize = '2vw'
          />
        </div>
      </div>
    </div>
  );
}
