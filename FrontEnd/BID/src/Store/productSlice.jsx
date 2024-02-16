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

    deleteProduct: (state, action) => {
      const productNo = action.payload;
      const newList = state.productList.filter((p) => p.no !== productNo);
      state.productList = [...newList];
    },

    modifyProduct: (state, action) => {
      const { productNo, patchData } = action.payload;
      state.productList.forEach((p) => {
        if(p.no === productNo){
          p.title = patchData.title;
          p.category = patchData.category;
          p.description = patchData.description;
        }
      })
    },
  },
});

export const { initProducts, deleteProduct, modifyProduct } = productSlice.actions;
