import React from "react";
import styled from "./BarChart.module.css";
import { ResponsiveBarCanvas } from "@nivo/bar";

function BarChart({ savingData }) {
  //   "savingDepositPeriod": 예치기간,
  // "savingInterestRate": 적금금리,
  // "savingCurrentPeriod": 현재입금일수,
  // "savingCurrentPrice": 현재금액,
  // "savingResultPrice": 만기금액,

  const gaugeData = [
    {
      id: "gauge",
      value: savingData.savingCurrentPrice,
      total: savingData.savingResultPrice,
      color: "#5FA1C4",
    },
  ];

  return (
    <div className={styled.savingsContainer}>
      {savingData.savingNo !== 0 ? (
        <div>
          <p className={styled.header}>
            {savingData.savingDepositPeriod / 7}주 적금
            <span className={styled.interestRate}>
              금리 {savingData.savingInterestRate}%
            </span>
          </p>
          <span className={`${styled.subText} ${styled.start}`}>
            <span>{savingData.savingDepositPeriod}일 중 </span>
            <span className={styled.current}>
              {savingData.savingCurrentPeriod}
            </span>
            <span>일</span>
          </span>
          <div className={styled.bar}>
            <ResponsiveBarCanvas
              data={gaugeData}
              keys={["value"]}
              indexBy="id"
              colors={"#5FA1C4"}
              margin={{ top: 0, right: 0, bottom: 0, left: 0 }}
              padding={0.78}
              enableLabel={false}
              animate={false}
              layers={[
                "grid",
                "bars",
                "markers",
                "legends",
                "annotations",
                "axes",
              ]}
              fill={[{ match: { id: "gauge" }, id: "gradient" }]}
              layout="horizontal"
              axisBottom={null} // Remove x-axis
              axisTop={null} // Remove x-axis
              axisLeft={null} // Remove y-axis
              axisRight={null} // Remove y-axis
              maxValue={savingData.savingResultPrice} // Set the maximum value for better scale
            />
          </div>
          <span className={styled.subText}>
            <span className={`${styled.current} ${styled.margin}`}>
              {savingData.savingCurrentPrice}비드
            </span>
            모았어요!
          </span>
          <p className={styled.interestRate}>
            {savingData.savingResultPrice} 비드로 돌려받을 수 있어요
          </p>
        </div>
      ) : (
        <div className={styled.noSaving}>현재 가입 중인 적금이 없습니다</div>
      )}
    </div>
  );
}

export default BarChart;
