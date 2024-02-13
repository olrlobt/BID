// 경매 중단 수업 시간
import { useDispatch } from 'react-redux';
import { initTime, changeTime } from '../Store/stopTimeSlice';

export default function useStopTime() {
  const dispatch = useDispatch();
  const handleInitTime = (time) => {
    dispatch(initTime(time));
  };

  const handleChangeTime = (time) => {
    dispatch(changeTime(time));
  };

  return {
    initTime: handleInitTime,
    changeTime: handleChangeTime,
  };
}
