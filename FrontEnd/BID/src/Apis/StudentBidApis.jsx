import axios from "axios";
import { getCookie } from "../cookie";

export const STUBidApis = axios.create({
  baseURL: process.env.REACT_APP_STU_API,
});

STUBidApis.interceptors.request.use(
  (config) => {
    // config.headers["Content-Type"] = "application/json";
    config.headers["Authorization"] = `Bearer ${getCookie("accessToken")}`;

    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

/**
 * 경매 목록 가져오기
 */
export const getProductListApi = async () => {
  return await STUBidApis.get(`boards`);
};

/**
 * 경매 상세 열람하기
 * @param boardNo 경매글 넘버
 */
export const getProductDetailApi = async (boardNo) => {
  return await STUBidApis.get(`boards/${boardNo}`);
};

/**
 * 경매 등록하기
 * @param productInfo 새 경매 정보
 */
export const addProductApi = async (productInfo) => {
  return await STUBidApis.post(`boards`, productInfo);
};

/**
 * 이미지 url 발급하기
 * @param imageFile 이미지 파일
 */
export const getImageUrlApi = async (imageFile) => {
  for (const pair of imageFile.entries()) {
    console.log(pair[0], pair[1]);
  }
  return await STUBidApis.post(`boards/images`, imageFile, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}

/**
 * 경매 수정하기
 * @param boardNo 경매글 넘버
 * @param productInfo 새 경매 정보
 */
export const patchProductApi = async (boardNo, productInfo) => {
  return await STUBidApis.patch(`boards/${boardNo}`, productInfo);
};

/**
 * 경매 삭제하기
 * @param boardNo 경매글 넘버
 */
export const deleteProductApi = async (boardNo) => {
  return await STUBidApis.delete(`boards/${boardNo}`);
};

/**
 * 댓글 등록하기
 * @param boardNo 경매글 넘버
 * @param commentInfo 새 댓글 정보
 */
export const addCommentApi = async (boardNo, commentInfo) => {
  return await STUBidApis.post(`boards/${boardNo}/reply`, commentInfo);
};

/**
 * 댓글 삭제하기
 * @param boardNo 경매글 넘버
 * @param replyNo 댓글 넘버
 */
export const deleteCommentApi = async (boardNo, replyNo) => {
  return await STUBidApis.delete(`boards/${boardNo}/reply/${replyNo}`);
};

/**
 * 경매 입찰하기
 * @param boardNo 경매글 넘버
 * @param biddingInfo 입찰 정보
 */
export const biddingApi = async (boardNo, biddingInfo) => {
  return await STUBidApis.post(`boards/${boardNo}/bid`, biddingInfo);
};
