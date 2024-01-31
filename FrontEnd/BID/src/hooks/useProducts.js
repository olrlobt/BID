import { useDispatch } from "react-redux";
import { initProducts, addProduct, removeProduct } from "../Store/productSlice";

export default function useProducts() {
  const dispatch = useDispatch();
  
  const handleInitProducts = ({ productList }) => {
    dispatch(initProducts(productList));
  }

  const handleAddProduct = ({ newProduct }) => {
    dispatch(addProduct(newProduct));
  }

  const handleRemoveProduct = ({ productNo }) => {
    dispatch(removeProduct(productNo));
  }

  return {
    initProducts: handleInitProducts,
    addProduct: handleAddProduct,
    removeProduct: handleRemoveProduct
  };
}
