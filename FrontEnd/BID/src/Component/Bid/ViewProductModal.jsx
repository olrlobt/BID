import React, { useState } from "react";
import styled from "./ViewProductModal.module.css";
import Modal from "../Common/Modal";
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { SvgIcon } from "@material-ui/core";
import { ArrowForward, Eject, Edit, Delete } from "@material-ui/icons";
// import SubmitButton from "../Common/SubmitButton";
import Comment from "./Comment";
import SettingButton from "../Common/SettingButton"
import NoContent from "./NoContent";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getProductDetailApi, deleteProductApi } from "../../Apis/TeacherBidApis";
import { addCommentApi, /*biddingApi*/ } from "../../Apis/StudentBidApis";

export default function ViewProductModal({ onClose, ...props }) {
  const boardNo = props[0];
  const parentQueryClient = props[1];
  const queryClient = useQueryClient();

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [description, setDescription] = useState('');
  const [comments, setComments] = useState([]);

  /** 경매 상세 쿼리 */
  const { data: productDetailIinfo } = useQuery({
    queryKey: ['getProductDetail'],
    queryFn: () =>
      getProductDetailApi(1, boardNo).then((res) => {
        if(res.data !== undefined){
          console.log(res.data);
          setTitle(res.data.title);
          setCategory(res.data.category);
          setDescription(res.data.description);
          setComments(res.data.comments);
        }
        return res.data;
      })
  });

  /** 경매 삭제 쿼리 */
  const deleteProductQuery = useMutation({
    mutationKey: ['delete'],
    mutationFn: (boardNo) => { deleteProductApi(1, boardNo) },
    onSuccess: () => { parentQueryClient.invalidateQueries('productList') },
    onError: (error) => { console.log("error;")}
  });

  /** 댓글 작성 쿼리 */
  const addCommentQuery = useMutation({
    mutationKey: ['addComment'],
    mutationFn: (params) => addCommentApi(params.boardNo, params.commentInfo),
    onSuccess:() => { queryClient.invalidateQueries('getProductDetail'); },
    onError: (error) => { console.log(error); }
  })

  /** 첫 입찰 쿼리 */
  // const biddingQuery = useMutation({
  //   mutationKey: ['bidding'],
  //   mutationFn: (params) => { biddingApi(params.boardNo, params.biddingInfo); },
  //   onSuccess: (res) => { console.log(res); },
  //   onError: (error) => { console.log(error); }
  // });

  /** 경매 삭제 함수 */
  const deleteProduct = () => {
    deleteProductQuery.mutate(boardNo);
    onClose();
  }

  /** 입찰 신청 함수 */
  // const bidSubmit = (e) => {
  //   e.preventDefault();
  //   const biddingPrice = e.target.price.value;
  //   if(biddingPrice==='' || biddingPrice===null || biddingPrice<1) {
  //     console.log('금액을 입력해주세요');
  //   } else{
  //     const biddingInfo = {
  //       price: e.target.price.value
  //     }
  //     const params = {
  //       boardNo: boardNo,
  //       biddingInfo:biddingInfo
  //     }
  //     biddingQuery.mutate(params);
  //     console.log(biddingPrice+'비드 입찰되었습니다');

  //   }
  // };

  /** 댓글 생성 함수 */
  const addNewComment = (e) => {
    e.preventDefault();
    const comment = e.target.newComment.value;
    if(comment === ''){
      console.log('댓글을 입력해주세요');
    }else{
      const commentInfo = {
        content: comment
      }
      const params = {
        boardNo: boardNo,
        commentInfo: commentInfo
      }
      addCommentQuery.mutate(params);
    }
  }

  return (
    <Modal onClose={onClose} {...props}>
      <div className={styled.header}>
        <div className={styled.top}>
          <h1>{ title }</h1>
          <span>{ productDetailIinfo && productDetailIinfo.userName }</span>
        </div>
        <div className={styled.info}>
          <div className={styled.commonArea}>
            <div className={styled.infoButton}>
              <RoundedInfoButton
                value = { category }
                unit = ''
                textColor = 'white'
                borderColor = '#BBBD32'
                backgroundColor = '#BBBD32'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className={styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo && productDetailIinfo.gradePeriodNo }
                unit = '교시'
                textColor = 'white'
                borderColor = '#BBBD32'
                backgroundColor = '#BBBD32'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className={styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo && productDetailIinfo.startPrice }
                unit = '비드'
                textColor = '#F23F3F'
                borderColor = '#F23F3F'
                backgroundColor = 'white'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className={styled.arrowArea}>
              <SvgIcon component={ArrowForward} fontSize="medium" />
            </div>
            <div className={styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo && productDetailIinfo.averagePrice }
                unit = '비드'
                textColor = 'white'
                borderColor = '#F23F3F'
                backgroundColor = '#F23F3F'
                padding = '0.5vw 1vw'
              />
            </div>
          </div>
          <div className={styled.isWriterArea}>
            {/* <div className={styled.notWriterArea}>
              <form id="newProductForm" onSubmit={bidSubmit}>
                <input
                  type="number"
                  name="price"
                  className={styled.biddingNumber}
                />
                <div className={styled.biddingUnit}>비드</div>
                <SubmitButton
                  text="입찰"
                  width="7vw"
                  height="3vw"
                  fontSize="1.7vw"
                />
              </form>
            </div> */}
            <div className={styled.writerArea}>
              <SettingButton
                onClick={ () => console.log('modify') }
                svg={ Edit }
                text='수정'
                height='1vw'
                backgroundColor='#A6A6A6'
              />
              <SettingButton
                onClick={ deleteProduct }
                svg={ Delete }
                text='삭제'
                height='1vw'
                backgroundColor='#F23F3F'
              />
            </div>
          </div>
        </div>
      </div>

      <div className={styled.body}>
        <div className={styled.left}>
          <div className={styled.imgArea}>
            <img src={ productDetailIinfo && productDetailIinfo.goodsImgUrl } alt="제품 이미지" />
          </div>
          <div className={styled.content}>
            <textarea name='bidContent' defaultValue={ description } disabled/>
          </div>
        </div>
        <div className={styled.right}>
          <div className={styled.commentArea}>
            <div className={styled.comments}>
            {
              comments.length===0?
              <NoContent text='아직 작성된 댓글이 없어요! 제일 먼저 달아볼까요? : )'/>
              :
              comments.map((c) =>
                <Comment
                  key = {c.replyNo}
                  boardNo = {boardNo}
                  replyNo = {c.replyNo}
                  userName = {c.userName}
                  content = {c.content}
                  createAt = {c.createAt}
                  userImgUrl = {c.userImgUrl}
                  queryClient = {queryClient}
                />
              )
            }
            </div>
            <div className={styled.newComment}>
              <form onSubmit={addNewComment}>
                <textarea
                  name="newComment"
                  placeholder="댓글은 상냥하게 작성해주세요 : D"
                />
                <button type="submit">
                  <SvgIcon
                    component={Eject}
                    style={{ fill: "#FFD43A", height: "2vw" }}
                  />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </Modal>
  );
}
