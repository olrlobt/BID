import { useDispatch } from "react-redux";
import { initCoupons, addCoupon, removeCoupon } from "../Store/couponSlice";

export default function useCoupons() {
  const dispatch = useDispatch();
  
  const handleInitCoupons = ({ couponList }) => {
    dispatch(initCoupons(couponList));
  }

  const handleAddCoupon = ({ newCoupon }) => {
    dispatch(addCoupon(newCoupon));
  }

  const handleRemoveCoupon = ({ couponNo }) => {
    dispatch(removeCoupon(couponNo));
  }

  return {
    initCoupons: handleInitCoupons,
    addCoupon: handleAddCoupon,
    removeCoupon: handleRemoveCoupon
  };
}
