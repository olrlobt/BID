import React from "react";
import styled from "./ViewProductModal.module.css";
import Modal from '../Common/Modal';
import { useState } from 'react';
import useProducts from "../../hooks/useProducts";
import ProductInfoButton from "./ProductInfoButton";
import { SvgIcon } from "@material-ui/core";
import { ArrowForward } from "@material-ui/icons";
import SubmitButton from "../Common/SubmitButton";
import Comment from "./Comment";

export default function ViewProductModal({ onClose, ...props }) {
  // 0  no,
  // 1  title,
  // 2  contents,
  // 3  imgUrl,
  // 4  goodsType,
  // 5  goodsName,
  // 6  userName,
  // 7  startPrice,
  // 8  avgPrice,
  // 9  endTime,
  // 10 createdAt,

  const { removeProduct } = useProducts();

  // console.log(props[11]);

  const [form, setForm] = useState({
    price: 0,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const addNewProduct = (e) => {
    e.preventDefault();
    if (form.price === 0) {
      console.log('금액을 입력해주세요');
    } else{
      // 데이터 저장
      // addProduct({
      //   newProduct: form
      // });s
      onClose();
    }
  }

  return(
    <Modal onClose={onClose} {...props}>
      <div className={styled.header}>
        <div className={styled.top}>
          <h1>{props[1]}</h1>
          <span>{props[6]}</span>
        </div>
        <div className={styled.info}>
          <div className = {styled.infoButton}>
            <ProductInfoButton
              value = { props[4] }
              unit = ''
              textColor = 'white'
              borderColor = '#BBBD32'
              backgroundColor = '#BBBD32'
            />
          </div>
          <div className = {styled.infoButton}>
            <ProductInfoButton
              value = { props[9] }
              unit = '교시'
              textColor = 'white'
              borderColor = '#BBBD32'
              backgroundColor = '#BBBD32'
            />
          </div>
          <div className = {styled.infoButton}>
            <ProductInfoButton
              value = { props[7] }
              unit = '비드'
              textColor = '#F23F3F'
              borderColor = '#F23F3F'
              backgroundColor = 'white'
            />
          </div>
          <div className = {styled.arrowArea}>
            <SvgIcon
              component = { ArrowForward }
              fontSize = 'medium'
            />
          </div>
          <div className = {styled.infoButton}>
            <ProductInfoButton
              value = { props[8] }
              unit = '비드'
              textColor = 'white'
              borderColor = '#F23F3F'
              backgroundColor = '#F23F3F'
            />
          </div>
        </div>
      </div>
      <div className={styled.body}>
        <div className={styled.left}>
          <div className={styled.imgArea}>
            <img src={props[3]} alt="제품 이미지" />
          </div>
          <div className={styled.biddingArea}>
            <form id='newProductForm' onSubmit={addNewProduct}>
              <input
                type="number"
                name="price"
                onChange={handleChange}
              />
              <SubmitButton
                text = '입찰'
                width = '7vw'
                height = '5vw'
              />
            </form>
          </div>
        </div>
        <div className={styled.right}>
          <div className={styled.content}>{ props[2]}</div>
          <div className={styled.commentArea}>
            {
              props[11].map((c) =>
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
        </div>
      </div>
    </Modal>
  );
}