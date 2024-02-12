import React from 'react';
import { useEffect, useState } from 'react';
import BarChart from '../FinData/BarChart';
import PieChart from '../FinData/PieChart';
import Coupon from '../FinData/Coupon';
import AttendRec from '../FinData/AttendRec';
import CalendarChart from '../FinData/CalendarChart';
import styled from './StudentFinData.module.css';
import { useQuery } from '@tanstack/react-query';
import { viewStudentDetail } from '../../Apis/TeacherManageApis';

const StudentFinData = ({ student }) => {
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

  const { data: studentData } = useQuery({
    queryKey: ['StudentFinData', `studentNo_${student.name}`],
    queryFn: () =>
      viewStudentDetail(1, student.no, '2024-02-01', '2024-02-28')
        .then((res) => {
          return res.data;
        })
        .catch((e) => console.log(e)),
  });

  useEffect(() => {
    if (studentData) {
      const {
        savingNo,
        savingDepositPeriod,
        savingInterestRate,
        savingCurrentPeriod,
        savingCurrentPrice,
        savingResultPrice,
      } = studentData;
      setAttendanceDays([
        studentData.attendanceMonday,
        studentData.attendanceTuesday,
        studentData.attendanceWednesday,
        studentData.attendanceThursday,
        studentData.attendanceFriday,
      ]);
      setCategoryData((prevCategoryData) => {
        return prevCategoryData.map((category) => {
          switch (category.id) {
            case '간식':
              return { ...category, value: studentData.snackSum };
            case '학습':
              return { ...category, value: studentData.learningSum };
            case '쿠폰':
              return { ...category, value: studentData.couponSum };
            case '대포':
              return { ...category, value: studentData.gameSum };
            case '기타':
              return { ...category, value: studentData.etcSum };
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
    }
  }, [studentData]);

  return (
    studentData && (
      <div className={styled.dashboardContainer}>
        {console.log(studentData)}
        <div className={styled.chartsContainer}>
          <BarChart savingData={savingData} />
          <PieChart data={categoryData} />
          <Coupon data={studentData.couponsResponses} />
        </div>
        <div className={styled.additionalChartsContainer}>
          <AttendRec
            className={styled.AttendRec}
            attandance={attandanceDays}
            ball={studentData.ballCount}
          />
          <CalendarChart />
        </div>
      </div>
    )
  );
};

export default StudentFinData;
