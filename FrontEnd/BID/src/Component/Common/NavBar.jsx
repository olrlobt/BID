import React from "react";
import styled from "./NavBar.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHouse,
  faPerson,
  faPeopleArrows,
  faCoins,
  faPiggyBank,
  faDice,
  faSchool,
  faBars,
} from "@fortawesome/free-solid-svg-icons";
import Logo from "../../Asset/Image/logo.png";
import { Link, useLocation } from "react-router-dom/dist";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";

export default function NavBar() {
  const location = useLocation();
  const teacherInfo = useSelector(userSelector);
  console.log(teacherInfo);
  return (
    <nav>
      <section className={styled.logoArea}>
        <img src={Logo} alt="logo" />
        <div>
          <div>{teacherInfo.data.adminInfo.schoolName}</div>
          <div className={styled.classInfo}>
            {/* {teacherInfo.data.adminInfo.}학년 {userInfo.classRoom}반 */}
          </div>
        </div>
      </section>
      <ul>
        <Link to="/">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faHouse}
              className={`${styled.navImage} ${
                location.pathname === "/" ? styled.clicked : ""
              }`}
            />
            <span>HOME</span>
          </li>
        </Link>
        <Link to="/bid">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/bid" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faPeopleArrows}
              className={`${styled.navImage} ${
                location.pathname === "/bid" ? styled.clicked : ""
              }`}
            />
            <span>경매</span>
          </li>
        </Link>
        <Link to="/class">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/class" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faPerson}
              className={`${styled.navImage} ${
                location.pathname === "/class" ? styled.clicked : ""
              }`}
            />
            <span>학생</span>
          </li>
        </Link>
        <Link to="/reward">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/reward" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faCoins}
              className={`${styled.navImage} ${
                location.pathname === "/reward" ? styled.clicked : ""
              }`}
            />
            <span>리워드</span>
          </li>
        </Link>
        <Link to="/bank">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/bank" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faPiggyBank}
              className={`${styled.navImage} ${
                location.pathname === "/bank" ? styled.clicked : ""
              }`}
            />
            <span>은행</span>
          </li>
        </Link>
        <Link to="/game">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/game" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faDice}
              className={`${styled.navImage} ${
                location.pathname === "/game" ? styled.clicked : ""
              }`}
            />
            <span>자리 뽑기</span>
          </li>
        </Link>
        <Link to="/for-student">
          <li
            className={`${styled.navLi} ${
              location.pathname === "/student-version" ? styled.active : ""
            }`}
          >
            <FontAwesomeIcon
              icon={faSchool}
              className={`${styled.navImage} ${
                location.pathname === "/student-version" ? styled.clicked : ""
              }`}
            />
            <span>학생 버전</span>
          </li>
        </Link>
      </ul>
      <Link
        to={`/classlist/${teacherInfo.data.adminInfo.userNo}`}
        className={styled.view}
      >
        <FontAwesomeIcon icon={faBars} />
        <span>전체학급목록</span>
      </Link>
    </nav>
  );
}
