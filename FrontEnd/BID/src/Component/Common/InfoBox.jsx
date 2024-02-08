import PropTypes from "prop-types";
import styled from "./InfoBox.module.css";

export default function InfoBox({ info, icons, text, modalClick }) {
  const containerStyle = {
    width: info[0]?.width || "auto",
    height: info[0]?.height || "auto",
  };

  return (
    <section
      style={containerStyle}
      className={styled.infoBox}
      onClick={modalClick}
    >
      {icons && (
        <img className={styled.icon} src={icons[0].src} alt={icons[0].alt} />
      )}
      <div className={styled.infoText}>
        <div>{info[0].text[0]}</div>
        <span className={styled.infoImportant}> {text} </span>
        {info[0].text[1]}
      </div>
    </section>
  );
}

// 넘어오는 props에 대한 정보
InfoBox.propTypes = {
  info: PropTypes.arrayOf(
    PropTypes.shape({
      width: PropTypes.string,
      height: PropTypes.string,
      text: PropTypes.string,
    })
  ).isRequired,
  icons: PropTypes.arrayOf(
    PropTypes.shape({
      src: PropTypes.string.isRequired,
      alt: PropTypes.string,
      css: PropTypes.string,
    })
  ),
  text: PropTypes.string,
};
