import React from "react";
import styled from './CalendarChart.module.css';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';

function CalendarChart() {
  const localizer = momentLocalizer(moment);

  // Dummy data for demonstration
  const events = [
    { title: 'Event 1', start: new Date(2024, 1, 10), end: new Date(2024, 1, 12) },
    { title: 'Event 2', start: new Date(2024, 1, 15), end: new Date(2024, 1, 17) },
    // Add more events as needed
  ];

  const calculateTotalAmount = (events, type) => {
    return events.reduce((total, event) => {
      if (type === 'expense') {
        return event.amount < 0 ? total + event.amount : total;
      } else if (type === 'income') {
        return event.amount > 0 ? total + event.amount : total;
      }
      return total;
    }, 0);
  };

  const totalExpense = calculateTotalAmount(events, 'expense');
  const totalIncome = calculateTotalAmount(events, 'income');

  return (
    <div className={styled.chartContainer}>
      <div className={styled.calendarContainer}>
        <h2>캘린더</h2>
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
        />
      </div>
      <div className={styled.detailsContainer}>
        <h2>이번 달 지출 및 수입</h2>
        <p>지출: {totalExpense} 원</p>
        <p>수입: {totalIncome} 원</p>
      </div>
    </div>
  );
}

export default CalendarChart;
