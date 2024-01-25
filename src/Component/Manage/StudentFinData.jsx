import React from "react";
import BarChart from "../FinData/BarChart";
import PieChart from "../FinData/PieChart";
import styled from './StudentFinData.module.css';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';
const localizer = momentLocalizer(moment);


const StudentFinData = ({ student }) => {
  // 가상의 데이터
  const savingsData = {
    total: 800,
    saved: 450
  };

  const categoryData = [
    { category: '식비', percentage: 30 },
    { category: '교통', percentage: 20 },
    // 다른 카테고리들도 추가
  ];

  const couponData = [
    { name: '노래 10분 틀기', count: 4 },
    { name: '급식 먼저 먹기', count: 2 },
    // 다른 쿠폰들도 추가
  ];

  const attendanceData = [
    { day: '월', status: 'O' },
    { day: '화', status: 'X' },
    // 다른 요일들도 추가
  ];

  return (
    <div className={styled.dashboardContainer}>
      <BarChart />

      <div className={styled.categoryContainer}>
        <h2>소비 카테고리 통계</h2>
        <PieChart data={categoryData} />
      </div>

      <div className={styled.couponContainer}>
        <h2>쿠폰 소유 현황</h2>
        {couponData.map((coupon, index) => (
          <div key={index} className={styled.coupon}>
            <div className={styled.couponIcon}></div>
            <p>{`${coupon.name} ${coupon.count}장`}</p>
          </div>
        ))}
      </div>

      <div className={styled.attendanceContainer}>
        <h2>출석부</h2>
        <div className={styled.attendance}>
          {attendanceData.map((day, index) => (
            <div key={index} className={day.status === 'O' ? styled.present : styled.absent}>
              {day.day}
            </div>
          ))}
        </div>
      </div>

      <div className={styled.calendarContainer}>
        <h2>캘린더</h2>
        <Calendar
          localizer={localizer}
          events={[]}
          startAccessor="start"
          endAccessor="end"
        />
      </div>
    </div>
  );
};

export default StudentFinData;
