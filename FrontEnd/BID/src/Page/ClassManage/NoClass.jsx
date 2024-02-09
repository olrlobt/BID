import React from 'react';
import styled from './MakeClass.module.css';
import { useNavigate } from 'react-router-dom';

export default function NoClass() {
  const navigate = useNavigate();
  return (
    <section className={styled.noClass}>
      <button
        className={styled.makeBtn}
        onClick={() => navigate('/classlist/:teacherId/make/')}
      >
        우리 반 만들기
      </button>
    </section>
  );
}
