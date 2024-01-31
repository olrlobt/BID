import { useDispatch } from "react-redux";
import { initStudents, addStudent, removeStudent, editStudent } from "../Store/studentSlice";

export default function useStudents() {
  const dispatch = useDispatch();
  
  const handleInitStudents = ({ students }) => {
    dispatch(initStudents(students));
  }

  const handleAddStudent = ({ newStudent }) => {
    dispatch(addStudent(newStudent));
  }

  const handleRemoveStudent = ({ studentNo }) => {
    dispatch(removeStudent(studentNo));
  }

  const handleEditStudent = ({updatedStudent}) => {
    dispatch(editStudent(updatedStudent));
  };

  return {
    initStudents: handleInitStudents,
    addStudent: handleAddStudent,
    removeStudent: handleRemoveStudent,
    editStudent: handleEditStudent
  };
}
