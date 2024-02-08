import styled from "./SettingButton.module.css"
import { SvgIcon } from "@material-ui/core";

export default function SettingButton(props){
	const { onClick, svg, text, height, backgroundColor } = props;

	const buttonStyle = {
    backgroundColor: backgroundColor,
  }

	return(
		<button
			className = {styled.newCouponButton}
			onClick = {onClick}
			style={buttonStyle}
		>
			<SvgIcon
				component={svg}
				style={{fill: 'white', height: {height}}}
			/>
			<div className={styled.text}>{text}</div >
		</button>
	)
};