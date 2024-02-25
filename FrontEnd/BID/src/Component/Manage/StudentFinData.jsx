import React from 'react';
import { useEffect, useState } from 'react';
import BarChart from '../FinData/BarChart';
import PieChart from '../FinData/PieChart';
import Coupon from '../FinData/Coupon';
import AttendRec from '../FinData/AttendRec';
import CalendarChart from '../FinData/CalendarChart';
import styled from './StudentFinData.module.css';

const StudentFinData = ({ studentData }) => {
  const studentInfo = studentData.data;
  const [attandanceDays, setAttendanceDays] = useState([
    false,
    false,
    false,
    false,
    false,
  ]);
  const [categoryData, setCategoryData] = useState([
    { id: '간식', value: 0 },
    { id: '학습', value: 0 },
    { id: '쿠폰', value: 0 },
    { id: '대포', value: 0 },
    { id: '기타', value: 0 },
  ]);

  const [savingData, setSavingData] = useState([
    { savingNo: 0 },
    { savingDepositPeriod: 0 },
    { savingCurrentPeriod: 0 },
    { savingInterestRate: 0 },
    { savingResultPrice: 0 },
    { savingCurrentPrice: 0 },
  ]);

  const [calendarInfo, setCalendarInfo] = useState([]);

  useEffect(() => {
    if (studentInfo) {
      const {
        savingNo,
        savingDepositPeriod,
        savingInterestRate,
        savingCurrentPeriod,
        savingCurrentPrice,
        savingResultPrice,
      } = studentInfo;

      setAttendanceDays([
        studentInfo.attendanceMonday,
        studentInfo.attendanceTuesday,
        studentInfo.attendanceWednesday,
        studentInfo.attendanceThursday,
        studentInfo.attendanceFriday,
      ]);
      setCategoryData((prevCategoryData) => {
        return prevCategoryData.map((category) => {
          switch (category.id) {
            case '간식':
              return { ...category, value: studentInfo.snackSum };
            case '학습':
              return { ...category, value: studentInfo.learningSum };
            case '쿠폰':
              return { ...category, value: studentInfo.couponSum };
            case '대포':
              return { ...category, value: studentInfo.gameSum };
            case '기타':
              return { ...category, value: studentInfo.etcSum };
            default:
              return category;
          }
        });
      });
      setSavingData({
        savingNo,
        savingDepositPeriod,
        savingInterestRate,
        savingCurrentPeriod,
        savingCurrentPrice,
        savingResultPrice,
      });

      setCalendarInfo(
        studentInfo.accountsResponses.map((data, index) => ({
          id: index,
          title: data.accountType,
          start: new Date(2024, new Date().getMonth(), data.day),
          end: new Date(2024, new Date().getMonth(), data.day),
          amount: data.totalPrice,
          type: data.dealType,
        }))
      );
    }
  }, [studentInfo]);

  return (
    studentData && (
      <div className={styled.dashboardContainer}>
        <div className={styled.chartsContainer}>
          <BarChart savingData={savingData} />
          <PieChart data={categoryData} />
          <Coupon data={studentInfo.couponsResponses} />
        </div>
        <div className={styled.additionalChartsContainer}>
          <AttendRec
            className={styled.AttendRec}
            attandance={attandanceDays}
            ball={studentInfo.ballCount}
          />
          <CalendarChart event={calendarInfo} />
        </div>
      </div>
    )
  );
};

export default StudentFinData;
