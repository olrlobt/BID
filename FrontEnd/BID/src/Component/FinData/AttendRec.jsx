import React from "react";
import styled from './AttendRec.module.css';

function AttendRec() {
  const daysOfWeek = ['월', '화', '수', '목', '금'];

  const attendanceData = [
    { status: 'O' },
    { status: 'X' },
    { status: 'O' },
    { status: 'O' },
    // Add other days as needed
  ];

  return (
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
          {attendanceData.map((entry, index) => (
            <td key={index} className={entry.status === 'O' ? styled.present : styled.absent}>
              {entry.status}
            </td>
          ))}
        </tr>
      </tbody>
    </table>
  );
}

export default AttendRec;
