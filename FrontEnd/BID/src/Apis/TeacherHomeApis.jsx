import axios from "axios";

export const TeacherHomeApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 대시보드 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 대시보드 정보
 */
export const viewDashboard = async () => {
  return await TeacherHomeApis.get(`/1/statistics`);
};
