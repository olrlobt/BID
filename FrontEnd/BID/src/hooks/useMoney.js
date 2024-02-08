import { useDispatch } from 'react-redux';
import { initMoney } from '../Store/moneySlice';

export default function useMoney() {
  const dispatch = useDispatch();
  const handleInitMoney = (money) => {
    dispatch(initMoney(money));
  };
  return { initMoney: handleInitMoney };
}
