import React from "react";
import styled from "./ViewProductModal.module.css";
import Modal from '../Common/Modal';
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { SvgIcon } from "@material-ui/core";
import { ArrowForward, Edit, Delete, Eject } from "@material-ui/icons";
import SubmitButton from "../Common/SubmitButton";
import Comment from "./Comment";
import SettingButton from "../Common/SettingButton"
import NoContent from "./NoContent";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getProductDetailApi } from "../../Apis/BidApis";

export default function ViewProductModal({ onClose, ...props }) {
  // 0 no,
  // 1 title,
  // 2 description,
  // 3 startPrice,
  // 4 boardStatus,
  // 5 averagePrice,
  // 6 resultPrice,
  // 7 category,
  // 8 goodsImgUrl,
  // 9 userName,
  // 10 gradePeriodNo,
  // 11 createdAt
  // 12 comments

  /** 경매 상세 쿼리 */
  const { data: productDetailIinfo } = useQuery({
    queryKey: ['getProductDetail'],
    queryFn: () =>
      getProductDetailApi(1, props[0]).then((res) => {
        if(res.data !== undefined){
          console.log(res.data);
          return res.data;
        }
      })
  });

  /** 입찰 신청 함수 */
  const bidSubmit = (e) => {
    e.preventDefault();
    const biddingPrice = e.target.price.value;
    if(biddingPrice==='' || biddingPrice===null || biddingPrice<1) {
      console.log('금액을 입력해주세요');
    } else{
      // 입찰 API
      console.log(biddingPrice+'비드 입찰되었습니다');
    }
  }

  /** 댓글 생성 함수 */
  const addNewComment = (e) => {
    e.preventDefault();
    console.log(e.target.newComment.value);
  }

  console.log(props);
  return(
    <Modal onClose={onClose} {...props}>
      <div className={styled.header}>
        <div className={styled.top}>
          <h1>{ productDetailIinfo.title }</h1>
          <span>{ productDetailIinfo.userName }</span>
        </div>
        <div className={styled.info}>
          <div className={styled.commonArea}>
            <div className = {styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo.category }
                unit = ''
                textColor = 'white'
                borderColor = '#BBBD32'
                backgroundColor = '#BBBD32'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className = {styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo.gradePeriodNo }
                unit = '교시'
                textColor = 'white'
                borderColor = '#BBBD32'
                backgroundColor = '#BBBD32'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className = {styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo.startPrice }
                unit = '비드'
                textColor = '#F23F3F'
                borderColor = '#F23F3F'
                backgroundColor = 'white'
                padding = '0.5vw 1vw'
              />
            </div>
            <div className = {styled.arrowArea}>
              <SvgIcon
                component = { ArrowForward }
                fontSize = 'medium'
              />
            </div>
            <div className = {styled.infoButton}>
              <RoundedInfoButton
                value = { productDetailIinfo.averagePrice }
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
              <form id='newProductForm' onSubmit={bidSubmit}>
                <input
                  type='number'
                  name='price'
                  className={styled.biddingNumber}
                />
                <div className={styled.biddingUnit}>비드</div>
                <SubmitButton
                  text = '입찰'
                  width = '7vw'
                  height = '3vw'
                  fontSize = '1.7vw'
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
                onClick={ () => console.log('delete') }
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
            <img src={ productDetailIinfo.goodsImgUrl } alt="제품 이미지" />
          </div>
          <div className={styled.content}>
            <textarea name='bidContent' defaultValue={ productDetailIinfo.description } disabled/>
          </div>
        </div>
        <div className={styled.right}>
          
          <div className={styled.commentArea}>
            <div className={styled.comments}>
            {
              props[12]===undefined?
              <NoContent text='아직 작성된 댓글이 없어요! 제일 먼저 달아볼까요? : )'/>
              :
              props[12].map((c) =>
                <Comment
                  key = {c.createdAt}
                  userNo = {c.userNo}
                  name = {c.name}
                  content = {c.content}
                  createdAt = {c.createdAt}
                  deleteAt = {c.deleteAt}
                />
              )
            }
            </div>
            <div className={styled.newComment}>
              <form onSubmit={addNewComment}>
                <textarea
                  name='newComment'
                  placeholder='댓글은 상냥하게 작성해주세요 : D'
                />
                <button type='submit'>
                  <SvgIcon
                    component={Eject}
                    style={{fill: '#FFD43A', height: '2vw'}}
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