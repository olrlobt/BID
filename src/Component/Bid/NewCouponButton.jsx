import React, { useEffect } from "react";
import styled from "./NewCouponButton.module.css"
import { SvgIcon } from "@material-ui/core";

export default function NewCouponButton(props){
	const {onClick, svg, text} = props;

	useEffect(() => {
		svg.className = styled.svgIcon;
	})

	return(
		<button
			className = {styled.newCouponButton}
			onClick = {onClick}
		>
			<SvgIcon
				component = {svg}
				style = {{fill: 'white', height: '3vh',}}
			/>
			{text}
		</button>
	)
};