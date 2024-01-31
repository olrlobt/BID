import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from 'axios'; 

export const loginUser = createAsyncThunk(
    'user/loginUser',
    async(userCredentials) => {
        const request = await axios.post(`api/login`, userCredentials)
        const response = await request.data.data
        localStorage.setItem('user',JSON.stringify(response))
        return response
    }
)


export const registerUser = createAsyncThunk(
    'user/registerUser',
    async (userData) => {
        const request = await axios.post(`api/register`, userData);
        const response = await request.data.data;
        // 처리가 필요한 경우 추가
        return response;
    }
);


const UserSlice = createSlice({
    name: 'user',
    initialState: {
        loading: false,
        user: null,
        error: null
    },
    extraReducers: (builder) => {
        builder
            .addCase(loginUser.pending, (state) => {
                state.loading = true;
                state.user = null;
                state.error = null;
            })
            .addCase(loginUser.fulfilled, (state, action) => {
                state.loading = false;
                state.user = action.payload;
                state.error = null;
            })
            .addCase(loginUser.rejected, (state, action) => {
                state.loading = false;
                state.user = null;
                if (action.error.message === 'Request failed with status code 401') {
                    state.error = 'Access Denied! Invalid Credentials';
                } else {
                    state.error = action.error.message;
                }
            })
            .addCase(registerUser.pending, (state) => {
                state.loading = true;
                state.user = null;
                state.error = null;
            })
            .addCase(registerUser.fulfilled, (state, action) => {
                state.loading = false;
                state.user = action.payload;
                state.error = null;
            })
            .addCase(registerUser.rejected, (state, action) => {
                state.loading = false;
                state.user = null;
                // 에러 처리
                state.error = action.error.message;
            });
    },
});

export default UserSlice.reducer