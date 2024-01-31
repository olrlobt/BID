import React from "react";

const Student = ({item,onClick, handleRemove, handleEdit, showRemove }) => {
    const onRemove = () => {
        handleRemove(item.id)
    }
    const onEdit = () => {
        handleEdit(item)
    }


    
    return (
        <>
        <tr onClick={onClick}>
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.bid}</td>
            {showRemove && (
            <td onClick={onEdit}>
                <i>비밀번호 초기화</i>
            </td>
            )}
            {showRemove && (
                <td onClick={onRemove}>
                <i>제거</i>
                </td>
            )}
            </tr>
            </>
    )
}

export default Student