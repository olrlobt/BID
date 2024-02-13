import { useDispatch } from "react-redux";
import { initProducts, deleteProduct, modifyProduct } from "../Store/productSlice";

export default function useProducts() {
  const dispatch = useDispatch();
  
  const handleInitProducts = ({ productList }) => {
    dispatch(initProducts(productList));
  }
  
  const handleDeleteProduct = ({ productNo }) => {
    dispatch(deleteProduct(productNo));
  }
  
  const handleModifyProduct = ({ productNo, patchData }) => {
    dispatch(modifyProduct({ productNo, patchData }));
  }

  return {
    initProducts: handleInitProducts,
    deleteProduct: handleDeleteProduct,
    modifyProduct: handleModifyProduct,
  };
}
