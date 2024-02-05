import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGear } from "@fortawesome/free-solid-svg-icons";
import styled from "./BankPage.module.css";
import { viewSavingList } from "../../Apis/TeacherBankApis";
import { useQuery } from "@tanstack/react-query";
import useSaving from "../../hooks/useSaving";

export default function BankPage() {
  // 이후 백엔드에서 국고 금액 받아오면 바꾸기
  // const [bankMoney, setBankMoney] = useState(0);
  const [savingInfo, setSavingInfo] = useState({
    savingA: 30,
    interestA: 5,
    savingB: 150,
    interestB: 5,
  });

  const [isEdit, setIsEdit] = useState(false);
  const { initSavingList } = useSaving();
  const handleClick = () => {
    if (isEdit) {
      handleSubmit();
    }
    setIsEdit(!isEdit);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSavingInfo((saving) => ({ ...saving, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 백엔드로 현재 savingInfo 넘기기
  };

  const { data: saving } = useQuery({
    queryKey: ["savingInfo"],
    queryFn: () =>
      viewSavingList().then((res) => {
        console.log(res.data);
        initSavingList(res.data);
        return res.data;
      }),
  });
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
          {isEdit ? (
            <button className={styled.classSetting} onClick={handleClick}>
              <FontAwesomeIcon className={styled.icon} icon={faGear} />
              편집 완료
            </button>
          ) : (
            <button className={styled.classSetting} onClick={handleClick}>
              <FontAwesomeIcon className={styled.icon} icon={faGear} />
              적금 편집
            </button>
          )}
        </section>
        <section className={styled.savingView}>
          <div className={styled.savingA}>
            <div className={styled.savingAImage}></div>
            <span className={styled.savingTitle}>적금</span>
            <span className={styled.kind}>A</span>
            <section className={styled.inputArea}>
              <div>
                <label htmlFor="period">총 기간</label>
                <input type="number" value="2" id="period" readOnly />주
              </div>
              <div>
                <label htmlFor="transit">이체 주기</label>
                <input type="number" value="1" id="transit" readOnly />일
              </div>
              <div>
                <label htmlFor="saving">입금 금액</label>
                <input
                  className={
                    isEdit
                      ? `${styled.saving} ${styled.isEdit}`
                      : `${styled.saving}`
                  }
                  type="number"
                  id="saving"
                  value={savingInfo.savingA}
                  name="savingA"
                  onChange={handleChange}
                  readOnly={isEdit ? false : true}
                />
                비드
              </div>
              <div>
                <label htmlFor="interest">금리</label>
                <input
                  className={isEdit ? `${styled.isEdit}` : ""}
                  type="number"
                  value={savingInfo.interestA}
                  id="interest"
                  name="interestA"
                  onChange={handleChange}
                  readOnly={isEdit ? false : true}
                />
                %
              </div>
            </section>
          </div>
          <div className={styled.savingB}>
            <div className={styled.savingBImage}></div>
            <span className={`${styled.savingTitle} ${styled.version2}`}>
              적금
            </span>
            <span className={`${styled.kind} ${styled.version2}`}>B</span>
            <section className={styled.inputArea}>
              <div>
                <label htmlFor="period">총 기간</label>
                <input type="number" value="4" id="period" readOnly />주
              </div>
              <div>
                <label htmlFor="transit">이체 주기</label>
                <input type="number" value="1" id="transit" readOnly />
                주일
              </div>
              <div>
                <label htmlFor="saving">입금 금액</label>
                <input
                  className={
                    isEdit
                      ? `${styled.saving} ${styled.isEdit}`
                      : `${styled.saving}`
                  }
                  type="number"
                  value={savingInfo.savingB}
                  onChange={handleChange}
                  id="saving"
                  name="savingB"
                  readOnly={isEdit ? false : true}
                />
                비드
              </div>
              <div>
                <label htmlFor="interest">금리</label>
                <input
                  className={isEdit ? `${styled.isEdit}` : ""}
                  type="number"
                  value={savingInfo.interestB}
                  onChange={handleChange}
                  id="interest"
                  name="interestB"
                  readOnly={isEdit ? false : true}
                />
                %
              </div>
            </section>
          </div>
        </section>
      </section>
    </>
  );
}
