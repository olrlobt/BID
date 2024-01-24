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

export default function NavBar() {
  const location = useLocation();

  return (
    <nav>
      <section className={styled.logoArea}>
        <img src={Logo} alt="logo" />
        <div>
          <div>해오름초등학교</div>
          <div className={styled.classInfo}>6학년 3반</div>
        </div>
      </section>
      <ul>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/" ? styled.active : ""
          }`}
        >
          <Link to="/">
            <FontAwesomeIcon
              icon={faHouse}
              className={`${styled.navImage} ${
                location.pathname === "/" ? styled.clicked : ""
              }`}
            />
            <span>HOME</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/bid" ? styled.active : ""
          }`}
        >
          <Link to="/bid">
            <FontAwesomeIcon
              icon={faPeopleArrows}
              className={`${styled.navImage} ${
                location.pathname === "/bid" ? styled.clicked : ""
              }`}
            />
            <span>경매</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/student" ? styled.active : ""
          }`}
        >
          <Link to="/student">
            <FontAwesomeIcon
              icon={faPerson}
              className={`${styled.navImage} ${
                location.pathname === "/student" ? styled.clicked : ""
              }`}
            />
            <span>학생</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/reward" ? styled.active : ""
          }`}
        >
          <Link to="/reward">
            <FontAwesomeIcon
              icon={faCoins}
              className={`${styled.navImage} ${
                location.pathname === "/reward" ? styled.clicked : ""
              }`}
            />
            <span>리워드</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/bank" ? styled.active : ""
          }`}
        >
          <Link to="/bank">
            <FontAwesomeIcon
              icon={faPiggyBank}
              className={`${styled.navImage} ${
                location.pathname === "/bank" ? styled.clicked : ""
              }`}
            />
            <span>은행</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/game" ? styled.active : ""
          }`}
        >
          <Link to="/game">
            <FontAwesomeIcon
              icon={faDice}
              className={`${styled.navImage} ${
                location.pathname === "/game" ? styled.clicked : ""
              }`}
            />
            <span>자리 뽑기</span>
          </Link>
        </li>
        <li
          className={`${styled.navLi} ${
            location.pathname === "/student-version" ? styled.active : ""
          }`}
        >
          <Link to="for-student">
            <FontAwesomeIcon
              icon={faSchool}
              className={`${styled.navImage} ${
                location.pathname === "/student-version" ? styled.clicked : ""
              }`}
            />
            <span>학생 버전</span>
          </Link>
        </li>
      </ul>

      <Link to="/all-class" className={styled.view}>
        <FontAwesomeIcon icon={faBars} />
        <span>전체학급목록</span>
      </Link>
    </nav>
  );
}
