import React from "react";
import styled from "./DropDownSelect.module.css";

export default function DropDownSelect(props){
	const { selectName, selectTitle, options } = props;

	return(
		<select name={selectName} id={selectName} className={styled.selectBox}>
      <option defaultValue='none' selected disabled>{selectTitle}</option>
      {
        options.map((op) =>
          <option key={op.value} value={op.value}>{op.text}</option>
        )
      }
    </select>
	);
}