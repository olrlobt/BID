import React, { useEffect, useState } from "react";
import styled from "./ClassList.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faStar } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import {
  deleteClass,
  editMainClass,
  getGrades,
} from "../../Apis/ClassManageApis";
export default function ClassList() {
  const location = useLocation();
  const navigate = useNavigate();
  const [editedClassList, setEditedClassList] = useState([]);

  const { data: classList } = useQuery({
    queryKey: ["ClassList"],
    queryFn: () =>
      getGrades().then((res) => {
        setEditedClassList(res.data);
        return res.data;
      }),
  });

  // x 버튼 클릭 핸들러
  const handleDeleteClass = (index) => {
    const updatedClassList = [...editedClassList];
    const deletedClass = editedClassList[index];
    if (deletedClass.main) {
      alert("메인 학급은 삭제할 수 없습니다.");
    } else {
      const deleteAlert = window.confirm(
        "삭제하신 학급은 다시 복구할 수 없습니다. 그래도 삭제하시겠습니까?"
      );
      if (deleteAlert) {
        updatedClassList.splice(index, 1); // 해당 인덱스의 요소 제거
        // 인덱스 대신 no
        deleteClass(deletedClass.no);
        setEditedClassList(updatedClassList); // 업데이트된 목록으로 상태 업데이트
      }
    }
  };

  // 메인 학급의 no만 던져줌
  // {no : 1}
  const handleCompleteEditing = () => {
    const mainClass = editedClassList.filter(
      (eachClass) => eachClass.main === true
    );

    editMainClass(mainClass[0].no);
  };

  const handleStar = (index) => {
    const updatedClassList = editedClassList.map((item, i) => {
      if (i === index) {
        return { ...item, main: true };
      } else {
        return { ...item, main: false };
      }
    });

    setEditedClassList(updatedClassList);
  };

  useEffect(() => {
    console.log(editedClassList);
  }, [classList, editedClassList]);
  return (
    <main className={styled.classList}>
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
          {editedClassList &&
            editedClassList.map((value) => (
              <li
                className={
                  value.main
                    ? `${styled.eachClass} ${styled.classMain}`
                    : `${styled.eachClass}`
                }
                onClick={() => navigate("/", { state: { schoolInfo: value } })}
              >
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
            {editedClassList &&
              editedClassList.map((value, index) => (
                <li className={styled.mEachClass}>
                  <span>{value.createdAt}년도 </span>
                  {/* 로그인 했을 때 사용자 정보 받아와야 함 */}
                  <span>{value.schoolName} </span>
                  <span>{value.year}학년 </span>
                  <span>{value.classRoom}반</span>
                  <button
                    onClick={() => handleStar(index)}
                    className={
                      value.main
                        ? `${styled.starBtn} ${styled.mainTrue}`
                        : `${styled.starBtn}`
                    }
                  >
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
            <button className={styled.complete} onClick={handleCompleteEditing}>
              편집 완료
            </button>
          </Link>
        </React.Fragment>
      )}
    </main>
  );
}
