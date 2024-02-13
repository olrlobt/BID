import axios from "axios";
import { getCookie } from "../cookie";

export const ModelApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

ModelApis.interceptors.request.use(
  (config) => {
    config.headers["Content-Type"] = "application/json";
    config.headers["Authorization"] = `Bearer ${getCookie("accessToken")}`;

    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

/**
 * 학생 로그인
 * @param studentData 사용자 정보
 * @returns 선생님 이름, 학교 이름
 */
export const studentLoginApi = async (studentData) => {
  return await ModelApis.post(`/login`, studentData);
};
