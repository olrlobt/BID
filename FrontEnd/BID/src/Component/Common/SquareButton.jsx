import React from "react";
import styled from "./SquareButton.module.css"

export default function RoundedInfoButton(props){
	const { text, textColor, backgroundColor, padding, onClick } = props;
  const wrapperStyle = {
    'backgroundColor': backgroundColor,
    'padding': padding,
    'color': textColor,
  }

	return(
		<button
      className={ styled.infoWrapper }
      style={ wrapperStyle }
      onClick={ onClick }
    >
      { text }
		</button>
	);
}