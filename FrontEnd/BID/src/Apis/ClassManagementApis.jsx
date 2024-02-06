import axios from "axios";

export const ClassManagementApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});
