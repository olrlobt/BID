import React from 'react';
import Models from './Models';
import { Link } from 'react-router-dom';
// import styled from "./StudentMain.module.css";
import { socket } from '../../Component/Models/SocketManager';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { viewSavingList } from '../../Apis/TeacherManageApis';
import useSaving from '../../hooks/useSaving';

function StudentMain() {
  const [chatMessage, setChatMessage] = useState('');
  const sendChatMessage = () => {
    if (chatMessage.length > 0) {
      socket.emit('chatMessage', chatMessage);
      setChatMessage('');
    }
  };
  const gradeNo = 1;

  // 선생님 적금 가입정보 가져와 저장
  const { initSavingList } = useSaving();
  const {
    data, // eslint-disable-line no-unused-vars
  } = useQuery({
    queryKey: ['SavingInfo'],
    queryFn: () =>
      viewSavingList(gradeNo).then((res) => {
        initSavingList(res.data);
      }),
  });

  return (
    <>
      <Link to="/studentmain/:studentId/">
        <button>캐릭터 이미지</button>
      </Link>
      <div>안녕하세요!</div>
      <button>출석</button>
      <Models />
      <input
        type="text"
        className=""
        placeholder="Message..."
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
    </>
  );
}

export default StudentMain;
