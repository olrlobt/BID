
import { useDispatch } from "react-redux";
import { loginStudent, logoutStudent, initModels, addModel, editModel } from "../Store/modelSlice";

export default function useModels() {
  const dispatch = useDispatch();

  const handleLoginStudent = (user) => {
    dispatch(loginStudent(user));
  };

  const handleLogoutStudent = () => {
    dispatch(logoutStudent());
  };

  const handleInitModels = ({ models }) => {
    dispatch(initModels(models));
  }

  const handleAddModel = ({ newModel }) => {
    dispatch(addModel(newModel));
  }

  const handleEditModel= (updatedImg) => {
    dispatch(editModel(updatedImg));
  };


  return {
    loginStudent: handleLoginStudent,
    logoutStudent: handleLogoutStudent,
    initModels: handleInitModels,
    addModel: handleAddModel,
    editModel: handleEditModel
  };
}
