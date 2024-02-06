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
    changeSavingList: (state, action) => {
      action.payload.forEach(({ no, depositPrice, interestRate }) => {
        const savingItem = state.savingList.find(
          (item) => item.savingNo === no
        );
        if (savingItem) {
          savingItem.savingDepositPrice = depositPrice;
          savingItem.savingInterestRate = interestRate;
        }
      });
    },
  },
});

export const { initSavingList, changeSavingList } = savingSlice.actions;
