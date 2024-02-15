import { changeBid } from "../Store/bidSlice";
import { useDispatch } from "react-redux";

export default function useBid() {
  const dispatch = useDispatch();
  const handleChangeBid = (bid) => {
    dispatch(changeBid(bid));
  };

  return { changeBid: handleChangeBid };
}
