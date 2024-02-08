import React from "react";
import styled from './Coupon.module.css'

function Coupon() {

const couponData = [
    { name: '노래 10분 틀기', count: 4 },
    { name: '급식 먼저 먹기', count: 2 },
    // 다른 쿠폰들도 추가
  ];


  return (<div className={styled.couponContainer}>
        <div>쿠폰 소유 현황</div>
        {couponData.map((coupon, index) => (
          <div key={index} className={styled.coupon}>
            <div className={styled.couponIcon}></div>
            <p>{`${coupon.name} ${coupon.count}장`}</p>
          </div>
        ))}
      </div>
  )
}

export default Coupon

