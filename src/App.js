import { Route, Routes } from "react-router-dom";
import MainPage from "./Page/Main/MainPage";
import RegisterPage from "./Page/User/RegisterPage";
import LoginPage from "./Page/User/LoginPage";
import FindIdPage from "./Page/User/FindIdPage";
import ChangePwdPage from "./Page/User/ChangePwdPage";
import DeletePage from "./Page/User/DeletePage";
import BidPage from "./Page/Manage/BidPage";
import RewardPage from "./Page/Manage/RewardPage";
import BankPage from "./Page/Manage/BankPage";
import GamePage from "./Page/Manage/GamePage";

import Home from "./Page/Main/Home";
import ClassPage from "./Page/Manage/ClassPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />}>
        <Route path="/" element={<Home />} />
        <Route path="/class" element={<ClassPage />} />
        <Route path="/bid" element={<BidPage />} />
        <Route path="/reward" element={<RewardPage />} />
        <Route path="/bank" element={<BankPage />} />
        <Route path="/game" element={<GamePage />} />
      </Route>
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/find_id" element={<FindIdPage />} />
      <Route path="/change_pwd" element={<ChangePwdPage />} />
      <Route path="/delete/:id/" element={<DeletePage />} />
      <Route />
    </Routes>
  );
}

export default App;
