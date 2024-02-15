import React from "react";
import styled from "./RoundedInfoButton.module.css"

export default function RoundedInfoButton(props){
	const { value, unit, textColor, borderColor, backgroundColor, padding } = props;
  const text = value +  unit;
  const wrapperStyle = {
    'border': '0.3vh solid ' + borderColor,
    'backgroundColor': backgroundColor,
    'padding': padding,
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