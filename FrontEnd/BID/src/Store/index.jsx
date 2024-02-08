import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import { userSlice } from './userSlice';
import { productSlice } from "./productSlice";
import { couponSlice } from "./couponSlice";
import { ballSlice } from "./ballSlice";
import { bidSlice } from "./bidSlice";
import { moneySlice } from "./moneySlice";
import { bidCountSlice } from "./bidCountSlice";
import { savingSlice } from "./savingSlice";
import { modelSlice } from "./modelSlice";

const rootReducer = combineReducers({
  user: userSlice.reducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  seatBall: ballSlice.reducer,
  products: productSlice.reducer,
  studentBid: bidSlice.reducer,
  classMoney: moneySlice.reducer,
  bidCount: bidCountSlice.reducer,
  savingInfo: savingSlice.reducer,
  model: modelSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
