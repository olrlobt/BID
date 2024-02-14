import { useState } from "react";
import Modal from "../Common/Modal";
import styled from "./ChangeBidModal.module.css";
import { useSelector } from "react-redux";
import { bidSelector } from "../../Store/bidSlice";
import useBid from "../../hooks/useBid";
import { useMutation } from "@tanstack/react-query";
import { changeSalaries } from "../../Apis/TeacherManageApis";
import { mainSelector } from "../../Store/mainSlice";

export default function ChangeBidModal({ onClose, ...props }) {
  const currentBid = useSelector(bidSelector);
  const mainClass = useSelector(mainSelector);
  const [bid, setBid] = useState(currentBid);
  const { changeBid } = useBid();
  const handleChange = (event) => {
    setBid(event.target.value);
  };

  const handleSubmit = () => {
    changeBidSalary.mutate();
  };

  const gradeNo = mainClass.no;

  const changeBidSalary = useMutation({
    mutationKey: ["changeSalaries"],
    mutationFn: () =>
      changeSalaries(gradeNo, bid)
        .then(() => {
          changeBid(bid);
          alert("변경되었습니다.");
          onClose();
        })
        .catch(() => alert("변경이 되지 않았습니다.")),
  });

  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <h2 className={styled.modalH2}>
        다음에는 학생들의 주급을 320비드로 설정해보세요!
      </h2>
      <section className={styled.bidInput}>
        <section>
          <label htmlFor="current">현재 주급</label>
          <input type="number" id="current" value={currentBid} readOnly />
        </section>
        <section>
          <label htmlFor="after">변경 주급</label>
          <input type="number" id="after" value={bid} onChange={handleChange} />
        </section>
      </section>
      <button className={styled.changeBid} onClick={handleSubmit}>
        변경
      </button>
    </Modal>
  );
}
