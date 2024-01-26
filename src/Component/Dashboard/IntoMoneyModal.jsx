import Modal from "../Common/Modal";
import styled from "./ChangeBidModal.module.css";

export default function IntoMoneyModal({ onClose, ...props }) {
  const hour = [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24,
  ];

  const time = [0, 10, 20, 30, 40, 50];

  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <section className={styled.bidInput}>
        <section className={styled.changeTime}>
          <label htmlFor="current">알림 시간</label>
          <div>
            <select id="current" className={styled.time}>
              {hour.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
            <span> : </span>
            <select id="current" className={styled.time}>
              {time.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
          </div>
        </section>
        <section className={styled.changeTime}>
          <label htmlFor="after">이체 시간</label>
          <div>
            <select id="current" className={styled.time}>
              {hour.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
            <span> : </span>
            <select id="current" className={styled.time}>
              {time.map((value) => (
                <option value>{value}</option>
              ))}
            </select>
          </div>
        </section>
      </section>
      <button className={styled.changeBid}>변경</button>
    </Modal>
  );
}
