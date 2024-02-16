import { useDispatch } from "react-redux";
import { updateAlarms, removeAlarms, initAlarms } from "../Store/alarmSlice";

export default function useAlarm() {
  const dispatch = useDispatch();

  const handleInitAlarms = (alarm) => {
    dispatch(initAlarms(alarm));
  };
  const handleUpdatedAlarms = (alarm) => {
    dispatch(updateAlarms(alarm));
  };

  const handleRemoveAlarms = () => {
    dispatch(removeAlarms());
  };

  return {
    initAlarms: handleInitAlarms,
    updateAlarms: handleUpdatedAlarms,
    removeAlarms: handleRemoveAlarms,
  };
}
