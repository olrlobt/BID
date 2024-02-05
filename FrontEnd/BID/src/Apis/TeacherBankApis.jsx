import axios from "axios";

export const TeacherBankApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 은행 적금 정보 확인
 * @param gradeNo 학급 넘버
 * @returns 은행 적금 정보
 */

export const viewSavingList = async () => {
  return await TeacherBankApis.get("/1/savings");
};
