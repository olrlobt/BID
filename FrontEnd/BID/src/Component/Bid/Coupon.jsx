import React from "react";
import styled from "./Coupon.module.css"
import Price from "../Common/Price";
import { SvgIcon } from "@material-ui/core";
import { Close } from "@material-ui/icons";
import { deleteCouponApi } from "../../Apis/CouponApis";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function Coupon(props){

	const {no, name, description, startPrice} = props;
	const queryClient = useQueryClient();

	/** 쿠폰 삭제 쿼리 */
	const deleteCouponQuery = useMutation({
		mutationKey: ['deleteCoupon'],
		mutationFn: (no) => deleteCouponApi(1, no),
		onSuccess: () => { queryClient.invalidateQueries('couponList') },
		onError: (error) => { console.log(error) }
	})

	/** X 버튼 클릭 -> 쿠폰 삭제 함수 */
	const deleteCoupon = (e) => {
		e.preventDefault();
		deleteCouponQuery.mutate(no);
	}

	return(
		<div className = {styled.couponWrapper}>	
			<div className = {styled.left}>
				<button
					className = {styled.removeButton}
					onClick = {deleteCoupon}
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