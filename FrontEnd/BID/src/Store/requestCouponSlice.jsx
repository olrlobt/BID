import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  requestCouponList: [],
};

export const requestCouponSelector = (state) => {
  return state.requestCoupon.requestCouponList;
};

export const requestCouponSlice = createSlice({
  name: 'requestCoupon',
  initialState,
  reducers: {
    changeRequestList: (state, action) => {
      state.requestCouponList = action.payload;
    },
  },
});

export const { changeRequestList } = requestCouponSlice.actions;
