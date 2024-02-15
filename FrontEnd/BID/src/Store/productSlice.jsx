// 경매 물품 slice
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  productList: [],
  biddingCoupon: [],
  biddingCannonball: [],
};

export const productSelector = (state) => {
  return state.products.productList;
};

export const biddingCouponSelector = (state) => {
  return state.products.biddingCoupon;
};

export const biddingCannonballSelector = (state) => {
  return state.products.biddingCannonball;
};

export const productSlice = createSlice({
  name: 'products',
  initialState: initialState,
  reducers: {
    initProducts: (state, action) => {
      const productList = action.payload;
      console.log(productList)
      state.biddingCoupon = productList.find((product) => product.category==='쿠폰');
      state.biddingCannonball = productList.find((product) => product.category==='대포알');
      state.productList = productList.filter((product) => product.category!=='쿠폰' && product.category!=='대포알');
      console.log(state.productList);
      console.log(state.biddingCoupon);
      console.log(state.biddingCannonball);
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
