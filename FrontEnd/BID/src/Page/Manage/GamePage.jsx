import { Link } from "react-router-dom";
import styled from "./GamePage.module.css";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import useBalls from "../../hooks/useBalls";
import { ballSelector } from "../../Store/ballSlice";

export default function GamePage() {
  // 벡엔으로부터 데이터 받아와~
  const dummyBalls = [
    {
      no: 1,
      name: "강하윤",
      ballCount: 4,
    },
    {
      no: 2,
      name: "김성민",
      ballCount: 3,
    },
    {
      no: 3,
      name: "박준영",
      ballCount: 2,
    },
    {
      no: 4,
      name: "강지민",
      ballCount: 1,
    },
    {
      no: 5,
      name: "김지우",
      ballCount: 1,
    },
    {
      no: 6,
      name: "김서연",
      ballCount: 5,
    },
    {
      no: 7,
      name: "김다은",
      ballCount: 2,
    },
    {
      no: 8,
      name: "김서연",
      ballCount: 5,
    },
    {
      no: 9,
      name: "김성민",
      ballCount: 3,
    },
  ];

  const dispatch = useDispatch();
  const { initBalls } = useBalls();
  const ballList = useSelector(ballSelector);

  useEffect(() => {
    // dummyBalls 백엔드 api 날려서 넣어줄 것
    initBalls({ ballList: dummyBalls });
  }, []);

  return (
    <>
      <section className={styled.gamePage}>
        <h1>이번 달 자리는 누구부터 정할까요?</h1>
        <section className={styled.gameTable}>
          <div className={styled.tableTitle}>
            우리 반 친구들의 자리 구슬 개수
          </div>
          <section className={styled.ballStage}>
            {ballList &&
              ballList.map((student) => (
                <div className={styled.studentList}>
                  <span>{student.name}</span>
                  <span>{student.ballCount}개</span>
                </div>
              ))}
          </section>
        </section>
        <Link to="/game/seat" state={ballList}>
          <button className={styled.seatBtn}>자리 뽑기 시작</button>
        </Link>
      </section>
    </>
  );
}
