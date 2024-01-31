import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  productList: null
};

export const productSelector = (state) => {
  return state.products.productList;
}

export const productSlice = createSlice({
  name: 'products',
  initialState : initialState,
  reducers: {
    initProducts: (state, action) => {
      const productList = action.payload;
      state.productList = productList;
    },
    addProduct: (state, action) => {
      const newProduct = action.payload;
      state.productList = [...state.productList, newProduct];
    },
    removeProduct: (state, action) => {
      const productNo = action.payload;
      const newList = state.productList.filter((c) => c.no !== productNo);
      state.productList = [...newList];
    }
  }
});

export const { initProducts, addProduct, removeProduct } = productSlice.actions;