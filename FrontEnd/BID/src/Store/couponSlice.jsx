import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  couponList: null
};

export const couponSelector = (state) => {
  return state.coupons.couponList;
}

export const couponSlice = createSlice({
  name: 'coupons',
  initialState : initialState,
  reducers: {
    initCoupons: (state, action) => {
      const couponList = action.payload;
      state.couponList = couponList;
    },
    addCoupon: (state, action) => {
      const newCoupon = action.payload;
      state.couponList = [...state.couponList, newCoupon];
    },
    removeCoupon: (state, action) => {
      const couponNo = action.payload;
      const newList = state.couponList.filter((c) => c.no !== couponNo);
      state.couponList = [...newList];
    }
  }
});

export const { initCoupons, addCoupon, removeCoupon } = couponSlice.actions;