// studentSlice.js

import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  students: null,
  selectedStudent: null,
};

export const studentSelector = (state) => {
  return state.student ? state.student.students : null;
};

export const studentSlice = createSlice({
  name: "student",
  initialState,
  reducers: {
    initStudents: (state, action) => {
      const students = action.payload;
      state.students = students;
    },
    addStudent: (state, action) => {
      const newStudent = action.payload;
      state.students = [...state.students, newStudent];
    },
    removeStudent: (state, action) => {
      const studentNo = action.payload;
      const newStudentList = state.students.filter((c) => c.no !== studentNo);
      state.students = [...newStudentList];
    },
    editStudent: (state, action) => {
      const updatedStudent = action.payload;
      const updatedStudents = state.students.map((student) =>
        student.no === updatedStudent.no ? updatedStudent : student
      );
      state.students = updatedStudents;
    },
  },
});

export const { initStudents, addStudent, removeStudent, editStudent } = studentSlice.actions;
