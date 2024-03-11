import React, { useEffect, useState, useMemo } from "react";
import styled from "../Manage/BidPage.module.css";
import WriterButton from "../../Component/Bid/WriterButton";
import SettingButton from "../../Component/Common/SettingButton";
import AddIcon from "@material-ui/icons/Add";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import BiddingCoupon from "../../Component/Bid/BiddingCoupon";
import CannonBall from "../../Component/Bid/Cannonball";
import RandomItemBox from "../../Component/Bid/RandomItemBox";
import Price from "../../Component/Common/Price";
import useModal from '../../hooks/useModal';
import SubmitButton from "../../Component/Common/SubmitButton";
import { useSelector } from "react-redux";
import { productSelector, biddingCouponSelector, biddingCannonballSelector } from "../../Store//productSlice";
import { useMutation } from "@tanstack/react-query";
import { buyAvatarsApi, biddingApi, getProductDetailApi } from "../../Apis/StudentBidApis";

export default function StudentBidPage(){
  const reduxProducts = useSelector(productSelector);

  const [products, setProducts] = useState(reduxProducts);
  const [productFilter, setProductFilter] = useState('전체');
  const [keyword, setKeyword] = useState('');
  const biddingCoupon = useSelector(biddingCouponSelector);
  const biddingCannonball = useSelector(biddingCannonballSelector);

  const { openModal } = useModal();

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

  /** 랜덤 박스 아이템 구입 쿼리 */
  const buyAvatarsQuery = useMutation({
    mutationKey: ['buyAvatar'],
    mutationFn: (params) => buyAvatarsApi(params),
    onSuccess: (res) => { console.log(res) },
    onError: (error) => { console.log(error) }
  })
  
  /** 랜덤 박스 아이템 구입 함수 */
  const buyRandomItem = (e) => {
    if(window.confirm("아이템 랜덤 박스를 구입하시겠습니까?")){
      const params = {
        price: 70
      }
      buyAvatarsQuery.mutate(params);
    }
  }

  /** 입찰 쿼리 */
  const biddingQuery = useMutation({
    mutationKey: ['bidding'],
    mutationFn: (params) => biddingApi(params.boardNo, params.biddingInfo),
    onSuccess: (res) => { console.log(res); },
    onError: (error) => { console.log(error); }
  });

  /** 쿠폰 입찰 신청 함수 */
  const couponBidSubmit = (e) => {
    e.preventDefault();
    const biddingPrice = e.target.price.value;
    if(biddingPrice==='' || biddingPrice===null || biddingPrice<1) {
      console.log('금액을 입력해주세요');
    } else{
      const biddingInfo = {
        price: e.target.price.value
      }
      const params = {
        boardNo: biddingCoupon.no,
        biddingInfo: biddingInfo
      }
      biddingQuery.mutate(params);
      // console.log(biddingPrice+'비드 입찰되었습니다');
    }
  };

  /** 자리 구슬 입찰 신청 함수 */
  const cannonBidSubmit = (e) => {
    e.preventDefault();
    const biddingPrice = e.target.price.value;
    if(biddingPrice==='' || biddingPrice===null || biddingPrice<1) {
      console.log('금액을 입력해주세요');
    } else{
      const biddingInfo = {
        price: e.target.price.value
      }
      const params = {
        boardNo: biddingCannonball.no,
        biddingInfo: biddingInfo
      }
      biddingQuery.mutate(params);
      // console.log(biddingPrice+'비드 입찰되었습니다');
    }
  };

  
  return (
    <>
    <div className={styled.bidSection} style={{width: '100%'}}>
      <div className={styled.bidHeader}>
					<SettingButton
						onClick = {() =>
							openModal({
								type: 'makeNewPost',
								props: ['경매 올리기'] })
							}
						svg = {AddIcon}
						text = '경매 올리기' 
						height = '3vw'
						backgroundColor = '#5FA1C4'
					/>
        <div>
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
        </div>
      </div>

      <div className={styled.bidBody}>
        { biddingCoupon && biddingCannonball &&
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
              <div className={styled.biddingArea}>
                <form onSubmit={couponBidSubmit}>
                  <input
                    type="number"
                    name="price"
                    className={styled.biddingNumber}
                  />
                  <div className={styled.biddingUnit}>비드</div>
                  <SubmitButton
                    text="입찰"
                    width="5vw"
                    height="4vw"
                    fontSize="1.5vw"
                  />
                </form>
              </div>
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
              <div className={styled.biddingArea}>
                <form onSubmit={cannonBidSubmit}>
                  <input
                    type="number"
                    name="price"
                    className={styled.biddingNumber}
                  />
                  <div className={styled.biddingUnit}>비드</div>
                  <SubmitButton
                    text="입찰"
                    width="5vw"
                    height="4vw"
                    fontSize="1.5vw"
                  />
                </form>
              </div>
            </div>

            <div className={styled.prod}>
              <div className={styled.header}>
                <h3>랜덤 아바타 뽑기</h3>
                <Price price = {70}/>
              </div>
              <div className={styled.desc}>이번엔 어떤 아바타가 나올까요?</div>
              <RandomItemBox/>
              <button className={styled.getAvatarBtn} type="button" onClick={buyRandomItem}>아바타 뽑기</button>
            </div>
        </div>
        }


        <div className = {styled.productsWrapper} style={{gap: '3vw'}} >
          {
            filteredProducts && filteredProducts.length === 0?
            <NoContent text='현재 진행 중인 경매가 없어요'/>
            :
            filteredProducts && filteredProducts.map((product) => 
              <Product
                onClick = {() => {
                  openModal({
                    type: 'viewProduct',
                    props: [product.no] })
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
      </div>
    </div>
    </>
  );
}
