import React from "react";
import BarChart from "../../Component/FinData/BarChart";
import PieChart from "../../Component/FinData/PieChart";
import Coupon from "../../Component/FinData/Coupon";
import AttendRec from '../../Component/FinData/AttendRec'
import CalendarChart from "../../Component/FinData/CalendarChart";
import styled from './MyPage.module.css';

function MyPage() {

    const categoryData = [
        { category: '식비', percentage: 30 },
        { category: '교통', percentage: 20 },
        // 다른 카테고리들도 추가
      ];
      
    return(
        <div className={styled.section}>
            <div className={styled.dashboardContainer}>
                <div className={styled.chartsContainer}>
                    <BarChart />
                    <PieChart data={categoryData} />
                    <Coupon />
                </div>
            <div className={styled.additionalChartsContainer}>
                <AttendRec className={styled.AttendRec} />
                <CalendarChart />
            </div>
        </div>    
    </div>
    ) 
}

export default MyPage;


