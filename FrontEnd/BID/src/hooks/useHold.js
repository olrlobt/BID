import { useDispatch } from "react-redux";
import { changeHold } from "../Store/holdSlice";

export default function useHold() {
  const dispatch = useDispatch();
  const handleChangeHold = (isHold) => {
    dispatch(changeHold(isHold));
  };
  return {
    changeHold: handleChangeHold,
  };
}
