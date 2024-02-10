import { useDispatch } from 'react-redux';
import { changeRequestList } from '../Store/requestCouponSlice';

export default function useRequestedCoupons() {
  const dispatch = useDispatch();

  const handleChangeRequestList = (couponList) => {
    dispatch(changeRequestList(couponList));
  };
  return {
    changeRequestList: handleChangeRequestList,
  };
}
