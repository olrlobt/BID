import axios from 'axios';

export const StudentRewardsApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

export const StudentApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

/**
 * 학생 목록 가져오기
 * @param gradeNo 학급 넘버
 * @returns 학급 내 학생 목록
 */
export const getStudentListApi = async () => {
  return await StudentRewardsApis.get(`/1/users`);
};

/**
 * 학생 적금 내역 가져오기
 * @returns 적금 가입 내역
 */
export const getStudentSavingInfo = async () => {
  return await StudentApis.get('/savings');
};

/**
 * 적금 가입
 * @param savingInfo 가입할 적금 정보
 * @returns 201 OK
 */

export const applyStudentSaving = async (savingInfo) => {
  return await StudentApis.post('/savings', savingInfo);
};


