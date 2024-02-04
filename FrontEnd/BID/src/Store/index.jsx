import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { modalSlice } from './modalSlice';
import { productSlice } from './productSlice';
import userReducer from './UserSlice';
import { couponSlice } from './couponSlice';
import { ballSlice } from './ballSlice';
import { bidSlice } from './bidSlice';
import { moneySlice } from './moneySlice';
import { bidCountSlice } from './bidCountSlice';

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  seatBall: ballSlice.reducer,
  products: productSlice.reducer,
  studentBid: bidSlice.reducer,
  classMoney: moneySlice.reducer,
  bidCount: bidCountSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
