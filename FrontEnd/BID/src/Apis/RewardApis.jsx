import axios from "axios";

export const RewardApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 리워드 목록 가져오기
 * @param gradeNo 학급 넘버
 * @returns 리워드 목록
 */
export const getRewardList = async () => {
  return await RewardApis.get('1/rewards');
}

/**
 * 리워드 추가
 * @param gradeNo 학급 넘버
 */
export const addNewReward = async (newRewardForm) => {
  return await RewardApis.post('1/rewards', newRewardForm);
}