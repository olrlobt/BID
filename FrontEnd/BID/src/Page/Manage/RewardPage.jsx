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
  const [newCouponForm, setNewCouponForm] = useState({
    'name': '',
    'price': 0
  })
  const [studentList] = useState([
    { no:1, number: 1, name: '백지윤', asset: '5,678' },
    { no:2, number: 2, name: '유현지', asset: '4,321' },
    { no:3, number: 3, name: '배민지', asset: '9,321' },
    { no:4, number: 4, name: '이현진', asset: '92,394' },
    { no:5, number: 5, name: '이승헌', asset: '321' },
    { no:6, number: 6, name: '김예림', asset: '54,321' },
    { no:7, number: 7, name: '백지윤', asset: '5,678' },
    { no:8, number: 8, name: '유현지', asset: '4,321' },
    { no:9, number: 9, name: '배민지', asset: '9,321' },
    { no:10, number: 10, name: '이현진', asset: '92,394' },
    { no:11, number: 11, name: '이승헌', asset: '321' },
    { no:12, number: 12, name: '김예림', asset: '54,321' },
    { no:13, number: 13, name: '백지윤', asset: '5,678' },
    { no:14, number: 14, name: '유현지', asset: '4,321' },
    { no:15, number: 15, name: '배민지', asset: '9,321' },
    { no:16, number: 16, name: '이현진', asset: '92,394' },
    { no:17, number: 17, name: '이승헌', asset: '321' },
    { no:18, number: 18, name: '김예림', asset: '54,321' },
  ]);
  const [rStudents, setRStudents] = useState([]);
  const [rReward, setRReward] = useState({
    'no': 0,
    'name': '',
    'price': 0
  });
  const [rComment, setRComment] = useState('');

  useEffect(() => {
    axios.get(('http://i10a306.p.ssafy.io:8081/1/rewards'))
      .then(response => {
        setRewardList(response.data);
      })
      .catch(error => {
        console.log(error);
      });
    // axios.get(('http://i10a306.p.ssafy.io:8081/1/users'))
    //   .then(response => {
    //     // setStudentList(response.data);
    //     console.log(response.data);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
  }, []);

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

  const selectReward = (no, name, price) => {
    setRReward({
      'no': no,
      'name': name,
      'price': price
    });
  }

  const updateNewCouponForm = (e) => {
    let { name, value } = e.target;
    setNewCouponForm({
      ...newCouponForm,
      [name]: value
    });
  }

  const createNewCoupon = (e) => {
    e.preventDefault();
    setNewCouponForm({
      ...newCouponForm,
      'price': parseInt(newCouponForm.price, 10)
    })
    // console.log(newCouponForm);
    axios.post('http://i10a306.p.ssafy.io:8081/1/rewards', newCouponForm)
      .then(response => {
        console.log('ok');
      })
      .catch(error => {
        console.log(error);
      });
  }

  const getNameByNo = (no) => {
    const find = studentList.find((std) => std.no===no);
    return find.name;
  }

  const updateComment = (e) => {
    setRComment(e.target.value);
  }

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
        'userNos': rStudents,
        comment: rComment,
        'price': rReward.price,
      }
      console.log(postData);
  
      axios.post('http://i10a306.p.ssafy.io:8081/rewards/send', postData)
        .then(response => {
          console.log('send');
        })
        .catch(error => {
          console.log(error);
        });
    }
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
                          checked={ isCheked(student.number) }
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
                  />
                ))
              }
            </div>
            {
              isSetting?
              <div className = { styled.footer }>
                <form onSubmit={ createNewCoupon }>
                  <input
                    type='text'
                    name='name'
                    placeholder='리워드 이름'
                    onChange={ updateNewCouponForm }
                  />
                  <input
                    type='number'
                    name='price'
                    placeholder='금액'
                    onChange={ updateNewCouponForm }
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
