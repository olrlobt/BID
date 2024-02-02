import styled from './TimeTable.module.css';
import React from 'react';

export default function TimeTable({ modalClick }) {
  const startH = [
    '09:00',
    '10:00',
    '11:00',
    '12:00',
    '13:00',
    '14:00',
    '15:00',
  ];

  const endH = ['09:40', '10:40', '11:40', '12:40', '13:40', '14:40', '15:40'];
  return (
    <section className={styled.timeTable} onClick={modalClick}>
      <div className={styled.timeImage}></div>
      <section className={styled.StopTime}>
        <div className={styled.timeTitle}>
          경매가 중단되는 <span className={styled.classT}>수업시간</span>은
        </div>
        {startH.map((value, index) => (
          <React.Fragment key={index}>
            <div className={styled.detailTime}>
              <span>{startH[index]}</span>
              <span>-</span>
              <span>{endH[index]}</span>
            </div>
            {index === startH.length - 1 ? '' : <hr />}
          </React.Fragment>
        ))}
      </section>
    </section>
  );
}
