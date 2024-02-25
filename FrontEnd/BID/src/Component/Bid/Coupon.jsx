import React from "react";
import styled from "./Coupon.module.css"
import Price from "../Common/Price";
import { SvgIcon } from "@material-ui/core";
import { Close } from "@material-ui/icons";
import { deleteCouponApi } from "../../Apis/CouponApis";
import { useMutation } from "@tanstack/react-query";
import useCoupons from "../../hooks/useCoupons";
import alertBtn from "../Common/Alert";
import confirmBtn from "../Common/Confirm";

export default function Coupon(props){
	const {no, name, description, startPrice, gradeNo} = props;

	const { deleteCoupon } = useCoupons();

	/** 쿠폰 삭제 쿼리 */
	const deleteCouponQuery = useMutation({
		mutationKey: ['deleteCoupon'],
		mutationFn: (no) => deleteCouponApi(gradeNo, no),
		onSuccess: () => {
			deleteCoupon({couponNo: no})
		}
	})

	/** X 버튼 클릭 -> 쿠폰 삭제 함수 */
	const onClickDeleteCoupon = (e) => {
		e.preventDefault();
		confirmBtn({
			icon: "warning",
			text: "삭제하신 쿠폰은 다시 복구할 수 없습니다. 그래도 삭제하시겠습니까?",
			confirmTxt: "삭제",
			confirmColor: "#F23F3F",
			cancelTxt: "취소",
			cancelColor: "#a6a6a6",
			confirmFunc: () => deleteCouponQuery.mutate(no),
      cancelFunc: () => {}
		})
	}

	return(
		<div className = {styled.couponWrapper}>	
			<div className = {styled.left}>
				<button
					className = {styled.removeButton}
					onClick = {onClickDeleteCoupon}
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