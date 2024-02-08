import React from 'react';
import Teacher from '../ClassManage/Teacher';
import styled from '../ClassManage/ClassList.module.css';
import { Outlet } from 'react-router';

export default function MainClass() {
  return (
    <main className={styled.mainClass}>
      <Teacher />
      <Outlet />
    </main>
  );
}
