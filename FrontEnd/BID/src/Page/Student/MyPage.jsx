import React, {useState} from "react";
// import BarChart from "../../Component/FinData/BarChart";
import PieChart from "../../Component/FinData/PieChart";
// import Coupon from "../../Component/FinData/Coupon";
// import AttendRec from '../../Component/FinData/AttendRec'
import CalendarChart from "../../Component/FinData/CalendarChart";
import styled from './MyPage.module.css';
// import Button2 from "../../Component/Common/Button2";
import SettingButton from "../../Component/Common/SettingButton";
import WriterButton from "../../Component/Bid/WriterButton";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import AvatarListComponent from "../../Component/User/AvatarListComponent";
import SettingsIcon from '@mui/icons-material/Settings';
import { useSelector } from "react-redux";
import { modelSelector } from "../../Store/modelSlice";
import { useMutation } from "@tanstack/react-query";
import { stuChangePwdApi, getMyBidListApi, getMyAvatarListApi } from '../../Apis/ModelApis';
import { useQuery } from "@tanstack/react-query";
import { openModal } from "../../Store/modalSlice";

function MyPage() {
  const [page, setPage] = useState('myFin');
  const myInfo = useSelector(modelSelector).model;
  const s3BaseUrl = 'https://ssafya306.s3.ap-northeast-2.amazonaws.com/';
  const avatarList = [
    {url: 'DefaultBody.png', name: '기본'},
    {url: 'AvocadoBody.png', name: '아보카도'},
    {url: 'DonutBody.png', name: '도넛'},
    {url: 'DoraemonBody.png', name: '도라에몽'},
    {url: 'IronManBody.png', name: '아이언맨'},
    {url: 'MarioBody.png', name: '슈퍼마리오'},
    {url: 'MinionBody.png', name: '미니언'},
    {url: 'PatrikBody.png', name: '뚱이'},
    {url: 'RibbonBody.png', name: '리본'},
    {url: 'SnowmanBody.png', name: '눈사람'}
    ];

  /** 사용자 경매 정보 쿼리 */
  const { data: myBiddingInfo } = useQuery({
    queryKey: ['myBidList'],
    queryFn: () =>
      getMyBidListApi(myInfo.no).then((res) => {
        return res.data;
      }),
  });

  /** 사용자 보유 아바타 정보 쿼리 */
  const { data: myAvatarInfo } = useQuery({
    queryKey: ['myAvatarList'],
    queryFn: () =>
      getMyAvatarListApi(myInfo.no).then((res) => {
        console.log(res.data);
        return res.data;
      }),
  });
  
  /** 비밀번호 변경 쿼리 */
  const stuChangePwdQuery = useMutation({
    mutationKey: ['stuChangePwd'],
    mutationFn: () => stuChangePwdApi(),
    onSuccess: (res) => {
      console.log(res);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 버튼 클릭 -> 내 활동 / 내 경매 / 내 아바타 전환 함수 */
  const changePage = (newPage) => {
  if(page==='myFin' && newPage==='myFin') {}
  else if(page==='myBid' && newPage==='myBid') {}
  else if(page==='myAvatar' && newPage==='myAvatar') {}
  else { setPage(newPage); }
}

  const categoryData = [
    { category: '식비', percentage: 30 },
    { category: '교통', percentage: 20 },
    // 다른 카테고리들도 추가
  ];
    
  return(
    <div className={styled.section}>
      <div className={styled.dashboardContainer}>
        <div className={styled.leftArea}>
          <div className={styled.userInfoArea}>
            <div className={styled.imgArea}>
              <img src={myInfo.profileImgUrl} alt="이미지" />
            </div>
            <div className={styled.descArea}>
              <div className={styled.schoolName}> {myInfo.schoolName} </div>
              <div className={styled.name}> {myInfo.name} </div>
              <div className={styled.asset}> 총 자산을 <span>4227비드</span> 모았어요! </div>
            </div>
          </div>
          <div className={styled.buttonContainer}>
              {/* <Button2 text="비밀번호 변경" /> */}
              <SettingButton
                onClick = {() => setPage('changePwd')}
                svg = {SettingsIcon}
                text = '비밀번호 변경' 
                height = '3vw'
                backgroundColor = '#5FA1C4'
              />
          </div>
        </div>
        <div className={styled.rightArea}>
          <div className={styled.pageSelectArea}>
            <WriterButton
              onClick = {() => changePage('myFin')}
              text = '내 활동'
              active = { page==='myFin' }
            />
            <WriterButton
              onClick = {() => changePage('myBid')}
              text = '내 경매'
              active = { page==='myBid' }
            />
            <WriterButton
              onClick = {() => changePage('myAvatar')}
              text = '내 아바타'
              active = { page==='myAvatar' }
            />
          </div>
          <div className={styled.pageContent}>
          {
            page==='myFin'?
            <>
              <div className={styled.chartsContainer}>
                {/* <BarChart /> */}
                <PieChart data={categoryData} />
                {/* <Coupon /> */}
              </div>
              <div className={styled.additionalChartsContainer}>
                {/* <AttendRec className={styled.AttendRec} /> */}
                <CalendarChart />
              </div>
            </>
            :
            page==='myBid'?
            <div className={styled.myBidContainer}>
              <div className={`${styled.biddedArea} ${styled.myBidArea}`}>
                <h2>내가 입찰한 경매</h2>
                <div className={styled.products}>
                  {
                    myBiddingInfo &&
                    myBiddingInfo.myBiddingBoards.length === 0?
                    <NoContent text='아직 입찰 중인 경매가 없어요'/>
                    :
                    myBiddingInfo.myBiddingBoards.map((product) =>
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
                        boardStatus = {product.boardStatus}
                      />
                    )
                  }
                </div>
              </div>
              <div className={`${styled.uploadedArea} ${styled.myBidArea}`}>
                <h2>내가 올린 경매</h2>
                <div className={styled.products}>
                  {
                    myBiddingInfo &&
                    myBiddingInfo.myBoards.length === 0?
                    <NoContent text='아직 작성한 경매글이 없어요'/>
                    :
                    myBiddingInfo.myBoards.map((product) =>
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
                        boardStatus = {product.boardStatus}
                      />
                    )
                  }
                </div>
              </div>
            </div>
            :
            page==='myAvatar'?
            <div className={styled.myAvatarContainer}>
              {
                avatarList.map((avatar) =>
                  <AvatarListComponent
                    url={s3BaseUrl+avatar.url}
                    name={avatar.name}
                    onClick={() => console.log('lets update avatar')}
                  />)
              }
            </div>
            :
            <>
              <div>changePwd</div>
            </>
          }
          </div>
        </div>
      </div>    
    </div>
  ) 
}

export default MyPage;


