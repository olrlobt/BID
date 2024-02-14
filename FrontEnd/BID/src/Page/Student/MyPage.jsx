import React, {useState} from "react";
// import BarChart from "../../Component/FinData/BarChart";
import PieChart from "../../Component/FinData/PieChart";
// import Coupon from "../../Component/FinData/Coupon";
// import AttendRec from '../../Component/FinData/AttendRec'
import CalendarChart from "../../Component/FinData/CalendarChart";
import styled from './MyPage.module.css';
import { useSelector } from "react-redux";
import { modelSelector } from "../../Store/modelSlice";
import Button2 from "../../Component/Common/Button2";
import { useMutation } from "@tanstack/react-query";
import { stuChangePwdApi } from '../../Apis/ModelApis';
import WriterButton from "../../Component/Bid/WriterButton";

function MyPage() {

    const myInfo = useSelector(modelSelector);
    console.log(myInfo)
    const [isTeacher, setIsTeacher] = useState(false);

    const stuChangePwdQuery = useMutation({
        mutationKey: ['stuChangePwd'],
        mutationFn: () => stuChangePwdApi(),
        onSuccess: (res) => {
          console.log(res);
        },
        onError: (error) => {
          console.log(error);
        },
      });

    const changeWriter = (writer) => {
    if(isTeacher && writer==='teacher') {}
    else if(!isTeacher && writer==='student') {}
    else {setIsTeacher(!isTeacher);}
  }

    
    
    const categoryData = [
        { category: '식비', percentage: 30 },
        { category: '교통', percentage: 20 },
        // 다른 카테고리들도 추가
      ];
      
    return(
        <div className={styled.section}>
            <div className={styled.dashboardContainer}>
                <div className={styled.myInfo}>
                    <img className={styled.img} src={myInfo.model.profileImgUrl} alt="이미지" />
                    <div className={styled.schoolName}>
                        {myInfo.model.schoolName}
                    </div>
                    <div className={styled.name}>
                        {myInfo.model.name}
                    </div>
                    <div className={styled.asset}>
                        총 자산을 <span>4227비드</span> 모았어요!
                    </div>
                    <div className={styled.buttonContainer}>
                        <Button2 text="비밀번호 변경"  />
                    </div>
                </div>
                <div className={styled.selection}>
                    <WriterButton
                        onClick = {() => changeWriter('teacher')}
                        text = {'선생'}
                        active = { isTeacher }
                    />
                    <WriterButton
                        onClick = {() => changeWriter('student')}
                        text = {'학생'}
                        active = { !isTeacher }
                    />
                </div>
                <div className={styled.chartsContainer}>
                    {/* <BarChart /> */}
                    <PieChart data={categoryData} />
                    {/* <Coupon /> */}
                </div>
            <div className={styled.additionalChartsContainer}>
                {/* <AttendRec className={styled.AttendRec} /> */}
                <CalendarChart />
            </div>
        </div>    
    </div>
    ) 
}

export default MyPage;


