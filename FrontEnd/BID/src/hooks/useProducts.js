import { useDispatch } from "react-redux";
import { initProducts } from "../Store/productSlice";

export default function useProducts() {
  const dispatch = useDispatch();
  
  const handleInitProducts = ({ productList }) => {
    dispatch(initProducts(productList));
  }

  return {
    initProducts: handleInitProducts,
  };
}
