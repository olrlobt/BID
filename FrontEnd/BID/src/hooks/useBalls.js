import { useDispatch } from "react-redux";
import { initBalls } from "../Store/ballSlice";

export default function useBalls() {
  const dispatch = useDispatch();

  const handleInitBalls = ({ ballList }) => {
    dispatch(initBalls(ballList));
  };
  return {
    initBalls: handleInitBalls,
  };
}
