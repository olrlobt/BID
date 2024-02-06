import styled from "./AlarmWindow.module.css";
import Bell from "../../Asset/Image/Student/bell.png";

export default function AlarmWindow() {
  return (
    <section className={styled.alarm}>
      <h1>
        <img className={styled.bell} src={Bell} />
        활동 알림
      </h1>
    </section>
  );
}
