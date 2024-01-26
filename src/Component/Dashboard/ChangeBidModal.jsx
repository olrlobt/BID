import Modal from "../Common/Modal";
import styled from "./ChangeBidModal.module.css";

export default function ChangeBidModal({ onClose }) {
  return (
    <Modal onClose={onClose}>
      <h1>주급변경</h1>
      <h2 className={styled.modalH2}>
        다음에는 학생들의 주급을 320비드로 설정해보세요!
      </h2>
      <section className={styled.bidInput}>
        <label>
          현재 주급
          <input type="number" />
        </label>
        <label>
          변경 주급
          <input type="number" />
        </label>
      </section>
      <button>변경하기</button>
    </Modal>
  );
}
