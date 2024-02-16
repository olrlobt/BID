import { useDispatch } from "react-redux";
import { openModal, closeModal } from "../Store/modalSlice";

export default function useModal() {
  const dispatch = useDispatch();
  const handleOpenModal = ({ type, props }) => {
    dispatch(openModal({ type, props }));
  };

  const handleCloseModal = (type) => {
    dispatch(closeModal());
  };
  return {
    openModal: handleOpenModal,
    closeModal: handleCloseModal,
  };
}
