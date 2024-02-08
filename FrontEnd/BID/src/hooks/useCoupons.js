import { useDispatch } from "react-redux";
import { initCoupons } from "../Store/couponSlice";

export default function useCoupons() {
  const dispatch = useDispatch();
  
  const handleInitCoupons = ({ couponList }) => {
    dispatch(initCoupons(couponList));
  }

  return {
    initCoupons: handleInitCoupons,
  };
}
