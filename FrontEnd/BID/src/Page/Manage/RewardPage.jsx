import React, { useEffect, useState } from "react";
import styled from "./RewardPage.module.css";
import SubmitButton from "../../Component/Common/SubmitButton";
import Reward from "../../Component/Reward/Reward";
import SettingButton from "../../Component/Common/SettingButton";
import SettingsIcon from "@mui/icons-material/Settings";
import {
  getRewardListApi,
  addNewRewardApi,
  sendRewardApi,
} from "../../Apis/RewardApis";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useSelector } from "react-redux";
import { studentSelector } from "../../Store/studentSlice";
import { mainSelector } from "../../Store/mainSlice";

export default function RewardPage() {
  const students = useSelector(studentSelector);
  const gradeNo = useSelector(mainSelector).no;

  const [isSetting, setIsSetting] = useState(false);
  const [studentList, setStudentList] = useState([]);
  const [rewardList, setRewardList] = useState([]);
  const [rStudents, setRStudents] = useState([]);
  const [rReward, setRReward] = useState(0);

  const queryClient = useQueryClient();

  /** 리워드 목록 쿼리 */
  useQuery({
    queryKey: ["rewardList"],
    queryFn: () =>
      getRewardListApi(gradeNo).then((res) => {
        if (res.data !== undefined) {
          setRewardList(res.data);
        }
        return res.data;
      }),
  });

  useEffect(() => {
    setStudentList(students);
  }, []);

  /** 리워드 추가 쿼리 */
  const addRewardQuery = useMutation({
    mutationKey: ["addNewReward"],
    mutationFn: (newRewardForm) => addNewRewardApi(gradeNo, newRewardForm),
    onSuccess: () => {
      queryClient.invalidateQueries("rewardList");
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 리워드 지급 쿼리 */
  const sendRewardQuery = useMutation({
    mutationKey: ["sendReward"],
    mutationFn: (postData) => {
      sendRewardApi(gradeNo, postData);
    },
    onSuccess: (res) => {
      console.log(res);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 새 리워드 등록 함수 */
  const addNewReward = (e) => {
    e.preventDefault();
    const newRewardForm = {
      name: e.target.name.value,
      price: e.target.price.value,
    };
    addRewardQuery.mutate(newRewardForm);
  };

  /** 리워드 지급 함수 */
  const sendReward = (e) => {
    e.preventDefault();
    if (rStudents.length === 0) {
      console.log("리워드를 지급할 학생을 선택해주세요");
    } else if (rReward === 0) {
      console.log("지급할 리워드를 선택해주세요");
    } else if (e.target.comment.value === "") {
      console.log("리워드와 함께 전달할 코멘트를 입력해주세요");
    } else {
      const rComment = e.target.comment.value;
      const postData = {
        no: rReward,
        usersNos: rStudents,
        comment: rComment,
      };
      sendRewardQuery.mutate(postData);
    }
  };

  /** 해당 학생의 현재 선택 여부 반환 함수 */
  const isCheked = (no) => {
    const findStudent = rStudents.find((stdNo) => stdNo === no);
    if (findStudent) {
      return true;
    } else {
      return false;
    }
  };

  /** 전체 학생 선택 함수 */
  const selectAllStudents = (checked) => {
    checked ? setRStudents(studentList.map((std) => std.no)) : setRStudents([]);
  };

  /** 특정 학생 선택 함수 */
  const selectStudent = (no, checked) => {
    checked
      ? setRStudents([...rStudents, no])
      : setRStudents(rStudents.filter((std) => std !== no));
  };

  /** 학생 번호 -> 이름 반환 함수 */
  const getStudentNameByNo = (no) => {
    const find = studentList.find((std) => std.no === no);
    return find.name;
  };

  /** 리워드 번호 -> 이름 반환 함수 */
  const getRewardNameByNo = (no) => {
    const find = rewardList.find((reward) => reward.no === no);
    return find.name;
  };

  return (
    <>
      <div className={styled.contentSection}>
        <div className={styled.rewardArea}>
          <div className={[styled.studentCol, styled.col].join(" ")}>
            <h3 className={styled.title}> 학생 목록 </h3>
            <div className={styled.body}>
              <table>
                <thead>
                  <tr>
                    <th>
                      <input
                        type="checkbox"
                        onChange={(e) => {
                          selectAllStudents(e.target.checked);
                        }}
                      />
                    </th>
                    <th>번호</th>
                    <th>이름</th>
                  </tr>
                </thead>
                <tbody>
                  {studentList &&
                    studentList.map((student) => (
                      <tr key={student.no}>
                        <td>
                          <input
                            type="checkbox"
                            checked={isCheked(student.no)}
                            onChange={(e) => {
                              selectStudent(student.no, e.target.checked);
                            }}
                          />
                        </td>
                        <td>{student.number}</td>
                        <td>{student.name}</td>
                      </tr>
                    ))}
                </tbody>
              </table>
            </div>
          </div>
          <div className={[styled.rewardCol, styled.col].join(" ")}>
            <div className={styled.header}>
              <h3 className={styled.title}> 리워드 목록 </h3>
              <SettingButton
                onClick={() => setIsSetting(!isSetting)}
                svg={SettingsIcon}
                text="리워드 편집"
                height="2vw"
                backgroundColor="#5FA1C4"
              />
            </div>
            <div className={styled.rewardContainer}>
              <div
                className={styled.body}
                style={{ height: isSetting ? "34vw" : "40vw" }}
              >
                {rewardList &&
                  rewardList.map((reward) => (
                    <Reward
                      key={reward.no}
                      rNo={reward.no}
                      rName={reward.name}
                      rPrice={reward.price}
                      isSetting={isSetting}
                      onClick={() => {
                        setRReward(reward.no);
                      }}
                      isActivated={rReward === reward.no}
                    />
                  ))}
              </div>
              {isSetting ? (
                <div className={styled.footer}>
                  <form onSubmit={addNewReward}>
                    <input type="text" name="name" placeholder="리워드 이름" />
                    <input type="number" name="price" placeholder="금액" />
                    <SubmitButton
                      text="추가"
                      width="4vw"
                      height="4vw"
                      fontSize="1.3vw"
                    />
                  </form>
                </div>
              ) : null}
            </div>
          </div>
          <div className={[styled.submitCol, styled.col].join(" ")}>
            <div className={styled.result}>
              <div className={styled.student}>
                <h3 className={styled.subTitle}> 리워드 대상 </h3>
                <div className={styled.body}>
                  {rStudents.map((std) => (
                    <div key={std}>{getStudentNameByNo(std)}</div>
                  ))}
                </div>
              </div>
              <div className={styled.reward}>
                <h3 className={styled.subTitle}> 지급 리워드 </h3>
                <div className={styled.body}>
                  {" "}
                  {rReward > 0 && getRewardNameByNo(rReward)}{" "}
                </div>
              </div>
            </div>
            <form onSubmit={sendReward}>
              <div className={styled.comment}>
                <h3 className={styled.title}> 코멘트 </h3>
                <textarea
                  name="comment"
                  placeholder="리워드와 함께 전달할 코멘트를 입력해주세요!"
                />
              </div>
              <SubmitButton
                text="리워드 지급"
                width="100%"
                height="4vw"
                fontSize="2vw"
              />
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
