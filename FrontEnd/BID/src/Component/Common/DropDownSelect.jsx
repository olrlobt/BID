import React from "react";

export default function RoundedInfoButton(props){
	const { selectName, selectTitle, options } = props;

	return(
		<select name={selectName} id={selectName}>
      <option value='none' selected disabled>{selectTitle}</option>
      {
        options.map((op) =>
          <option key={op.value} value={op.value}>{op.text}</option>
        )
      }
    </select>
	);
}