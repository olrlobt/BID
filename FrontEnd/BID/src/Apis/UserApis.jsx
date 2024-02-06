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
export const duplicateId = async (id) => {
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
  