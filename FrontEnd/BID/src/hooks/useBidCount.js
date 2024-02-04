import { useDispatch } from 'react-redux';
import { initCount } from '../Store/bidCountSlice';

export default function useBidCount() {
  const dispatch = useDispatch();
  const handleInitBidCount = (count) => {
    dispatch(initCount(count));
  };
  return { initCount: handleInitBidCount };
}
