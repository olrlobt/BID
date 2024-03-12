import React from "react";
import styled from "./BiddingCoupon.module.css"

export default function BiddingCoupon(props){
	const {no, name, description} = props;

	return(
		<div className = {styled.couponWrapper}>	
			<div className = {styled.left}>
			</div>
			<div className = {styled.right}>
				<div className = {styled.couponHeader}>
					<p className = {styled.couponName}>{name}</p>
				</div>
				<p className = {styled.couponDesc}>{description}</p>
			</div>
	</div>
	);
}