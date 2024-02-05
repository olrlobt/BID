// 국고 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  classMoney: null,
};

export const moneySeletor = (state) => state.classMoney.classMoney;

export const moneySlice = createSlice({
  name: 'classMoney',
  initialState,
  reducers: {
    initMoney: (state, action) => {
      const money = action.payload;
      state.classMoney = money;
    },
  },
});

export const { initMoney } = moneySlice.actions;
