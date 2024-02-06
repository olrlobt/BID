import { Link } from "react-router-dom";
import styled from "./GamePage.module.css";
import { useSelector } from "react-redux";
import useBalls from "../../hooks/useBalls";
import { ballSelector } from "../../Store/ballSlice";
import { useQuery } from "@tanstack/react-query";
import { viewStudentBalls } from "../../Apis/TeacherManageApis";

export default function GamePage() {
  const { initBalls } = useBalls();
  const ballList = useSelector(ballSelector);

  const { data: stuBalls } = useQuery({
    queryKefinishGamey: ["studentBall"],
    queryFn: () =>
      viewStudentBalls().then((res) => {
        initBalls({ ballList: res.data });
        return res.data;
      }),
  });

  return (
    <>
      {stuBalls && (
        <section className={styled.gamePage}>
          <h1>이번 달 자리는 누구부터 정할까요?</h1>
          <section className={styled.gameTable}>
            <div className={styled.tableTitle}>
              우리 반 친구들의 자리 구슬 개수
            </div>
            <section className={styled.ballStage}>
              {ballList &&
                ballList.map((student) => (
                  <div className={styled.studentList} key={ballList.no}>
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
      )}
    </>
  );
}
