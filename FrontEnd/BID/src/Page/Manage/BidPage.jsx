import React, { useEffect, useLayoutEffect, useState, useMemo } from "react";
import styled from "./BidPage.module.css";
import WriterButton from "../../Component/Bid/WriterButton";
import SettingButton from "../../Component/Common/SettingButton";
import CouponList from "../../Component/Bid/CouponList";
import Price from "../../Component/Common/Price";
import AddIcon from "@material-ui/icons/Add";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import BiddingCoupon from "../../Component/Bid/BiddingCoupon";
import CannonBall from "../../Component/Bid/Cannonball";
import RandomItemBox from "../../Component/Bid/RandomItemBox";
import useModal from '../../hooks/useModal';
import useCoupons from "../../hooks/useCoupons";
import { useSelector } from "react-redux";
import { couponSelector } from "../../Store/couponSlice";
import { productSelector, biddingCouponSelector, biddingCannonballSelector } from "../../Store/productSlice";
import { DragDropContext } from "react-beautiful-dnd";
import { useMutation } from "@tanstack/react-query";
import { registerCouponApi, unregisterCouponApi } from "../../Apis/CouponApis";
import { mainSelector } from "../../Store/mainSlice";

export default function BidPage(){
  const mainClass = useSelector(mainSelector);
  const reduxCoupons = useSelector(couponSelector);
  const reduxProducts = useSelector(productSelector);
  const biddingCoupon = useSelector(biddingCouponSelector);
  const biddingCannonball = useSelector(biddingCannonballSelector);

  const biddingCouponInfo = reduxCoupons.find(c => c.no === biddingCoupon.no);
  console.log(biddingCouponInfo)

  const gradeNo = mainClass.no;

  const [isCoupon, setIsCoupon] = useState(false);
  const [coupons, setCoupons] = useState(reduxCoupons);
  const [products, setProducts] = useState(reduxProducts);
  const [productFilter, setProductFilter] = useState('전체');
  const [keyword, setKeyword] = useState('');

  const { openModal } = useModal();
  const { initCoupons, registCoupon, unregistCoupon } = useCoupons();

  /** 게시자 종류를 toggle하는 함수 */
  const changeWriter = (writer) => {
    if(isCoupon && writer==='teacher') {}
    else if(!isCoupon && writer==='student') {}
    else {setIsCoupon(!isCoupon);}
  }

/******************************* 쿠폰 */
  /** redux에 저장된 값 변경될 때마다 쿠폰 목록 세팅 */
  useLayoutEffect(() => {
    setCoupons(reduxCoupons);
  }, [reduxCoupons]);

  
  /** 경매 포함/제외 쿠폰 구분 */
  const { unregisteredCoupons, registeredCoupons } = useMemo(() => {
    const unregisteredCoupons = coupons && coupons.filter((coupon) => coupon.couponStatus==='UNREGISTERED');
    const registeredCoupons = coupons && coupons.filter((coupon) => coupon.couponStatus==='REGISTERED');
    return { unregisteredCoupons, registeredCoupons };
  }, [coupons]); 

  /** 쿠폰 경매 포함 쿼리 */
  const registerCouponQuery = useMutation({
    mutationKey: ['includeCoupon'],
    mutationFn: (params) => registerCouponApi(params.gradeNo, params.couponNo),
    onSuccess: (data, variables) => { registCoupon({couponNo: variables.couponNo}); },
    onError: (error, variables) => { console.log(variables, error); }
  });

  /** 쿠폰 경매 제외 쿼리 */
  const unregisterCouponQuery = useMutation({
    mutationKey: ['excludeCoupon'],
    mutationFn: (params) => unregisterCouponApi(params.gradeNo, params.couponNo),
    onSuccess: (data, variables) => { unregistCoupon({couponNo: variables.couponNo}); },
    onError: (error, variables) => { console.log(variables, error); }
  }); 

  /** 쿠폰을 드래그 해서 옮길 때 실행되는 함수 */
  const onDragEnd = ({source, destination}) => {
    if(!destination || source.droppableId===destination.droppableId){ return; }
    const params = {
      gradeNo: gradeNo,
      couponNo: source.index
    }
    if(JSON.parse(destination.droppableId)){
      registerCouponQuery.mutate(params);
    }
    else{
      unregisterCouponQuery.mutate(params);
    }
  }

/******************************* 경매 */
  /** redux에 저장된 값 변경될 때마다 경매 목록 세팅 */
  useEffect(() => {
    setProducts(reduxProducts);
  }, [reduxProducts]);

  
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

  
  return (
    <>
    <div className={styled.bidSection}>
      <div className={styled.bidHeader}>
        <div>
          <WriterButton
            onClick = {() => changeWriter('teacher')}
            text = {'선생님'}
            active = { isCoupon }
          />
          <WriterButton
            onClick = {() => changeWriter('student')}
            text = {'학생'}
            active = { !isCoupon }
          />
        </div>
        
        <div>
        {
          isCoupon ?
          <SettingButton
            onClick = {() =>
              openModal({
                type: 'newCoupon',
                props: ['새 쿠폰 등록', gradeNo] })
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
          isCoupon?
          (<div className = {styled.couponListWrapper}>
              <DragDropContext onDragEnd={onDragEnd} >
                <div>
                  <div className = {styled.couponListTitle}>미등록 쿠폰</div>
                  <CouponList
                    title = 'false'
                    coupons = {unregisteredCoupons? unregisteredCoupons: []}
                    gradeNo = {gradeNo}
                  />
                </div>
                <div>
                  <div className = {styled.couponListTitle}>등록된 쿠폰</div>
                  <CouponList
                    title= 'true'
                    coupons = {registeredCoupons? registeredCoupons: []}
                    gradeNo = {gradeNo}
                  />
                </div>
              </DragDropContext>
          </div>)
          :
          (<div>
            <div className={styled.teacherProducts}>
          <div className={styled.prod}>
              <div className={styled.header}>
                <h3>오늘의 쿠폰</h3>
                <Price price = {biddingCoupon.displayPrice}/>
              </div>
              <div className={styled.desc}>쿠폰은 매일매일 바뀌어요!</div>
              <BiddingCoupon
                no = {biddingCoupon.no}
                name = {biddingCoupon.title}
                description = ''
                startPrice = {biddingCoupon.displayPrice}
              />
            </div>

            <div className={styled.prod}>
              <div className={styled.header}>
                <h3>자리 구슬</h3>
                <Price price = {biddingCannonball.displayPrice}/>
              </div>
              <div className={styled.desc}>내가 먼저 뽑힐 확률을 높일 수 있어요</div>
              <CannonBall
                displayPrice = {biddingCannonball.displayPrice}
              />
            </div>

            <div className={styled.prod}>
              <div className={styled.header}>
                <h3>랜덤 아바타 뽑기</h3>
                <Price price = {70}/>
              </div>
              <div className={styled.desc}>이번엔 어떤 아바타가 나올까요?</div>
              <RandomItemBox/>
            </div>
        </div>
            <div className = {styled.productsWrapper}>
              {
                filteredProducts && filteredProducts.length === 0?
                <NoContent text='현재 진행 중인 경매가 없어요'/>
                :
                filteredProducts && filteredProducts.map((product) => 
                  <Product
                    onClick = {() => {
                      openModal({
                        type: 'manageProduct',
                        props: [gradeNo, product.no] })
                    }}
                    key = {product.no}
                    title = {product.title}
                    displayPrice = {product.displayPrice}
                    goodsImgUrl = {product.goodsImgUrl}
                    userName = {product.userName}
                    boardStatus = {product.boardStatus}
                  />
                )
              }
            </div>
          </div>)
        }
      </div>
    </div>
    </>
  );
}
