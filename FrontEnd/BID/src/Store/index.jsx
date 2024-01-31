import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import userReducer from "./UserSlice";
import { couponSlice } from "./couponSlice";
import { productSlice } from "./productSlice";

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  products: productSlice.reducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
