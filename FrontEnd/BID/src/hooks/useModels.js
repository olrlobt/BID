
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

  const handleEditModel= ({updatedModel}) => {
    dispatch(editModel(updatedModel));
  };


  return {
    loginStudent: handleLoginStudent,
    logoutStudent: handleLogoutStudent,
    initStudents: handleInitModels,
    addStudent: handleAddModel,
    editStudent: handleEditModel
  };
}
