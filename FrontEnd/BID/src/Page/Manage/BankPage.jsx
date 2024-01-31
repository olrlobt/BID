import styled from "./BankPage.module.css";

export default function BankPage() {
  // const [bankMoney, setBankMoney] = useState(0);
  return (
    <>
      <section className={styled.bankPage}>
        <section className={styled.bankSection}>
          <div className={styled.saveInfo}>
            현재 국고에는 <span>1,839,500비드</span> 있어요
          </div>
          <div className={styled.saveTime}>
            <span className={styled.saveTimeAlarm}>
              매일 1교시 시작 전 이체 알림이 발송되고, 6교시 종료 전 자동으로
              이체돼요
            </span>
            <span>
              이체될 때 가지고 있는 비드가 부족하면 적금은 자동으로 해지돼요
            </span>
          </div>
        </section>
      </section>
    </>
  );
}
