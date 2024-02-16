import React, { useState, useEffect } from "react";
// import BarChart from "../../Component/FinData/BarChart";
import PieChart from "../../Component/FinData/PieChart";
// import Coupon from "../../Component/FinData/Coupon";
// import AttendRec from '../../Component/FinData/AttendRec'
import CalendarChart from "../../Component/FinData/CalendarChart";
import styled from "./MyPage.module.css";
// import Button2 from "../../Component/Common/Button2";
import SettingButton from "../../Component/Common/SettingButton";
import WriterButton from "../../Component/Bid/WriterButton";
import Product from "../../Component/Bid/Product";
import NoContent from "../../Component/Bid/NoContent";
import AvatarListComponent from "../../Component/User/AvatarListComponent";
import SettingsIcon from "@mui/icons-material/Settings";
import { useSelector } from "react-redux";
import { modelSelector, modelImgSelector } from "../../Store/modelSlice";
import { useMutation } from "@tanstack/react-query";
import { SvgIcon } from "@material-ui/core";
import LockIcon from "@mui/icons-material/Lock";
import {
  stuChangePwdApi,
  getMyBidListApi,
  getMyAvatarListApi,
  editAvatarApi,
  updateUsersApi,
} from "../../Apis/ModelApis";
import { useQuery } from "@tanstack/react-query";
import useModal from "../../hooks/useModal";
import useModels from "../../hooks/useModels";
import Back from "../../Asset/Image/SeatGame/back_btn.png";
import { useNavigate } from "react-router-dom";
import BarChart from "../../Component/FinData/BarChart";
import Coupon from "../../Component/FinData/Coupon";
import AttendRec from "../../Component/FinData/AttendRec";
import { getFinData } from "../../Apis/StudentApis";

function MyPage() {
  const [page, setPage] = useState("myFin");
  const myInfo = useSelector(modelSelector).model;
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordCheck, setNewPasswordCheck] = useState("");
  const [error, setError] = useState("");
  const [step, setStep] = useState(1); // Default step is 1
  const { initModels } = useModels();
  const { editModel } = useModels();
  const imgInfo = useSelector(modelImgSelector);
  const navigate = useNavigate();
  const [attandanceDays, setAttendanceDays] = useState([
    false,
    false,
    false,
    false,
    false,
  ]);
  const [categoryData, setCategoryData] = useState([
    { id: "간식", value: 0 },
    { id: "학습", value: 0 },
    { id: "쿠폰", value: 0 },
    { id: "대포", value: 0 },
    { id: "기타", value: 0 },
  ]);

  const [savingData, setSavingData] = useState([
    { savingNo: 0 },
    { savingDepositPeriod: 0 },
    { savingCurrentPeriod: 0 },
    { savingInterestRate: 0 },
    { savingResultPrice: 0 },
    { savingCurrentPrice: 0 },
  ]);

  const [calendarInfo, setCalendarInfo] = useState([]);

  // const [myProfileImg, SetmyProfileImg] =
  const { openModal } = useModal();
  const s3BaseUrl = "https://ssafya306.s3.ap-northeast-2.amazonaws.com/";
  const avatarList = [
    { url: "DefaultBody.png", name: "기본", avatarNo: 1 },
    { url: "AvocadoBody.png", name: "아보카도", avatarNo: 2 },
    { url: "DonutBody.png", name: "도넛", avatarNo: 3 },
    { url: "DoraemonBody.png", name: "도라에몽", avatarNo: 4 },
    { url: "IronManBody.png", name: "아이언맨", avatarNo: 5 },
    { url: "MarioBody.png", name: "슈퍼마리오", avatarNo: 6 },
    { url: "MinionBody.png", name: "미니언", avatarNo: 7 },
    { url: "PatrikBody.png", name: "뚱이", avatarNo: 8 },
    { url: "RibbonBody.png", name: "리본", avatarNo: 9 },
    { url: "SnowmanBody.png", name: "눈사람", avatarNo: 10 },
  ];

  /** 사용자 경매 정보 쿼리 */
  const { data: myBiddingInfo } = useQuery({
    queryKey: ["myBidList"],
    queryFn: () =>
      getMyBidListApi(myInfo.no).then((res) => {
        return res.data;
      }),
  });

  /** 사용자 보유 아바타 정보 쿼리 */
  const { data: myAvatarInfo } = useQuery({
    queryKey: ["myAvatarList"],
    queryFn: () =>
      getMyAvatarListApi(myInfo.no).then((res) => {
        return res.data;
      }),
  });

  /** 아바타 변경 쿼리 */
  const editAvatarQuery = useMutation({
    mutationKey: ["editAvatar"],
    mutationFn: (avatarNo) => editAvatarApi(avatarNo),
    onSuccess: (data) => {
      console.log(data);
      updateUsersQuery.mutate();
    },
    onError: (error) => {
      console.log(error);
      setError("비밀번호 확인 값이 일치하지 않습니다");
    },
  });

  /** 비밀번호 변경 쿼리 */
  const stuChangePwdQuery = useMutation({
    mutationKey: ["stuChangePwd"],
    mutationFn: (newPwd) => stuChangePwdApi(newPwd),
    onSuccess: (data) => {
      console.log(data);
      setStep(2);
    },
    onError: (error) => {
      console.log(error);
      setError("비밀번호 확인 값이 일치하지 않습니다");
    },
  });

  /** 비밀번호 변경 handler */
  const handleChangePwd = (e) => {
    e.preventDefault();
    let newPwd = {
      currentPassword,
      newPassword,
      newPasswordCheck,
    };
    console.log(newPwd);
    stuChangePwdQuery.mutate(newPwd);
  };
  useEffect(() => {
    // step이 변경될 때 에러 메시지를 초기화합니다.
    setError("");
  }, [step]);

  /** 버튼 클릭 -> 내 활동 / 내 경매 / 내 아바타 전환 함수 */
  const changePage = (newPage) => {
    if (page === "myFin" && newPage === "myFin") {
    } else if (page === "myBid" && newPage === "myBid") {
    } else if (page === "myAvatar" && newPage === "myAvatar") {
    } else {
      setPage(newPage);
    }
  };

  const updateUsersQuery = useMutation({
    mutationKey: ["updateUsers"],
    mutationFn: () => updateUsersApi(),
    onSuccess: (res) => {
      console.log(res);
      initModels({ models: res.data }); // 데이터 업데이트
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const changeAvatar = (clickedAvatarNo) => {
    const targetAvatar = myAvatarInfo.find(
      (avatarInfo) => avatarInfo.avatarNo === clickedAvatarNo
    );
    if (targetAvatar) {
      let avatarNo = {
        no: targetAvatar.userAvatarNo,
      };
      console.log("장착하기");
      // 찾은 아바타가 있으면 해당 아바타의 userAvatarNo를 사용하여 editAvatarApi 호출
      editAvatarQuery.mutate(avatarNo);
      editModel(targetAvatar.url);
    } else {
      // 찾은 아바타가 없으면 어떤 처리를 하거나 메시지를 표시할 수 있습니다.
      alert("해당 아바타를 찾을 수 없습니다.");
    }
  };

  const { data: studentData } = useQuery({
    queryKey: ["StudentFinData", `studentNo_${myInfo.no}`],
    queryFn: () =>
      getFinData(myInfo.no, "2024-02-01", "2024-02-28")
        .then((res) => {
          return res.data;
        })
        .catch((e) => console.log(e)),
  });

  useEffect(() => {
    if (studentData) {
      console.log(studentData);
      const {
        savingNo,
        savingDepositPeriod,
        savingInterestRate,
        savingCurrentPeriod,
        savingCurrentPrice,
        savingResultPrice,
      } = studentData;
      setAttendanceDays([
        studentData.attendanceMonday,
        studentData.attendanceTuesday,
        studentData.attendanceWednesday,
        studentData.attendanceThursday,
        studentData.attendanceFriday,
      ]);
      setCategoryData((prevCategoryData) => {
        return prevCategoryData.map((category) => {
          switch (category.id) {
            case "간식":
              return { ...category, value: studentData.snackSum };
            case "학습":
              return { ...category, value: studentData.learningSum };
            case "쿠폰":
              return { ...category, value: studentData.couponSum };
            case "대포":
              return { ...category, value: studentData.gameSum };
            case "기타":
              return { ...category, value: studentData.etcSum };
            default:
              return category;
          }
        });
      });

      setSavingData({
        savingNo,
        savingDepositPeriod,
        savingInterestRate,
        savingCurrentPeriod,
        savingCurrentPrice,
        savingResultPrice,
      });

      setCalendarInfo(
        studentData.accountsResponses.map((data, index) => ({
          id: index,
          title: data.accountType,
          start: new Date(2024, new Date().getMonth(), data.day),
          end: new Date(2024, new Date().getMonth(), data.day),
          amount: data.totalPrice,
          type: data.dealType,
        }))
      );
    }
  }, [studentData]);

  return (
    <div className={styled.section}>
      <div className={styled.dashboardContainer}>
        <div className={styled.leftArea}>
          <div className={styled.userInfoArea}>
            <img
              className={styled.back}
              src={Back}
              alt="뒤로가기"
              onClick={() => navigate("/studentMain")}
              onError={(e) =>
                (e.target.src =
                  "https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg")
              }
            />
            <div className={styled.imgArea}>
              <img
                src={imgInfo}
                alt="이미지"
                onError={(e) =>
                  (e.target.src =
                    "https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg")
                }
              />
            </div>
            <div className={styled.descArea}>
              <div className={styled.schoolName}>
                {myInfo.schoolName} {myInfo.IdInfo.grade}학년{" "}
                {parseInt(myInfo.IdInfo.class)}반{" "}
                {parseInt(myInfo.IdInfo.number)}번
              </div>
              <div className={styled.name}> {myInfo.name} </div>
              <div className={styled.asset}>
                {" "}
                총 자산을 <span>{myInfo.asset}비드</span> 모았어요!{" "}
              </div>
            </div>
          </div>
          <div className={styled.buttonContainer}>
            {/* <Button2 text="비밀번호 변경" /> */}
            <SettingButton
              onClick={() => setPage("changePwd")}
              svg={SettingsIcon}
              text="비밀번호 변경"
              height="3vw"
              backgroundColor="#5FA1C4"
            />
          </div>
        </div>
        <div className={styled.rightArea}>
          <div className={styled.pageSelectArea}>
            <WriterButton
              onClick={() => changePage("myFin")}
              text="내 활동"
              active={page === "myFin"}
            />
            <WriterButton
              onClick={() => changePage("myBid")}
              text="내 경매"
              active={page === "myBid"}
            />
            <WriterButton
              onClick={() => changePage("myAvatar")}
              text="내 아바타"
              active={page === "myAvatar"}
            />
          </div>
          <div className={styled.pageContent}>
            {page === "myFin" ? (
              <>
                {studentData && (
                  <div className={styled.dashboard}>
                    <div className={styled.chartsContainer}>
                      <BarChart savingData={savingData} />
                      <PieChart data={categoryData} />
                      <Coupon data={studentData.couponsResponses} />
                    </div>
                    <div className={styled.additionalChartsContainer}>
                      <AttendRec
                        className={styled.AttendRec}
                        attandance={attandanceDays}
                        ball={studentData.ballCount}
                      />
                      <CalendarChart event={calendarInfo} />
                    </div>
                  </div>
                )}
              </>
            ) : page === "myBid" ? (
              <div className={styled.myBidContainer}>
                <div className={`${styled.biddedArea} ${styled.myBidArea}`}>
                  <h3>내가 입찰한 경매</h3>
                  <div className={styled.products}>
                    {myBiddingInfo &&
                    myBiddingInfo.myBiddingBoards.length === 0 ? (
                      <NoContent text="아직 입찰 중인 경매가 없어요" />
                    ) : (
                      myBiddingInfo &&
                      myBiddingInfo.myBiddingBoards.map((product) => (
                        <Product
                          onClick={() => {
                            console.log(product);
                            openModal({
                              type: "viewProduct",
                              props: [product.no],
                            });
                          }}
                          key={product.no}
                          title={product.title}
                          displayPrice={product.displayPrice}
                          goodsImgUrl={product.goodsImgUrl}
                          userName={product.userName}
                          boardStatus={product.boardStatus}
                        />
                      ))
                    )}
                  </div>
                </div>
                <div className={`${styled.uploadedArea} ${styled.myBidArea}`}>
                  <h3>내가 올린 경매</h3>
                  <div className={styled.products}>
                    {myBiddingInfo && myBiddingInfo.myBoards.length === 0 ? (
                      <NoContent text="아직 작성한 경매글이 없어요" />
                    ) : (
                      myBiddingInfo &&
                      myBiddingInfo.myBoards.map((product) => (
                        <Product
                          onClick={() => {
                            console.log(product);
                            openModal({
                              type: "viewProduct",
                              props: [product.no],
                            });
                          }}
                          key={product.no}
                          title={product.title}
                          displayPrice={product.displayPrice}
                          goodsImgUrl={product.goodsImgUrl}
                          boardStatus={product.boardStatus}
                        />
                      ))
                    )}
                  </div>
                </div>
              </div>
            ) : page === "myAvatar" ? (
              <div className={styled.myAvatarContainer}>
                {console.log(myAvatarInfo)}
                {myAvatarInfo &&
                  avatarList.map((avatar) => {
                    const isEquipped = myAvatarInfo.some(
                      (info) => info.avatarNo === avatar.avatarNo
                    );
                    return (
                      <div
                        key={avatar.url}
                        className={
                          isEquipped ? styled.avatarEquipped : styled.avatar
                        }
                        onClick={() => {
                          if (isEquipped) {
                            changeAvatar(avatar.avatarNo); // 클릭 시 changeAvatar 함수 호출
                          }
                        }}
                      >
                        <img
                          src={s3BaseUrl + avatar.url}
                          alt={avatar.name}
                          className={
                            isEquipped
                              ? styled.avatarImage
                              : styled.avatarImageInactive
                          }
                          onError={(e) =>
                            (e.target.src =
                              "https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg")
                          }
                        />
                        {!isEquipped && (
                          <div className={styled.avatarOverlay}></div>
                        )}
                      </div>
                    );
                  })}
              </div>
            ) : (
              <>
                <div>
                  {step === 1 && (
                    <form
                      className={styled.contentInput}
                      onSubmit={handleChangePwd}
                    >
                      <div className={styled.telInput}>
                        <div className={styled.inputWithIcon}>
                          <SvgIcon
                            component={LockIcon}
                            style={{ fill: "#4D4D4D", height: "2.5vh" }}
                            className={styled.icon}
                          />
                          <input
                            type="password"
                            placeholder="현재 비밀번호"
                            value={currentPassword}
                            onChange={(e) => setCurrentPassword(e.target.value)}
                          />
                        </div>
                        <div className={styled.inputWithIcon}>
                          <SvgIcon
                            component={LockIcon}
                            style={{ fill: "#4D4D4D", height: "2.5vh" }}
                            className={styled.icon}
                          />
                          <input
                            type="password"
                            placeholder="새 비밀번호"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                          />
                        </div>
                        <div className={styled.inputWithIcon}>
                          <SvgIcon
                            component={LockIcon}
                            style={{ fill: "#4D4D4D", height: "2.5vh" }}
                            className={styled.icon}
                          />
                          <input
                            type="password"
                            placeholder="새 비밀번호 확인"
                            value={newPasswordCheck}
                            onChange={(e) =>
                              setNewPasswordCheck(e.target.value)
                            }
                          />
                        </div>
                      </div>
                      {error && <p className={styled.error}>{error}</p>}
                      <button className={styled.changePwdBtn} type="submit">
                        비밀번호 재설정
                      </button>
                    </form>
                  )}
                  {step === 2 && <div>비밀번호 변경 성공!</div>}
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyPage;
