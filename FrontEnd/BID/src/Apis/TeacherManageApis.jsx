import axios from "axios";
import { getCookie } from "../cookie";

export const TeacherManageApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

TeacherManageApis.interceptors.request.use(
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
 * 경매 중단 수업시간
 * @param gradeNo 학급 넘버
 * @param parseUpdatedTime 업데이트 되는 시간
 * @returns 200OK
 */
export const changeStopTime = async (gradeNo, parseUpdatedTime) => {
  return await TeacherManageApis.patch(`/${gradeNo}/grade-periods`, {
    gradePeriodUpdateRequests: parseUpdatedTime,
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

/**
 * 학생 정보 가져오기
 * @param gradeNo 학급 넘버
 * @param userNo 학생 번호
 * @param startDate 시작 일자
 * @param endDate 마지막 일자
 * @returns
 */

export const viewStudentDetail = (gradeNo, userNo, startDate, endDate) => {
  return TeacherManageApis.get(
    `/${gradeNo}/users/${userNo}?startDate=${startDate}&endDate=${endDate}`
  );
};
