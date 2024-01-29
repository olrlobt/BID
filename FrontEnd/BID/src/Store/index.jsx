import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { modalSlice } from "./modalSlice";
import userReducer from "./UserSlice";

const rootReducer = combineReducers({
  user: userReducer,
  modal: modalSlice.reducer,
});
const store = configureStore({
  reducer: rootReducer,
});

export default store;
