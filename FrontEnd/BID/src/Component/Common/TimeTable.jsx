import styled from './TimeTable.module.css';
import React from 'react';

export default function TimeTable({ gradePeriods, modalClick }) {
  return (
    <section className={styled.timeTable} onClick={modalClick}>
      <div className={styled.timeImage}></div>
      <section className={styled.StopTime}>
        <div className={styled.timeTitle}>
          경매가 중단되는 <span className={styled.classT}>수업시간</span>은
        </div>
        {gradePeriods.map((value, index) => (
          <React.Fragment key={value.no}>
            <div className={styled.detailTime}>
              <span>{value.startPeriod}</span>
              <span>-</span>
              <span>{value.endPeriod}</span>
            </div>
            {index === 5 ? '' : <hr />}
          </React.Fragment>
        ))}
      </section>
    </section>
  );
}
