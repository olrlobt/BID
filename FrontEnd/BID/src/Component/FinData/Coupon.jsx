// Coupon.js
import React from 'react';
import styled from './Coupon.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleCheck } from '@fortawesome/free-solid-svg-icons';

function Coupon({ data }) {
  return (
    <div className={styled.couponContainer}>
      {data.length === 0 ? (
        <div className={styled.noData}>보유 쿠폰이 없습니다</div>
      ) : (
        <div className={styled.couponLeft}>
          {data.map((coupon, index) => (
            <div key={index} className={styled.coupons}>
              <div className={styled.couponName}>
                <FontAwesomeIcon
                  className={styled.couponCheck}
                  icon={faCircleCheck}
                />
                <span>{coupon.name}</span>
              </div>
              <p className={styled.couponCount}>
                {coupon.count > 100 ? coupon.count % 10 : coupon.count}장
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Coupon;
