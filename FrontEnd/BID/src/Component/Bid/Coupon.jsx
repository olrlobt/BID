import React from "react";
import styled from "./Coupon.module.css"
import Price from "./Price";
import { SvgIcon } from "@material-ui/core";
import { Close } from "@material-ui/icons";
import useCoupons from "../../hooks/useCoupons";

export default function Coupon(props){

	const {no, name, description, startPrice} = props;
	const { removeCoupon } = useCoupons();

	const removeACoupon = (e) => {
		e.preventDefault();
		removeCoupon({couponNo: no});
	}

	return(
		<div className = {styled.couponWrapper}>	
				<div className = {styled.left}>
					<button
						className = {styled.removeButton}
						onClick = {removeACoupon}
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