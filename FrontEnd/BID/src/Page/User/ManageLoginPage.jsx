import React, { useEffect, useState } from "react";
import styled from "./LoginPage.module.css";
import { Link, useNavigate } from "react-router-dom";
import Logo from "../../Asset/Image/LoginLogo.png";
import useUser from "../../hooks/useUser";
import { loginUserApi } from "../../Apis/UserApis";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { setCookie } from "../../cookie";
import { getGrades } from "../../Apis/ClassManageApis";
import useMain from "../../hooks/useMain";
import { useSelector } from "react-redux";
import { mainSelector } from "../../Store/mainSlice";

function ManageLoginPage() {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const { loginUser } = useUser();
  const { initClass } = useMain();
  const mainClass = useSelector(mainSelector);

  const navigate = useNavigate();
  const queryClient = useQueryClient();

  /** 로그인 쿼리 */
  const loginUserQuery = useMutation({
    mutationKey: ["loginUser"],
    mutationFn: (userCredentials) => loginUserApi(userCredentials),
    onSuccess: async (data) => {
      console.log(">> 2 "+"get into loginUserQuery");
      loginUser(data);

      console.log(">> 4 "+"after loginUser");
      setCookie("accessToken", data.data.tokenResponse.accessToken);
      console.log(">> 5 "+"after setCookie");
      console.log(">> 6 "+"before setQueryData('ClassList')");
      await queryClient.invalidateQueries("ClassList");
      console.log("????끝난 거 맞냐? 다시");
      if (mainClass) {
        console.log(">> 8-1 "+"mainClass exist!");
        console.log(mainClass)
        navigate("/");
      } else {
        console.log(">> 8-2 "+"mainClass not exist!");
        navigate(`/classlist/${data.data.adminInfo.userNo}/no-class`, {
          state: {
            teacherId: data.data.adminInfo.userNo,
          },
        });
      }
    },
    onError: (error) => {
      console.log(error);
    },
  });

  useQuery({
    queryKey: ["ClassList"],
    queryFn: () =>
      getGrades().then((res) => {
        console.log(">> 8 "+"Entering setQueryData('ClassList'), res:");
        console.log(res);
        const foundMainClass = res.data.find((item) => item.main === true);
        console.log('so... found this!')
        console.log(foundMainClass);
        initClass(foundMainClass);
        queryClient.setQueryData("ClassList", res.data);
        return res.data;
      }),
  });


  /** 로그인 버튼 */
  const handleLoginEvent = (e) => {
    e.preventDefault();
    let userCredentials = {
      id,
      password,
    };
    console.log(">> 1 "+"handleLoginEvent");
    loginUserQuery.mutate(userCredentials);
  };

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} onSubmit={handleLoginEvent}>
          <input
            type="id"
            value={id}
            onChange={(e) => setId(e.target.value)}
            placeholder="아이디"
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호"
          />
          <button type="submit">LOGIN</button>
          <div className={styled.ContentOption}>
            <div className={styled.ContentOptions}>
              <Link to="/find_id" className={styled.findIdLink}>
                <p>아이디 찾기</p>
              </Link>
              <Link to="/change_pwd" className={styled.changePwdLink}>
                <p>비밀번호 찾기</p>
              </Link>
              <Link to="/register" className={styled.registerLink}>
                <p>회원가입</p>
              </Link>
            </div>
          </div>
        </form>
      </div>
    </section>
  );
}

export default ManageLoginPage;
