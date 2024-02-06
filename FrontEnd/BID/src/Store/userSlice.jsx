import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  currentUser: null,
  isLoggedIn: false,
};

export const userSelector = (state) => {
  return state.user ? state.user.currentUser : null;
};

export const userLoggedInSelector = (state) => {
  return state.user ? state.user.isLoggedIn : false;
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loginUser: (state, action) => {
      const user = action.payload;
      state.currentUser = user;
      state.isLoggedIn = true;
    },
    logoutUser: (state) => {
      state.currentUser = null;
      state.isLoggedIn = false;
    },
  },
});

export const { loginUser, logoutUser } = userSlice.actions;
