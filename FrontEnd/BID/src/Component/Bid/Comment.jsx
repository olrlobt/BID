import React from "react";
import styled from "./Comment.module.css";
import { Edit, Delete } from "@material-ui/icons";
import SettingButton from "../Common/SettingButton"

export default function Comment(props){
  const { name, content, createdAt } = props;

  return(
    <div className={styled.commentWrapper}>
      <div className={styled.left}>
        <div className={styled.profile}>
          {/* <img src="" alt="" /> */}
        </div>
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
          {
          <SettingButton
            onClick={ () => console.log('modify') }
            svg={ Edit }
            text='수정'
            height='1vw'
            backgroundColor='#A6A6A6'
          />
          }
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
  );
}