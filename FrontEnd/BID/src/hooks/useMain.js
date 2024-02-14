import { useDispatch } from "react-redux";
import { initClass } from "../Store/mainSlice";

export default function useMain() {
  const dispatch = useDispatch();

  const handleInitClass = (main) => {
    dispatch(initClass(main));
  };
  return {
    initClass: handleInitClass,
  };
}
