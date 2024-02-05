import React, { useState, useEffect } from "react";
import styled from "./RewardPage.module.css";
import SubmitButton from "../../Component/Common/SubmitButton";
import Reward from "../../Component/Reward/Reward";
import SettingButton from "../../Component/Common/SettingButton";
import SettingsIcon from '@mui/icons-material/Settings';
import axios from 'axios';

export default function RewardPage() {
  const [isSetting, setIsSetting] = useState(false);
  const [rewardList, setRewardList] = useState([]);
  const [newRewardForm, setNewRewardForm] = useState({
    'name': '',
    'price': 0
  })
  const [studentList, setStudentList] = useState([]);
  const [rStudents, setRStudents] = useState([]);
  const [rReward, setRReward] = useState({
    'no': 0,
    'name': '',
  });
  const [rComment, setRComment] = useState('');

  useEffect(() => {
    axios.get(('http://i10a306.p.ssafy.io:8081/1/users'))
      .then(response => {
        setStudentList(response.data);
      })
      .catch(error => {
        console.log(error);
      });

      axios.get(('http://i10a306.p.ssafy.io:8081/1/rewards'))
      .then(response => {
        setRewardList(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  /** 새 리워드를 등록하는 함수 */
  const createNewReward = (e) => {
    e.preventDefault();
    axios.post('http://i10a306.p.ssafy.io:8081/1/rewards', newRewardForm)
      .then(() => {
        axios.get(('http://i10a306.p.ssafy.io:8081/1/rewards'))
        .then(response => {
          setRewardList(response.data);
        })
        .catch(error => {
          console.log(error);
        });
      })
      .catch(error => {
        console.log(error);
      });
  }
  
  /** 리워드를 지급하는 함수 */
  const sendReward = () => {
    if(rStudents.length===0){
      console.log('리워드를 지급할 학생을 선택해주세요');
    }
    else if(rReward.no===0){
      console.log('지급할 리워드를 선택해주세요');
    }
    else if(rComment===''){
      console.log('리워드와 함께 전달할 코멘트를 입력해주세요');
    }
    else{
      const postData = {
        'no': rReward.no,
        'usersNos': rStudents,
        comment: rComment,
      }
      axios.post('http://i10a306.p.ssafy.io:8081/rewards/send', postData)
        .then(() => {
        })
        .catch(error => {
          console.log(error);
        });
    }
  }

  const isCheked = (no) => {
    const findStudent = rStudents.find((stdNo) => stdNo===no);
    if(findStudent) { return true; }
    else { return false; }
  }

  const selectAllStudents = (checked) => {
    checked?
    setRStudents(studentList.map((std) => std.no))
    :
    setRStudents([])
  }

  const selectStudent = (no, checked) => {
    checked?
    setRStudents([...rStudents, no])
    :
    setRStudents(rStudents.filter((std) => std !== no))
  }

  const selectReward = (no, name) => {
    setRReward({
      'no': no,
      'name': name,
    });
  }

  const updateNewRewardForm = (e) => {
    let { name, value } = e.target;
    setNewRewardForm({
      ...newRewardForm,
      [name]: value
    });
  }

  const getNameByNo = (no) => {
    const find = studentList.find((std) => std.no===no);
    return find.name;
  }

  const updateComment = (e) => {
    setRComment(e.target.value);
  }


  return (
    <div className = {styled.contentSection}>
      <div className = {styled.rewardArea}>
        <div className = {[styled.studentCol, styled.col].join(" ")}>
          <h3 className = {styled.title}> 학생 목록 </h3>
          <div className = {styled.body}>
            <table>
              <thead>
                <tr>
                  <th><input type='checkbox' onChange={(e) => {selectAllStudents(e.target.checked)}}/></th>
                  <th>번호</th>
                  <th>이름</th>
                </tr>
              </thead>
              <tbody>
                {
                  studentList.map((student) => (
                    <tr key={ student.no }>
                      <td>
                        <input
                          type='checkbox'
                          checked={ isCheked(student.no) }
                          onChange={ (e) => { selectStudent(student.no, e.target.checked) }}
                        />
                      </td>
                      <td>{ student.number }</td>
                      <td>{ student.name }</td>
                    </tr>
                  ))
                }
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
            <div
              className = {styled.body}
              style = {{ height: isSetting? '34vw': '40vw'}}
            >
              {
                rewardList.map(reward => (
                  <Reward
                    key={ reward.no }
                    rNo={ reward.no }
                    rName={ reward.name }
                    rPrice={ reward.price }
                    isSetting={ isSetting }
                    onClick={ () => { selectReward(reward.no, reward.name, reward.price);} }
                    isActivated={ rReward.no===reward.no }
                    setRewardList={ setRewardList }
                  />
                ))
              }
            </div>
            {
              isSetting?
              <div className = { styled.footer }>
                <form onSubmit={ createNewReward }>
                  <input
                    type='text'
                    name='name'
                    placeholder='리워드 이름'
                    onChange={ updateNewRewardForm }
                  />
                  <input
                    type='number'
                    name='price'
                    placeholder='금액'
                    onChange={ updateNewRewardForm }
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
              {
                rStudents.map((std) => (
                  <div key={std}>{getNameByNo(std)}</div>
                ))
              }
              </div>
            </div>
            <div className = {styled.reward}>
              <h3 className = {styled.subTitle}> 지급 리워드 </h3>
              <div className = {styled.body}> { rReward.name } </div>
            </div>
          </div>
          <div className = {styled.comment}>
              <h3 className = {styled.title}> 코멘트 </h3>
              <textarea 
                placeholder='리워드와 함께 전달할 코멘트를 입력해주세요!'
                onChange={ updateComment }
              />
          </div>
          <SubmitButton
            text = '리워드 지급'
            width = '100%'
            height = '4vw'
            fontSize = '2vw'
            onClick = { sendReward }
          />
        </div>
      </div>
    </div>
  );
}
