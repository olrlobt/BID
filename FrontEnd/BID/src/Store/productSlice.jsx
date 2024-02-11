// 경매 물품 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  productList: [],
};

export const productSelector = (state) => {
  return state.products.productList;
};

export const productSlice = createSlice({
  name: 'products',
  initialState: initialState,
  reducers: {
    initProducts: (state, action) => {
      const productList = action.payload;
      state.productList = productList;
    },
  },
});

export const { initProducts } = productSlice.actions;
