import Modal from "../Common/Modal";
import styled from "./CouponModal.module.css";

export default function CouponModal({ onClose, ...props }) {
  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <section className={styled.couponList}>
        {props[1].map((v) => (
          <div className={styled.coupon}>
            <div className={styled.couponContent}>
              <span>{v.name}</span> | <span>{v.coupon}</span>
            </div>
            <button className={styled.acceptBtn}>승인</button>
            <button className={styled.denyBtn}>거절</button>
          </div>
        ))}
      </section>
    </Modal>
  );
}
