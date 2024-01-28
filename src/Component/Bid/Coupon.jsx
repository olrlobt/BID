import React from "react";
import styled from "./Coupon.module.css"
import Price from "./Price";
import { SvgIcon } from "@material-ui/core";
import { Close } from "@material-ui/icons";

export default function Coupon(props){
	const {no, name, description, startPrice, coupons, removeCoupon} = props;

	const removeThatCoupon = () => {
		removeCoupon(coupons, no);
	}

	return(
		<div className = {styled.couponWrapper}>	
				<div className = {styled.left}>
					<button
						className = {styled.removeButton}
						onClick = {removeThatCoupon}
					>
						<SvgIcon
							component = {Close}
							style = {{fill: 'white', height: '3vh',}}
						/>
					</button>
				</div>
				<div className = {styled.right}>
					<div className = {styled.couponHeader}>
						<div className = {styled.couponName}>{name}</div>
						<Price price = {startPrice}/>
					</div>
					<div className = {styled.couponDesc}>{description}</div>
				</div>
		</div>
	);
}