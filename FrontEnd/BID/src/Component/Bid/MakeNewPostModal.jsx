import React, { useState } from 'react';
import styled from './MakeNewPostModal.module.css';
import Modal from '../Common/Modal';
import DropDownSelect from '../Common/DropDownSelect';
import SubmitButton from "../Common/SubmitButton";
import { getProductListApi, addProductApi, getImageUrlApi } from "../../Apis/StudentBidApis";
import { useMutation } from "@tanstack/react-query";
import useProducts from '../../hooks/useProducts';

export default function MakeNewPostModal({ onClose, ...props }) {
  const { initProducts } = useProducts();
  const [imgUrl, setImgUrl] = useState('data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw8PDw8PDw8PDw8PDw8NDw8PDw8PDw8PFRUWFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDQ0NDg0NDysZFRktNystNysrNSsrLSstKysrKysrKystKysrKy0rKys3LS0rKystLSsrKysrKysrKysrK//AABEIAOEA4QMBIgACEQEDEQH/xAAYAAEBAQEBAAAAAAAAAAAAAAABAAIDBv/EABYQAQEBAAAAAAAAAAAAAAAAAAARAf/EABYBAQEBAAAAAAAAAAAAAAAAAAABAv/EABURAQEAAAAAAAAAAAAAAAAAAAAR/9oADAMBAAIRAxEAPwD12iNaEUIhQIoAkgQKAREAkkCBAJEAkkARQgRQBJAkiARQOoa0I0EQogUIEUKEQARQgDQAIoAkgSSBAoAigCKAJIQpIHbQ1oRplNAAigAKAIoAGkDKKVGUVEAilAigAKAIoAigCKBIwA7gpFZRQBFAA1EDKKAAoAGgARQBFCBEAgUARUAYigChQJGIHVHQKEUARQANAAigZTQABpAyigCKAIgAigSUQBNRQRkogIiQdNDQFSiQCIoGUUAiKgMooAGoAEUKAIoAigEDSBmJpAEUIFCgSSB1DWhFCMQBFAyiooIoQARQBFAyjEAUKAIqAIioARiARQqCBQrMBQGIHUNBFCKABpAyigCIARGIBFCgZUMIMqEgymhACKUCMQBFCBEgA0gdA0EUKGIAioDKjQAIoAGhAERQMkoAoiARgBCNAAigChSgJigBGISNqFRFCKAKEAEUARQMkoGYigCMQMkxAASABQBRoAhCYAiKUERiBtGJAIoGU0ACKAIgAigCKAIgEjFAAKBAoAUgQMQJFAIGko2tKQCKAAqAFCgAaABFAEUADQAIoAigCKAKFAEUAKSiRiBtEIBFAEUABQCIoAkgCKAIoAigCKAJIEkgSSBIoFEYlGkdCARQBFAAUARQBEAkkCSQBFAgUASIMooAUooikgiioVoSCwpAEkCWpAEUARQAFAEUCBQBFAEkARSgKQFJIFJKP//Z');

  /** 경매 등록 쿼리 */
  const addNewProductQuery = useMutation({
    mutationKey: ['addNewProduct'],
    mutationFn: (newProductData) => addProductApi(newProductData),
    onSuccess: () => {
      getProductListApi().then((res) => {
        if(res.data !== undefined){
          initProducts({productList: res.data});
        }
      })
    },
    onsError: (error) => { console.log(error); }
  })

  /** 이미지 url 발급 쿼리 */
  const getImageUrlQuery = useMutation({
    mutationKey: ['getImageUrl'],
    mutationFn: (formData) => getImageUrlApi(formData),
    onSuccess: (res) => { setImgUrl(res.data) },
    onError: (error) => { console.log(error) }
  })

  /** 경매 등록 함수 */
  const addNewProduct = (e) => {
    e.preventDefault();
    const newProductData = {
      title: e.target.title.value,
      description: e.target.description.value,
      category: e.target.category.value,
      goodsImgUrl: imgUrl,
      startPrice: e.target.startPrice.value,
      gradePeriodNo: e.target.gradePeriodNo.value,
    }
    console.log(newProductData);
    addNewProductQuery.mutate(newProductData);
    onClose();
  }

  /** 이미지 등록 -> url 반환 함수 */
  const changeProfileImage = async (e) => {
    const formData = new FormData();
    formData.append('file', e.target.files[0]);
    getImageUrlQuery.mutate(formData);
  }

  return(
    <Modal onClose={onClose} {...props}>
      <form onSubmit={addNewProduct}>
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
              selectTitle='상품 유형'
              options={[
                {'value': 'SNACK', 'text': '간식'},
                {'value': 'LEARNING', 'text': '학습'},
                {'value': 'GAME', 'text': '오락'},
                {'value': 'ETC', 'text': '기타'},
              ]}
            />
            <DropDownSelect
              selectName='gradePeriodNo'
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
            <div className={styled.myInfoImgPre}>
              <img src={imgUrl} onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
            </div>
            <div className={styled.fileSelect}>
              <input id='uploadName' className={styled.uploadName} defaultValue='첨부파일' placeholder='첨부파일'/>
              <label htmlFor='fileUpload'>파일찾기</label> 
              <input 
                type='file'
                name='biddingImage'
                id ='fileUpload'
                onChange={changeProfileImage}
              />
            </div>
          </div>
          <div className={styled.right}>
            <textarea
              name='description'
              placeholder='상품을 설명해주세요!&#13;&#10;자세하게 설명할수록&#13;&#10;친구들에게 도움이 돼요 : D'
            />
          </div>
        </div>
        <div className={styled.footer}>
          <SubmitButton
            text='등록'
            width='10vw'
            height='3vw'
            fontSize='1.7vw'
          />
        </div>
      </form>
    </Modal>
  );
}