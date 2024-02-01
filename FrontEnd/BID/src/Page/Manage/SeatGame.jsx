import styled from "./SeatGame.module.css";
import Back from "../../Asset/Image/SeatGame/back_btn.png";
import { useNavigate } from "react-router-dom";

export default function SeatGame() {
  const navigate = useNavigate();
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
        <div className={styled.cane}></div>
        <div className={styled.basket}></div>
      </section>
    </section>
  );
}
