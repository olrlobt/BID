import React from "react";
import styled from "./Comment.module.css";

export default function Comment(props){
  const { name, content, createdAt } = props;

  return(
    <div className={styled.commentWrapper}>
      <div className={styled.left}>
        <div className={styled.profile}></div>
        {/* <img src="" alt="" /> */}
      </div>
      <div className={styled.right}>
        <div className={styled.commentHeader}>
          <h3>{ name }</h3>
          <div>{ createdAt }</div>
        </div>
        <div className={styled.commentBody}>
          <div>{ content }</div>
        </div>
        <div className={styled.commentFooter}>
        </div>
      </div>
    </div>
  );
}