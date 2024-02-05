import styled from "./Home.module.css";
import InfoBox from "../../Component/Common/InfoBox";
import Card from "../../Asset/Image/HOME_icons/coupon.png";
import Coin from "../../Asset/Image/HOME_icons/Coins.png";
import LinkFront from "../../Asset/Image/HOME_icons/transaction.png";
import Locker from "../../Asset/Image/HOME_icons/bank.png";
import Clock from "../../Asset/Image/HOME_icons/clock.png";
import useModal from "../../hooks/useModal";
import TimeTable from "../../Component/Common/TimeTable";
import { useSelector } from "react-redux";
import { bidSelector } from "../../Store/bidSlice";
import { useEffect, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { viewDashboard } from "../../Apis/TeacherHomeApis";
import useBid from "../../hooks/useBid";
import useMoney from "../../hooks/useMoney";
import useBidCount from "../../hooks/useBidCount";
import { moneySeletor } from "../../Store/moneySlice";
import { bidCountSelector } from "../../Store/bidCountSlice";
import PieChart from "../../Component/Common/PieChart";
import LineChart from "../../Component/Common/LineChart";

export default function Home() {
  const { openModal } = useModal();
  const { changeBid } = useBid();
  const { initMoney } = useMoney();
  const { initCount } = useBidCount();
  const currentBid = useSelector(bidSelector);
  const classMoney = useSelector(moneySeletor);
  const bidCount = useSelector(bidCountSelector);
  const [lineData, setLineData] = useState([]);
  const dumpData = [
    { name: "이승헌", coupon: "청소 역할 선점 쿠폰" },
    { name: "이현진", coupon: "급식 먼저 먹기 쿠폰" },
    { name: "배민지", coupon: "10분 노래 틀기 쿠폰" },
    { name: "배미남", coupon: "자유 이용 쿠폰" },
  ];

  const { data: dashboardInfo } = useQuery({
    queryKey: ["HomeDashboard"],
    queryFn: () =>
      viewDashboard().then((res) => {
        if (res.data !== undefined) {
          changeBid(res.data.salary);
          initMoney(res.data.asset);
          initCount(res.data.dealCount);

          const sortWinningBiddingCounts = res.data.winningBiddingCounts.sort(
            (a, b) => new Date(a.date) - new Date(b.date)
          );
          setLineData(
            sortWinningBiddingCounts.map((item) => {
              return {
                x: `${item.date.split("-")[1]}.${item.date.split("-")[2]}`,
                y: item.count,
              };
            })
          );
        }
        return res.data;
      }),
  });

  useEffect(() => {}, [currentBid]);
  return (
    <>
      {console.log(dashboardInfo)}
      {dashboardInfo && (
        <main className={styled.home}>
          <button className={styled.holdBtn}>
            <span className={styled.hold}>HOLD</span>
            <span className={styled.holdInfo}>
              지금은 경매가 진행되고 있어요
            </span>
          </button>
          <InfoBox
            info={[
              {
                width: "30vw",
                height: "15vh",
                text: ["승인할 쿠폰이", "있어요"],
              },
            ]}
            icons={[{ src: Card, alt: "카드", css: "card" }]}
            text={"7건"}
            modalClick={() =>
              openModal({ type: "coupon", props: ["쿠폰 신청 목록", dumpData] })
            }
          />
          <section className={styled.secondRow}>
            <section className={styled.graphSec}>
              <LineChart
                data={[
                  {
                    id: "line",
                    color: "hsl(82, 70%, 50%)",
                    data: lineData,
                  },
                ]}
              />
              <PieChart
                data={[
                  dashboardInfo.totalCategorySum,
                  dashboardInfo.couponSum,
                  dashboardInfo.etcSum,
                  dashboardInfo.gameSum,
                  dashboardInfo.learningSum,
                  dashboardInfo.snackSum,
                ]}
              />
            </section>
            <section className={styled.infoSec}>
              <InfoBox
                info={[
                  {
                    width: "30vw",
                    height: "15vh",
                    text: ["현재 학생들은 주급으로", "를 받아요"],
                  },
                ]}
                icons={[{ src: Coin, alt: "주급", css: "coin" }]}
                text={`${currentBid}비드`}
                modalClick={() =>
                  openModal({
                    // 여기 text로 비드 붙이기
                    type: "changeBid",
                    props: ["주급 변경", currentBid],
                  })
                }
              />
              <InfoBox
                info={[
                  {
                    width: "30vw",
                    height: "15vh",
                    text: ["오늘 우리반은", "의 거래를 했어요!"],
                  },
                ]}
                icons={[{ src: LinkFront, alt: "거래", css: "linkFront" }]}
                text={`${bidCount}건`}
              />
            </section>
          </section>
          <section className={styled.thirdRow}>
            <section className={styled.infoSec}>
              <InfoBox
                info={[
                  {
                    width: "35vw",
                    height: "15vh",
                    text: ["현재 국고에는", "있어요"],
                  },
                ]}
                icons={[{ src: Locker, alt: "국고", css: "locker" }]}
                text={`${classMoney}비드`}
              />
              <div className={styled.infoBox}>
                <img className={styled.icon} src={Clock} alt="시계" />
                <div className={styled.infoText}>
                  <div>
                    적금 알림은{" "}
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
                gradePeriods={dashboardInfo.gradePeriods}
                modalClick={() =>
                  openModal({
                    type: "timeModal",
                    props: ["수업 시간 변경", {}],
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
