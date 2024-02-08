import axios from "axios";

export const TCHBidApis = axios.create({
  baseURL: process.env.REACT_APP_TCH_API,
});
export const STUBidApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

/************************************ 선생님 */
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
 * 경매 등록하기
 * @param productInfo 경매 정보
 */
export const addProductApi = async (productInfo) => {
  return await TCHBidApis.delete(`boards`, productInfo);
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
 * @param boardNo 경매글 넘버
 */
export const deleteCommentApi = async (gradeNo, boardNo, replyNos) => {
  return await TCHBidApis.delete(`${gradeNo}/boards/${boardNo}/${replyNos}`);
}


/************************************ 학생 */

/**
 * 댓글 작성하기
 * @param gradeNo 학급 넘버
 * @param boardNo 경매글 넘버
 */
export const addCommentApi = async (boardNo, commentInfo) => {
  return await STUBidApis.post(`boards/${boardNo}/reply`, commentInfo);
}

/**
 * 경매 입찰하기
 * @param boardNo 경매글 넘버
 */
export const firstBiddingApi = async (boardNo, biddingInfo) => {
  console.log(biddingInfo)
  return await STUBidApis.post(`boards/${boardNo}/bid`, biddingInfo);
}