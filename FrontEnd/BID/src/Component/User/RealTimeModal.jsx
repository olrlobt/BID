import styled from "./RealTimeModal.module.css";
import Bell from "../../Asset/Image/HOME_icons/bell-front-color.png";
import { useSelector } from "react-redux";
import { alarmSelector } from "../../Store/alarmSlice";
import { useMutation } from "@tanstack/react-query";
import { transferMoney } from "../../Apis/StudentBidApis";
import alertBtn from "../Common/Alert";

export default function RealTimeModal({ handleClick }) {
  const alarmList = useSelector(alarmSelector);

  const handleComplete = (boardNo) => {
    completeBid(boardNo).mutate();
  };

  const completeBid = useMutation({
    mutationKey: ["completeBid"],
    mutationFn: (boardNo) =>
      transferMoney(boardNo)
        .then(() => {
          alertBtn({ text: "완료되었습니다." });
        })
        .catch(() => {
          alertBtn({ text: "완료되지 않았습니다." });
        }),
  });

  return (
    <section className={styled.alarmBackground}>
      <section className={styled.alarm}>
        <h1 className={styled.title}>
          <img className={styled.bell} src={Bell} alt="종" onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}/>
          <span className={styled.activity}>활동 알림</span>
          <button className={styled.delete} onClick={handleClick}>
            x
          </button>
        </h1>
        <div className={styled.listContainer}>
          {alarmList &&
            alarmList.map((alarm, index) => (
              <div className={styled.alarmContainer}>
                {/* 1번째 리워드 전송*/}
                {JSON.parse(alarm).notificationType === "REWARD" ? (
                  <section className={styled.alarmList}>
                    <div>
                      <div>{JSON.parse(alarm).content.split("\n")[0]}</div>
                      <div>
                        <span>선생님 코멘트: </span>
                        {JSON.parse(alarm).content.split("\n")[1]}
                      </div>
                    </div>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {/* 2번째 적금 이체 알림*/}
                {JSON.parse(alarm).notificationType === "SAVING_TRANSFER" ? (
                  <section className={styled.alarmList}>
                    <div>{JSON.parse(alarm).content.split("\n")[0]}</div>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {/* 3번째 적금 만기 알림*/}
                {JSON.parse(alarm).notificationType === "SAVING_EXPIRE" ? (
                  <section className={styled.alarmList}>
                    <div>{JSON.parse(alarm).content.split("\n")[0]}</div>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {/* 4번째 경매 낙찰 알림*/}
                {JSON.parse(alarm).notificationType === "BIDDING_WINNING" ? (
                  <section className={styled.alarmList}>
                    {console.log(alarm)}
                    <div>
                      <div>{JSON.parse(alarm).content.split("요")[0]}요</div>
                      <span>
                        {JSON.parse(alarm)
                          .content.split("요")[1]
                          .split("\n")
                          .map((v) => (
                            <div>{v}</div>
                          ))}
                      </span>
                    </div>
                    <button
                      className={styled.complete}
                      onClick={() => handleComplete(JSON.parse(alarm).title)}
                    >
                      완료
                    </button>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {/* 5번째 경매 유찰 알림*/}
                {JSON.parse(alarm).notificationType === "BIDDING_FAILED" ? (
                  <section className={styled.alarmList}>
                    {console.log(JSON.parse(alarm))}
                    <div>
                      <div>{JSON.parse(alarm).content.split("요")[0]}요</div>
                      <span>
                        {JSON.parse(alarm)
                          .content.split("요")[1]
                          .split("\n")
                          .map((v) => (
                            <div>{v}</div>
                          ))}
                      </span>
                    </div>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {/* 6번째 경매 낙찰 확정(송금)*/}
                {JSON.parse(alarm).notificationType === "BIDDING_UPLOADER" ? (
                  <section className={styled.alarmList}>
                    <div>
                      <div>{JSON.parse(alarm).content.split("!")[0]}!</div>
                      <div>{JSON.parse(alarm).content.split("!")[1]}</div>
                    </div>
                    <span className={styled.createdAt}>
                      {JSON.parse(alarm)
                        .createdAt.split("T")[0]
                        .split("-")
                        .join(".")}
                    </span>
                  </section>
                ) : (
                  ""
                )}
                {index === alarmList.length - 1 ? (
                  ""
                ) : (
                  <div className={styled.bar}></div>
                )}
              </div>
            ))}
        </div>
      </section>
    </section>
  );
}
