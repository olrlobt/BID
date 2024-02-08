import React from "react";
import styled from "./WriterButton.module.css"

export default function WriterButton(props){
	const {onClick, text, active} = props;

	return(
		<button
			onClick = {onClick}
			className = {[styled.writerButton, active? styled.active: ''].join(' ')}>
			{text}
		</button>
	)
};