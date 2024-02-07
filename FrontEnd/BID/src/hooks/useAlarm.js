import { useDispatch } from "react-redux";
import { updateAlarms, removeAlarms } from "../Store/alarmSlice";

export default function useAlarm() {
  const dispatch = useDispatch();

  const handleUpdatedAlarms = ({ alarm }) => {
    dispatch(updateAlarms(alarm));
  };

  const handleRemoveAlarms = () => {
    dispatch(removeAlarms());
  };

  return {
    updateAlarms: handleUpdatedAlarms,
    removeAlarms: handleRemoveAlarms,
  };
}
