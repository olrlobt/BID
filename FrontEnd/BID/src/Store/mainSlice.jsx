import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  mainClass: [],
};

export const mainSelector = (state) => state.mainClass.mainClass;

export const mainSlice = createSlice({
  name: 'mainClass',
  initialState: initialState,
  reducers: {
    initClass: (state, action) => {
      const main = action.payload;
      console.log("sososososo initClass");
      console.log(main)
      state.mainClass = main;
      console.log("sososososo statestaestate");
      console.log(state.mainClass)
    },
    changeMainClass: (state, action) => {
      const changed = action.payload;

      if (state.mainClass !== changed) {
        state.mainClass = changed;
      }
    },
  },
});

export const { initClass, changeMainClass } = mainSlice.actions;
