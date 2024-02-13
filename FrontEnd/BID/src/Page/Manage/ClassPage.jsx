import React, { useEffect, useState } from 'react';
import styled from './ClassPage.module.css';
import StudentList from '../../Component/Manage/StudentList';
import StudentFinData from '../../Component/Manage/StudentFinData';
import Button from '../../Component/Common/Button';
import Button2 from '../../Component/Common/Button2';
import Button3 from '../../Component/Common/Button3';
import useModal from '../../hooks/useModal';
import { getStudentListApi } from '../../Apis/StudentApis';
import { useSelector } from 'react-redux';
import { studentSelector } from '../../Store/studentSlice';
import useStudents from '../../hooks/useStudents';
import { useQuery } from '@tanstack/react-query';
import { useLocation } from 'react-router-dom';

function ClassPage() {
  const [studentList, setStudentList] = useState([]);
  const [activeButton, setActiveButton] = useState('number'); // 기본 정렬 기준은 번호(id)로 설정
  const [showAdd, setShowAdd] = useState(false); // New state to toggle Add Student button
  const [showRemove, setShowRemove] = useState(false); // New state to toggle Add Student button
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const location = useLocation();
  const gradeNo = location.state.schoolInfo.no;

  const { initStudents } = useStudents();
  const students = useSelector(studentSelector);

  /** 학생 목록 쿼리 */
  useQuery({
    queryKey: ['studentList'],
    queryFn: () =>
      getStudentListApi(gradeNo).then((res) => {
        if (res.data !== undefined) {
          const sortedInfo = res.data.sort((a, b) => a.number - b.number);
          setStudentList(sortedInfo);
        }
        initStudents({ students: studentList });
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

  useEffect(() => {
    setSelectedStudent(studentList[0]);
  }, [studentList]);

  const handleEdit = (student) => {
    console.log('Edit student:', student);
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

  const handleRemove = (no) => {
    setSelectedStudent(null);
  };

  const handleSort = (type) => {
    setActiveButton(type);
    const sortedInfo = studentList.sort((a, b) => {
      if (type === 'number') {
        return a.number - b.number;
      } else if (type === 'asset') {
        return b.asset - a.asset;
      }
      return 0;
    });
    setStudentList(sortedInfo);
  };

  return (
    <div className={styled.section}>
      <div className={styled.topRightButtons}>
        {showAdd && (
          <Button3
            text="학생 추가"
            onClick={() =>
              openModal({
                type: 'addStudent',
                props: ['학생 등록'],
              })
            }
          />
        )}
        <div className={styled.buttonSpacing} />
        <Button2 text="학생 편집" onClick={handleButton2Click} />
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
              students={studentList}
              handleStudentClick={handleStudentClick}
              handleRemove={handleRemove}
              showRemove={showRemove}
              isEditing={isEditing}
              handleEdit={handleEdit}
            />
          </table>
        </div>
        {selectedStudent && (
          <div className={styled.studentFinDataContainer}>
            <StudentFinData student={selectedStudent} />
          </div>
        )}
      </div>
    </div>
  );
}

export default ClassPage;
