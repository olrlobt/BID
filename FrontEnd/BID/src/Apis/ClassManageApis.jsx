import axios from "axios";
import { getCookie } from "../cookie";

// axios 인스턴스 생성
export const ClassManageApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

ClassManageApis.interceptors.request.use(
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
 * 선생님 개인 학급 조회
 * @returns 학급 조회
 */
export const getGrades = async () => {
  console.log(">> 6+++ "+"getGrades");
  return await ClassManageApis.get("/grades");
};

/**
 * 학급 등록
 * @param 클래스 정보
 * @returns created 201
 */
export const AddClass = ({ classInfo }) => {
  return ClassManageApis.post("/grades", classInfo);
};

/**
 * 학급 삭제
 * @param gradeNo 삭제하고 싶은 학급 PK
 * @returns No_CONTENT 204
 */

export const deleteClass = (gradeNo) => {
  console.log(gradeNo);
  return ClassManageApis.delete(`/grades/${gradeNo}`);
};

/**
 * 메인 학급 변경
 * @param gradeNo 변경하고 싶은 메인 학급 PK
 * @returns 200 OK
 */

export const editMainClass = (gradeNo) => {
  console.log("gradeNo " + gradeNo);
  return ClassManageApis.patch("/grades", { no: gradeNo });
};
