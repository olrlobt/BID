import { useState } from 'react';
import Modal from '../Common/Modal';
import styled from './CouponModal.module.css';
import { approveCoupon, denyCoupon } from '../../Apis/CouponApis';

export default function CouponModal({ onClose, ...props }) {
  const [editCoupon, setEditCoupon] = useState(props[1]);

  const groupNo = 1;
  const handleCoupon = (index, which) => {
    const updatedCouponList = [...editCoupon];
    const deletedCoupon = editCoupon[index];

    if (which === 'accept') {
      const accept = window.confirm('승인하시겠습니까?');
      if (accept) {
        updatedCouponList.splice(index, 1);
        approveCoupon(groupNo, deletedCoupon.no);
        setEditCoupon(updatedCouponList);
      }
    } else if (which === 'deny') {
      const deny = window.confirm('거절하시겠습니까?');
      if (deny) {
        updatedCouponList.splice(index, 1);
        denyCoupon(groupNo, deletedCoupon.no);
        setEditCoupon(updatedCouponList);
      }
    }
  };

  return (
    <Modal onClose={onClose} {...props}>
      {console.log(editCoupon)}
      <h1>{props[0]}</h1>
      <section className={styled.couponList}>
        {props[1].map((v, i) => (
          <div className={styled.coupon}>
            <div className={styled.couponContent}>
              <span>{v.userName}</span> | <span>{v.couponName}</span>
            </div>
            <button
              className={styled.acceptBtn}
              onClick={() => handleCoupon(i, 'accept')}
            >
              승인
            </button>
            <button
              className={styled.denyBtn}
              onClick={() => handleCoupon(i, 'deny')}
            >
              거절
            </button>
          </div>
        ))}
      </section>
    </Modal>
  );
}
