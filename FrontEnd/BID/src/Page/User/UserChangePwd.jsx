import React, { useState } from "react";
import styled from "./UserChangePwd.module.css";
import { useMutation } from "@tanstack/react-query";
import { SvgIcon } from "@material-ui/core";
import LockIcon from "@mui/icons-material/Lock";
import { userChangePwdApi } from "../../Apis/UserApis";
import alertBtn from "../../Component/Common/Alert";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { userSelector } from "../../Store/userSlice";
import { modelSelector } from "../../Store/modelSlice";

function UserChangePwd() {
  const teacher = useSelector(userSelector);
  const student = useSelector(modelSelector);

  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordCheck, setNewPasswordCheck] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const userChangePwdQuery = useMutation({
    mutationKey: ["changePwd"],
    mutationFn: (newPwd) => userChangePwdApi(newPwd), // corrected typo here
    onSuccess: (data) => {
      alertBtn({
        text: "비밀번호가 변경되었습니다.",
        confirmButtonColor: "#ffd43a",
        icon: "success",
      });
      setError("");
      if (teacher) {
        navigate(`/classlist/${teacher.adminInfo.userNo}/`);
      } else {
        console.log(student);
      }
    },
    onError: (error) => {
      setError("비밀번호 확인 값이 일치하지 않습니다");
    },
  });

  const handleChangePwd = (e) => {
    e.preventDefault();
    let newPwd = {
      password,
      newPassword,
      newPasswordCheck,
    };
    console.log(newPwd);
    userChangePwdQuery.mutate(newPwd);
  };

  return (
    <section className={styled.back}>
      <div className={styled.content}>
        <form className={styled.contentInput} onSubmit={handleChangePwd}>
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
                value={password}
                onChange={(e) => setPassword(e.target.value)}
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
                onChange={(e) => setNewPasswordCheck(e.target.value)}
              />
            </div>
          </div>
          {error && <p className={styled.error}>{error}</p>}
          <button className={styled.changePwdBtn} type="submit">
            비밀번호 재설정
          </button>
        </form>
      </div>
    </section>
  );
}

export default UserChangePwd;
