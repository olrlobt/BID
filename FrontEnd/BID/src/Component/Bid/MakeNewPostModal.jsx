import React from 'react';
import styled from './MakeNewPostModal.module.css';
import Modal from '../Common/Modal';
import DropDownSelect from '../Common/DropDownSelect';
import SquareButton from '../Common/SquareButton';

export default function MakeNewPostModal({ onClose, ...props }) {

  // let profileImage = '';

  /** 경매 등록 함수 */
  // const submitPost = (e) => {
  //   e.preventDefault();
  //   console.log(e.target);
  // }

  const changeProfileImage = async () => {
    console.log('img uploaded');
  }

  return(
    <Modal onClose={onClose} {...props}>
      <div className={styled.header}>
        <div className={styled.title}>
          <input
            type='text'
            name='title'
            placeholder='제목을 입력하세요'
          />
        </div>
        <div className={styled.productInfo}>
          <DropDownSelect
            selectName='category'
            name='category'
            selectTitle='상품 유형'
            options={[
              {'value': '간식', 'text': '간식'},
              {'value': '학습', 'text': '학습'},
              {'value': '오락', 'text': '오락'},
              {'value': '기타', 'text': '기타'},
            ]}
          />
          <DropDownSelect
            selectName='endTime'
            name='"gradePreriodNo'
            selectTitle='종료 시간'
            options={[
              {'value': '1', 'text': '1교시'},
              {'value': '2', 'text': '2교시'},
              {'value': '3', 'text': '3교시'},
              {'value': '4', 'text': '4교시'},
              {'value': '5', 'text': '5교시'},
              {'value': '6', 'text': '6교시'},
            ]}
          />
          <div className={styled.startPrice}>
            <input
              type='number'
              name='startPrice'
              placeholder='경매 시작가'
            />
            <div>비드</div>
          </div>
        </div>
      </div>
      <div className={styled.body}>
        <div className={styled.left}>
          <div class={styled.myInfoImgPre}>
            {/* <img src={'data:image/gif;base64' + profileImage}/> */}
          </div>
          <div class={styled.fileSelect}>
            <input id='uploadName' class={styled.uploadName} value='첨부파일' placeholder='첨부파일'/>
            <label htmlFor='fileUpload'>파일찾기</label> 
            <input 
              type='file'
              id ='fileUpload'
              onChange={changeProfileImage}
            />
          </div>
        </div>
        <div className={styled.right}>
          <textarea
            placeholder='상품을 설명해주세요!&#13;&#10;자세하게 설명할수록&#13;&#10;친구들에게 도움이 돼요 : D'
          />
        </div>
      </div>
      <div className={styled.footer}>
        <div className={styled.buttons}>
          <SquareButton
            text='등록'
            textColor='white'
            backgroundColor='#5FA1C4'
            padding='1vw'
            onClick={() => {console.log('등록')}}
          />
        </div>
      </div>
    </Modal>
  );
}