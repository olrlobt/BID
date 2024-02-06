// 적금 정보  Slice
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  savingList: null,
};

export const savingSelector = (state) => state.savingInfo.savingList;

export const savingSlice = createSlice({
  name: "savingInfo",
  initialState,
  reducers: {
    initSavingList: (state, action) => {
      const savingList = action.payload;
      state.savingList = savingList;
    },
  },
});

export const { initSavingList } = savingSlice.actions;
