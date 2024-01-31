// Coupon.js

import React from "react";
import styled from "./Coupon.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMusic } from "@fortawesome/free-solid-svg-icons";

function Coupon() {
  const couponData = [
    { name: '노래 10분 틀기', count: 4 },
    { name: '급식 먼저 먹기', count: 2 },
    // Add other coupons here
  ];

  return (
    <div className={styled.couponContainer}>
      {couponData.map((coupon, index) => (
        <div key={index} className={styled.coupon}>
          <div className={styled.couponName}>
            <FontAwesomeIcon icon={faMusic} /> | {coupon.name}
          </div>
          <p className={styled.couponCount}>{coupon.count}</p>
        </div>
      ))}
    </div>
  );
}

export default Coupon;
