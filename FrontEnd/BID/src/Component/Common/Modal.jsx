import React, { useEffect, useRef } from "react";
import styled from "./Modal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import useOutSideClick from "../../hooks/useOutSideClick";

function Modal({ onClose, children }) {
  const modalRef = useRef(null);
  const handleClose = () => {
    onClose?.();
  };

  useOutSideClick(modalRef, handleClose);
  useEffect(() => {
    const $body = document.querySelector("body");
    $body.style.overflow = "hidden";
    return () => ($body.style.overflow = "auto");
  }, []);

  return (
    <section className={styled.modalSection}>
      <div className={styled.modalWrap} ref={modalRef}>
        <div className={styled.closeBtn} onClick={handleClose}>
          <FontAwesomeIcon icon={faXmark} />
        </div>
        <div className={styled.contents}>{children}</div>
      </div>
    </section>
  );
}

export default Modal;
