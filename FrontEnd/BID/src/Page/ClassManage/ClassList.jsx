import React from 'react';
import styled from './ClassList.module.css';
import { useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark, faStar } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
export default function ClassList() {
  const location = useLocation();
  // 여기 query 던져서 캐싱
  const classList = [
    {
      grade: 4,
      class: 1,
      year: 2024,
    },
    {
      grade: 6,
      class: 3,
      year: 2023,
    },
    {
      grade: 5,
      class: 2,
      year: 2022,
    },
    {
      grade: 6,
      class: 1,
      year: 2021,
    },
    {
      grade: 4,
      class: 5,
      year: 2022,
    },
  ];

  return (
    <main className={styled.classList}>
      <section>
        <div className={styled.classTitle}>학급 목록</div>
        {location.pathname === '/classlist/:teacherId/' ? (
          ''
        ) : (
          <Link to="/classlist/:teacherId/make/">
            <button className={styled.addClass}>학급 생성</button>
          </Link>
        )}
      </section>
      {/* 편집버전 아닐 때 */}
      {location.pathname === '/classlist/:teacherId/' ? (
        <ul className={styled.classes}>
          {classList.map((value) => (
            <li className={styled.eachClass}>
              <span>{value.year}년도 </span>
              {/* 로그인 했을 때 사용자 정보 받아와야 함 */}
              <span>해오름초등학교 </span>
              <span>{value.grade}학년 </span>
              <span>{value.class}반</span>
            </li>
          ))}
        </ul>
      ) : (
        // 편집 버전일 떄
        <React.Fragment>
          <ul className={styled.mClasses}>
            {classList.map((value) => (
              <li className={styled.mEachClass}>
                <span>{value.year}년도 </span>
                {/* 로그인 했을 때 사용자 정보 받아와야 함 */}
                <span>해오름초등학교 </span>
                <span>{value.grade}학년 </span>
                <span>{value.class}반</span>
                <button className={styled.starBtn}>
                  <FontAwesomeIcon icon={faStar} />
                </button>
                <button className={styled.xBtn}>
                  <FontAwesomeIcon icon={faXmark} />
                </button>
              </li>
            ))}
          </ul>
          {/* 클릭시 편집상황 반영 후 편집 완료 */}
          <Link to="/classlist/:teacherId/">
            <button className={styled.complete}>편집 완료</button>
          </Link>
        </React.Fragment>
      )}
    </main>
  );
}
