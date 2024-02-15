import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  alarmList: [],
};

export const alarmSelector = (state) => state.alarmList.alarmList;

export const alarmSlice = createSlice({
  name: "alarmList",
  initialState,
  reducers: {
    initAlarms: (state, action) => {
      const alarmList = action.payload;
      state.alarmList = alarmList;
    },

    updateAlarms: (state, action) => {
      const updatedList = action.payload;
      let tmpList = [...state.alarmList, updatedList];
      state.alarmList = [...tmpList];
    },
    removeAlarms: (state, action) => {},
  },
});

export const { initAlarms, updateAlarms, removeAlarms } = alarmSlice.actions;
