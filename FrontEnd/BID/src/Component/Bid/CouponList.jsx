import React from "react"; // { useEffect, useMemo, useState }
import styled from "./CouponList.module.css";
import Coupon from "./Coupon";

export default function CouponList(props) {
  const { coupons } = props;

  return (
    <div className={styled.couponListWrapper}>
      {coupons.map((coupon) => (
        <Coupon
          key={coupon.no}
          no={coupon.no}
          name={coupon.name}
          description={coupon.description}
          startPrice={coupon.startPrice}
          coupons={coupons}
        />
      ))}
    </div>
  );
}
