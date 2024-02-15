import styled from "./SmallEvent.module.css";

export default function SmallEvent({ date, ...props }) {
  const selectedDate = new Date(date);
  const detailDate = props.events.filter((event) => {
    const eventStartDate = new Date(event.start);
    return eventStartDate.getTime() === selectedDate.getTime();
  });
  return (
    <section className={styled.container}>
      <div className={styled.currentDate}>
        <span>{selectedDate.getMonth() + 1}월</span>
        <span>{selectedDate.getDate()}일 세부내역</span>
      </div>
      <section className={styled.innerContainer}>
        <div className={`${styled.select} ${styled.title}`}>구분</div>
        <div className={`${styled.category} ${styled.title}`}>카테고리</div>
        <div className={`${styled.money}  ${styled.title}`}>금액</div>
      </section>
      {detailDate.map((date) =>
        date.title === "EXPENDITURE" ? (
          <section className={styled.innerContainer}>
            <span className={styled.select}>지출</span>
            {date.type === "COUPON" ? (
              <span className={styled.category}>쿠폰</span>
            ) : (
              ""
            )}
            {date.type === "SAVING" ? (
              <span className={styled.category}>저금</span>
            ) : (
              ""
            )}
            {date.type === "SNACK" ? (
              <span className={styled.category}>간식</span>
            ) : (
              ""
            )}
            {date.type === "REWARD" ? (
              <span className={styled.category}>리워드</span>
            ) : (
              ""
            )}
            {date.type === "ETC" ? (
              <span className={styled.category}>기타</span>
            ) : (
              ""
            )}
            {date.type === "LEARNING" ? (
              <span className={styled.category}>학습</span>
            ) : (
              ""
            )}
            {date.type === "SALARY" ? (
              <span className={styled.category}>주급</span>
            ) : (
              ""
            )}
            {date.type === "GAME" ? (
              <span className={styled.category}>게임</span>
            ) : (
              ""
            )}
            <span className={styled.money}>{date.amount}비드</span>
          </section>
        ) : (
          <section className={styled.innerContainer}>
            <span className={styled.select}>소비</span>
            {date.type === "COUPON" ? (
              <span className={styled.category}>쿠폰</span>
            ) : (
              ""
            )}
            {date.type === "SAVING" ? (
              <span className={styled.category}>저금</span>
            ) : (
              ""
            )}
            {date.type === "SNACK" ? (
              <span className={styled.category}>간식</span>
            ) : (
              ""
            )}
            {date.type === "REWARD" ? (
              <span className={styled.category}>리워드</span>
            ) : (
              ""
            )}
            {date.type === "ETC" ? (
              <span className={styled.category}>기타</span>
            ) : (
              ""
            )}
            {date.type === "LEARNING" ? (
              <span className={styled.category}>학습</span>
            ) : (
              ""
            )}
            {date.type === "SALARY" ? (
              <span className={styled.category}>주급</span>
            ) : (
              ""
            )}
            {date.type === "GAME" ? (
              <span className={styled.category}>게임</span>
            ) : (
              ""
            )}
            <span className={styled.money}>{date.amount}비드</span>
          </section>
        )
      )}
    </section>
  );
}
