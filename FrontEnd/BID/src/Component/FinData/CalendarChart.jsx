// CalendarChart.js
import React, { useMemo, useState, useCallback } from 'react';
import PropTypes from 'prop-types';
import * as dates from 'date-arithmetic';
import styled from './CalendarChart.module.css';
import {
  Calendar,
  Views,
  onView,
  Navigate,
  momentLocalizer,
} from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';
import Toolbar from './ToolBar';
import CustomEvent from './CustomEvent';
import monthDate from './monthDate';
import monthHeader from './monthHeader';
import SmallEvent from './SmallEvent';

function CalendarChart() {
  moment.locale('ko-KR');
  const localizer = momentLocalizer(moment);

  function MyDay({
    date,
    localizer,
    onDayClick,
    scrollToTime = localizer.startOf(new Date(), 'day'),
    ...props
  }) {
    return (
      <SmallEvent
        date={date}
        handleClick={() => onDayClick(Views.MONTH)}
        localizer={localizer}
        scrollToTime={scrollToTime}
        {...props}
      />
    );
  }

  MyDay.propTypes = {
    date: PropTypes.instanceOf(Date).isRequired,
    localizer: PropTypes.object,
  };

  MyDay.range = (date, { localizer }) => {
    const start = date;
    const end = dates.add(start, 2, 'day');

    let current = start;
    const range = [];

    while (localizer.lte(current, end, 'day')) {
      range.push(current);
      current = localizer.add(current, 1, 'day');
    }

    return range;
  };

  MyDay.navigate = (date, action, { localizer }) => {
    switch (action) {
      case Navigate.PREVIOUS:
        return localizer.add(date, -3, 'day');

      case Navigate.NEXT:
        return localizer.add(date, 3, 'day');

      default:
        return date;
    }
  };

  MyDay.title = (date) => {
    return `My awesome week: ${date.toLocaleDateString()}`;
  };
  const { views } = useMemo(
    () => ({
      views: {
        month: true,
        day: MyDay,
      },
    }),
    []
  );
  const [view, setView] = useState(Views.MONTH);
  const onDayClick = useCallback((newView) => setView(newView), [setView]);
  const events = [
    {
      id: 0,
      title: '지출',
      start: new Date(2024, 0, 15),
      end: new Date(2024, 0, 15),
      amount: -50,
    },
    {
      id: 1,
      title: '지출',
      start: new Date(2024, 0, 26),
      end: new Date(2024, 0, 26),
      amount: -100,
    },
    {
      id: 2,
      title: '수입',
      start: new Date(2024, 0, 15),
      end: new Date(2024, 0, 15),
      amount: 200,
    },
    {
      id: 3,
      title: '수입',
      start: new Date(2024, 0, 1),
      end: new Date(2024, 0, 1),
      amount: 200,
    },
    {
      id: 4,
      title: '수입',
      start: new Date(2024, 0, 8),
      end: new Date(2024, 0, 8),
      amount: 200,
    },
    {
      id: 5,
      title: '수입',
      start: new Date(2024, 0, 22),
      end: new Date(2024, 0, 22),
      amount: 200,
    },
    {
      id: 6,
      title: '수입',
      start: new Date(2024, 0, 29),
      end: new Date(2024, 0, 29),
      amount: 180,
    },
    // Add more events as needed
  ];

  const calculateTotalAmount = (events, type) => {
    return events.reduce((total, event) => {
      if (type === '지출') {
        return event.amount < 0 ? total + event.amount : total;
      } else if (type === '수입') {
        return event.amount > 0 ? total + event.amount : total;
      }
      return total;
    }, 0);
  };

  // const [selectedEvent, setSelectedEvent] = useState(null);
  // const [modalIsOpen, setModalIsOpen] = useState(false);

  const eventStyleGetter = (event, start, end, isSelected) => {
    const style = {
      borderRadius: '0px',
      opacity: 0.8,
      color: 'white',
      border: '0px',
      display: 'block',
      backgroundColor: 'transparent', // Set background color to transparent
      width: '100%', // Make the event fill the entire day cell
      cursor: 'pointer', // Enable cursor for events
    };

    const content = (
      <div>
        <div>{event.amount}</div>
      </div>
    );

    return {
      style,
      content,
    };
  };
  return (
    <div className={styled.chartContainer}>
      <div className={styled.detailsContainer}>
        <div className={styled.calendarContainer}>
          <Calendar
            localizer={localizer}
            events={events}
            defaultDate={moment().toDate()}
            startAccessor="start"
            endAccessor="end"
            style={{ height: 350 }}
            views={views}
            onView={onDayClick}
            components={{
              toolbar: Toolbar,
              event: CustomEvent,
              // month: {
              //   header: monthHeader,
              //   dateHeader: monthDate,
              // },
            }}
            eventPropGetter={eventStyleGetter}
          />
        </div>
      </div>
    </div>
  );
}

export default CalendarChart;
