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
import { Link, useLocation, useNavigate } from "react-router-dom/dist";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";
import { mainSelector } from "../../Store/mainSlice";

export default function NavBar() {
  const location = useLocation();
  const navigate = useNavigate();
  const teacherInfo = useSelector(userSelector);
  const mainClass = useSelector(mainSelector);

  return (
    <nav>
      <section className={styled.logoArea}>
        <img src={Logo} alt="logo" />
        <div>
          <div>{teacherInfo.adminInfo.schoolName}</div>
          <div className={styled.classInfo}>
            {mainClass.year}학년 {mainClass.classRoom}반
          </div>
        </div>
      </section>
      <ul>
        <div onClick={() => navigate("/")}>
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
        </div>
        <div onClick={() => navigate("/bid")}>
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
        </div>
        <div onClick={() => navigate("/class")}>
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
        </div>
        <div onClick={() => navigate("/reward")}>
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
        </div>
        <div onClick={() => navigate("/bank")}>
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
        </div>
        <div onClick={() => navigate("/game")}>
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
        </div>
      </ul>
      <Link
        to={`/classlist/${teacherInfo.adminInfo.userNo}`}
        className={styled.view}
      >
        <FontAwesomeIcon icon={faBars} />
        <span>전체학급목록</span>
      </Link>
    </nav>
  );
}
