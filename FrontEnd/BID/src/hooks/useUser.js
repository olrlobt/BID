// useUser.js

import { useDispatch } from "react-redux";
import { loginUser, logoutUser } from "../Store/userSlice";

export default function useUser() {
  const dispatch = useDispatch();

  const handleLoginUser = (user) => {
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
