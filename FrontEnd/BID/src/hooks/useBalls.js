import { useDispatch } from "react-redux";
import { initBalls, removeBalls } from "../Store/ballSlice";

export default function useBalls() {
  const dispatch = useDispatch();

  const handleInitBalls = ({ ballList }) => {
    dispatch(initBalls(ballList));
  };

  const handleRemoveBalls = ({ studentNo }) => {
    dispatch(removeBalls(studentNo));
  };
  return {
    initBalls: handleInitBalls,
    removeBalls: handleRemoveBalls,
  };
}
