// 적금 정보
import { useDispatch } from "react-redux";
import { initSavingList, changeSavingList } from "../Store/savingSlice";

export default function useSaving() {
  const dispatch = useDispatch();
  const handleInitSavingList = (list) => {
    dispatch(initSavingList(list));
  };

  const handleUpdateSavingList = (list) => {
    dispatch(changeSavingList(list));
  };

  return {
    initSavingList: handleInitSavingList,
    changeSavingList: handleUpdateSavingList,
  };
}
