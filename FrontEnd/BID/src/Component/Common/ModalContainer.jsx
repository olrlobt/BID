import React from "react";
import { createPortal } from "react-dom";
import { useSelector } from "react-redux";
import { modalSelector } from "../../Store/modalSlice";
import CouponModal from "../Dashboard/CouponModal";
import ChangeBidModal from "../Dashboard/ChangeBidModal";
import useModal from "../../hooks/useModal";
import IntoMoneyModal from "../Dashboard/IntoMoneyModal";
import NewCouponModal from "../Bid/NewCouponModal";
import StudentAdd from "../Manage/StudentAdd";
import PwdRemoveModal from '../Manage/PwdRemoveModal'
import StudentEditModal from '../Manage/StudentEditModal'

const MODAL_COMPONENTS = {
  coupon: CouponModal,
  changeBid: ChangeBidModal,
  intoMoney: IntoMoneyModal,
  newCoupon: NewCouponModal,
  addStudent: StudentAdd,
  pwdRemove: PwdRemoveModal,
  editStudent: StudentEditModal,
};

function ModalContainer() {
  const { closeModal } = useModal();
  const { type, props } = useSelector(modalSelector);
  if (!type) {
    return null;
  }

  const Modal = MODAL_COMPONENTS[type];
  return createPortal(
    <>
      <Modal onClose={closeModal} {...props} />
    </>,
    document.getElementById("modal")
  );
}

export default ModalContainer;