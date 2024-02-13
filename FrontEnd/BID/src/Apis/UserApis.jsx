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
  console.log(userData)
  return await UserApis.post(`/register`, userData);
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
  console.log(userData)
  return await UserApis.patch(`/password`, userData);
};




/**
* 학교 검색
* @param schoolName 새 비밀번호, 새 비밀번호 확인
* @returns 해당 관리자 pk, isAuthenticated(true/false)
*/

export const searchSchoolApi = async (schoolName) => {
  return await UserApis.get(`/schools`,{ params: { name: schoolName } });
};


/**
* 회원가입 전 휴대폰 코드 전송
* @param tel 전화번호
* @returns  
*/

export const registerCodeApi = async (tel) => {
  console.log(tel)
  return await UserApis.post(`/send-code`, tel);
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