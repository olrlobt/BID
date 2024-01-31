import React from "react";
import styled from "./CouponList.module.css"
import Coupon from "./Coupon";

export default function CouponList(props){
	const { coupons } = props;
	
	return(
		<div className = {styled.couponListWrapper}>
			{
				coupons.map((coupon) =>
					<Coupon
						key = {coupon.no}
						no = {coupon.no}
						name = {coupon.name}
						description = {coupon.description}
						startPrice = {coupon.startPrice}
					/>)
			}
		</div>
	);
}