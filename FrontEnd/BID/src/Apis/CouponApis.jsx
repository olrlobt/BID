import axios from "axios";

export const CouponApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 쿠폰 목록 가져오기
 * @param gradeNo 학급 넘버 
 * @returns 쿠폰 목록
 */
export const getCouponListApi = async (gradeNo) => {
  return await CouponApis.get(gradeNo+'/coupons');
}

/**
 * 쿠폰 등록하기
 * @param gradeNo 학급 넘버 
 * @param newCoupon 새 쿠폰 정보
 */
export const addNewCouponApi = async (gradeNo, newCoupon) => {
  return await CouponApis.post(gradeNo+'/coupons', newCoupon);
}

/**
 * 쿠폰 삭제하기
 * @param gradeNo 학급 넘버 
 * @param couponNo 쿠폰 번호
 */
export const deleteCouponApi = async (gradeNo, couponNo) => {
  return await CouponApis.delete(gradeNo+'/coupons/'+couponNo);
}

/**
 * 쿠폰 경매 포함하기
 * @param gradeNo 학급 넘버 
 * @param couponNo 쿠폰 번호
 */
export const registerCouponApi = async (gradeNo, couponNo) => {
  return await CouponApis.patch(gradeNo+'/coupons/'+couponNo+'/register');
}

/**
 * 쿠폰 경매 제외하기
 * @param gradeNo 학급 넘버 
 * @param couponNo 쿠폰 번호
 */
export const unregisterCouponApi = async (gradeNo, couponNo) => {
  return await CouponApis.patch(gradeNo+'/coupons/'+couponNo+'/unregister');
}