import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  models: [],
  modelLoggedIn: false,
  model: null,
  modelImg: null
};

export const modelListSelector = (state) => {
  return state.studentmodel.models;
}

export const modelLoggedInSelector = (state) => {
  return state.studentmodel.modelLoggedIn;
};

export const modelSelector = (state) => {
  return state.studentmodel.model;
}

export const modelImgSelector = (state) => {
  return state.studentmodel.modelImg;
}
export const modelSlice = createSlice({
  name: "studentmodel",
  initialState,
  reducers: {
    initModels: (state, action) => {
      const models = action.payload;
      console.log(models)
      state.models = models;
    },
    addModel: (state, action) => {
      const newModel = action.payload;
      state.models = [...state.models, newModel];
    },
    editModel: (state, action) => {
      const updatedModel = action.payload;
      state.modelImg = updatedModel
    },
    loginStudent: (state, action) => {
        const model = action.payload;
        state.model = model;
        state.modelLoggedIn = true;
    },
    logoutStudent: (state) => {
      state.modelLoggedIn = false;
      state.model = null
      state.modelImg = null
      state.models = []
    },
  },
});

export const { initModels, addModel, editModel, loginStudent, logoutStudent } = modelSlice.actions;
