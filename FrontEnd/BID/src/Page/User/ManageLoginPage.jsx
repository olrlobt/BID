import React,{useState} from "react";
import styled from "./LoginPage.module.css";
import { Link , useNavigate } from "react-router-dom";
import Logo from '../../Asset/Image/LoginLogo.png';
import useUser from "../../hooks/useUser";
import { loginUserApi } from "../../Apis/UserApis";
import { useMutation } from "@tanstack/react-query";

function ManageLoginPage() {

  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const { loginUser } = useUser();
  const navigate = useNavigate()


  /** 로그인 쿼리 */
  const loginUserQuery = useMutation({
    mutationKey: ['loginUser'],
    mutationFn: (userCredentials) => loginUserApi( userCredentials),
    onSuccess: (data) => {
      loginUser(data);
      navigate('/');
      console.log(data)
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 로그인 버튼 */
  const handleLoginEvent = (e) => {
    e.preventDefault()
    let userCredentials = {
      id, 
      password
    }
    console.log(userCredentials)
    loginUserQuery.mutate(userCredentials);
  }
  
  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} 
        onSubmit={handleLoginEvent}>
          <input 
            type="id" 
            value={id} 
            onChange={(e)=> setId(e.target.value)}
            placeholder="아이디" 
          />
          <input 
            type="password" 
            value={password} 
            onChange={(e)=> setPassword(e.target.value)}
            placeholder="비밀번호"
          />
          <button type="submit">
            LOGIN
          </button>
          <div className={styled.ContentOption}>
            <div className={styled.ContentOptions}>
              <Link to="/find_id" className={styled.findIdLink}>
                <p>아이디 찾기</p> 
              </Link >
              <Link to="/change_pwd" className={styled.changePwdLink}>
                <p>비밀번호 찾기</p> 
              </Link >
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
