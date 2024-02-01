import styled from "./SeatGame.module.css";
import Back from "../../Asset/Image/SeatGame/back_btn.png";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import useBalls from "../../hooks/useBalls";

export default function SeatGame() {
  const navigate = useNavigate();
  const location = useLocation();
  const ballList = location.state;

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
  const [studentName, setStudentName] = useState("");

  // const {removeBalls} = useBalls();

  /** 지팡이 클릭했을 때 발생하는 이벤트 */
  const handleCaneClick = () => {
    setIsCaneClicked(true);

    setTimeout(() => {
      setIsBallOpen(true);
      setIsCaneClicked(false);
      pickSeat();

      setTimeout(() => {
        setIsBallOpen(false);
      }, 3000);
    }, 1000);
  };

  /** 한 좌석 뽑는 이벤트 */
  const pickSeat = () => {
    if (pickList.length === 0) {
      alert("더 이상 뽑을 학생이 없습니다.");
      setIsBallOpen(false);
      // redux에 상태 업데이트 해주기
    }

    // 무작위 하나 선택 후 동일한 숫자를 가진 항목 제거
    const randomIndex = Math.floor(Math.random() * pickList.length);
    const selectedNum = pickList[randomIndex];

    // 학생이름을 공에 띄우기 위한 것
    setStudentName(
      ballList.filter((student) => student.no === selectedNum)[0].name
    );

    setPickList(pickList.filter((num) => num !== selectedNum));
  };

  return (
    <section className={styled.seatGame}>
      <section>
        <div className={styled.seatInfo}>자리선정순서</div>
        <section className={styled.seatTable}></section>
        <button onClick={() => navigate("/game/")}>
          <img className={styled.back} src={Back} alt="뒤로가자" />
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
    </section>
  );
}