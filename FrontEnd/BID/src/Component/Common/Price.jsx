import React from "react";
import styled from "./Price.module.css"

export default function Price(props){
	const {price} = props;

	return(
		<div className = {styled.priceWrapper}>
			<span>{price}</span>
			<span>비드</span>
		</div>
	);
}