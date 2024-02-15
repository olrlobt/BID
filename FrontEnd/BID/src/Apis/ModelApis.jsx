import axios from "axios";
import { getCookie } from "../cookie";

export const ModelApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

ModelApis.interceptors.request.use(
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
 * 학생 로그인
 * @param studentData 사용자 정보
 * @returns 선생님 이름, 학교 이름
 */
export const studentLoginApi = async (studentData) => {
    return await ModelApis.post(`/login`, studentData);
  };
  
/**
 * 학생 패스워드 수정
 * @param studentData 사용자 정보
 * @returns 선생님 이름, 학교 이름
 */
export const stuChangePwdApi = async (studentData) => {
  return await ModelApis.patch(`/password`, studentData);
};

/**
 * 학생 출석 체크
 * @param  사용자 정보
 */
export const stuAttendApi = async () => {
  return await ModelApis.patch(`/users/attendance/check`);
};

/**
 * 학생 출석 체크 확인 여부
 * @param  사용자 정보
 */
export const stuAttendCheckApi = async () => {
  return await ModelApis.get(`/users/attendance/exists`);
};

/**
 * 경매 입찰/업로드 현황
 * @param userNo 사용자 pk
 */
export const getMyBidListApi = async (userNo) => {
  return await ModelApis.get(`/users/${userNo}/boards`)
}

/**
 * 보유 아바타 현황
 * @param userNo 사용자pk
 */
export const getMyAvatarListApi = async (userNo) => {
  return await(ModelApis).get(`/${userNo}/avatars`);
}

/**
 * 아바타 수정
 * @param AvatarNo 아바타pk
 */
export const editAvatarApi = async (AvatarNo) => {
  return await ModelApis.patch(`/avatars`, AvatarNo); // body에 no: AvatarNo 넣어서 보내기
}


/**
 * 아바타 업데이트후 유저들 불러오기
 * @param 
 */
export const updateUsersApi = async () => {
  return await ModelApis.get(`/users`); // body에 no: AvatarNo 넣어서 보내기
}
