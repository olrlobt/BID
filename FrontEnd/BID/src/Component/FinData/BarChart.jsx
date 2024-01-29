import React from "react";
import styled from "./BarChart.module.css";
// import { ResponsiveBarCanvas } from '@nivo/bar'

function BarChart() {
  const savingsData = {
    total: 800,
    saved: 450,
  };
  const currentDate = 9;

  return (
    <div className={styled.savingsContainer}>
      <p className={styled.header}>
        2주 적금 <span className={styled.interestRate}>금리 2.2%</span>
      </p>
      <p className={styled.subText}>
        14일 중 <span style={{ color: "#5FA1C4" }}>{currentDate}</span>일
      </p>
      <span
        className={styled.subText}
        style={{ color: "#5FA1C4" }}
      >{`${savingsData.saved} 비드`}</span>
      모았어요!
      <p
        className={styled.interestRate}
      >{`${savingsData.total} 비드로 돌려받을 수 있어요`}</p>
    </div>
  );
}

export default BarChart;
