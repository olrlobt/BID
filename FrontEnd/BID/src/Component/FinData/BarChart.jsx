import React from "react";
import styled from './BarChart.module.css';
import { ResponsiveBarCanvas } from '@nivo/bar';

function BarChart() {
  const savingsData = {
    total: 800,
    saved: 450
  };

  const gaugeData = [
    {
      id: 'gauge',
      value: savingsData.saved,
      total: savingsData.total,
      color: '#5FA1C4',
    },
  ];

  const currentDate = 9;

  return (
    <div className={styled.savingsContainer}>
      <p className={styled.header}>2주 적금 <span className={styled.interestRate}>금리 2.2%</span></p>
      <p className={styled.subText}>14일 중 <span style={{ color: "#5FA1C4" }}>{currentDate}</span>일</p>
      <div style={{ height: '50px', position: 'relative' }}>
        <ResponsiveBarCanvas
          data={gaugeData}
          keys={['value']}
          indexBy="id"
          colors={'#5FA1C4'}
          margin={{ top: 0, right: 0, bottom: 0, left: 0 }}
          padding={0.78}
          enableLabel={false}
          animate={false}
          layers={['grid', 'bars', 'markers', 'legends', 'annotations', 'axes']}
          fill={[
            { match: { id: 'gauge' }, id: 'gradient' },
          ]}
          layout="horizontal"
          axisBottom={null} // Remove x-axis
          axisTop={null} // Remove x-axis
          axisLeft={null} // Remove y-axis
          axisRight={null} // Remove y-axis
          maxValue={savingsData.total} // Set the maximum value for better scale
        />
      </div>
      <span className={styled.subText} style={{ color: "#5FA1C4" }}>{`${savingsData.saved} 비드`}</span>
      모았어요!
      <p className={styled.interestRate}>{`${savingsData.total} 비드로 돌려받을 수 있어요`}</p>
    </div>
  );
}

export default BarChart;
