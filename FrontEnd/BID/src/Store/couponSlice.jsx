// 쿠폰 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  couponList: null,
};

export const couponSelector = (state) => {
  return state.coupons.couponList;
};

export const couponSlice = createSlice({
  name: 'coupons',
  initialState: initialState,
  reducers: {
    initCoupons: (state, action) => {
      const couponList = action.payload;
      state.couponList = couponList;
    },
  },
});

export const { initCoupons } = couponSlice.actions;
