import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import { productSlice } from "./productSlice";
import userReducer from "./UserSlice";
import { couponSlice } from "./couponSlice";
import { ballSlice } from "./ballSlice";
import { bidSlice } from "./bidSlice";

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  seatBall: ballSlice.reducer,
  products: productSlice.reducer,
  studentBid: bidSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
