import { Route, Routes } from "react-router-dom";
import MainPage from './Page/User/MainPage';
import RegisterPage from "./Page/User/RegisterPage";
import LoginPage from "./Page/User/LoginPage";
import FindIdPage from './Page/User/FindIdPage';
import ChangePwdPage from './Page/User/ChangePwdPage'
import DeletePage from './Page/User/DeletePage'


function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path='/find_id' element={<FindIdPage/>} />
      <Route path='/change_pwd' element={<ChangePwdPage/>} />
      <Route path='/delete/:id/' element={<DeletePage />} />
    </Routes>
  );
}

export default App;
