import React, { useState } from "react";
import styled from "./ManageProductModal.module.css";
import Modal from "../Common/Modal";
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { SvgIcon } from "@material-ui/core";
import { Eject, Edit, Delete } from "@material-ui/icons";
import SubmitButton from "../Common/SubmitButton";
import Comment from "./Comment";
import SettingButton from "../Common/SettingButton"
import NoContent from "./NoContent";
import DropDownSelect from '../Common/DropDownSelect';
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getProductDetailApi, patchProductApi, deleteProductApi, addCommentApi, biddingApi } from "../../Apis/StudentBidApis";
import { useSelector } from 'react-redux';
import { modelSelector } from '../../Store/modelSlice';
import useProducts from "../../hooks/useProducts";

export default function ViewProductModal({ onClose, ...props }) {
  const boardNo = props[0];
  
  const currentUser = useSelector(modelSelector);
  console.log(currentUser.model);
  const nowUserId = currentUser.model.no;

  const queryClient = useQueryClient();
  const { deleteProduct } = useProducts();

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [averagePrice, setAveragePrice] = useState(0);
  const [description, setDescription] = useState('');
  const [comments, setComments] = useState([]);
  const [isSetting, setIsSetting] = useState(false);

  /** 경매 상세 쿼리 */
  const { data: productDetailIinfo } = useQuery({
    queryKey: ['getProductDetailSTU'],
    queryFn: () =>
      getProductDetailApi(boardNo).then((res) => {
        if(res.data !== undefined){
          console.log(res.data);
          setTitle(res.data.title);
          setCategory(res.data.category);
          setAveragePrice(res.data.averagePrice);
          setDescription(res.data.description);
          setComments(res.data.comments);
        }
        return res.data;
      })
  });

  /** 경매 수정 쿼리 */
  const patchProductQuery = useMutation({
    mutationKey: ['patchProduct'],
    mutationFn: (params) => patchProductApi(params.boardNo, params.productInfo),
    onSuccess: () => { queryClient.invalidateQueries('getProductDetail');; },
    onError: (error) => { console.log(error); }
  })

  /** 경매 삭제 쿼리 */
  const deleteProductQuery = useMutation({
    mutationKey: ['deleteProduct'],
    mutationFn: (boardNo) => deleteProductApi(boardNo),
    onSuccess: () => { deleteProduct({productNo: boardNo}); },
    onError: (error) => { console.log(error);}
  });

  /** 입찰 쿼리 */
  const biddingQuery = useMutation({
    mutationKey: ['bidding'],
    mutationFn: (params) => biddingApi(params.boardNo, params.biddingInfo),
    onSuccess: (res) => { console.log(res); },
    onError: (error) => { console.log(error); }
  });

  /** 댓글 작성 쿼리 */
  const addCommentQuery = useMutation({
    mutationKey: ['addComment'],
    mutationFn: (params) => addCommentApi(params.boardNo, params.commentInfo),
    onSuccess:() => { queryClient.invalidateQueries('getProductDetail'); },
    onError: (error) => { console.log(error); }
  })

  /** 경매 수정 함수 */
  const toggleSettingMode = () => {
    if(isSetting){
      const productInfo = {
        title: title,
        description: description,
        category: category
      }
      const params = {
        boardNo: boardNo,
        productInfo: productInfo
      }
      console.log(params)
      patchProductQuery.mutate(params);
    }
    setIsSetting(!isSetting);
  }

  /** 경매 삭제 함수 */
  const onClickDeleteProduct = (e) => {
    deleteProductQuery.mutate(boardNo);
    onClose();
  }

  /** 입찰 신청 함수 */
  const bidSubmit = (e) => {
    e.preventDefault();
    const biddingPrice = e.target.price.value;
    if(biddingPrice==='' || biddingPrice===null || biddingPrice<1) {
      console.log('금액을 입력해주세요');
    } else{
      const biddingInfo = {
        price: e.target.price.value
      }
      const params = {
        boardNo: boardNo,
        biddingInfo: biddingInfo
      }
      biddingQuery.mutate(params);
      // console.log(biddingPrice+'비드 입찰되었습니다');
    }
  };

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
      e.target.newComment.value = '';
    }
  }

  /** 날짜 형식 변환 함수 */
  const trimmedCreateAt = (originalDate) => {
    let trimmedDate = new Date(originalDate);
    const year = trimmedDate.getFullYear();
    const month = trimmedDate.getMonth() + 1;
    const day = trimmedDate.getDate();
    const hours = trimmedDate.getHours();
    const minutes = trimmedDate.getMinutes();
    trimmedDate = year+"."+month+"."+day+" "+hours+":"+minutes;
    return trimmedDate;
  }

  return (
    <>
    {
      productDetailIinfo &&
      <Modal onClose={onClose} {...props}>
      <div className={styled.wrapper}>
        <div className={styled.left}>
          {
            productDetailIinfo.userNo===nowUserId?
            <div className={styled.header}>
              <SettingButton
                onClick={ toggleSettingMode }
                svg={ Edit }
                text='수정'
                height='1vw'
                backgroundColor='#A6A6A6'
              />
              <SettingButton
                onClick={ onClickDeleteProduct }
                svg={ Delete }
                text='삭제'
                height='1vw'
                backgroundColor='#F23F3F'
              />
            </div>
            :
            null
          }
          <div className={styled.content}>
            <div className={styled.imgArea}>
              <img src='https://img.freepik.com/premium-psd/chocolate-3d-render_553817-59.jpg?w=2000' alt='' />
            </div>
            <div className={styled.infoArea}>
              <input 
                type='text'
                defaultValue={title}
                onChange={ (e) => setTitle(e.target.value) }
                disabled={!isSetting}
              />
              {
                isSetting?
                <DropDownSelect
                  selectName='category'
                  selectTitle='상품 유형'
                  options={[
                    {'value': 'SNACK', 'text': '간식'},
                    {'value': 'LEARNING', 'text': '학습'},
                    {'value': 'GAME', 'text': '오락'},
                    {'value': 'ETC', 'text': '기타'},
                  ]}
                  onChange={ (e) => setCategory(e.target.value) }
                />
                :
                <>
                <RoundedInfoButton
                  value = {category}
                  unit = ''
                  textColor = '#ff3f3f'
                  borderColor = '#ff3f3f'
                  backgroundColor = 'white'
                  padding = '0.5vw 1vw'
                />
                <RoundedInfoButton
                  value = {productDetailIinfo.gradePeriodNo}
                  unit = '교시'
                  textColor = '#ff3f3f'
                  borderColor = '#ff3f3f'
                  backgroundColor = 'white'
                  padding = '0.5vw 1vw'
                />
                </>
              }
            </div>
            {
              isSetting?
              null
              :
              <div className={styled.priceArea}>
                <div className={styled.verticalLine}></div>
                <div className={styled.startPrice}>
                  <div className={styled.priceCategory}>시작가</div>
                  <div className={styled.price}>{productDetailIinfo.startPrice}비드</div>
                </div>
                <div className={styled.verticalLine}></div>
                <div className={styled.averagePrice}>
                  <div className={styled.priceCategory}>평균가</div>
                  <div className={`${styled.price} ${styled.average}`}>{averagePrice}비드</div>
                </div>
                <div className={styled.verticalLine}></div>
              </div>
            }
            
          </div>
          {
            productDetailIinfo.userNo===nowUserId?
            null
            :
            <div className={styled.footer}>
              <div className={styled.notWriterArea}>
                <form onSubmit={bidSubmit}>
                  <input
                    type="number"
                    name="price"
                    className={styled.biddingNumber}
                  />
                  <div className={styled.biddingUnit}>비드</div>
                  <SubmitButton
                    text="입찰"
                    width="7vw"
                    height="4vw"
                    fontSize="1.7vw"
                  />
                </form>
              </div>
            </div>
          }
        </div>

        <div className={styled.right}>
          <div className={styled.commentsArea} style={isSetting? {height: '100%'}: {height: '42vw'}}>
            <div className={styled.writerArea}>
              <div className={styled.left}>
                <div className={styled.descHeader}>
                  <h3>{ productDetailIinfo.userName }</h3>
                  <div>{ trimmedCreateAt(productDetailIinfo.createdAt) }</div>
                </div>
                <div className={styled.descBody}>
                  <textarea
                    defaultValue={ description }
                    disabled={!isSetting}
                    onChange={ (e) => setDescription(e.target.value) }
                    />
                </div>
              </div>
              <div className={styled.right}>
                <img src={ productDetailIinfo.userProfileImgUrl } alt='프로필 이미지' />
              </div>
            </div>
            {
              isSetting?
              null
              :
              <div className={styled.othersArea}>
              {
                comments.length===0?
                <NoContent text='아직 작성된 댓글이 없어요'/>
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
                    isWriter = {productDetailIinfo.userNo===c.userNo} // 게시글 작성자가 쓴 댓글인가
                    isDelete = {c.userNo===nowUserId} // 현재 로그인한 유저가 쓴 댓글인가
                    isTeacher = {false}
                  />
                )
              }
              </div>
            }
            
          </div>
          {
            isSetting?
            null:
            <div className={styled.newCommentArea}>
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
          }
          
        </div>
      </div>
    </Modal>
    }
  </>
  );
}
