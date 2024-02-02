import styled from "./SettingButton.module.css"
import { SvgIcon } from "@material-ui/core";

export default function SettingButton(props){
	const { onClick, svg, text, height } = props;

	return(
		<button
			className = {styled.newCouponButton}
			onClick = {onClick}
		>
			<SvgIcon
				component = { svg }
				style = {{ fill: 'white', height: { height } }}
			/>
			<div className={styled.text}>{ text }</div >
		</button>
	)
};