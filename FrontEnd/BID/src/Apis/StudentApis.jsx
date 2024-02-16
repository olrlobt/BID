import axios from 'axios';
import { getCookie } from '../cookie';

export const StudentApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

StudentApis.interceptors.request.use(
  (config) => {
    config.headers['Content-Type'] = 'application/json';
    config.headers['Authorization'] = `Bearer ${getCookie('accessToken')}`;

    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

/**
 * 학생 적금 내역 가져오기
 * @returns 적금 가입 내역
 */
export const getStudentSavingInfo = async () => {
  return await StudentApis.get(`/savings`);
};

/**
 * 적금 가입
 * @param savingInfo 가입할 적금 정보
 * @returns 201 OK
 */

export const applyStudentSaving = async (savingInfo) => {
  return await StudentApis.post('/savings', savingInfo);
};
