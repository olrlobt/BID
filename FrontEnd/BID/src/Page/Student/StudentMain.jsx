import React, { useEffect, useState } from "react";
import Models from "./Models";
import { Link } from "react-router-dom";
import { stuAttendApi, stuAttendCheckApi } from "../../Apis/ModelApis";
import { useSelector } from "react-redux";
import { modelListSelector, modelSelector, modelImgSelector } from "../../Store/modelSlice";
import styled from "./StudentMain.module.css";
import alertBtn from '../../Component/Common/Alert';
import { socket } from "../../Component/Models/SocketManager";
import { useMutation, useQuery } from "@tanstack/react-query";
import useAlarm from "../../hooks/useAlarm";
import useProducts from "../../hooks/useProducts";
import { getProductListApi } from "../../Apis/StudentBidApis";

function StudentMain() {
  const models = useSelector(modelListSelector);
  const myInfo = useSelector(modelSelector);
  const imgInfo = useSelector(modelImgSelector)
  console.log(imgInfo)
  const gradeNo = myInfo.model.gradeNo;
  useEffect(() => {
    // gradeNo를 사용하여 방에 조인
    socket.emit("joinRoom", gradeNo);

    // 컴포넌트 언마운트 시에 방을 떠나는 로직도 추가할 수 있습니다.
    return () => {
      socket.emit("leaveRoom", gradeNo);
    };
  }, []);

  const { updateAlarms } = useAlarm();


  useEffect(() => {
    if (models.length > 0) {
      socket.emit("characters", models); // 서버에 gradeNo 기반으로 캐릭터 데이터 전송
      console.log(models)
    }
    const eventSource = new EventSource(
      `${process.env.REACT_APP_TCH_API}/notification/subscribe/${myInfo.model.no}`
    );
    eventSource.onopen = (e) => {
      console.log("Connection established");
    };
    eventSource.addEventListener("sse", (e) => {
      console.log(e.data);
      if (JSON.parse(e.data).title !== "EventStream Created.") {
        console.log([e.data]);
        updateAlarms([e.data]);
      }
    });

    eventSource.onerror = (e) => {
      console.log(e);
      eventSource.close();
    };
  }, [models, gradeNo]);

  const [chatMessage, setChatMessage] = useState("");
  const [attendanceSuccess, setAttendanceSuccess] = useState(false); // 출석 성공 상태 추가

  const sendChatMessage = () => {
    if (chatMessage.length > 0) {
      socket.emit('chatMessage', chatMessage, myInfo.model.no, myInfo.model.gradeNo);
      setChatMessage('');
    }
  };

  const studentId = myInfo.model.no;

  const stuAttendQuery = useMutation({
    mutationKey: ["stuAttend"],
    mutationFn: () => stuAttendApi(),
    onSuccess: (res) => {
      setAttendanceSuccess(true); // 출석 성공 시 상태 변경
      alertBtn({
        text: '출석 완료!',
        confirmColor: '#ffd43a',
        icon: 'success',
      });
      
    },
    onError: (error) => {
      console.log(error);
    },
  });


  const stuAttendCheckQuery = useMutation({
    mutationKey: ["stuAttendCheck"],
    mutationFn: () => stuAttendCheckApi(), // 출석 상태를 체크하는 API 호출
    onSuccess: (res) => {
      if (res.data == false) {
        console.log(res)
        stuAttendQuery.mutate();
      } else {
        console.log(res)
        alertBtn({
          text: '이미 출석이 되었습니다!',
          confirmColor: '#ffd43a',
          icon: 'success',
        });
        // 출석이 안될 상태일때, 출석 체크
      }
    },
    onError: (error) => {
      console.log(error);
    },
  });

  // 출석 버튼 클릭 이벤트 핸들러
  const handleAttendEvent = (e) => {
    e.preventDefault();
    // 출석 상태를 체크하는 쿼리 호출
    stuAttendCheckQuery.mutate();
  };

  return (
    <div className={styled.container}>
      <div className={styled.header}>
        <Link to={`/studentmain/${studentId}/`}>
          <img className={styled.img} src={imgInfo} alt="이미지" />
        </Link>
        <div>
          <p>안녕하세요!</p>
          <p className={styled.name}>{myInfo.model.name}님</p>
          {/* 출석 성공 시 버튼 스타일 변경 */}
          <button className={styled.attendanceBtn}
           onClick={handleAttendEvent}>출석</button>
        </div>
      </div>
      <Models myInfo={myInfo} />
      <input
        type="text"
        className={styled.chatMessage}
        placeholder="채팅하기"
        onKeyDown={(e) => {
          if (e.key === "Enter") {
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

// DJD50201
//130524
