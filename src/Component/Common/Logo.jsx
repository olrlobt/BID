import React from "react";
import PropTypes from "prop-types";
import styled from "./Logo.module.css";
import Face from "../../Asset/Image/logo.png";
import B_D from "../../Asset/Image/B_D.png";

function Logo({ text }) {
  return (
    <div className={styled.logoContainer}>
        <img src={Face} className={styled.Face} />
        <img src={B_D} className={styled.B_D} />
      <p>{text}</p>
    </div>
  );
}

Logo.propTypes = {
  text: PropTypes.string.isRequired,
};

export default Logo;
