import styled from './SeatGame.module.css';
import Back from '../../Asset/Image/SeatGame/back_btn.png';
import { useLocation, useNavigate } from 'react-router-dom';
import { useRef, useState } from 'react';
import useBalls from '../../hooks/useBalls';
import html2canvas from 'html2canvas';
import { resetStudentBalls } from '../../Apis/TeacherManageApis';
import { useMutation } from '@tanstack/react-query';
import { useSelector } from 'react-redux';
import { mainSelector } from '../../Store/mainSlice';
import alertBtn from '../../Component/Common/Alert';

export default function SeatGame() {
  const navigate = useNavigate();
  const location = useLocation();
  const ballList = location.state;
  const mainClass = useSelector(mainSelector);

  const gradeNo = mainClass.no;
  // 캡쳐
  const captureRef = useRef(null);

  /** 캡쳐 버튼 눌렀을 때 작동하는 함수 */
  const handleCapture = () => {
    if (captureRef.current) {
      html2canvas(captureRef.current).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const blob = dataURLtoBlob(imgData);
        const file = new File([blob], 'captured_image.png', {
          type: 'image/png',
        });
        saveAs(file, 'captured_image.png');
        console.log(imgData);
      });
    }
  };

  /** Data URL을 Blob 객체로 변환하는 함수 */
  const dataURLtoBlob = (dataURL) => {
    const parts = dataURL.split(';base64,');
    const contentType = parts[0].split(':')[1];
    const raw = window.atob(parts[1]);
    const array = new Uint8Array(raw.length);

    for (let i = 0; i < raw.length; i++) {
      array[i] = raw.charCodeAt(i);
    }

    return new Blob([array], { type: contentType });
  };

  /** Blob을 파일로 저장하는 함수 */
  const saveAs = (blob, fileName) => {
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;

    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
  };

  // 공 count만큼 개수 넣기
  const tempList = [];
  ballList.forEach((student) => {
    for (let i = 0; i < student.ballCount; i++) {
      tempList.push(student.no);
    }
  });
  const [isCaneClicked, setIsCaneClicked] = useState(false);
  const [isBallOpen, setIsBallOpen] = useState(false);
  const [pickList, setPickList] = useState(tempList);
  const [studentName, setStudentName] = useState('');
  const [seatIndex, setSeatIndex] = useState(0);

  // 자리배정을 위한 배열
  const [seatList, setSeatList] = useState(
    Array.from({ length: ballList.length }, () => null)
  );

  const { removeBalls } = useBalls();

  /** 지팡이 클릭했을 때 발생하는 이벤트 */
  const handleCaneClick = () => {
    setIsCaneClicked(true);

    setTimeout(() => {
      setIsBallOpen(true);
      setIsCaneClicked(false);
      pickSeat(seatIndex);

      setTimeout(() => {
        setIsBallOpen(false);
      }, 3000);
    }, 1000);
  };

  /** 한 좌석 뽑는 이벤트 */
  const pickSeat = () => {
    if (pickList.length === 0) {
      alertBtn({
        text: '더 이상 뽑을 학생이 없습니다.',
        confirmColor: '#E81818',
        icon: 'error',
      });
      setIsBallOpen(false);
      finishGame.mutate();
      // redux에 상태 업데이트 해주기
      removeBalls();
      return;
    }

    // 무작위 하나 선택 후 동일한 숫자를 가진 항목 제거
    const randomIndex = Math.floor(Math.random() * pickList.length);
    const selectedNum = pickList[randomIndex];

    // 학생이름을 공에 띄우기 위한 것
    setStudentName(
      ballList.filter((student) => student.no === selectedNum)[0].name
    );

    setSeatList((prevSeatList) => {
      const updatedList = [...prevSeatList];
      updatedList[seatIndex] = ballList.find(
        (student) => student.no === selectedNum
      ).name;
      return updatedList;
    });

    setPickList(pickList.filter((num) => num !== selectedNum));
    setSeatIndex(seatIndex + 1);
  };

  const finishGame = useMutation({
    mutationKey: ['resetBalls'],
    mutationFn: () =>
      resetStudentBalls(gradeNo)
        .then(() => {
          alertBtn({
            text: '게임이 성공적으로 끝났습니다.',
            confirmColor: '#ffd43a',
            icon: 'success',
          });
        })
        .catch(() => {
          alertBtn({
            text: '에러가 발생했습니다.',
            confirmColor: '#E81818',
            icon: 'error',
          });
        }),
  });

  return (
    <section ref={captureRef} className={styled.seatGame}>
      <section>
        <div className={styled.seatInfo}>자리선정순서</div>
        <section className={styled.seatTable}>
          {seatList.map((student, index) => (
            <div className={styled.mySeat}>
              <span className={styled.order}>{index + 1}</span>
              <span>{student !== null ? student : ''}</span>
            </div>
          ))}
        </section>
        <button onClick={() => navigate('/game/')}>
          <img
            className={styled.back}
            src={Back}
            alt="뒤로가자"
            onError={(e) =>
              (e.target.src =
                'https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg')
            }
          />
        </button>
      </section>
      <section className={styled.gameArea}>
        <div
          className={`${styled.cane} ${isCaneClicked && styled.falling}`}
          onClick={handleCaneClick}
        ></div>
        <div className={styled.basket}></div>
        <div className={`${styled.ball} ${isBallOpen && styled.popBall}`}>
          <div>{studentName}</div>
        </div>
      </section>
      <button className={styled.downloadBtn} onClick={handleCapture}>
        결과 다운로드
      </button>
    </section>
  );
}
