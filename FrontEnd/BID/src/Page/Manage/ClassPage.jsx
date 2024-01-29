import React, { useEffect, useState, useRef } from "react";
import styled from "./ClassPage.module.css";
import StudentList from "../../Component/Manage/StudentList";
import StudentFinData from "../../Component/Manage/StudentFinData";
import StudentAdd from "../../Component/Manage/StudentAdd";
import Button from "../../Component/Common/Button";
import Button2 from "../../Component/Common/Button2";
import Button3 from "../../Component/Common/Button3";
import Modal from "react-modal";

function ClassPage() {
  const [info, setInfo] = useState([]);
  const [activeButton, setActiveButton] = useState("id"); // 기본 정렬 기준은 번호(id)로 설정
  const [sortType, setSortType] = useState("id"); // 정렬 기준 추가
  const [showAdd, setShowAdd] = useState(false); // New state to toggle Add Student button
  const [showRemove, setShowRemove] = useState(false); // New state to toggle Add Student button
  const [modalIsOpen, setModalIsOpen] = useState(false); // 모달 표시 여부를 제어하기 위한 새로운 상태
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  const handleStudentClick = (student) => {
    if (!isEditing) {
      setSelectedStudent(student);
    } else {
      setSelectedStudent(null);
    }
  };

  const nextId = useRef(100);

  const dummyData = [
    { id: 1, name: "백지윤", bid: "5,678" },
    { id: 2, name: "유현지", bid: "4,321" },
    { id: 3, name: "배민지", bid: "9,321" },
    { id: 4, name: "이현진", bid: "92,394" },
    { id: 5, name: "이승헌", bid: "321" },
    { id: 6, name: "김예림", bid: "54,321" },
  ];

  useEffect(() => {
    setInfo(dummyData);
    // 초기 목록에서 최대 id 찾기
    const maxId = dummyData.reduce(
      (max, student) => Math.max(max, student.id),
      0
    );
    nextId.current = maxId + 1;

    // 첫 번째 학생을 기본적으로 선택
    setSelectedStudent(dummyData[0]);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  const handleAddClick = () => {
    setModalIsOpen(true); // 모달을 엽니다.
  };

  const handleButton2Click = () => {
    console.log("Button2 클릭됨");
    setShowAdd(!showAdd);
    setShowRemove(!showRemove);
    setIsEditing(!isEditing);
    if (!showRemove) {
      setSelectedStudent(null);
    }
  };

  const handleButton3Click = () => {
    console.log("Button3 클릭됨");
    handleAddClick();
  };

  const handleModalClose = () => {
    setModalIsOpen(false); // 모달을 닫습니다.
  };

  useEffect(() => {
    setActiveButton("id");
  }, []);

  const handleSave = (data) => {
    if (data.id) {
      setInfo((prevInfo) =>
        prevInfo.map((row) =>
          data.id === row.id ? { ...row, name: data.name, bid: data.bid } : row
        )
      );
    } else {
      setInfo((prevInfo) =>
        prevInfo.concat({
          id: nextId.current,
          name: data.name,
          bid: data.bid,
        })
      );
      nextId.current += 1;
    }
  };

  const handleRemove = (id) => {
    setInfo((prevInfo) => {
      const updatedInfo = prevInfo.filter((item) => item.id !== id);
      // 삭제된 후 목록에서 최대 id 찾기
      const maxId = updatedInfo.reduce(
        (max, student) => Math.max(max, student.id),
        0
      );
      nextId.current = maxId + 1;
      return updatedInfo;
    });
  };

  const handleSort = (type) => {
    if (type !== activeButton) {
      setActiveButton(type);
      setSortType(type);
    }
  };

  const sortedInfo = [...info].sort((a, b) => {
    if (sortType === "id") {
      return a.id - b.id;
    } else if (sortType === "bid") {
      return (
        parseInt(a.bid.replace(/,/g, "")) - parseInt(b.bid.replace(/,/g, ""))
      );
    }
    return 0;
  });

  return (
    <div className={styled.section}>
      <div className={styled.topRightButtons}>
        {showAdd && <Button3 text="학생 추가" onClick={handleButton3Click} />}
        <div className={styled.buttonSpacing} />
        <Button2 text="학생 편집" onClick={handleButton2Click} />
      </div>
      <div className={styled.orderlist}>
        <Button
          text="번호"
          onClick={() => handleSort("id")}
          active={activeButton === "id"}
        />
        <Button
          text="총 비드"
          onClick={() => handleSort("bid")}
          active={activeButton === "bid"}
        />
      </div>
      <div style={{ display: "flex" }}>
        <div className={styled.orderTableContainer}>
          <table className={styled.orderTable}>
            <thead>
              <tr>
                <th>번호</th>
                <th>이름</th>
                <th>총 비드</th>
                {isEditing && showRemove && (
                  <>
                    <th>비밀번호 초기화</th>
                    <th>제거</th>
                  </>
                )}
              </tr>
            </thead>
            <StudentList
              info={sortedInfo}
              handleStudentClick={handleStudentClick}
              handleRemove={handleRemove}
              showRemove={showRemove}
              isEditing={isEditing}
            />
          </table>
          {selectedStudent && (
            <div
              className={`${styled.studentFinDataContainer} ${styled.studentFinDataTable}`}
            >
              <StudentFinData student={selectedStudent} />
            </div>
          )}
        </div>
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={handleModalClose}
        contentLabel="Add Student Modal"
      >
        <StudentAdd onSaveData={handleSave} onCloseModal={handleModalClose} />
      </Modal>
    </div>
  );
}

export default ClassPage;
