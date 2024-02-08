import Modal from "../Common/Modal";
import styled from "./ChangeBidModal.module.css";

export default function ChangeBidModal({ onClose, ...props }) {
  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <h2 className={styled.modalH2}>
        다음에는 학생들의 주급을 320비드로 설정해보세요!
      </h2>
      <section className={styled.bidInput}>
        <section>
          <label htmlFor="current">현재 주급</label>
          <input type="number" id="current" />
        </section>
        <section>
          <label htmlFor="after">변경 주급</label>
          <input type="number" id="after" />
        </section>
      </section>
      <button className={styled.changeBid}>변경</button>
    </Modal>
  );
}
