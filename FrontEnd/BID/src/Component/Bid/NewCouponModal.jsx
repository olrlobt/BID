import React from 'react';
import styled from './NewCouponModal.module.css';
import Modal from '../Common/Modal';
import SubmitButton from '../Common/SubmitButton';
import { getCouponListApi, addNewCouponApi } from '../../Apis/CouponApis';
import { useMutation } from '@tanstack/react-query';
import useCoupons from "../../hooks/useCoupons";

export default function NewCouponModal({ onClose, ...props }){
  const { initCoupons } = useCoupons();

  /** 쿠폰 추가 쿼리 */
  const addNewCouponQuery  = useMutation({
    mutationKey: ['addNewCoupon'],
    mutationFn: (form) => addNewCouponApi(1, form),
    onSuccess: () => {
      getCouponListApi(1).then((res) => {
        if(res.data !== undefined){
          initCoupons({ couponList: res.data.coupons });
        }
      })
    },
    onError: (error) => { console.log(error); }
  })

  /** 버튼 클릭 -> 쿠폰 추가 함수 */
  const addNewCoupon = (e) => {
    e.preventDefault();
    if (e.target.name.value === '') {
      console.log('쿠폰 이름을 입력하세요');
    } else if (e.target.description.value === '') {
      console.log('쿠폰 설명을 입력하세요');
    } else if (e.target.startPrice.value === 0) {
      console.log('쿠폰 금액을 입력하세요');
    } else {
      const form = {
        name: e.target.name.value,
        description: e.target.description.value,
        startPrice: e.target.startPrice.value,
      };
      addNewCouponQuery.mutate(form);
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
          />
        </div>
        <div className={styled.inputRow}>
          <label htmlFor='couponDesc'>쿠폰 내용</label>
          <input
            id='couponDesc'
            name='description'
            type='text'
          />
        </div>
        <div className={styled.inputRow}>
          <label htmlFor='couponPrice'>시작 금액</label>
          <input
            id='couponPrice'
            name='startPrice'
            type='number'
            min={0}
          />
        </div>
        <SubmitButton
          text = '등록'
          width = '100%'
          height = '7vh'
          fontSize = '2vw'
        />
      </form>
    </Modal>
  );
}