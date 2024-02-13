import React,{useState} from "react";
import styled from "./LoginPage.module.css";
import { Link , useNavigate } from "react-router-dom";
import Logo from '../../Asset/Image/LoginLogo.png';
import useModels from "../../hooks/useModels";
import { studentLoginApi } from "../../Apis/ModelApis";
import { useMutation } from "@tanstack/react-query";
import { SvgIcon } from "@material-ui/core";
import SearchIcon from '@mui/icons-material/Search';
import useModal from "../../hooks/useModal";

function LoginPage() {

  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const { loginStudent } = useModels();
  const { initModels } = useModels();
  const navigate = useNavigate()
  const { openModal } = useModal();
  const [schoolCode, setSchoolCode] = useState("");


  /** 로그인 쿼리 */
  const studentLoginQuery = useMutation({
    mutationKey: ['studentLogin'],
    mutationFn: (userCredentials) => studentLoginApi( userCredentials),
    onSuccess: (res) => {
      loginStudent({ model: res.data});
      initModels({ models: res.data.studentList });
      localStorage.setItem('accessToken', res.data.tokenResponse.accessToken);
      localStorage.setItem('refreshToken', res.data.tokenResponse.refreshToken);
      navigate('/studentmain');
      console.log(res.data)
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
  

  const handlerModalClose = (schoolCode) => {
    console.log(schoolCode)
    if (schoolCode) {
      setSchoolCode(schoolCode)
    }
  }

  return (
    <section className={styled.back}>
      <div className={styled.logo}>
        <img src={Logo} alt="로고" />
      </div>
      <div className={styled.content}>
        <form className={styled.contentInput} 
        onSubmit={handleLoginEvent}>
          <div className={styled.inputWithIcon}>
          <input 
            type="id" 
            value={schoolCode} 
            onChange={(e)=> setId(e.target.value)}
            placeholder="아이디" 
          />
          <SvgIcon 
            component={SearchIcon} 
            style={{ fill: "#4D4D4D", height: "2.5vh" }}
            className={styled.icon}
            onClick={(e) => {
              e.preventDefault(); // Prevent default form submission behavior
              openModal({
                type: "studentSchool",
                props: ["학교 검색", handlerModalClose]
              });
            }}
          />
          </div>
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
