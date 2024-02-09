import axios from 'axios';

export const TeacherManageApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 대시보드 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 대시보드 정보
 */
export const viewDashboard = async (gradeNo) => {
  return await TeacherManageApis.get(`/${gradeNo}/statistics`);
};

/**
 * 주급 수정
 * @param  salary 주급
 * @returns 200 OK
 */

export const changeSalaries = async (gradeNo, salary) => {
  return await TeacherManageApis.patch(`/${gradeNo}/salaries`, {
    salary: salary,
  });
};

/**
 * 은행 적금 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 은행 적금 정보
 */
export const viewSavingList = async (gradeNo) => {
  return await TeacherManageApis.get(`/${gradeNo}/savings`);
};

/**
 * 적금 정보 편집
 * @param gradeNo 학급 넘버
 */
export const updateSavingList = async (gradeNo, savingList) => {
  return await TeacherManageApis.patch(`/${gradeNo}/savings`, {
    savingRequests: savingList,
  });
};

/**
 * 학급 자리 공 개수 확인
 * @param gradeNo 학급 넘버
 * @returns 학급 학생 공 개수
 */

export const viewStudentBalls = async (gradeNo) => {
  return await TeacherManageApis.get(`/${gradeNo}/balls`);
};

/**
 * 학급 자리 공 개수 초기화
 * @param gradeNo 학급 넘버
 * @returns 학급 학생 공 개수
 */

export const resetStudentBalls = async (gradeNo) => {
  return await TeacherManageApis.patch(`/${gradeNo}/balls`);
};
