import React, { useEffect, useState } from "react";
import styled from "./ClassPage.module.css";
import StudentList from '../../Component/Manage/StudentList'
import StudentFinData from '../../Component/Manage/StudentFinData';
import Button from "../../Component/Common/Button";
import Button2 from "../../Component/Common/Button2";
import Button3 from "../../Component/Common/Button3";
import useModal from "../../hooks/useModal";
import { getStudentListApi } from "../../Apis/StudentApis";
import { useSelector } from "react-redux";
import { studentSelector  } from "../../Store/studentSlice";
import  useStudents  from "../../hooks/useStudents";
import { useQuery } from "@tanstack/react-query";



function ClassPage() {
  const [studentList, setStudentList] = useState([]);
  const [activeButton, setActiveButton] = useState('number'); // 기본 정렬 기준은 번호(id)로 설정
  const [sortType, setSortType] = useState('number'); // 정렬 기준 추가
  const [showAdd, setShowAdd] = useState(false); // New state to toggle Add Student button
  const [showRemove, setShowRemove] = useState(false); // New state to toggle Add Student button
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  const { initStudents } = useStudents();
  const students = useSelector(studentSelector);
  

  /** 학생 목록 쿼리 */
  useQuery({
    queryKey: ['studentList'],
    queryFn: () => 
      getStudentListApi().then((res) => {
        if(res.data !== undefined) 
        { setStudentList(res.data); }
        initStudents({ students: studentList });
        console.log(res.data)
        return res.data;
    }),
  });

  const handleStudentClick = (student) => {
    if (!isEditing) {
      setSelectedStudent(student);
    } else {
      setSelectedStudent(null);
    }
  };

  
  const { openModal } = useModal();
  
  const dummyData = [
    { no:1, number: 1, name: '백지윤', asset: '5,678' },
    { no:2, number: 2, name: '유현지', asset: '4,321' },
    { no:3, number: 3, name: '배민지', asset: '9,321' },
    { no:4, number: 4, name: '이현진', asset: '92,394' },
    { no:5, number: 8, name: '이승헌', asset: '321' },
    { no:6, number: 6, name: '김예림', asset: '54,321' },
  ];

  useEffect(() => {
    setSelectedStudent(studentList[0]); 

  }, [studentList]);

  

  const handleEdit = (student) => {
    console.log("Edit student:", student);
  };


  const handleButton2Click = () => {
    setShowAdd(!showAdd);
    setShowRemove(!showRemove);
    setIsEditing(!isEditing);
    if (!showRemove) {
      setSelectedStudent(null);
    } else if (studentList.length > 0) {
      setSelectedStudent(studentList[0]);
    }
  };


  
  useEffect(() => {
    setActiveButton('number');
  }, []);


  const handleRemove = (no) => {
    setSelectedStudent(null);
  };
  

  const handleSort = (type) => {
    if (type !== activeButton) {
      setActiveButton(type);
      setSortType(type);
    }
  };


  
  const sortedInfo = students && [...students].sort((a, b) => {
    if (sortType === 'number') {
      return a.number - b.number;
    } else if (sortType === 'asset') {
      return parseInt(a.asset.replace(/,/g, '')) - parseInt(b.asset.replace(/,/g, ''));
    }
    return 0;
  });

  
  return (
    <div className={styled.section}>
      <div className={styled.topRightButtons}>
        {showAdd && (
          <Button3
            text="학생 추가"
            onClick={() =>
              openModal({ 
              type: "addStudent", 
              props: ["학생 등록"] 
              })
            }
          />
        )}
        <div className={styled.buttonSpacing} />
        <Button2
          text="학생 편집"
          onClick={handleButton2Click}
        />
      </div>
      <div className={styled.orderlist}>
        <Button
          text="번호"
          onClick={() => handleSort('number')}
          active={activeButton === 'number'}
        />
        <Button
          text="총 비드"
          onClick={() => handleSort('asset')}
          active={activeButton === 'asset'}
        />
      </div>
      <div style={{ display: 'flex' }}>
      <div className={styled.orderTableContainer}>
        <table className={styled.orderTable}>
          <thead>
            <tr>
              <th>번호</th>
              <th>이름</th>
              <th>총 비드</th>
              {isEditing && showRemove && (
                  <>
                    <th></th>
                    <th></th>
                    <th></th>
                  </>
                )}
            </tr>
          </thead>
          <StudentList
              info={sortedInfo}
              students={studentList.concat(dummyData)}
              handleStudentClick={handleStudentClick}
              handleRemove={handleRemove}
              showRemove={showRemove}
              isEditing={isEditing}
              handleEdit={handleEdit}
            />
        </table>
        { (selectedStudent || dummyData.length > 0)  && (
            <div className={`${styled.studentFinDataContainer} ${styled.studentFinDataTable}`}>
              <StudentFinData student={selectedStudent || dummyData[0]} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ClassPage;
