import React, { useEffect, useState, useMemo } from "react";
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
import { getProductListApi } from "../../Apis/BidApis";

export default function BidPage(){

  // const dummyProducts = [
  //   {
  //     no: 1,
  //     title: '곰돌이 인형',
  //     description: '제가 좋아하던 곰돌이 인형이에요! 조금 오래됐지만 깨끗해요 ㅎㅎ',
  //     goodsImgUrl: 'https://sitem.ssgcdn.com/64/46/21/item/1000524214664_i1_750.jpg',
  //     category: '기타',
  //     userName: '백지윤',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON(),
  //     comments: [
  //       {
  //         userNo: 17,
  //         name: '유현지',
  //         content: '우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 ',
  //         createdAt: new Date('2022-5-20 10:32:20').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 3,
  //         name: '곽희웅',
  //         content: '음 친구군',
  //         createdAt: new Date('2022-5-20 10:32:25').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 34,
  //         name: '장수민',
  //         content: '3대 몇인가요?',
  //         createdAt: new Date('2022-5-20 10:33:20').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 25,
  //         name: '왕종욱',
  //         content: '후욱 곰돌쟝...',
  //         createdAt: new Date('2022-5-20 10:42:20').toJSON(),
  //         deleteAt: null,
  //       }
  //     ],
  //   }, 
  //   {
  //     no: 2,
  //     title: '주판',
  //     description: '주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다! 연락 많이 주세용',
  //     goodsImgUrl: 'https://img.freepik.com/premium-psd/abacus-icon-3d-render-illustration-for-children-education_620202-2754.jpg?w=2000',
  //     category: '기타',
  //     userName: '김예림',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON(),
  //     comments: [
  //       {
  //         userNo: 17,
  //         name: '유현지',
  //         content: '우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 우효~ 귀여워라 ',
  //         createdAt: new Date('2022-5-20 10:32:20').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 3,
  //         name: '곽희웅',
  //         content: '음 친구군',
  //         createdAt: new Date('2022-5-20 10:32:25').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 34,
  //         name: '장수민',
  //         content: '3대 몇인가요?',
  //         createdAt: new Date('2022-5-20 10:33:20').toJSON(),
  //         deleteAt: null,
  //       },
  //       {
  //         userNo: 25,
  //         name: '왕종욱',
  //         content: '후욱 곰돌쟝...',
  //         createdAt: new Date('2022-5-20 10:42:20').toJSON(),
  //         deleteAt: null,
  //       }
  //     ],
  //   },
  //   {
  //     no: 3,
  //     title: 'ABC 초콜릿',
  //     description: '맛있는 쪼꼬',
  //     goodsImgUrl: 'https://img.freepik.com/premium-psd/chocolate-3d-render_553817-59.jpg',
  //     category: '간식',
  //     userName: '이승헌',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON()
  //   }, 
  //   {
  //     no: 4,
  //     title: '초미니 퍼즐',
  //     description: '주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다!',
  //     goodsImgUrl: 'https://img.freepik.com/premium-psd/jigsaw-puzzle-cube-isolated-gaming-and-streaming-icon-set-cute-minimal-style-3d-render_570783-710.jpg',
  //     category: '기타',
  //     userName: '이현진',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON()
  //   },
  //   {
  //     no: 5,
  //     title: '빈츠',
  //     description: '제가 좋아하던 곰돌이 인형이에요! 조금 오래됐지만 깨끗해요 ㅎㅎ',
  //     goodsImgUrl: 'https://img.freepik.com/free-psd/3d-birthday-icon-with-candy_23-2149664024.jpg?size=338&ext=jpg&ga=GA1.1.1546980028.1703721600&semt=ais',
  //     category: '간식',
  //     userName: '유현지',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON()
  //   }, 
  //   {
  //     no: 6,
  //     title: '사탕',
  //     description: '주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다!',
  //     goodsImgUrl: 'https://previews.123rf.com/images/virtosmedia/virtosmedia2303/virtosmedia230311535/199668012-%ED%8C%8C%EB%9E%80%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%ED%99%94%EB%A0%A4%ED%95%9C-%EC%82%AC%ED%83%95%EA%B3%BC-%EA%B3%BC%EC%9E%90%EC%9E%85%EB%8B%88%EB%8B%A4-3d-%EA%B7%B8%EB%A6%BC.jpg',
  //     category: '간식',
  //     userName: '배민지',
  //     startPrice: 360,
  //     averagePrice: 480,
  //     gradePeriodNo: 6,
  //     createdAt: new Date('2022-5-20 10:30:20').toJSON()
  //   }
  // ]
  
  const queryClient = useQueryClient();
  const reduxCoupons = useSelector(couponSelector);
  const reduxProducts = useSelector(productSelector);

  const [isTeacher, setIsTeacher] = useState(true);
  const [coupons, setCoupons] = useState(reduxCoupons);
  const [products, setProducts] = useState(reduxProducts);
  const [productFilter, setProductFilter] = useState('전체');
  const [keyword, setKeyword] = useState('');

  const { openModal } = useModal();
  const { initCoupons } = useCoupons();
  const { initProducts } = useProducts();
  let filteredProducts = [];

  /** 게시자 종류를 toggle하는 함수 */
  const changeWriter = (writer) => {
    if(isTeacher && writer==='teacher') {}
    else if(!isTeacher && writer==='student') {}
    else {setIsTeacher(!isTeacher);}
  }

/******************************* 쿠폰 */
  /** redux에 저장된 값 변경될 때마다 쿠폰 목록 세팅 */
  useEffect(() => {
    setCoupons(reduxCoupons);
  }, [reduxCoupons]);

  /** 쿠폰 목록 쿼리 */
  useQuery({
    queryKey: ['couponList'],
    queryFn: () => 
      getCouponListApi(1).then((res) => {
        if(res.data !== undefined){
          initCoupons({ couponList: res.data.coupons });
        }
        return res.data;
      }),
  });
  
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
    onSuccess: () => { queryClient.invalidateQueries('couponList'); },
    onError: (error) => { console.log(error); }
  });

  /** 쿠폰 경매 제외 쿼리 */
  const unregisterCouponQuery = useMutation({
    mutationKey: ['excludeCoupon'],
    mutationFn: (couponNo) => unregisterCouponApi(1, couponNo),
    onSuccess: () => { queryClient.invalidateQueries('couponList'); },
    onError: (error) => { console.log(error); }
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
  useQuery({
    queryKey: ['productList'],
    queryFn: () => 
      getProductListApi(1).then((res) => {
        if(res.data !== undefined){
          initProducts({ productList: res.data });
        }
        return res.data;
      }),
  });
  
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

  /** 경매 카테고리 초기 설정 */
  if(products !== null) {
    if(productFilter === '전체'){
      if(keyword === ''){ filteredProducts = [...products]; }
      else{
        filteredProducts = products.filter((product) => product.title.includes(keyword));
      }
    }
    else{
      if(keyword === ''){ filteredProducts = products.filter((product) => product.category === productFilter); }
      else{
        filteredProducts = products.filter((product) => product.category === productFilter && product.title.includes(keyword));
      }
    }
  }


  return (
    <>
    <div className = {styled.bidSection}>
      <div className = {styled.bidHeader}>
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
                props: ['새 쿠폰 등록', queryClient] })
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
                onClick = { () => changeFilter('쿠폰') }
                text = '쿠폰'
                active = { productFilter==='쿠폰' }
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

      <div>
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
              filteredProducts.length === 0?
              <NoContent text='현재 진행 중인 경매가 없어요'/>
              :
              filteredProducts.map((product) => 
                <Product
                  onClick = {() => {
                    openModal({
                      type: 'viewProduct',
                      props: [
                        product.no,
                        // product.title,
                        // product.description,
                        // product.startPrice,
                        // product.boardStatus,
                        // product.averagePrice,
                        // product.resultPrice,
                        // product.category,
                        // product.goodsImgUrl,
                        // product.userName,
                        // product.gradePeriodNo,
                        // product.createdAt,
                        // product.comments
                      ] })
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
