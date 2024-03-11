import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isHold: false,
};

export const holdSelector = (state) => {
  return state.holdBtn.isHold;
};

export const holdSlice = createSlice({
  name: "holdBtn",
  initialState,
  reducers: {
    changeHold: (state, action) => {
      const holdState = action.payload;
      state.isHold = holdState;
    },
  },
});

export const { changeHold } = holdSlice.actions;
