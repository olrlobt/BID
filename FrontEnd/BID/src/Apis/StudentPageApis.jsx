import axios from "axios";

export const StudentPageApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

/**
 * 학생 로그인
 * @param studentData 사용자 정보
 * @returns 선생님 이름, 학교 이름
 */
export const studentLoginApi = async (studentData) => {
    return await StudentPageApis.post(`/login`, studentData);
  };
  
  