import axios from "axios";

// TCH Api는 gradeNo 받아와서 baseURL에 붙이기
export const TCHBidApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});

/**
 * 경매 목록 가져오기
 * @param gradeNo 학급 넘버
 */
export const getProductListApi = async (gradeNo) => {
  return await TCHBidApis.get(`${gradeNo}/boards`);
}

/**
 * 경매 상세 열람하기
 * @param gradeNo 학급 넘버
 * @param boardNo 경매글 넘버
 */
export const getProductDetailApi = async (gradeNo, boardNo) => {
  return await TCHBidApis.get(`${gradeNo}/boards/${boardNo}`);
}

/**
 * 경매 삭제하기
 * @param gradeNo 학급 넘버
 * @param boardNo 경매글 넘버
 */
export const deleteProductApi = async (gradeNo, boardNo) => {
  return await TCHBidApis.delete(`${gradeNo}/boards/${boardNo}`);
}

/**
 * 댓글 삭제하기
 * @param gradeNo 학급 넘버
 * @param boardNo 경매글 넘버
 * @param replyNos 댓글 넘버
 */
export const deleteCommentApi = async (gradeNo, boardNo, replyNos) => {
  return await TCHBidApis.delete(`${gradeNo}/boards/${boardNo}/${replyNos}`);
}