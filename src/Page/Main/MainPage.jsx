import React from "react";
import styled from "./MainPage.module.css";
import NavBar from "../../Component/Common/NavBar";
import { Outlet } from "react-router";

export default function MainPage() {
  return (
    <>
      <main>
        <NavBar />
        <Outlet />
      </main>
    </>
  );
}
