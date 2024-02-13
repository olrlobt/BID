import { useDispatch } from "react-redux";
import { initCoupons, addCoupon, deleteCoupon, registCoupon, unregistCoupon } from "../Store/couponSlice";

export default function useCoupons() {
  const dispatch = useDispatch();
  
  const handleInitCoupons = ({ couponList }) => {
    dispatch(initCoupons(couponList));
  }

  const handleDeleteCoupon = ({ couponNo }) => {
    dispatch(deleteCoupon(couponNo));
  }
  
  const handlerRegistCoupon = ({ couponNo }) => {
    dispatch(registCoupon(couponNo));
  }
  
  const handleUnregistCoupon = ({ couponNo }) => {
    dispatch(unregistCoupon(couponNo));
  }

  return {
    initCoupons: handleInitCoupons,
    deleteCoupon: handleDeleteCoupon,
    registCoupon: handlerRegistCoupon,
    unregistCoupon: handleUnregistCoupon,
  };
}
