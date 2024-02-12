import React, { useState } from "react";
import styled from "./ViewProductModal.module.css";
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
import { getProductDetailApi, deleteProductApi } from "../../Apis/TeacherBidApis";
// import { addCommentApi, biddingApi } from "../../Apis/StudentBidApis";

export default function ViewProductModal({ onClose, ...props }) {
  const boardNo = props[0];
  const parentQueryClient = props[1];

  const queryClient = useQueryClient();

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [averagePrice, setAveragePrice] = useState(0);
  const [description, setDescription] = useState('');
  const [comments, setComments] = useState([]);
  const [userType, /*setUserType*/] = useState('writer');
  const [isSetting, setIsSetting] = useState(false);

  /** 경매 상세 쿼리 */
  const { data: productDetailIinfo } = useQuery({
    queryKey: ['getProductDetail'],
    queryFn: () =>
      getProductDetailApi(1, boardNo).then((res) => {
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

  /** 경매 삭제 쿼리 */
  const deleteProductQuery = useMutation({
    mutationKey: ['deleteProduct'],
    mutationFn: (boardNo) => deleteProductApi(1, boardNo),
    onSuccess: () => { parentQueryClient.invalidateQueries('productList') },
    onError: (error) => { console.log(error);}
  });

  /** 댓글 작성 쿼리 */
  // const addCommentQuery = useMutation({
  //   mutationKey: ['addComment'],
  //   mutationFn: (params) => addCommentApi(params.boardNo, params.commentInfo),
  //   onSuccess:() => { queryClient.invalidateQueries('getProductDetail'); },
  //   onError: (error) => { console.log(error); }
  // })

  /** 첫 입찰 쿼리 */
  // const biddingQuery = useMutation({
  //   mutationKey: ['bidding'],
  //   mutationFn: (params) => { biddingApi(params.boardNo, params.biddingInfo); },
  //   onSuccess: (res) => { console.log(res); },
  //   onError: (error) => { console.log(error); }
  // });

  /** 경매 삭제 함수 */
  const deleteProduct = (e) => {
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
  // const addNewComment = (e) => {
  //   e.preventDefault();
  //   const comment = e.target.newComment.value;
  //   if(comment === ''){
  //     console.log('댓글을 입력해주세요');
  //   }else{
  //     const commentInfo = {
  //       content: comment
  //     }
  //     const params = {
  //       boardNo: boardNo,
  //       commentInfo: commentInfo
  //     }
  //     addCommentQuery.mutate(params);
  //   }
  // }

  return (
    <>
    {
      productDetailIinfo &&
      <Modal onClose={onClose} {...props}>
      <div className={styled.wrapper}>
        <div className={styled.left}>
          {
            userType==='reader'?
            null:
            <div className={styled.header}>
              {
                userType==='writer'?
                <>
                <SettingButton
                  onClick={ () => setIsSetting(!isSetting) }
                  svg={ Edit }
                  text='수정'
                  height='1vw'
                  backgroundColor='#A6A6A6'
                />
                </>
                :
                null
              }
              {
                !isSetting?
                  <SettingButton
                    onClick={ deleteProduct }
                    svg={ Delete }
                    text='삭제'
                    height='1vw'
                    backgroundColor='#F23F3F'
                  />
                  :
                  null
              }
              
            </div>
          }
          <div className={styled.content}>
            <div className={styled.imgArea}>
              <img src='https://img.freepik.com/premium-psd/chocolate-3d-render_553817-59.jpg?w=2000' alt='' />
            </div>
            <div className={styled.infoArea}>
              <input 
                type='text'
                defaultValue={title}
                disabled={!isSetting}
              />
              {
                isSetting?
                <DropDownSelect
                  selectName='category'
                  selectTitle='상품 유형'
                  options={[
                    {'value': '간식', 'text': '간식'},
                    {'value': '학습', 'text': '학습'},
                    {'value': '오락', 'text': '오락'},
                    {'value': '기타', 'text': '기타'},
                  ]}
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
            userType==='reader'?
            <div className={styled.footer}>
              <div className={styled.notWriterArea}>
                <form id="newProductForm">
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
            :
            null
          }
        </div>

        <div className={styled.right}>
          <div className={styled.commentsArea}>
            <div className={styled.writerArea}>
              <Comment
                key = {-1}
                boardNo = {boardNo}
                replyNo = {-1}
                userName = {productDetailIinfo.userName}
                content = {description}
                createAt = {productDetailIinfo.createdAt}
                userImgUrl = {productDetailIinfo.userProfileImgUrl}
                isWriter = {true}
              />
            </div>
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
                  isWriter = {false}
                  isDelete = {userType==='teacher'/* || productDetailIinfo.userNo===c.userNo*/}
                />
              )
            }
            </div>
          </div>
          {
            userType==='teacher' || isSetting?
            null:
            <div className={styled.newCommentArea}>
              <form /*onSubmit={addNewComment}*/>
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
