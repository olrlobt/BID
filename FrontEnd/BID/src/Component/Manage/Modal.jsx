import React, {useState} from "react";

const Modal = ({selectedData, handleCancle, handleEditSubmit}) => {
    const [edited, setEdited] = useState(selectedData)

    const onCancel = () => {
        handleCancle()
    }

    const onEditChange = (e) => {
        setEdited({
            ...edited,
            [e.target.name]: e.target.value
        })
    }

    const onSubmitEdit = (e) => {
        e.preventDefault()
        handleEditSubmit(edited)
    }
    
    return(
        <div>
            <div>
                <div>
                    <h3>고객 정보 수정하기</h3>
                    <i onClick={onCancel}>취소</i>
                </div>
                <form onSubmit={onSubmitEdit}>
                    <div>
                        <div>ID: {edited.id}</div>
                        <div>Name: 
                            <input type="text" 
                            name="name"
                            value={edited.name}
                            onChange={onEditChange} />
                        </div>
                        <div>Bid: 
                            <input type="text" 
                            name="bid"
                            value={edited.bid}
                            onChange={onEditChange} />
                        </div>
                    </div>
                    <div>
                        <button onClick={onCancel}>취소</button>
                        <button type="submit">수정</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Modal