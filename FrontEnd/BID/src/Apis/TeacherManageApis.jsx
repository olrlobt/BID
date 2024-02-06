import axios from "axios";

export const TeacherManageApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 대시보드 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 대시보드 정보
 */
export const viewDashboard = async () => {
  return await TeacherManageApis.get(`/1/statistics`);
};

/**
 * 은행 적금 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 은행 적금 정보
 */

export const viewSavingList = async () => {
  return await TeacherManageApis.get("/1/savings");
};

/**
 * 적금 정보 편집
 * @param gradeNo 학급 넘버
 */
export const updateSavingList = (savingList) => {
  return TeacherManageApis.patch("/1/savings", { savingRequests: savingList });
};

/**
 * 학급 자리 공 개수 확인
 * @param gradeNo 학급 넘버
 * @returns 학급 학생 공 개수
 */

export const viewStudentBalls = async () => {
  return await TeacherManageApis.get("/1/balls");
};
