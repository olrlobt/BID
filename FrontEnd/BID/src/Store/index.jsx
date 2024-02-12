import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { modalSlice } from './modalSlice';
import { userSlice } from './userSlice';
import { productSlice } from './productSlice';
import { couponSlice } from './couponSlice';
import { ballSlice } from './ballSlice';
import { bidSlice } from './bidSlice';
import { moneySlice } from './moneySlice';
import { bidCountSlice } from './bidCountSlice';
import { savingSlice } from './savingSlice';
import { modelSlice } from './modelSlice';
import { stopTimeSlice } from './stopTimeSlice';
import { requestCouponSlice } from './requestCouponSlice';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

const rootReducer = combineReducers({
  user: userSlice.reducer,
  modal: modalSlice.reducer,
  coupons: couponSlice.reducer,
  seatBall: ballSlice.reducer,
  products: productSlice.reducer,
  studentBid: bidSlice.reducer,
  classMoney: moneySlice.reducer,
  bidCount: bidCountSlice.reducer,
  savingInfo: savingSlice.reducer,
  model: modelSlice.reducer,
  stopTime: stopTimeSlice.reducer,
  requestCoupon: requestCouponSlice.reducer,
});

const persistConfig = {
  key: 'test',
  storage: storage
}

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
  reducer: persistedReducer,
});

const persistor = persistStore(store);

export { store, persistor };