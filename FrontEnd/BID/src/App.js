import { Route, Routes, useNavigate } from 'react-router-dom';
import MainPage from './Page/Main/MainPage';
import RegisterPage from './Page/User/RegisterPage';
import ManageLoginPage from './Page/User/ManageLoginPage';
import LoginPage from './Page/User/LoginPage';
import FindIdPage from './Page/User/FindIdPage';
import ChangePwdPage from './Page/User/ChangePwdPage';
import DeletePage from './Page/User/DeletePage';
import BidPage from './Page/Manage/BidPage';
import RewardPage from './Page/Manage/RewardPage';
import BankPage from './Page/Manage/BankPage';
import GamePage from './Page/Manage/GamePage';
import Home from './Page/Main/Home';
import ClassPage from './Page/Manage/ClassPage';
import ClassList from './Page/ClassManage/ClassList';
import MainClass from './Page/Main/MainClass';
import AddClass from './Page/ClassManage/MakeClass';
import SeatGame from './Page/Manage/SeatGame';
import StudentMain from './Page/Student/StudentMain';
import StudentBidPage from './Page/Student/StudentBidPage';
import MyPage from './Page/Student/MyPage';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import NoClass from './Page/ClassManage/NoClass';
import StudentSaving from './Page/Student/StudentSaving';
import UserChangePwd from './Page/User/UserChangePwd';
import { useSelector } from 'react-redux';
import { modelSelector } from './Store/modelSlice';
import { userSelector } from './Store/userSlice';
import { getCookie } from './cookie';
import { useEffect } from 'react';

const queryClient = new QueryClient();

function App() {
  const teacher = useSelector(userSelector);
  const student = useSelector(modelSelector);
  const cookie = getCookie('accessToken');
  const navigate = useNavigate();

  useEffect(() => {
    if (cookie && teacher) {
      navigate('/');
    }
    // 학생 로그인 여부 확인 후 리디렉션
    if (cookie && student) {
      console.log(student)
      navigate('/studentmain');
    }
    // 쿠키가 없으면 로그인 페이지로 이동
    if (!cookie) {
      navigate('/login');
    }
  }, [cookie, teacher, student]);

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={true} />
      <Routes>
        {/* 선생님 접근 가능 */}
        {cookie && teacher && (
          <Route path="/" element={<MainPage />}>
            <Route path="/" element={<Home />} />
            <Route path="/class" element={<ClassPage />} />
            <Route path="/bid" element={<BidPage />} />
            <Route path="/reward" element={<RewardPage />} />
            <Route path="/bank" element={<BankPage />} />
            <Route path="/game" element={<GamePage />} />
          </Route>
        )}

        {cookie && teacher && (
          <Route path="/classlist/:teacherId/" element={<MainClass />}>
            <Route path="/classlist/:teacherId/" element={<ClassList />} />
            <Route
              path="/classlist/:teacherId/no-class/"
              element={<NoClass />}
            />
            <Route
              path="/classlist/:teacherId/modify/"
              element={<ClassList />}
            />
            <Route
              path="/classlist/:teacherId/changepass/"
              element={<UserChangePwd />}
            />
            <Route path="/classlist/:teacherId/make/" element={<AddClass />} />
          </Route>
        )}
        {cookie && teacher && (
          <Route path="/game/seat" element={<SeatGame />} />
        )}

        {/* 모두 접근 가능 */}
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/managelogin" element={<ManageLoginPage />} />
        <Route path="/find_id" element={<FindIdPage />} />
        <Route path="/change_pwd" element={<ChangePwdPage />} />
        <Route path="/delete/:id/" element={<DeletePage />} />

        {/* 학생 접근 가능 */}
        {cookie && student && (
          <Route>
            <Route path="/studentmain" element={<StudentMain />} />
            <Route path="/studentmain/:studentId/" element={<MyPage />} />
            <Route
              path="/studentmain/auctionHouse"
              element={<StudentBidPage />}
            />
            <Route path="/studentmain/saving" element={<StudentSaving />} />
          </Route>
        )}
      </Routes>
    </QueryClientProvider>
  );
}

export default App;