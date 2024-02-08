import React from 'react';
import { createPortal } from 'react-dom';
import { useSelector } from 'react-redux';
import { modalSelector } from '../../Store/modalSlice';
import CouponModal from '../DashboardModals/CouponModal';
import ChangeBidModal from '../DashboardModals/ChangeBidModal';
import useModal from '../../hooks/useModal';
import NewCouponModal from '../Bid/NewCouponModal';
import StudentAdd from '../Manage/StudentAdd';
import PwdRemoveModal from '../Manage/PwdRemoveModal';
import StudentEditModal from '../Manage/StudentEditModal';
import ViewProductModal from '../Bid/ViewProductModal';
import TimeModal from '../DashboardModals/TimeModal';
import SearchSchoolModal from '../User/SearchSchoolModal';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

const MODAL_COMPONENTS = {
  coupon: CouponModal,
  changeBid: ChangeBidModal,
  timeModal: TimeModal,
  newCoupon: NewCouponModal,
  addStudent: StudentAdd,
  pwdRemove: PwdRemoveModal,
  editStudent: StudentEditModal,
  viewProduct: ViewProductModal,
  searchSchool: SearchSchoolModal
};

function ModalContainer() {
  const { closeModal } = useModal();
  const { type, props } = useSelector(modalSelector);
  if (!type) {
    return null;
  }

  const Modal = MODAL_COMPONENTS[type];
  return createPortal(
    <QueryClientProvider client={queryClient}>
      <Modal onClose={closeModal} {...props} />
    </QueryClientProvider>,
    document.getElementById('modal')
  );
}

export default ModalContainer;
