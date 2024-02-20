// useUser.js

import { useDispatch } from "react-redux";
import { loginUser, logoutUser } from "../Store/userSlice";
import { logoutStudent } from "../Store/modelSlice";

export default function useUser() {
  const dispatch = useDispatch();

  const handleLoginUser = (user) => {
    dispatch(logoutStudent());
    dispatch(loginUser(user));
  };

  const handleLogoutUser = () => {
    dispatch(logoutUser());
  };

  return {
    loginUser: handleLoginUser,
    logoutUser: handleLogoutUser,
  };
}
