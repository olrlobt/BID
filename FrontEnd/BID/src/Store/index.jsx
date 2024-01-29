import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import userReducer from "./UserSlice";
import { couponSlice } from "./couponSlice";

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
