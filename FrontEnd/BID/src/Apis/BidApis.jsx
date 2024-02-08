import axios from "axios";

export const BidApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 경매 목록 가져오기
 * @param gradeNo 학급 넘버
 */
export const getProductListApi = async (gradeNo) => {
  return BidApis.get(`${gradeNo}/boards`);
}

/**
 * 경매 상세 열람하기
 * @param gradeNo 학급 넘버
 * @param boardNo 경매글 넘버
 */
export const getProductDetailApi = async (gradeNo, boardNo) => {
  return BidApis.get(`${gradeNo}/boards/${boardNo}`);
}