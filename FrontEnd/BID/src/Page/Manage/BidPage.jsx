import React, {
  useEffect,
  //  useMemo,
  useState,
} from "react";
import styled from "./BidPage.module.css";
import WriterButton from "../../Component/Bid/WriterButton";
import NewCouponButton from "../../Component/Bid/NewCouponButton";
import CouponList from "../../Component/Bid/CouponList";
import AddIcon from "@material-ui/icons/Add";
import Product from "../../Component/Bid/Product";
import useModal from "../../hooks/useModal";
import useCoupons from "../../hooks/useCoupons";
import { useSelector } from "react-redux";
import { couponSelector } from "../../Store/couponSlice";
import { useDispatch } from "react-redux";

export default function BidPage() {
  // const dummyNotIncluded = [
  //   {no: 1, name: '자유 이용 쿠폰', description: '원하는 무엇으로든 사용할 수 있는 쿠폰이에요!', startPrice: 200},
  //   {no: 2, name: '청소 역할 쿠폰', description: '이번 달 청소 역할은 꼭 먼저 정해야지! 청소 역할을 먼저 정해봐요', startPrice: 150},
  //   {no: 3, name: '1일 자리 쿠폰', description: '오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!', startPrice: 170},
  // ];

  // const dummyIncluded = [
  //   {no: 4, name: '1인 1역 쿠폰', description: '원하는 무엇으로든 사용할 수 있는 쿠폰이에요!', startPrice: 130},
  //   {no: 5, name: '도서 이용 쿠폰', description: '이번 달 청소 역할은 꼭 먼저 정해야지! 청소 역할을 먼저 정해봐요', startPrice: 70},
  //   {no: 6, name: '자유시간 쿠폰', description: '오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!', startPrice: 120},
  //   {no: 7, name: '고양이 쿠폰', description: '오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!', startPrice: 300},
  // ];

  const dummyCoupons = [
    {
      no: 1,
      name: "자유 이용 쿠폰",
      description: "원하는 무엇으로든 사용할 수 있는 쿠폰이에요!",
      startPrice: 200,
      selected: false,
    },
    {
      no: 2,
      name: "청소 역할 쿠폰",
      description:
        "이번 달 청소 역할은 꼭 먼저 정해야지! 청소 역할을 먼저 정해봐요",
      startPrice: 150,
      selected: false,
    },
    {
      no: 3,
      name: "1일 자리 쿠폰",
      description: "오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!",
      startPrice: 170,
      selected: false,
    },
    {
      no: 4,
      name: "1인 1역 쿠폰",
      description: "원하는 무엇으로든 사용할 수 있는 쿠폰이에요!",
      startPrice: 130,
      selected: true,
    },
    {
      no: 5,
      name: "도서 이용 쿠폰",
      description:
        "이번 달 청소 역할은 꼭 먼저 정해야지! 청소 역할을 먼저 정해봐요",
      startPrice: 70,
      selected: true,
    },
    {
      no: 6,
      name: "자유시간 쿠폰",
      description: "오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!",
      startPrice: 120,
      selected: true,
    },
    {
      no: 7,
      name: "고양이 쿠폰",
      description: "오늘 하루 내가 원하는 자리에 앉을 수 있는 쿠폰이에요!",
      startPrice: 300,
      selected: true,
    },
  ];

  const dummyProduct = [
    {
      no: 1,
      title: "곰돌이 인형",
      contents:
        "제가 좋아하던 곰돌이 인형이에요! 조금 오래됐지만 깨끗해요 ㅎㅎ",
      imgUrl: "https://sitem.ssgcdn.com/64/46/21/item/1000524214664_i1_750.jpg",
      goodsType: "기타",
      goodsName: "인형",
      userName: "백지윤",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
    {
      no: 2,
      title: "주판",
      contents:
        "주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다!",
      imgUrl:
        "https://img.freepik.com/premium-psd/abacus-icon-3d-render-illustration-for-children-education_620202-2754.jpg?w=2000",
      goodsType: "기타",
      goodsName: "인형",
      userName: "김예림",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
    {
      no: 3,
      title: "ABC 초콜릿",
      contents: "맛있는 쪼꼬",
      imgUrl:
        "https://img.freepik.com/premium-psd/chocolate-3d-render_553817-59.jpg",
      goodsType: "간식",
      goodsName: "초콜릿",
      userName: "이승헌",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
    {
      no: 4,
      title: "미니 퍼즐",
      contents:
        "주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다!",
      imgUrl:
        "https://img.freepik.com/premium-psd/jigsaw-puzzle-cube-isolated-gaming-and-streaming-icon-set-cute-minimal-style-3d-render_570783-710.jpg",
      goodsType: "기타",
      goodsName: "퍼즐",
      userName: "이현진",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
    {
      no: 5,
      title: "빈츠",
      contents:
        "제가 좋아하던 곰돌이 인형이에요! 조금 오래됐지만 깨끗해요 ㅎㅎ",
      imgUrl:
        "https://img.freepik.com/free-psd/3d-birthday-icon-with-candy_23-2149664024.jpg?size=338&ext=jpg&ga=GA1.1.1546980028.1703721600&semt=ais",
      goodsType: "간식",
      goodsName: "과자",
      userName: "유현지",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
    {
      no: 6,
      title: "사탕",
      contents:
        "주판 학원 다닐 때 쓰던 건데 이젠 사용하지 않아서 경매에 올립니다!",
      imgUrl:
        "https://previews.123rf.com/images/virtosmedia/virtosmedia2303/virtosmedia230311535/199668012-%ED%8C%8C%EB%9E%80%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%ED%99%94%EB%A0%A4%ED%95%9C-%EC%82%AC%ED%83%95%EA%B3%BC-%EA%B3%BC%EC%9E%90%EC%9E%85%EB%8B%88%EB%8B%A4-3d-%EA%B7%B8%EB%A6%BC.jpg",
      goodsType: "간식",
      goodsName: "사탕",
      userName: "배민지",
      startPrice: 360,
      avgPrice: 480,
      endTime: 6,
      createdAt: new Date("2022-5-20 10:30:20"),
    },
  ];

  // const [isInitialRender, setIsInitialRender] = useState(true);
  const [isTeacher, setIsTeacher] = useState(true);
  const [products, setProducts] = useState([]);

  const dispatch = useDispatch();
  const { openModal } = useModal();
  const { initCoupons } = useCoupons();
  const coupons = useSelector(couponSelector);

  let notSelected = [];
  let selected = [];
  if (coupons !== null) {
    console.log(coupons);
    notSelected = coupons.filter((coupon) => coupon.selected === false);
    selected = coupons.filter((coupon) => coupon.selected === true);
  }

  useEffect(() => {
    initCoupons({ couponList: dummyCoupons });

    setProducts(dummyProduct);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dispatch]);

  /** 게시자 종류를 toggle해주는 함수 */
  const changeWriter = (writer) => {
    if (isTeacher && writer === "teacher") {
    } else if (!isTeacher && writer === "student") {
    } else {
      setIsTeacher(!isTeacher);
    }
  };

  // /** X 버튼을 클릭해 해당 쿠폰을 삭제하는 함수 */
  // const removeCoupon = (coupons, no) => {
  //   const coupon = coupons.find((c) => c.no === no);

  //   console.log(coupon);
  // }

  return (
    <div className={styled.bidSection}>
      <div className={styled.bidHeader}>
        <div>
          <WriterButton
            onClick={() => changeWriter("teacher")}
            text={"선생님"}
            active={isTeacher}
          />
          <WriterButton
            onClick={() => changeWriter("student")}
            text={"학생"}
            active={!isTeacher}
          />
        </div>
        <div>
          {isTeacher ? (
            <NewCouponButton
              onClick={() =>
                openModal({
                  type: "newCoupon",
                  props: ["새 쿠폰 등록"],
                })
              }
              svg={AddIcon}
              text={"새 쿠폰 등록"}
            />
          ) : null}
        </div>
      </div>

      <div className={styled.bidBody}>
        {isTeacher ? (
          <div className={styled.couponListWrapper}>
            <div>
              <div className={styled.couponListTitle}>미등록 쿠폰</div>
              <CouponList coupons={notSelected} />
            </div>
            <div>
              <div className={styled.couponListTitle}>등록된 쿠폰</div>
              <CouponList coupons={selected} />
            </div>
          </div>
        ) : (
          <div className={styled.productsWrapper}>
            {products.map((product) => (
              <Product
                key={product.no}
                no={product.no}
                title={product.title}
                contents={product.contents}
                imgUrl={product.imgUrl}
                goodsType={product.goodsType}
                goodsName={product.goodsName}
                userName={product.userName}
                startPrice={product.startPrice}
                avgPrice={product.avgPrice}
                endTime={product.endTime}
                createdAt={product.createdAt}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
