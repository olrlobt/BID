import Calender from "../../Asset/Image/HOME_icons/calender-dynamic-color.png";
import styled from "./TimeTable.module.css";

export default function TimeTable() {
  const hour = [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24,
  ];

  const time = [0, 10, 20, 30, 40, 50];
  return (
    <section>
      <img className={styled.timeImage} src={Calender} alt="캘린더" />
      <section>
        <section>
          <label htmlFor="current">알림 시간</label>
          <div>
            <select id="current">
              {hour.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
            <span> : </span>
            <select id="current">
              {time.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
          </div>
        </section>
      </section>
    </section>
  );
}
