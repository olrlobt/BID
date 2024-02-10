import React from "react";
import styled from "./Comment.module.css";
import { Delete } from "@material-ui/icons";
import SettingButton from "../Common/SettingButton"
import { useMutation } from "@tanstack/react-query";
import { deleteCommentApi } from "../../Apis/TeacherBidApis";

export default function Comment(props){
  const { boardNo, replyNo, userName, content, createAt, userImgUrl, queryClient } = props;

  /** 댓글 삭제 쿼리 */
  const deleteCommentQuery = useMutation({
    mutationKey: ['deleteComment'],
    mutationFn: () => deleteCommentApi(1, boardNo, replyNo),
    onSuccess: () => { queryClient.invalidateQueries('getProductDetail'); },
    onError: (error) => { console.log(error); },
  });

  /** 댓글 삭제 함수 */
  const deleteComment = (e) => {
    e.preventDefault();
    deleteCommentQuery.mutate();
  }

  return(
    <div className={styled.commentWrapper}>
      <div className={styled.left}>
        <div className={styled.profile}>
          {/* <img src={ userImgUrl } alt="" /> */}
        </div>
      </div>
      <div className={styled.right}>
        <div className={styled.commentHeader}>
          <h3>{ userName }</h3>
          <div>{ (new Date(createAt)).toLocaleString('ko-KR') }</div>
        </div>
        <div className={styled.commentBody}>
          <div>{ content }</div>
        </div>
        <div className={styled.commentFooter}>
          <SettingButton
            onClick={ deleteComment }
            svg={ Delete }
            text='삭제'
            height='1vw'
            backgroundColor='#f23f3fd5'
          />
        </div>
      </div>
    </div>
  );
}