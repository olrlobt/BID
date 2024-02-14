import React from "react";
import styled from "./MainPage.module.css";
import NavBar from "../../Component/Common/NavBar";
import { Outlet } from "react-router";
import { useLocation } from "react-router-dom";

export default function MainPage() {
  // const location = useLocation();

  return (
    <>
      <main className={styled.mainPage}>
        <NavBar />
        <Outlet />
      </main>
    </>
  );
}
