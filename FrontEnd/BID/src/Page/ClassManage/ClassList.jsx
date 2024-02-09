import React, { useState } from "react";
import styled from "./ClassList.module.css";
import { useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faStar } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getGrades } from "../../Apis/ClassManageApis";
export default function ClassList() {
  const location = useLocation();

  const { data: classList } = useQuery({
    qureyKey: ["classList"],
    queryFn: () =>
      getGrades().then((res) => {
        return res.data;
      }),
  });

  // x 버튼 클릭 시 상태를 업데이트하기 위한 useState 훅
  const [editedClassList, setEditedClassList] = useState([]);

  // x 버튼 클릭 핸들러
  const handleDeleteClass = (index) => {
    const updatedClassList = [...editedClassList];
    updatedClassList.splice(index, 1); // 해당 인덱스의 요소 제거
    // 인덱스 대신 no
    // deleteClass(index);
    console.log(index);
    console.log(updatedClassList);
    setEditedClassList(updatedClassList); // 업데이트된 목록으로 상태 업데이트
  };

  return (
    <main className={styled.classList}>
      {console.log(classList)}
      <section>
        <div className={styled.classTitle}>학급 목록</div>
        {location.pathname === "/classlist/:teacherId/" ? (
          ""
        ) : (
          <Link to="/classlist/:teacherId/make/">
            <button className={styled.addClass}>학급 생성</button>
          </Link>
        )}
      </section>
      {/* 편집버전 아닐 때 */}
      {location.pathname === "/classlist/:teacherId/" ? (
        <ul className={styled.classes}>
          {classList &&
            classList.map((value) => (
              <li className={styled.eachClass}>
                <span>{value.createdAt}년도 </span>
                <span>{value.schoolName} </span>
                <span>{value.year}학년 </span>
                <span>{value.classRoom}반</span>
              </li>
            ))}
        </ul>
      ) : (
        // 편집 버전일 떄
        <React.Fragment>
          <ul className={styled.mClasses}>
            {classList &&
              classList.map((value, index) => (
                <li className={styled.mEachClass}>
                  <span>{value.createdAt}년도 </span>
                  {/* 로그인 했을 때 사용자 정보 받아와야 함 */}
                  <span>{value.schoolName} </span>
                  <span>{value.year}학년 </span>
                  <span>{value.classRoom}반</span>
                  <button className={styled.starBtn}>
                    <FontAwesomeIcon icon={faStar} />
                  </button>
                  <button
                    className={styled.xBtn}
                    onClick={() => handleDeleteClass(index)}
                  >
                    <FontAwesomeIcon icon={faXmark} />
                  </button>
                </li>
              ))}
          </ul>
          {/* 클릭시 편집상 황 반영 후 편집 완료 */}
          <Link to="/classlist/:teacherId/">
            <button className={styled.complete}>편집 완료</button>
          </Link>
        </React.Fragment>
      )}
    </main>
  );
}
