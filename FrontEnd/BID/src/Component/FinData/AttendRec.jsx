// AttendRec.jsx
import React from "react";
import styled from "./AttendRec.module.css";

function AttendRec({ attandance, ball }) {
  const daysOfWeek = ["월", "화", "수", "목", "금"];
  return (
    <div className={styled.attendanceContainer}>
      <div className={styled.attendance}>
        <table className={styled.attendanceTable}>
          <thead>
            <tr>
              {daysOfWeek.map((day) => (
                <th key={day}>{day}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            <tr className={styled.attendanceRow}>
              {attandance.map((value, index) => (
                <td
                  key={index}
                  className={value ? styled.present : styled.absent}
                >
                  {value ? "⭕️" : "❌"}
                </td>
              ))}
            </tr>
          </tbody>
        </table>
      </div>
      <div className={styled.beadsContainer}>
        <div>자리 구슬</div>
        <div className={styled.bead}>{ball}개</div>
      </div>
    </div>
  );
}

export default AttendRec;
