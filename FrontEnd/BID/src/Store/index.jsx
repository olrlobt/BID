import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import { productSlice } from "./productSlice";
import userReducer from "./UserSlice";
import { couponSlice } from "./couponSlice";
import { ballSlice } from "./ballSlice";
import { alarmSlice } from "./alarmSlice";

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  seatBall: ballSlice.reducer,
  products: productSlice.reducer,
  alarmList: alarmSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
