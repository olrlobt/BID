import React from "react";
import styled from "./ProductInfoButton.module.css"

export default function ProductInfoButton(props){
	const { value, unit, textColor, borderColor, backgroundColor } = props;
  const text = value +  unit;
  const wrapperStyle = {
    'border': '0.3vh solid ' + borderColor,
    'backgroundColor': backgroundColor
  }
  const textStyle = {
    'color': textColor,
  }


	return(
		<div
      className = { styled.infoWrapper }
      style = { wrapperStyle }
    >
			<span style = {textStyle}>{ text }</span>
		</div>
	);
}