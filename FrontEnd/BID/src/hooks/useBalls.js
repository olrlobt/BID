import { useDispatch } from 'react-redux';
import { initBalls, removeBalls } from '../Store/ballSlice';

export default function useBalls() {
  const dispatch = useDispatch();

  const handleInitBalls = ({ ballList }) => {
    dispatch(initBalls(ballList));
  };

  const handleRemoveBalls = () => {
    dispatch(removeBalls());
  };
  return {
    initBalls: handleInitBalls,
    removeBalls: handleRemoveBalls,
  };
}
