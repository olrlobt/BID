// 주급 slice
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  bidPrice: 200,
};

export const bidSelector = (state) => {
  return state.studentBid.bidPrice;
};

export const bidSlice = createSlice({
  name: "studentBid",
  initialState,
  reducers: {
    changeBid: (state, action) => {
      const changedBid = action.payload;
      state.bidPrice = changedBid;
    },
  },
});

export const { changeBid } = bidSlice.actions;
