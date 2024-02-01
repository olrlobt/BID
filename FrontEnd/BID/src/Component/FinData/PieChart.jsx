import React from 'react';
import { ResponsivePie } from '@nivo/pie';
import styled from './PieChart.module.css';

const Piechart = () => {
  const data = [
    { id: '간식', value: 324 },
    { id: '쿠폰', value: 88 },
    { id: '캐릭터', value: 221 },
  ];

  const totalValue = data.reduce((sum, entry) => sum + entry.value, 0);

  // const handle = {
  //   padClick: (data) => {
  //     console.log(data);
  //   },

  //   legendClick: (data) => {
  //     console.log(data);
  //   },
  // };

  return (
    <div className={styled.PieContainer}>
      <div style={{ width: '100%', height: '90%', position: 'relative' }}>
        <ResponsivePie
          data={data}
          innerRadius={0.1}
          padAngle={2}

          colors={['#8FD9B6', '#FF9999', '#FFC000']}
          arcLinkLabelsSkipAngle={0}
          enableArcLinkLabels={false}
          arcLinkLabelsColor={{ from: 'color' }}
          arcLabel={(d) => `${d.id}\n(${(d.value / totalValue * 100).toFixed(1)}%)`}
          arcLabels={(arc) => {
            const label = `${arc.id}`;
            const position = [arc.x + arc.width / 2, arc.y + arc.height / 2];
            return <text textAnchor="middle" alignmentBaseline="middle" style={{ fontSize: 10, fill: '#000000' }} transform={`translate(${position})`}>{label}</text>;
          }}
        />
        <div className={styled.header} style={{ fontSize: '0.8rem', position: 'relative' }}>소비 카테고리 통계</div>
      </div>
    </div>
  );
};

export default Piechart;
