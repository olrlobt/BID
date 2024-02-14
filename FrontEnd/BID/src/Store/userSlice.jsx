import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  currentUser: null,
  isLoggedIn: false,
};

export const userSelector = (state) => {
  return state.user.currentUser;
};

export const userLoggedInSelector = (state) => {
  return state.user.isLoggedIn;
};

export const userSlice = createSlice({
  name: "user",
  initialState: initialState,
  reducers: {
    loginUser: (state, action) => {
      const currentUser = action.payload.data;
      state.currentUser = currentUser;
      state.isLoggedIn = true;
    },
    logoutUser: (state) => {
      state.currentUser = null;
      state.isLoggedIn = false;
    },
  },
});

export const { loginUser, logoutUser } = userSlice.actions;
