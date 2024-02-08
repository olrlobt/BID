import React,{useState} from "react";
import styled from "./LoginPage.module.css";
import { Link , useNavigate } from "react-router-dom";
import Logo from '../../Asset/Image/LoginLogo.png';
import useModels from "../../hooks/useModels";
import { studentLoginApi } from "../../Apis/StudentPageApis";
import { useMutation } from "@tanstack/react-query";

function LoginPage() {

  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const { loginStudent } = useModels();
  const navigate = useNavigate()


  /** 로그인 쿼리 */
  const studentLoginQuery = useMutation({
    mutationKey: ['studentLogin'],
    mutationFn: (userCredentials) => studentLoginApi( userCredentials),
    onSuccess: (data) => {
      loginStudent(data);
      navigate('/studentmain');
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
    studentLoginQuery.mutate(userCredentials);
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
              <Link to="/managelogin" className={styled.findIdLink}>
                <p>선생님으로 로그인하기</p> 
              </Link >
            </div>
          </div>
        </form>
      </div>
    </section>
  );
}

export default LoginPage;
