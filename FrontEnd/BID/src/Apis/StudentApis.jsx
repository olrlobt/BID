import axios from "axios";

export const StudentApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 학생 목록 가져오기
 * @param gradeNo 학급 넘버
 * @returns 학급 내 학생 목록
 */
export const getStudentList = async () => {
  return await StudentApis.get(`/1/users`);
};
