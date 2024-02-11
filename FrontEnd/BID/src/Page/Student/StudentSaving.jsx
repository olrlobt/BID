import React from 'react';
import styled from '../Manage/BankPage.module.css';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useSelector } from 'react-redux';
import { savingSelector } from '../../Store/savingSlice';
import {
  applyStudentSaving,
  getStudentSavingInfo,
} from '../../Apis/StudentApis';
import Back from '../../Asset/Image/SeatGame/back_btn.png';
import { useNavigate } from 'react-router-dom';
import { studentSavingSelector } from '../../Store/studentSavingSlice';
import useStudentSaving from '../../hooks/useStudentSaving';

export default function StudentSaving() {
  const navigate = useNavigate();
  const savingList = useSelector(savingSelector);
  const studentSaving = useSelector(studentSavingSelector);
  const { initStudentSavingList, changeStudentSavingList } = useStudentSaving();
  // 학생 가입 정보 가져오기
  const {
    data, // eslint-disable-line no-unused-vars
  } = useQuery({
    queryKey: ['studentSavingInfo'],
    queryFn: () =>
      getStudentSavingInfo().then((res) => {
        initStudentSavingList(res.data);
        return res.data;
      }),
  });

  const handleSubmit = (wantedSaving) => {
    const alreadyApplied = studentSaving.filter((saving) => saving.mySaving);
    const applySavingData = {
      no: wantedSaving.savingNo,
      depositPeriod: wantedSaving.savingDepositPeriod,
      depositCycle: wantedSaving.savingDepositCycle,
      depositPrice: wantedSaving.savingDepositPrice,
      interestRate: wantedSaving.savingInterestRate,
    };
    if (alreadyApplied.length === 0) {
      changeStudentSavingList(applySavingData.no);
      applySaving.mutate(applySavingData);
    } else {
      alert('이미 가입된 적금이 있어 가입이 어렵습니다.');
    }
  };

  const applySaving = useMutation({
    mutationKey: ['studenApplySaving'],
    mutationFn: (wantedSaving) =>
      applyStudentSaving(wantedSaving)
        .then(() => {
          alert('가입되었습니다.');
        })
        .catch(() => {
          alert('가입이 되지 않았습니다.');
        }),
  });

  return (
    <>
      {savingList && studentSaving && (
        <section className={styled.studentBank}>
          <img
            className={styled.back}
            src={Back}
            alt="뒤로가기"
            onClick={() => navigate('/studentMain')}
          />
          <section className={styled.bankPage}>
            <section className={styled.bankSection}>
              <div className={styled.saveInfo}>
                현재 국고에는 <span>{savingList[0].asset}비드</span> 있어요
              </div>
              <div className={styled.saveTime}>
                <span className={styled.saveTimeAlarm}>
                  매일 1교시 시작 전 이체 알림이 발송되고, 6교시 종료 전
                  자동으로 이체돼요
                </span>
                <span>
                  이체될 때 가지고 있는 비드가 부족하면 적금은 자동으로 해지돼요
                </span>
              </div>
            </section>
            <section className={styled.savingView}>
              <div className={`${styled.savingA} ${styled.forStu}`}>
                <div className={styled.savingAImage}></div>
                <span className={styled.savingTitle}>적금</span>
                <span className={styled.kind}>A</span>
                {studentSaving[0].mySaving ? (
                  <button
                    className={`${styled.applyBtn} ${styled.already}`}
                    disabled
                  >
                    가입
                  </button>
                ) : (
                  <button
                    className={styled.applyBtn}
                    onClick={() => handleSubmit(studentSaving[0])}
                  >
                    가입
                  </button>
                )}
                <section className={styled.inputArea}>
                  <div>
                    <span className={styled.title}>총 기간</span>
                    <span>{savingList[0].savingDepositPeriod / 7}주</span>
                  </div>
                  <div>
                    <span className={styled.title}>이체 주기</span>
                    <span>{savingList[0].savingDepositCycle}일</span>
                  </div>
                  <div>
                    <span className={styled.title}>입금 금액</span>
                    <span>{savingList[0].savingDepositPrice}비드</span>
                  </div>
                  <div>
                    <span className={styled.title}>금리</span>
                    <span>{savingList[0].savingInterestRate}%</span>
                  </div>
                </section>
              </div>
              <div className={`${styled.savingB} ${styled.forStu}`}>
                <div className={styled.savingBImage}></div>
                <span className={`${styled.savingTitle} ${styled.version2}`}>
                  적금
                </span>
                <span className={`${styled.kind} ${styled.version2}`}>B</span>
                {studentSaving[1].mySaving ? (
                  <button
                    className={`${styled.applyBtn} ${styled.already}`}
                    disabled
                  >
                    가입
                  </button>
                ) : (
                  <button
                    className={styled.applyBtn}
                    onClick={() => handleSubmit(studentSaving[1])}
                  >
                    가입
                  </button>
                )}
                <section className={styled.inputArea}>
                  <div>
                    <span className={styled.title}>총 기간</span>
                    <span>{savingList[1].savingDepositPeriod / 7}주</span>
                  </div>
                  <div>
                    <span className={styled.title}>이체 주기</span>
                    <span>{savingList[1].savingDepositCycle}일</span>
                  </div>
                  <div>
                    <span className={styled.title}>입금 금액</span>
                    <span>{savingList[1].savingDepositPrice}비드</span>
                  </div>
                  <div>
                    <span className={styled.title}>금리</span>
                    <span>{savingList[1].savingInterestRate}%</span>
                  </div>
                </section>
              </div>
            </section>
          </section>
        </section>
      )}
    </>
  );
}
