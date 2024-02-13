import { useDispatch } from 'react-redux';
import {
  initStudentSavingList,
  changeStudentSavingList,
} from '../Store/studentSavingSlice';

export default function useStudentSaving() {
  const dispatch = useDispatch();

  const handleInitSavingList = (savingList) => {
    dispatch(initStudentSavingList(savingList));
  };

  const handleChangeSavingList = (savingNo) => {
    dispatch(changeStudentSavingList(savingNo));
  };

  return {
    initStudentSavingList: handleInitSavingList,
    changeStudentSavingList: handleChangeSavingList,
  };
}
