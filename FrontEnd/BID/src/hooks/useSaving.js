// 적금 정보
import { useDispatch } from "react-redux";
import { initSavingList } from "../Store/savingSlice";

export default function useSaving() {
  const dispatch = useDispatch();
  const handleInitSavingList = (list) => {
    dispatch(initSavingList(list));
  };
  return { initSavingList: handleInitSavingList };
}
