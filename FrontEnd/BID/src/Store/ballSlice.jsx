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
      const newBallsList = state.ballList.map((student) => ({
        ...student,
        ballCount: 1,
      }));
      state.ballList = [...newBallsList];
    },
  },
});

export const { initBalls, removeBalls } = ballSlice.actions;
