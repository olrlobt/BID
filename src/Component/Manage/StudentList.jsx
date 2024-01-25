import React from "react";
import Student from './Student'

const StudentList = ({info, handleRemove,handleStudentClick, handleEdit, showRemove}) => {
    return (
        <tbody>
            {info.map((item) => (
                <Student
                    key={item.id}  // 이 부분을 추가하여 각 학생의 고유한 id를 키로 사용
                    item={item}
                    handleRemove={handleRemove} 
                    handleEdit={handleEdit}
                    showRemove={showRemove}
                    onClick={() => handleStudentClick(item)}
                />
            ))}
        </tbody>
    )
}

export default StudentList