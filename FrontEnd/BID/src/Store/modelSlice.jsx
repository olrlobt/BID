import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  models: [],
  modelLoggedIn: false,
  model: null,
};

export const modelListSelector = (state) => {
  return state.studentmodel.models;
};

export const modelLoggedInSelector = (state) => {
  return state.studentmodel.modelLoggedIn;
};

export const modelSelector = (state) => {
  return state.studentmodel.model;
};

export const modelSlice = createSlice({
  name: 'studentmodel',
  initialState,
  reducers: {
    initModels: (state, action) => {
      const models = action.payload;
      state.models = models;
    },
    addModel: (state, action) => {
      const newModel = action.payload;
      state.models = [...state.models, newModel];
    },
    editModel: (state, action) => {
      const updatedModel = action.payload;
      const updatedModels = state.models.map((model) =>
        model.no === updatedModel.no ? updatedModel : model
      );
      state.models = updatedModels;
    },
    loginStudent: (state, action) => {
      const model = action.payload;
      state.model = model;
      state.modelLoggedIn = true;
    },
    logoutStudent: (state) => {
      state.currentModel = null;
      state.modelLoggedIn = false;
    },
  },
});

export const { initModels, addModel, editModel, loginStudent, logoutStudent } =
  modelSlice.actions;
