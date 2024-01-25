import React from "react";
import BarChart from "../FinData/BarChart";
import Piechart from "../FinData/PieChart";
import styled from './StudentFinData.module.css';

const StudentFinData = ({ student }) => {
    return (
        <>
            <div className={styled.dashboardContainer}>
                <Piechart />
                {/* 추가적인 차트나 정보를 여기에 추가 */}
            </div>
            {/* 학생 상세 정보를 여기에 추가할 수 있습니다 */}
        </>
    );
};

export default StudentFinData;