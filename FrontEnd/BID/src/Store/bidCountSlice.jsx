// 거래 횟수 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  bidCount: 0,
};

export const bidCountSelector = (state) => {
  return state.bidCount.bidCount;
};

export const bidCountSlice = createSlice({
  name: 'bidCount',
  initialState,
  reducers: {
    initCount: (state, action) => {
      const bidCount = action.payload;
      state.bidPrice = bidCount;
    },
  },
});

export const { initCount } = bidCountSlice.actions;
