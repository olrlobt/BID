import React, { useEffect } from 'react';
import Models from './Models';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { viewSavingList } from '../../Apis/TeacherManageApis';
import { stuAttendApi } from '../../Apis/ModelApis';
import useSaving from '../../hooks/useSaving';
import { useSelector } from "react-redux";
import { modelListSelector, modelSelector } from "../../Store/modelSlice";
import styled from "./StudentMain.module.css";
import { socket  } from '../../Component/Models/SocketManager'; 

function StudentMain() {
  const models = useSelector(modelListSelector);
  const myInfo = useSelector(modelSelector);
  const gradeNo = myInfo.model.gradeNo

  console.log(models)
  console.log(myInfo)
  useEffect(() => {
    if (models.length > 0) {
      socket.emit("characters", models); // 서버에 gradeNo 기반으로 캐릭터 데이터 전송
    }
  }, [models, gradeNo]);
  
  const [chatMessage, setChatMessage] = useState('');

  const sendChatMessage = () => {
    if (chatMessage.length > 0) {
      socket.emit('chatMessage', chatMessage,myInfo.model.no);
      setChatMessage('');
    }
  };

  const studentId = myInfo.model.no
  // const gradeNo = 1;
  // const selectedCharacter = myInfo.model.profileImgUrl.split('/').pop().replace('.png', '');

  // 선생님 적금 가입정보 가져와 저장
  const { initSavingList } = useSaving();
  const { data } = useQuery({
    queryKey: ['SavingInfo'],
    queryFn: () =>
      viewSavingList(gradeNo).then((res) => {
        initSavingList(res.data);
      }),
  });


  return (
    <div className={styled.container}>
      <div className={styled.header}>
        <Link to={`/studentmain/${studentId}/`}>
          <img className={styled.img} src={myInfo.model.profileImgUrl} alt="이미지" />
        </Link>
        <div>
          <p>안녕하세요!</p>
          <p className={styled.name}>{myInfo.model.name}님</p>
          <button className={styled.attendanceBtn}>출석</button>
        </div>
      </div>
      <Models myInfo={myInfo}/>
      <input
        type="text"
        className={styled.chatMessage}
        placeholder="채팅하기"
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            sendChatMessage();
          }
        }}
        value={chatMessage}
        onChange={(e) => setChatMessage(e.target.value)}
      />
      <button onClick={sendChatMessage}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth={1.5}
          stroke="currentColor"
          className="w-6 h-6"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5"
          />
        </svg>
      </button>
    </div>
  );
}

export default StudentMain;
