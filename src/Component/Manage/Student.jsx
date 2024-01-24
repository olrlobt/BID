import React from "react";

const Student = ({item, handleRemove, handleEdit}) => {
    const onRemove = () => {
        handleRemove(item.id)
    }
    const onEdit = () => {
        handleEdit(item)
    }

    return (
        <>
        <tr className="">
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.bid}</td>
            <td onClick={onEdit}>
                <i>편집</i>
            </td>
            <td onClick={onRemove}>
                <i>제거</i>
            </td>
            </tr>
            </>
    )
}

export default Student