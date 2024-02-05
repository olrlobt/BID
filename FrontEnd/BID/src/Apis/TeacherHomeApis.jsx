import axios from 'axios';

const TEACHER_URL = 'http://i10a306.p.ssafy.io:8081';

export const TeacherHomeApis = axios.create({
  baseURL: TEACHER_URL,
});

/**
 * 대시보드 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 대시보드 정보
 */
export const viewDashboard = async () => {
  return await TeacherHomeApis.get(`/1/statistics`);
};
