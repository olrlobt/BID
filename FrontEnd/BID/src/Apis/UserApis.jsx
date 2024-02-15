import axios from "axios";
import { getCookie } from "../cookie";

export const UserApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

UserApis.interceptors.request.use(
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
   * 회원가입
   * @param userData 사용자 정보
   * @returns
  */
 export const addUserApi = async (userData) => {
   console.log(userData);
   return await UserApis.post(`/register`, userData);
  };
  
  /**
   * 회원가입 전 휴대폰 코드 전송
   * @param tel 전화번호
   * @returns
   */
  
  export const registerCodeApi = async (tel) => {
    console.log(tel);
    return await UserApis.post(`/send-code`, tel);
  };

  /**
   * 아이디 찾기
   * @param userData 사용자 정보
 * @returns 해당 아이디 값
 */

export const findIdApi = async (userData) => {
  return await UserApis.post(`/find-id`, userData);
};

/**
 * 아이디 중복 확인
 * @param id 작성 아이디
 * @returns
 */
export const duplicateIdApi = async (userData) => {
  console.log(userData);
  return await UserApis.get(`/check-id`, { params: { id: userData } });
};

/**
 * 로그인
 * @param userData 사용자 정보
 * @returns
 */
export const loginUserApi = async (userData) => {
  return await UserApis.post(`/login`, userData);
};

/**
 * 로그아웃
 * @param userData 사용자 정보
 * @returns
 */
export const logoutUserApi = async (userData) => {
  return await UserApis.get(`/signout`, userData);
};




/**
 * 비밀번호 재설정 휴대폰 코드 전송
 * @param userData 해당 id, 전화번호
 * @returns
 */

export const sendCodeApi = async (userData) => {
  console.log(userData);
  return await UserApis.post(`/password/send-code`, userData);
};

/**
 * 휴대폰 코드 인증
 * @param userData 해당 id, 전화번호, verifyCode
 * @returns 해당 관리자 pk, isAuthenticated(true/false)
 */

export const authenticateApi = async (userData) => {
  return await UserApis.post(`/check-code`, userData);
};

/**
 * 비밀번호 재설정
 * @param userNo 새 비밀번호, 새 비밀번호 확인
 * @returns 해당 관리자 pk, isAuthenticated(true/false)
 */

export const changePwdApi = async (userData) => {
  console.log(userData);
  return await UserApis.patch(`/password`, userData);
};

/**
 * 학교 검색
 * @param schoolName 새 비밀번호, 새 비밀번호 확인
 * @returns 해당 관리자 pk, isAuthenticated(true/false)
 */

export const searchSchoolApi = async (schoolName) => {
  return await UserApis.get(`/schools`, { params: { name: schoolName } });
};


/**
 * 학생 등록하기
 * @param gradeNo schoolNo도 필요
 * @returns 학급 내 학생 목록
 */
export const addStudentApi = async () => {
  return await UserApis.post(`/students`);
};

/**
 * 학생 편집하기
 * @param gradeNo schoolNo도 필요
 * @returns 학급 내 학생 목록
 */
export const editStudentApi = async () => {
  return await UserApis.post(`/students`);
};


/**
 * 회원 탈퇴
 * @param gradeNo
 * @param userNo 
 * @param userData 사용자 정보
 */
export const deleteUserApi = async (gradeNo, userNo, userData) => {
  const requestData = {
    data: { password: userData.password } // 사용자 비밀번호만 필요한 형태로 데이터 조정
  };

  return await UserApis.delete(`${gradeNo}/users/${userNo}`, requestData);
};