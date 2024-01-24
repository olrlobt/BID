// ClassPage.jsx
import React, { useEffect, useState, useRef } from "react";
import styled from "./ClassPage.module.css";
import StudentList from '../../Component/Manage/StudentList'
import StudentAdd from "../../Component/Manage/StudentAdd";
import Modal from "../../Component/Manage/Modal";
import Button from "../../Component/Common/Button";

function ClassPage() {
  const [info, setInfo] = useState([])
  const [selected, setSelected] = useState('')
  const [modalOn, setModalOn] = useState(false)
  const [activeButton, setActiveButton] = useState('id'); // 기본 정렬 기준은 번호(id)로 설정
  const [sortType, setSortType] = useState('id'); // 정렬 기준 추가

  const nextId = useRef(6)

  const dummyData = [
    { id: 1, name: '백지윤', bid: '5,678' },
    { id: 2, name: '유현지', bid: '4,321' },
    { id: 3, name: '배민지', bid: '9,321' },
    { id: 4, name: '이현진', bid: '2,394' },
    { id: 5, name: '이승헌', bid: '321' },
    { id: 6, name: '김예림', bid: '54,321' },
  ];

  useEffect(() => {
    setInfo(dummyData);
  }, []);

  useEffect(() => {
    // Set "번호" as the default active button when the component mounts
    setActiveButton('id');
  }, []);

  const handleSave = (data) => {
    if (data.id) {
      setInfo(
        info.map((row) =>
          data.id === row.id ? { ...row, name: data.name, bid: data.bid } : row
        )
      );
    } else {
      setInfo((info) =>
        info.concat({
          id: nextId.current,
          name: data.name,
          bid: data.bid,
        })
      );
      nextId.current += 1;
    }
  };

  const handleRemove = (id) => {
    setInfo(info => info.filter(item => item.id !== id))
  }
  const handleEdit = (item) => {
    setModalOn(true)
    const selectedData = {
      id: item.id,
      name: item.name,
      bid: item.bid,
    }
    console.log(selectedData)
    setSelected(selectedData)
  }
  const handleCancel = () => {
    setModalOn(false)
  }

  const handleEditSubmit = (item) => {
    console.log(item)
    handleSave(item)
    setModalOn(false)
  }

  const handleSort = (type) => {
    setActiveButton(type);
    setSortType(type);
  };

  const sortedInfo = [...info].sort((a, b) => {
    if (sortType === 'id') {
      return a.id - b.id;
    } else if (sortType === 'bid') {
      return parseInt(a.bid.replace(/,/g, '')) - parseInt(b.bid.replace(/,/g, ''));
    }
    return 0;
  });

  return (
    <div className={styled.section}>
      <div className={styled.orderlist}>
        <Button
          text="번호"
          onClick={() => handleSort('id')}
          active={activeButton === 'id'}
        />
        <Button
          text="총 비드"
          onClick={() => handleSort('bid')}
          active={activeButton === 'bid'}
        />
      </div>
      <div>
        <table className={styled.orderTable}>
          <thead>
            <tr>
              <th>번호</th>
              <th>이름</th>
              <th>총 비드</th>
            </tr>
          </thead>
          <StudentList
            info={sortedInfo}
            handleRemove={handleRemove}
            handleEdit={handleEdit}
          />
        </table>
        <StudentAdd onSaveData={handleSave} />
        {modalOn && (
          <Modal
            selectedData={selected}
            handleCancel={handleCancel}
            handleEditSubmit={handleEditSubmit}
          />
        )}
      </div>
    </div>
  );
}

export default ClassPage;
