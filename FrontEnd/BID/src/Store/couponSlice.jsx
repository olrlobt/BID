// 쿠폰 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  couponList: [],
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

    deleteCoupon: (state, action) => {
      const couponNo = action.payload;
      const newList = state.couponList.filter((c) => c.no !== couponNo);
      state.couponList = [...newList];
    },

    registCoupon: (state, action) => {
      const couponNo = action.payload;
      state.couponList.forEach((c) => {
        if(c.no===couponNo){
          c.couponStatus = 'REGISTERED';
          return;
        }
      });
    },
    
    unregistCoupon: (state, action) => {
      const couponNo = action.payload;
      state.couponList.forEach((c) => {
        if(c.no===couponNo){
          c.couponStatus = 'UNREGISTERED';
          return;
        }
      });
    },
  },
});

export const { initCoupons, deleteCoupon, registCoupon, unregistCoupon } = couponSlice.actions;
