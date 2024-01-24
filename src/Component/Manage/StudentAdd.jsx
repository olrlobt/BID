import React, {useState} from "react";

const StudentAdd = ({ onSaveData}) => {
    const [form, setForm] = useState({
        name: '',
        bid: ''
    })

    const handleChange = (e) => {
        const {name, value } = e.target
        setForm({
            ...form,
            [name]: value
        })
    }
    const handleSubmit = (e) => {
        e.preventDefault()
        onSaveData(form)
        console.log(form)
        setForm({
            name: '',
            bid: ''
        })
    }
    return (
        <>
            <div>학생 추가하기</div>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username">
                            Name
                            <input type="text" name='name' 
                            placeholder="이름을 입력하세요"
                            value={form.name}
                            onChange={handleChange} />
                        </label>
                        <label htmlFor="bid">
                            Bid
                            <input type="text" name='bid' 
                            placeholder="비드를 입력하세요"
                            value={form.bid}
                            onChange={handleChange} />
                        </label>
                    </div>
                    <div>
                        <button type="submit">저장</button>
                    </div>
                </form>
        </>
    )
}

export default StudentAdd