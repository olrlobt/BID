import { useState } from 'react';
import Modal from '../Common/Modal';
import styled from './TimeModal.module.css';
import useStopTime from '../../hooks/useStopTime';
import { changeStopTime } from '../../Apis/TeacherManageApis';
import alertBtn from '../Common/Alert';

export default function TimeModal({ onClose, ...props }) {
  const hour = [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24,
  ];
  const time = [0, 10, 20, 30, 40, 50];

  const { changeTime } = useStopTime();

  const initialSelectedTimeRange = props[1].map((timeRange) => ({
    ...timeRange,
    startHour: parseInt(timeRange.startPeriod.split(':')[0]),
    startMinute: parseInt(timeRange.startPeriod.split(':')[1]),
    endHour: parseInt(timeRange.endPeriod.split(':')[0]),
    endMinute: parseInt(timeRange.endPeriod.split(':')[1]),
  }));
  const [selectedTimeRange, setSelectedTimeRange] = useState(
    initialSelectedTimeRange
  );

  const handleTimeChange = (event, index, fieldName) => {
    const { value } = event.target;
    setSelectedTimeRange((prevState) => {
      const newState = [...prevState];
      newState[index][fieldName] = value;
      return newState;
    });
  };

  const updatedTime = selectedTimeRange.map(
    ({ no, sequence, startHour, startMinute, endHour, endMinute }) => {
      // const startTime = parseInt(startHour) * 60 + parseInt(startMinute);
      // const endTime = parseInt(endHour) * 60 + parseInt(endMinute);

      // if (startTime >= endTime) {
      //   alert('시작시간과 끝나는 시간을 올바로 설정해주세요.');
      //   return initialSelectedTimeRange.find(
      //     (timeRange) => timeRange.no === no
      //   );
      // } else {
      return {
        no,
        sequence,
        startPeriod: `${String(startHour).padStart(2, '0')}:${String(
          startMinute
        ).padStart(2, '0')}`,
        endPeriod: `${String(endHour).padStart(2, '0')}:${String(
          endMinute
        ).padStart(2, '0')}`,
      };
      // }
    }
  );

  const parseUpdatedTime = updatedTime.map(
    ({ no, startPeriod, endPeriod }) => ({ no, startPeriod, endPeriod })
  );

  const handleSubmit = () => {
    changeTime(updatedTime);
    changeStopTime(1, parseUpdatedTime);
    setSelectedTimeRange(updatedTime);
    alertBtn({
      text: '변경되었습니다.',
      confirmColor: '#ffd43a',
      icon: 'success',
    });
    onClose();
  };

  return (
    <Modal onClose={onClose} {...props}>
      <h1>{props[0]}</h1>
      <section className={styled.bidInput}>
        {selectedTimeRange &&
          selectedTimeRange.map((classTime, index) => (
            <section className={styled.changeTime}>
              <label htmlFor="current">
                <span>{classTime.sequence}</span>교시
              </label>
              <div>
                <select
                  id={`startHour_${index}`}
                  className={styled.time}
                  value={classTime.startHour}
                  onChange={(e) => handleTimeChange(e, index, 'startHour')}
                >
                  {hour.map((value) => (
                    <option value={value}>{value}</option>
                  ))}
                </select>
                <span> : </span>
                <select
                  id={`startMinute_${index}`}
                  className={styled.time}
                  value={classTime.startMinute}
                  name="startMinute"
                  onChange={(e) => handleTimeChange(e, index, 'startMinute')}
                >
                  {time.map((value) => (
                    <option value={value}>{value}</option>
                  ))}
                </select>
              </div>
              <span className={styled.spaceTime}>-</span>
              <div>
                <select
                  id={`endHour_${index}`}
                  className={styled.time}
                  value={classTime.endHour}
                  onChange={(e) => handleTimeChange(e, index, 'endHour')}
                >
                  {hour.map((value) => (
                    <option value={value}>{value}</option>
                  ))}
                </select>
                <span> : </span>
                <select
                  id={`endMinute_${index}`}
                  className={styled.time}
                  value={classTime.endMinute}
                  name="endMinute"
                  onChange={(e) => handleTimeChange(e, index, 'endMinute')}
                >
                  {time.map((value) => (
                    <option value={value}>{value}</option>
                  ))}
                </select>
              </div>
            </section>
          ))}
      </section>
      <button className={styled.changeBid} onClick={handleSubmit}>
        변경
      </button>
    </Modal>
  );
}
