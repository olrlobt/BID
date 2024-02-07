import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  alarmList: null,
};

export const alarmSelector = (state) => state.alarmList.alarmList;

export const alarmSlice = createSlice({
  name: "alarmList",
  initialState,
  reducers: {
    updateAlarms: (state, action) => {
      const updatedList = action.payload;
      state.alarmList = [...state.alarmList, updatedList];
      state.alarmList.sort(
        (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
      );
    },
  },
});

export const { updateAlarms } = alarmSlice.actions;
