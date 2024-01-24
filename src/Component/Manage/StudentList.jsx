import React from "react";
import Student from './Student'

const StudentList = ({info, handleRemove, handleEdit}) => {
    return (
        <tbody>
            {
                info.map(item => {
                    return (
                        <Student key={item.id} item={item} 
                        handleRemove={handleRemove} handleEdit={handleEdit} />
                    )
                })
            }
        </tbody>
    )
}

export default StudentList