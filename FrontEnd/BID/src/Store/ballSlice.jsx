import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  ballList: null,
};

export const ballSelector = (state) => state.seatBall.ballList;

export const ballSlice = createSlice({
  name: "seatBall",
  initialState,
  reducers: {
    initBalls: (state, action) => {
      const ballList = action.payload;
      state.ballList = ballList;
    },

    removeBalls: (state, action) => {
      const ballNo = action.payload;
      const newBallsList = state.ballList.filter(
        (student) => student.no !== ballNo
      );
      state.ballList = [...newBallsList];
    },
  },
});

export const { initBalls, removeBalls } = ballSlice.actions;
