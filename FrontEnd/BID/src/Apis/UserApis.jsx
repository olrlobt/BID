import axios from "axios";

export const UserApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 사용자 추가
 * @param userData 사용자 정보
 * @returns
 */
export const addUserApi = async (userData) => {
  return await UserApis.post(`/register`, userData);
};


/**
 * 아이디 중복 확인
 * @param id 작성 아이디
 * @returns
 */
export const duplicateIdApi = async (id) => {
    return await UserApis.get(`/check-id`,id);
  };
  

/**
 * 사용자 로그인
 * @param userData 사용자 정보
 * @returns
 */
export const loginUserApi = async (userData) => {
  return await UserApis.post(`/login`, userData);
  };
  

/**
* 사용자 아이디 찾기
* @param userData 사용자 정보
* @returns 해당 아이디 값 
*/

export const findIdApi = async (userData) => {
    return await UserApis.post(`/find-id`, userData);
  };
  

/**
* 비밀번호 재설정 휴대폰 코드 전송
* @param userData 해당 id, 전화번호
* @returns  
*/

export const sendCodeApi = async (userData) => {
  console.log(userData)
  return await UserApis.post(`/password/code/send`, userData);
};


/**
* 비밀번호 재설정 휴대폰 코드 인증
* @param userData 해당 id, 전화번호, verifyCode
* @returns 해당 관리자 pk, isAuthenticated(true/false)
*/

export const authenticateApi = async (userData) => {
  return await UserApis.post(`/password/code/authenticate`, userData);
};


/**
* 비밀번호 재설정
* @param userNo 새 비밀번호, 새 비밀번호 확인
* @returns 해당 관리자 pk, isAuthenticated(true/false)
*/

export const changePwdApi = async (userNo, userData) => {
  return await UserApis.patch(`/password/`+userNo, userData);
};

