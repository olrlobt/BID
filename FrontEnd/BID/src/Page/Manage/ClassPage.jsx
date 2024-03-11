import React, { useEffect, useState } from "react";
import styled from "./ClassPage.module.css";
import StudentList from '../../Component/Manage/StudentList'
import StudentFinData from '../../Component/Manage/StudentFinData';
import Button from "../../Component/Common/Button";
import Button2 from "../../Component/Common/Button2";
import Button3 from "../../Component/Common/Button3";
import useModal from "../../hooks/useModal";
import { getStudentListApi  } from "../../Apis/TeacherManageApis";
import { deleteStudentApi } from "../../Apis/UserApis";
import { useSelector } from "react-redux";
import { studentSelector  } from "../../Store/studentSlice";
import  useStudents  from "../../hooks/useStudents";
import { useQuery } from "@tanstack/react-query";
import { mainSelector } from "../../Store/mainSlice";

function ClassPage() {
  const [studentList, setStudentList] = useState([]);
  const [activeButton, setActiveButton] = useState('number');
  const [sortType, setSortType] = useState('number');
  const [showAdd, setShowAdd] = useState(false);
  const [showRemove, setShowRemove] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const mainClass = useSelector(mainSelector);

  const gradeNo = mainClass.no;
  const { initStudents } = useStudents();
  const students = useSelector(studentSelector);
  console.log(students)
  const { openModal } = useModal();

  useEffect(() => {
    fetchStudentList();
  }, [showAdd, onclose]);

  const fetchStudentList = () => {
    getStudentListApi(gradeNo).then((res) => {
      if (res.data !== undefined) {
        setStudentList(res.data);
        initStudents(res.data );
      }
    }).catch(error => {
      console.error("학생 목록을 불러오는 중 오류가 발생했습니다:", error);
    });
  };

  const handleStudentClick = (student) => {
    if (!isEditing) {
      setSelectedStudent(student);
    } else {
      setSelectedStudent(null);
    }
  };

  useEffect(() => {
    const firstStudentWithNumberOne = studentList.find(student => student.number === 1);
    if (firstStudentWithNumberOne) {
      setSelectedStudent(firstStudentWithNumberOne);
    } else {
      setSelectedStudent(studentList[0])
    }
  }, [studentList]);

  const handleEdit = (student) => {
    console.log("Edit student:", student);
  };

  const handleButton2Click = () => {
    setShowAdd(!showAdd);
    setShowRemove(!showRemove);
    setIsEditing(!isEditing);
  };

  const handleRemove = (no) => {
    console.log(no)
    deleteStudentApi(no, gradeNo).then((res) => {
      console.log(res);
      fetchStudentList();
    }).catch(error => {
      console.error("학생 삭제 중 오류가 발생했습니다:", error);
    });
  };

  const handleSort = (type) => {
    setActiveButton(type);
    setSortType(type);
  };

  const sortedInfo = studentList && [...studentList].sort((a, b) => {
    if (sortType === 'number') {
      return a.number - b.number;
    } else if (sortType === 'asset') {
      const assetA = parseInt(a.asset);
      const assetB = parseInt(b.asset);
      return assetB - assetA;
    }
    return 0;
  });

  const handleAddStudentComplete = () => {
    setIsEditing(true); // 학생 추가 후 편집 모드로 전환
  };

  return (
    <div className={styled.section}>
      <div className={styled.topRightButtons}>
        {showAdd && (
          <Button3
            text="학생 추가"
            onClick={() =>
              openModal({ 
              type: "addStudent", 
              props: ["학생 등록", handleAddStudentComplete],
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
      <div className={styled.studentData}>
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
              students={sortedInfo}
              handleStudentClick={handleStudentClick}
              handleRemove={handleRemove}
              showRemove={showRemove}
              isEditing={isEditing}
              handleEdit={handleEdit}
            />
          </table>
        </div>
        <div
          className={`${styled.studentFinDataContainer} ${styled.studentFinDataTable}`}
        >
          {selectedStudent && !isEditing && studentData.length > 0 && (
            <StudentFinData
              studentData={studentData.find((v) => v.no === selectedStudent.no)}
            />
          )}
        </div>
      </div>
    </div>
  );
}

export default ClassPage;
