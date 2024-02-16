import React from "react";
import styled from "./Comment.module.css";
import { useMutation } from "@tanstack/react-query";
import { deleteCommentApi as TCHdeleteCommentApi } from "../../Apis/TeacherBidApis";
import { deleteCommentApi as STUdeleteCommentApi } from "../../Apis/StudentBidApis";

export default function Comment(props){
  const {
    boardNo,
    replyNo,
    userName,
    content,
    createAt,
    userImgUrl,
    queryClient,
    isWriter,
    isDelete,
    isSetting,
    isTeacher,
    handleDescription,
    gradeNo
  } = props;
  
  /** 날짜 형식 변환 */
  let trimmedCreateAt = new Date(createAt);
  const year = trimmedCreateAt.getFullYear();
  const month = trimmedCreateAt.getMonth() + 1;
  const day = trimmedCreateAt.getDate();
  const hours = trimmedCreateAt.getHours();
  const minutes = trimmedCreateAt.getMinutes();
  trimmedCreateAt = year+"."+month+"."+day+" "+hours+":"+minutes;

  /** 작성자일 경우 레이아웃 */
  const writerLayout = {
    flexDirection: 'row-reverse',
  }
  /** 작성자일 경우 박스 디자인 */
  const writerBox = {
    border: '0.3vw solid #ECECEC',
    backgroundColor: 'white'
  }

  /** [선생님] 댓글 삭제 쿼리 */
  const TCHdeleteCommentQuery = useMutation({
    mutationKey: ['TCHdeleteComment'],
    mutationFn: () => TCHdeleteCommentApi(gradeNo, boardNo, replyNo),
    onSuccess: () => { queryClient.invalidateQueries('getProductDetail'); },
    onError: (error) => { console.log(error); },
  });

  /** [학생] 댓글 삭제 쿼리 */
  const STUdeleteCommentQuery = useMutation({
    mutationKey: ['STUdeleteComment'],
    mutationFn: () => STUdeleteCommentApi(boardNo, replyNo),
    onSuccess: () => { queryClient.invalidateQueries('getProductDetail'); },
    onError: (error) => { console.log(error); },
  });

  /** 댓글 삭제 함수 */
  const deleteComment = () => {
    if(window.confirm('댓글을 삭제하시겠습니까?')){
      isTeacher?
      TCHdeleteCommentQuery.mutate():
      STUdeleteCommentQuery.mutate();
    }
  }

  return(
    <div className={styled.commentWrapper} style={isWriter? writerLayout: null}>
      <div className={styled.left}>
        <img src={ userImgUrl } alt='프로필 이미지' onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
      </div>
      <div className={styled.right} style={isWriter? writerBox: null}>
        <div className={styled.commentHeader}>
          <h3>{ userName }</h3>
          <div>{ trimmedCreateAt }</div>
          {
            isDelete?
            <div
              className={styled.deleteArea}
              onClick={deleteComment}
            >
              • 삭제
            </div>
            :
            null
          }
        </div>
        <div className={styled.commentBody}>
          <textarea
            defaultValue={content}
            disabled={!isSetting}
            onChange={handleDescription}
            />
        </div>
      </div>
    </div>
  );
}