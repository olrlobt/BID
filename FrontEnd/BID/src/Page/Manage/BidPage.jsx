import React, { useEffect, useLayoutEffect, useState, useMemo } from "react";
import styled from "./BidPage.module.css";
import WriterButton from "../../Component/Bid/WriterButton";
import SettingButton from "../../Component/Common/SettingButton";
import CouponList from "../../Component/Bid/CouponList";
import AddIcon from "@material-ui/icons/Add";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import useModal from '../../hooks/useModal';
import useCoupons from "../../hooks/useCoupons";
import useProducts from "../../hooks/useProducts";
import { useSelector } from "react-redux";
import { couponSelector } from "../../Store/couponSlice";
import { productSelector } from "../../Store/productSlice";
import { DragDropContext } from "react-beautiful-dnd";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getCouponListApi, registerCouponApi, unregisterCouponApi } from "../../Apis/CouponApis";
import { getProductListApi } from "../../Apis/TeacherBidApis";

export default function BidPage(){
  // const queryClient = useQueryClient();
  const reduxCoupons = useSelector(couponSelector);
  const reduxProducts = useSelector(productSelector);

  const [isTeacher, setIsTeacher] = useState(false);
  const [coupons, setCoupons] = useState(reduxCoupons);
  const [products, setProducts] = useState(reduxProducts);
  const [productFilter, setProductFilter] = useState('전체');
  const [keyword, setKeyword] = useState('');

  const { openModal } = useModal();
  const { initCoupons, registCoupon, unregistCoupon } = useCoupons();
  const { initProducts } = useProducts();

  /** 게시자 종류를 toggle하는 함수 */
  const changeWriter = (writer) => {
    if(isTeacher && writer==='teacher') {}
    else if(!isTeacher && writer==='student') {}
    else {setIsTeacher(!isTeacher);}
  }

/******************************* 쿠폰 */
  /** redux에 저장된 값 변경될 때마다 쿠폰 목록 세팅 */
  useLayoutEffect(() => {
    setCoupons(reduxCoupons);
  }, [reduxCoupons]);

  /** 쿠폰 목록 쿼리 */
  // useQuery({
  //   queryKey: ['couponList'],
  //   queryFn: () => 
  //     getCouponListApi(1).then((res) => {
  //       if(res.data !== undefined){
  //         initCoupons({ couponList: res.data.coupons });
  //         console.log(res.data);
  //       }
  //       return res.data;
  //     }),
  // });
  
  /** 경매 포함/제외 쿠폰 구분 */
  const { unregisteredCoupons, registeredCoupons } = useMemo(() => {
    const unregisteredCoupons = coupons && coupons.filter((coupon) => coupon.couponStatus==='UNREGISTERED');
    const registeredCoupons = coupons && coupons.filter((coupon) => coupon.couponStatus==='REGISTERED');
    return { unregisteredCoupons, registeredCoupons };
  }, [coupons]); 

  /** 쿠폰 경매 포함 쿼리 */
  const registerCouponQuery = useMutation({
    mutationKey: ['includeCoupon'],
    mutationFn: (couponNo) => registerCouponApi(1, couponNo),
    onSuccess: (data, variables) => { registCoupon({couponNo: variables}); },
    onError: (error, variables) => { console.log(variables, error); }
  });

  /** 쿠폰 경매 제외 쿼리 */
  const unregisterCouponQuery = useMutation({
    mutationKey: ['excludeCoupon'],
    mutationFn: (couponNo) => unregisterCouponApi(1, couponNo),
    onSuccess: (data, variables) => { unregistCoupon({couponNo: variables}); },
    onError: (error, variables) => { console.log(variables, error); }
  }); 

  /** 쿠폰을 드래그 해서 옮길 때 실행되는 함수 */
  const onDragEnd = ({source, destination}) => {
    if(!destination || source.droppableId===destination.droppableId){ return; }
    if(JSON.parse(destination.droppableId)){
      registerCouponQuery.mutate(source.index);
    }
    else{
      unregisterCouponQuery.mutate(source.index);
    }
  }

/******************************* 경매 */
  /** redux에 저장된 값 변경될 때마다 경매 목록 세팅 */
  useEffect(() => {
    setProducts(reduxProducts);
  }, [reduxProducts]);

  /** 경매 목록 쿼리 */
  // useQuery({
  //   queryKey: ['productList'],
  //   queryFn: () => 
  //     getProductListApi(1).then((res) => {
  //       if(res.data !== undefined){
  //         initProducts({ productList: res.data });
  //       }
  //       return res.data;
  //     }),
  // });
  
  /** 게시글 필터를 toggle하는 함수 */
  const changeFilter = (filter) => {
    if(productFilter !== filter){
      setProductFilter(filter);
    }
  }

  /** keyword 기준으로 게시글을 검색하는 함수 */
  const handleChange = (e) => {
    const { value } = e.target;
    setKeyword(value);
  };

  /** 필터/검색 조건에 따른 결과 */
  const filteredProducts = useMemo(() => {
    let filteredProducts = products && [...products];
    if(productFilter !== '전체'){
      filteredProducts = filteredProducts.filter((p) => p.category === productFilter);
    }
    if(keyword !== ''){
      filteredProducts = filteredProducts.filter((p) => p.title.includes(keyword));
    }
    return filteredProducts;
  }, [products, productFilter, keyword]);
  /** 경매 카테고리 초기 설정 */
  
  return (
    <>
    <div className={styled.bidSection}>
      <div className={styled.bidHeader}>
        <div>
          <WriterButton
            onClick = {() => changeWriter('teacher')}
            text = {'선생님'}
            active = { isTeacher }
          />
          <WriterButton
            onClick = {() => changeWriter('student')}
            text = {'학생'}
            active = { !isTeacher }
          />
        </div>
        
        <div>
        {
          isTeacher ?
          <SettingButton
            onClick = {() =>
              openModal({
                type: 'newCoupon',
                props: ['새 쿠폰 등록'] })
              }
            svg = {AddIcon}
            text = '새 쿠폰 등록' 
            height = '3vw'
            backgroundColor = '#5FA1C4'
          /> :
          <div className = {styled.filterArea}>
            <div className = {styled.filterButtons}>
              <WriterButton
                onClick = { () => changeFilter('전체') }
                text = '전체'
                active = { productFilter==='전체' }
              />
              <WriterButton
                onClick = { () => changeFilter('간식') }
                text = '간식'
                active = { productFilter==='간식' }
              />
              <WriterButton
                onClick = { () => changeFilter('학습') }
                text = '학습'
                active = { productFilter==='학습' }
              />
              <WriterButton
                onClick = { () => changeFilter('오락') }
                text = '오락'
                active = { productFilter==='오락' }
              />
              <WriterButton
                onClick = { () => changeFilter('기타') }
                text = '기타'
                active = { productFilter==='기타' }
              />
            </div>
            <div className = {styled.keywordSearch}>
              <input
                placeholder = '경매 상품을 검색해보세요!'
                type = 'text'
                name = 'keyword'
                onChange = { handleChange }
              />
            </div>
          </div>
        }
        </div>
      </div>

      <div className={styled.bidBody}>
        {
          isTeacher?
          (<div className = {styled.couponListWrapper}>
              <DragDropContext onDragEnd={onDragEnd} >
                <div>
                  <div className = {styled.couponListTitle}>미등록 쿠폰</div>
                  <CouponList
                    title = 'false'
                    coupons = {unregisteredCoupons? unregisteredCoupons: []}
                  />
                </div>
                <div>
                  <div className = {styled.couponListTitle}>등록된 쿠폰</div>
                  <CouponList
                    title= 'true'
                    coupons = {registeredCoupons? registeredCoupons: []}
                  />
                </div>
              </DragDropContext>
          </div>)
          :
          (<div className = {styled.productsWrapper}>
            {
              filteredProducts && filteredProducts.length === 0?
              <NoContent text='현재 진행 중인 경매가 없어요'/>
              :
              filteredProducts && filteredProducts.map((product) => 
                <Product
                  onClick = {() => {
                    openModal({
                      type: 'manageProduct',
                      props: [product.no] })
                  }}
                  key = {product.no}
                  title = {product.title}
                  displayPrice = {product.displayPrice}
                  goodsImgUrl = {product.goodsImgUrl}
                  userName = {product.userName}
                />
              )
            }
          </div>)
        }
      </div>
    </div>
    </>
  );
}
