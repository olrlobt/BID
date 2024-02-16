import { Route, Routes } from "react-router-dom";
import MainPage from "./Page/Main/MainPage";
import RegisterPage from "./Page/User/RegisterPage";
import ManageLoginPage from "./Page/User/ManageLoginPage";
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
import ClassList from "./Page/ClassManage/ClassList";
import MainClass from "./Page/Main/MainClass";
import AddClass from "./Page/ClassManage/MakeClass";
import SeatGame from "./Page/Manage/SeatGame";
import StudentMain from "./Page/Student/StudentMain";
import StudentBidPage from "./Page/Student/StudentBidPage";
import MyPage from "./Page/Student/MyPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import NoClass from "./Page/ClassManage/NoClass";
import StudentSaving from "./Page/Student/StudentSaving";
import UserChangePwd from "./Page/User/UserChangePwd";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={true} />
      <Routes>
        <Route path="/" element={<MainPage />}>
          <Route path="/" element={<Home />} />
          <Route path="/class" element={<ClassPage />} />
          <Route path="/bid" element={<BidPage />} />
          <Route path="/reward" element={<RewardPage />} />
          <Route path="/bank" element={<BankPage />} />
          <Route path="/game" element={<GamePage />} />
        </Route>
        <Route path="/classlist/:teacherId/" element={<MainClass />}>
          <Route path="/classlist/:teacherId/" element={<ClassList />} />
          <Route path="/classlist/:teacherId/no-class/" element={<NoClass />} />
          <Route path="/classlist/:teacherId/modify/" element={<ClassList />} />
          <Route path="/classlist/:teacherId/changepass/" element={<UserChangePwd />} />
          <Route path="/classlist/:teacherId/make/" element={<AddClass />} />
        </Route>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/managelogin" element={<ManageLoginPage />} />
        <Route path="/find_id" element={<FindIdPage />} />
        <Route path="/change_pwd" element={<ChangePwdPage />} />
        <Route path="/delete/:id/" element={<DeletePage />} />
        <Route path="/game/seat" element={<SeatGame />} />
        <Route path="/studentmain/" element={<StudentMain />} />
        <Route path="/studentmain/:studentId/" element={<MyPage />} />
        <Route path="/studentmain/auctionHouse" element={<StudentBidPage />} />
        <Route path="/studentmain/saving" element={<StudentSaving />} />
      </Routes>
    </QueryClientProvider>
  );
}

export default App;
