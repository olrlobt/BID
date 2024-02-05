import React from "react";
import styled from "./Coupon.module.css"
import Price from "../Common/Price";
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
					<p className = {styled.couponName}>{name}</p>
					<Price price = {startPrice}/>
				</div>
				<p className = {styled.couponDesc}>{description}</p>
			</div>
	</div>
	);
}