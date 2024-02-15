import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  savingList: null,
};

export const studentSavingSelector = (state) => state.studentSaving.savingList;

export const studentSavingSlice = createSlice({
  name: "studentSaving",
  initialState,
  reducers: {
    initStudentSavingList: (state, action) => {
      state.savingList = action.payload;
    },

    changeStudentSavingList: (state, action) => {
      const savingNum = action.payload;
      const appliedList = state.savingList.find(
        (saving) => saving.savingNo === savingNum
      );
      if (appliedList) {
        appliedList.mySaving = true;
      }
    },
  },
});

export const { initStudentSavingList, changeStudentSavingList } =
  studentSavingSlice.actions;
