import React from 'react';
import PropTypes from 'prop-types';
import styled from './Logo.module.css';
import Face from '../../Asset/Image/logo.png';
import B_D from '../../Asset/Image/B_D.png';

export default function Logo({ text }) {
  return (
    <div className={styled.logoContainer}>
      <img src={Face} className={styled.Face} onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
      <div className={styled.logoInfo}>
        <img src={B_D} className={styled.B_D} onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
        <p>{text}</p>
      </div>
    </div>
  );
}

Logo.propTypes = {
  text: PropTypes.string.isRequired,
};
