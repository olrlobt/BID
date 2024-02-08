import React from "react";
import PropTypes from "prop-types";
import styled from "./FinCoupon.module.css";


const FinCoupon = ({ bgColor, logo, text, count }) => {
    return (
      <div className={styled.couponWrapper} style={{ backgroundColor: bgColor }}>
        <div className={styled.logo}>{logo}</div>
        <span className={styled.text}>{text}</span>
        <span className={styled.count}>{count}</span>
      </div>
    );
  };
  
  FinCoupon.propTypes = {
    bgColor: PropTypes.string.isRequired,
    logo: PropTypes.node.isRequired,
    text: PropTypes.string.isRequired,
    count: PropTypes.number.isRequired,
  };
  
  export default FinCoupon;