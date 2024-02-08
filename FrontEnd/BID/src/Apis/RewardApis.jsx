import axios from "axios";

export const RewardApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 리워드 목록 가져오기
 * @param gradeNo 학급 넘버
 * @returns 리워드 목록
 */
export const getRewardListApi = async (gradeNo) => {
  return await RewardApis.get(`${gradeNo}/rewards`);
}

/**
 * 리워드 추가
 * @param gradeNo 학급 넘버
 * @param newRewardForm 새 리워드 정보(name, price)
 */
export const addNewRewardApi = async (gradeNo, newRewardForm) => {
  return await RewardApis.post(`${gradeNo}/rewards`, newRewardForm);
}

/**
 * 리워드 삭제
 * @param rNo 리워드 번호
 */
export const deleteRewardApi = async (gradeNo, rNo) => {
  return await RewardApis.delete(`${gradeNo}/rewards/${rNo}`);

}

/**
 * 리워드 지급
 * @param postData 리워드 지급 정보
 */
export const sendRewardApi = async (gradeNo, postData) => {
  return await RewardApis.post(`${gradeNo}/rewards/send`, postData);
}