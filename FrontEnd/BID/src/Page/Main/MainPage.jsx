import React, { useEffect } from "react";
import styled from "./MainPage.module.css";
import NavBar from "../../Component/Common/NavBar";
import { Outlet } from "react-router";
import { useSelector } from "react-redux";
import { userLoggedInSelector } from "../../Store/userSlice";

export default function MainPage() {
  const teacherLogin = useSelector(userLoggedInSelector);
  useEffect(() => {}, [teacherLogin]);

  return (
    <>
      <main className={styled.mainPage}>
        <NavBar />
        <Outlet />
      </main>
    </>
  );
}
