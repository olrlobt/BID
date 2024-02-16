import React from 'react';
import Student from './Student';

const StudentList = ({
  students,
  handleRemove,
  handleStudentClick,
  handleEdit,
  showRemove,
}) => {
  const studentData = students || [];

  return (
    <tbody>
      {studentData.map((item) => (
        <Student
          key={item.no}
          item={item}
          handleRemove={handleRemove}
          handleEdit={handleEdit}
          showRemove={showRemove}
          onClick={() => handleStudentClick(item)}
        />
      ))}
    </tbody>
  );
};

export default StudentList;
