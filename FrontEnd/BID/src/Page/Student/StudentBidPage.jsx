import React, { useEffect, useState, useMemo } from "react";
import styled from "../Manage/BidPage.module.css";
import WriterButton from "../../Component/Bid/WriterButton";
import SettingButton from "../../Component/Common/SettingButton";
import AddIcon from "@material-ui/icons/Add";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import useModal from '../../hooks/useModal';
// import useProducts from "../../hooks/useProducts";
import { useSelector } from "react-redux";
import { productSelector } from "../../Store/productSlice";
// import { useQuery } from "@tanstack/react-query";
// import { getProductListApi } from "../../Apis/StudentBidApis";

export default function StudentBidPage(){
  const reduxProducts = useSelector(productSelector);

  const [products, setProducts] = useState(reduxProducts);
  const [productFilter, setProductFilter] = useState('전체');
  const [keyword, setKeyword] = useState('');

  const { openModal } = useModal();
  // const { initProducts } = useProducts();

/******************************* 경매 */
  /** redux에 저장된 값 변경될 때마다 경매 목록 세팅 */
  useEffect(() => {
    setProducts(reduxProducts);
  }, [reduxProducts]);

  /** 경매 목록 쿼리 */
  // useQuery({
    //   queryKey: ['productList'],
    //   queryFn: () => 
      //     getProductListApi().then((res) => {
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
              />
            )
          }
        </div>
      </div>
    </div>
    </>
  );
}
