import React from "react";
import styled from "./Button.module.css"

export default function Button(props){
	const {onClick, text, active} = props;

	return(
		<button
			onClick = {onClick}
			className = {[styled.writerButton, active? styled.active: ''].join(' ')}>
			{text}
		</button>
	)
};