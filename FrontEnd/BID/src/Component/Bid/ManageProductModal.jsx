import React, { useState } from "react";
import styled from "./ManageProductModal.module.css";
import Modal from "../Common/Modal";
import RoundedInfoButton from "../Common/RoundedInfoButton";
import { Delete } from "@material-ui/icons";
import Comment from "./Comment";
import SettingButton from "../Common/SettingButton"
import NoContent from "./NoContent";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getProductDetailApi, deleteProductApi } from "../../Apis/TeacherBidApis";
import useProducts from "../../hooks/useProducts";

export default function ManageProductModal({ onClose, ...props }) {
  const gradeNo = props[0];
  const boardNo = props[1];

  const { deleteProduct } = useProducts();

  const queryClient = useQueryClient();

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [averagePrice, setAveragePrice] = useState(0);
  const [description, setDescription] = useState('');
  const [comments, setComments] = useState([]);

  /** 경매 상세 쿼리 */
  const { data: productDetailIinfo } = useQuery({
    queryKey: ['getProductDetailTCH'],
    queryFn: () =>
      getProductDetailApi(gradeNo, boardNo).then((res) => {
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
    mutationFn: (boardNo) => deleteProductApi(gradeNo, boardNo),
    onSuccess: () => { deleteProduct({productNo: boardNo}); },
    onError: (error) => { console.log(error);}
  });

  /** 경매 삭제 함수 */
  const onClickDeleteProduct = () => {
    if(window.confirm('게시글을 삭제하시겠습니까?')){
      deleteProductQuery.mutate(boardNo);
      onClose();
    }
  }

  return (
    <>
    {
      productDetailIinfo &&
      <Modal onClose={onClose} {...props}>
      <div className={styled.wrapper}>
        <div className={styled.left}>
          <div className={styled.header}>
            <SettingButton
              onClick={ onClickDeleteProduct }
              svg={ Delete }
              text='삭제'
              height='1vw'
              backgroundColor='#F23F3F'
            />
          </div>
          <div className={styled.content}>
            <div className={styled.imgArea}>
              <img src='https://img.freepik.com/premium-psd/chocolate-3d-render_553817-59.jpg?w=2000' alt='' />
            </div>
            <div className={styled.infoArea}>
              <input 
                type='text'
                defaultValue={title}
                disabled={true}
              />
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
            </div>
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
          </div>
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
                isSetting = {false}
                isTeacher = {false}
                gradeNo = {gradeNo}
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
                    isDelete = {true}
                    isTeacher = {true}
                    gradeNo = {gradeNo}
                  />
                )
              }
              </div>
          </div>
        </div>
      </div>
    </Modal>
  }
  </>
  );
}
