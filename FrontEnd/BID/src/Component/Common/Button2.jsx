import React, { useState, useEffect } from "react";
import styled from "./Button2.module.css";
import { SvgIcon } from "@material-ui/core";
import SettingsIcon from '@mui/icons-material/Settings';

export default function Button2(props) {
  const { onClick, active, text } = props;

  const [isClicked, setIsClicked] = useState(active);

  useEffect(() => {
    setIsClicked(active);
  }, [active]);

  const handleClick = () => {
    setIsClicked(!isClicked);
    onClick(); // Call the onClick prop here
  };

  return (
    <button
      className={styled.newCouponButton}
      onClick={handleClick} // Use handleClick instead of onClick
    >
    <SvgIcon component={SettingsIcon} style={{ fill: "white", height: "2.5vh" }}  />
      {isClicked ? "편집 완료" : text}
    </button>
  );
}
