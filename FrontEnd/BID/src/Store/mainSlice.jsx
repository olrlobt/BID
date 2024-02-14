import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  mainClass: [],
};

export const mainSelector = (state) => state.mainClass.mainClass;

export const mainSlice = createSlice({
  name: "mainClass",
  initialState,
  reducers: {
    initClass: (state, action) => {
      const main = action.payload;
      state.mainClass = main;
    },
  },
});

export const { initClass } = mainSlice.actions;
