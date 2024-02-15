import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  classTime: [],
};

export const stopTimeSelector = (state) => state.stopTime.classTime;

export const stopTimeSlice = createSlice({
  name: 'stopTime',
  initialState,
  reducers: {
    initTime: (state, action) => {
      const timeList = action.payload;
      state.classTime = timeList;
    },

    changeTime: (state, action) => {
      // 이 때 시간대 별로 sort 해야 할듯
      action.payload.forEach(({ no, startPeriod, endPeriod }) => {
        const timeList = state.classTime.find((time) => time.no === no);
        if (timeList) {
          timeList.startPeriod = startPeriod;
          timeList.endPeriod = endPeriod;
        }
      });
    },
  },
});

export const { initTime, changeTime } = stopTimeSlice.actions;
