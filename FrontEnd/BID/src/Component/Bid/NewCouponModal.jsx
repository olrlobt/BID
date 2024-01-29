import React from 'react';
import styled from './NewCouponModal.module.css';
import { useState } from 'react';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import useCoupons from "../../hooks/useCoupons";

export default function NewCouponModal({ onClose, ...props }){

  const { addCoupon } = useCoupons();

  const [form, setForm] = useState({
    no: 10,
    name: '',
    description: '',
    startPrice: 0,
    selected: false,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const addNewCoupon = (e) => {
    e.preventDefault();
    if (form.name === '') {
      console.log('쿠폰 이름을 입력하세요');
    } else if (form.desc === '') {
      console.log('쿠폰 설명을 입력하세요');
    } else if (form.price === 0) {
      console.log('쿠폰 금액을 입력하세요');
    } else{
      // 데이터 저장

      addCoupon({
        newCoupon: form
      });
      onClose();
    }
  }

  return(
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <form id='newCouponForm' onSubmit={addNewCoupon}>
        <div className={styled.inputRow}>
          <label htmlFor='couponName'>쿠폰 이름</label>
          <input
            id='couponName'
            name='name'
            type='text'
            onChange={handleChange}
          />
        </div>
        <div className={styled.inputRow}>
          <label htmlFor='couponDesc'>쿠폰 내용</label>
          <input
            id='couponDesc'
            name='description'
            type='text'
            onChange={handleChange}
          />
        </div>
        <div className={styled.inputRow}>
          <label htmlFor='couponPrice'>시작 금액</label>
          <input
            id='couponPrice'
            name='startPrice'
            type='number'
            min={0}
            onChange={handleChange}
          />
        </div>
        <SubmitButton
          text = '등록'
          width = '100%'
          height = '7vh'
        />
      </form>
    </Modal>
  );
}