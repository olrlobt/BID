import React from "react";
import BarChart from "../FinData/BarChart";
import PieChart from "../FinData/PieChart";
import Coupon from "../FinData/Coupon";
import AttendRec from '../FinData/AttendRec'
import CalendarChart from "../FinData/CalendarChart";
import styled from './StudentFinData.module.css';

const StudentFinData = ({ student }) => {
  // 가상의 데이터

  const categoryData = [
    { category: '식비', percentage: 30 },
    { category: '교통', percentage: 20 },
    // 다른 카테고리들도 추가
  ];



  return (
  
    <div className={styled.dashboardContainer}>
      <div className={styled.chartsContainer}>
      <BarChart />
      <PieChart data={categoryData} />
      <Coupon />
      {student.name}
      </div>
      <div className={styled.additionalChartsContainer}>
      <AttendRec className={styled.AttendRec} />
      <CalendarChart />
      </div>
    </div>
  );
};

export default StudentFinData;
