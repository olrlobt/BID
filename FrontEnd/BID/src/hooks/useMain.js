import { useDispatch } from 'react-redux';
import { initClass, changeMainClass } from '../Store/mainSlice';

export default function useMain() {
  const dispatch = useDispatch();

  const handleInitClass = (main) => {
    dispatch(initClass(main));
  };

  const handleChangeMainClass = (main) => {
    dispatch(changeMainClass(main));
  };

  return {
    initClass: handleInitClass,
    changeMainClass: handleChangeMainClass,
  };
}
