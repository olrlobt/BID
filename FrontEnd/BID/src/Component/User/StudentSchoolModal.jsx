import React, { useState } from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import styled from './SearchSchoolModal.module.css';
import { searchSchoolApi } from '../../Apis/UserApis';
import { useMutation } from "@tanstack/react-query";

export default function StudentSchoolModal({ onClose, ...props }) {
  console.log(props[1])
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  
  /** 학교 검색 */
  const searchSchoolQuery = useMutation({
    mutationKey: ["searchSchool"],
    mutationFn: (searchQuery) => searchSchoolApi(searchQuery),
    onSuccess: (res) => {
      console.log("Data received:", res);
      setSearchResults(res.data); // Extracting the array from the response object
    },
    onError: (error) => {
      console.log(error);
    },
  });

  // Function to handle the search action
  const handleSearch = () => {
    searchSchoolQuery.mutate(searchQuery);
  };


  // Function to handle input change
  const handleInputChange = (e) => {
    setSearchQuery(e.target.value);
  };

  // Function to handle clicking on search result
  const handleClickSearchResult = (schoolCode) => {
    console.log("Selected school code:", schoolCode);
    props[1](schoolCode)
    onClose(schoolCode); // Close the modal
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
          <tbody>
            {searchResults.map((school, index) => (
              <tr key={index} onClick={() => handleClickSearchResult(school.code)}> {/* Call handleClickSearchResult onClick */}
                <td>{school.name}</td>
                <td>{school.region}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Modal>
  );
}
