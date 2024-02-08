import React,{useState} from "react";
import styled from "./LoginPage.module.css";
import { Link, useNavigate } from "react-router-dom";
import Logo from '../../Asset/Image/LoginLogo.png';
import { useDispatch, useSelector } from "react-redux";
import { loginUser } from "../../Store/UserSlice";

function LoginPage() {

  const [id, setId] = useState('')
  const [password, setPassword] = useState('')


  const {loading, error} = useSelector((state)=>state.user)

  const dispatch = useDispatch()
  const navigate = useNavigate()
  const handleLoginEvent = (e) => {
    e.preventDefault()
    let userCredentials={
      id, password
    }
    dispatch(loginUser(userCredentials)).then((result)=>{
      if (result.payload){
        setId('')
        setPassword('')
        navigate('/')
      }
    })
  }

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} onSubmit={handleLoginEvent}>
          <input type="id" value={id} 
          onChange={(e)=> setId(e.target.value)}
          placeholder="아이디" />
          <input type="password" value={password} 
          onChange={(e)=> setPassword(e.target.value)}
          placeholder="비밀번호" />
          <button type="submit" disabled={loading}>
            {loading?'Loading..':'LOGIN'}
            </button>
            {error&&(
              <div>{error}</div>
            )}
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

export default LoginPage;
