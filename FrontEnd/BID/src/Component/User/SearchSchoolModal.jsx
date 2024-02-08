import React, { useState } from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import styled from './SearchSchoolModal.module.css';

export default function SearchSchoolModal({ onClose, ...props }) {
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  // Dummy data
  const dummyData = [
    { name: '해오름초등학교', region: '대구광역시' },
    { name: '해오름초등학교', region: '경기도' },
    { name: '해오름초등학교', region: '경상북도' },
    { name: '해오름초등학교', region: '전라북도' },
    { name: '신백현초등학교', region: '경기도' },
  ];

  // Function to handle the search action
  const handleSearch = () => {
    const filteredData = dummyData.filter(school =>
      school.name.includes(searchQuery)
    );
    setSearchResults(filteredData);
  };

  // Function to handle input change
  const handleInputChange = (e) => {
    setSearchQuery(e.target.value);
  };

  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <div>
        <input
          type="text"
          placeholder="학교 검색"
          value={searchQuery}
          onChange={handleInputChange}
        />
        <button onClick={handleSearch}>검색</button>
      </div>
      <div className={styled.tableContainer}>
        <table>
          <thead>
            <tr>
              <th>학교 이름</th>
              <th>지역</th>
            </tr>
          </thead>
          <tbody className={styled.tableBody}>
            {(searchQuery.trim() === '' ? dummyData : searchResults).map((school, index) => (
              <tr key={index}>
                <td>{school.name}</td>
                <td>{school.region}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <SubmitButton
        text='선택'
        width='100%'
        height='5vh'
        fontSize='2vw'
      />
    </Modal>
  );
}
