import styled from './Home.module.css';
import InfoBox from '../../Component/Common/InfoBox';
import Card from '../../Asset/Image/HOME_icons/coupon.png';
import Coin from '../../Asset/Image/HOME_icons/Coins.png';
import LinkFront from '../../Asset/Image/HOME_icons/transaction.png';
import Locker from '../../Asset/Image/HOME_icons/bank.png';
import Clock from '../../Asset/Image/HOME_icons/clock.png';
import useModal from '../../hooks/useModal';
import TimeTable from '../../Component/Common/TimeTable';
import { useSelector } from 'react-redux';
import { bidSelector } from '../../Store/bidSlice';
import { bidCountSelector } from '../../Store/bidCountSlice';
import { useEffect, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { viewDashboard } from '../../Apis/TeacherManageApis';
import { stopTimeSelector } from '../../Store/stopTimeSlice';
import { getCouponList } from '../../Apis/CouponApis';
import { requestCouponSelector } from '../../Store/requestCouponSlice';
import useBid from '../../hooks/useBid';
import useMoney from '../../hooks/useMoney';
import useBidCount from '../../hooks/useBidCount';
import useStopTime from '../../hooks/useStopTime';
import useRequestedCoupons from '../../hooks/useRequestedCoupons';
import { moneySeletor } from '../../Store/moneySlice';
import PieChart from '../../Component/Common/PieChart';
import LineChart from '../../Component/Common/LineChart';
import { useLocation } from 'react-router-dom';

export default function Home() {
  const { openModal } = useModal();
  const { changeBid } = useBid();
  const { initMoney } = useMoney();
  const { initCount } = useBidCount();
  const { initTime } = useStopTime();
  const { changeRequestList } = useRequestedCoupons();
  const currentBid = useSelector(bidSelector);
  const classMoney = useSelector(moneySeletor);
  const bidCount = useSelector(bidCountSelector);
  const stopTime = useSelector(stopTimeSelector);
  const requestedCoupons = useSelector(requestCouponSelector);
  const [lineData, setLineData] = useState([]);

  const location = useLocation();
  const gradeNo = location.state.schoolInfo.no;

  const { data: dashboardInfo } = useQuery({
    queryKey: ['HomeDashboard'],
    queryFn: () =>
      viewDashboard(gradeNo).then((res) => {
        if (res.data !== undefined) {
          changeBid(res.data.salary);
          initMoney(res.data.asset);
          initCount(res.data.biddingStatisticsFindResponses[13].count);
          initTime(res.data.gradePeriodsGetResponses);
          setLineData(
            res.data.biddingStatisticsFindResponses.map((item) => {
              return {
                x: `${item.date.split('-')[1]}.${item.date.split('-')[2]}`,
                y: item.count,
              };
            })
          );
        }
        return res.data;
      }),
  });

  const { data: couponList } = useQuery({
    queryKey: ['CouponList'],
    queryFn: () =>
      getCouponList(gradeNo).then((res) => {
        changeRequestList(res.data);
        return res.data;
      }),
  });

  useEffect(() => {}, [currentBid, couponList]);
  return (
    <>
      {dashboardInfo && couponList && (
        <main className={styled.home}>
          {console.log(dashboardInfo)}
          <button className={styled.holdBtn}>
            <span className={styled.hold}>HOLD</span>
            <span className={styled.holdInfo}>
              지금은 경매가 진행되고 있어요
            </span>
          </button>
          <InfoBox
            info={[
              {
                width: '30vw',
                height: '15vh',
                text: ['승인할 쿠폰이', '있어요'],
              },
            ]}
            icons={[{ src: Card, alt: '카드', css: 'card' }]}
            text={`${requestedCoupons.length}건`}
            modalClick={() =>
              openModal({
                type: 'coupon',
                props: ['쿠폰 신청 목록', requestedCoupons],
              })
            }
          />
          <section className={styled.secondRow}>
            <section className={styled.graphSec}>
              <LineChart
                data={[
                  {
                    id: 'line',
                    color: 'hsl(82, 70%, 50%)',
                    data: lineData,
                  },
                ]}
              />
              <PieChart
                data={[
                  dashboardInfo.sumTotalExpenditure,
                  dashboardInfo.sumCouponExpenditure,
                  dashboardInfo.sumEtcExpenditure,
                  dashboardInfo.sumGameExpenditure,
                  dashboardInfo.sumLearningExpenditure,
                  dashboardInfo.sumSnackExpenditure,
                ]}
              />
            </section>
            <section className={styled.infoSec}>
              <InfoBox
                info={[
                  {
                    width: '30vw',
                    height: '15vh',
                    text: ['현재 학생들은 주급으로', '를 받아요'],
                  },
                ]}
                icons={[{ src: Coin, alt: '주급', css: 'coin' }]}
                text={`${currentBid}비드`}
                modalClick={() =>
                  openModal({
                    // 여기 text로 비드 붙이기
                    type: 'changeBid',
                    props: ['주급 변경', currentBid],
                  })
                }
              />
              <InfoBox
                info={[
                  {
                    width: '30vw',
                    height: '15vh',
                    text: ['오늘 우리반은', '의 거래를 했어요!'],
                  },
                ]}
                icons={[{ src: LinkFront, alt: '거래', css: 'linkFront' }]}
                text={`${bidCount}건`}
              />
            </section>
          </section>
          <section className={styled.thirdRow}>
            <section className={styled.infoSec}>
              <InfoBox
                info={[
                  {
                    width: '35vw',
                    height: '15vh',
                    text: ['현재 국고에는', '있어요'],
                  },
                ]}
                icons={[{ src: Locker, alt: '국고', css: 'locker' }]}
                text={`${classMoney}비드`}
              />
              <div className={styled.infoBox}>
                <img className={styled.icon} src={Clock} alt="시계" />
                <div className={styled.infoText}>
                  <div>
                    적금 알림은{' '}
                    <span className={styled.infoImportant}>8:00</span>에
                    발송돼요
                  </div>
                  <div>
                    적금은 <span className={styled.infoImportant}>15:00</span>에
                    이체돼요
                  </div>
                </div>
              </div>
            </section>
            <section className={styled.schedule}>
              <TimeTable
                gradePeriods={stopTime}
                modalClick={() =>
                  openModal({
                    type: 'timeModal',
                    props: ['수업 시간 변경', stopTime],
                  })
                }
              />
            </section>
          </section>
        </main>
      )}
    </>
  );
}
