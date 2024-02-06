import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGear } from "@fortawesome/free-solid-svg-icons";
import styled from "./BankPage.module.css";
import { updateSavingList, viewSavingList } from "../../Apis/TeacherManageApis";
import { useMutation, useQuery } from "@tanstack/react-query";
import useSaving from "../../hooks/useSaving";
import { useSelector } from "react-redux";
import { moneySeletor } from "../../Store/moneySlice";

export default function BankPage() {
  // 이후 백엔드에서 국고 금액 받아오면 바꾸기
  const classMoney = useSelector(moneySeletor);

  const [isEdit, setIsEdit] = useState(false);
  const { initSavingList, changeSavingList } = useSaving();
  const [savingBasket, setSavingBasket] = useState([
    {
      no: 1,
      depositPrice: 0,
      interestRate: 0,
    },
    {
      no: 2,
      depositPrice: 0,
      interestRate: 0,
    },
  ]);

  const handleClick = () => {
    setIsEdit(!isEdit);
  };

  const handleChange = (e, index) => {
    const { name, value } = e.target;
    setSavingBasket((prevSaving) => {
      const updatedSaving = [...prevSaving];
      updatedSaving[index] = { ...updatedSaving[index], [name]: value };
      return updatedSaving;
    });
  };

  const handleSubmit = () => {
    changeSavings.mutate();
  };

  const changeSavings = useMutation({
    mutationKey: ["changeSaving"],
    mutationFn: () =>
      updateSavingList(savingBasket)
        .then(() => {
          changeSavingList(savingBasket);
          setIsEdit(!isEdit);
          alert("변경되었습니다.");
        })
        .catch(() => {
          alert("변경이 되지 않았습니다.");
        }),
  });

  const { data: savingInfo } = useQuery({
    queryKey: ["savingInfo"],
    queryFn: () =>
      viewSavingList().then((res) => {
        initSavingList(res.data);
        setSavingBasket((prev) => [
          {
            ...prev[0],
            depositPrice: res.data[0].savingDepositPrice,
            interestRate: res.data[0].savingInterestRate,
          },
          {
            ...prev[1],
            depositPrice: res.data[1].savingDepositPrice,
            interestRate: res.data[1].savingInterestRate,
          },
        ]);
        return res.data;
      }),
  });

  useEffect(() => {}, [isEdit]);

  return (
    <>
      {savingInfo && (
        <section className={styled.bankPage}>
          <section className={styled.bankSection}>
            <div className={styled.saveInfo}>
              현재 국고에는 <span>{classMoney}비드</span> 있어요
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
              <button className={styled.classSetting} onClick={handleSubmit}>
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
                  <input
                    type="number"
                    value={savingInfo[0].savingDepositPeriod / 7}
                    id="period"
                    readOnly
                  />
                  주
                </div>
                <div>
                  <label htmlFor="transit">이체 주기</label>
                  <input
                    type="number"
                    value={savingInfo[0].savingDepositCycle}
                    id="transit"
                    readOnly
                  />
                  일
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
                    name="depositPrice"
                    value={savingBasket[0].depositPrice}
                    onChange={(e) => handleChange(e, 0)}
                    readOnly={isEdit ? false : true}
                  />
                  비드
                </div>
                <div>
                  <label htmlFor="interest">금리</label>
                  <input
                    className={isEdit ? `${styled.isEdit}` : ""}
                    type="number"
                    value={savingBasket[0].interestRate}
                    id="interest"
                    name="interestRate"
                    onChange={(e) => handleChange(e, 0)}
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
                  <input
                    type="number"
                    value={savingInfo[1].savingDepositPeriod / 7}
                    id="period"
                    readOnly
                  />
                  주
                </div>
                <div>
                  <label htmlFor="transit">이체 주기</label>
                  <input
                    type="number"
                    value={savingInfo[1].savingDepositCycle / 7}
                    id="transit"
                    readOnly
                  />
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
                    value={savingBasket[1].depositPrice}
                    onChange={(e) => handleChange(e, 1)}
                    id="saving"
                    name="depositPrice"
                    readOnly={isEdit ? false : true}
                  />
                  비드
                </div>
                <div>
                  <label htmlFor="interest">금리</label>
                  <input
                    className={isEdit ? `${styled.isEdit}` : ""}
                    type="number"
                    value={savingBasket[1].interestRate}
                    onChange={(e) => handleChange(e, 1)}
                    id="interest"
                    name="interestRate"
                    readOnly={isEdit ? false : true}
                  />
                  %
                </div>
              </section>
            </div>
          </section>
        </section>
      )}
    </>
  );
}
