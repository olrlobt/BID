// CalendarChart.js
import React, { useCallback, useEffect, useMemo } from "react";
import PropTypes from "prop-types";
import styled from "./CalendarChart.module.css";
import { Calendar, Navigate, momentLocalizer } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import moment from "moment";
import Toolbar from "./ToolBar";
import CustomEvent from "./CustomEvent";
import monthHeader from "./monthHeader";
import SmallEvent from "./SmallEvent";
import "./chart.css";

function CalendarChart({ event }) {
  moment.locale("ko-KR");
  const localizer = momentLocalizer(moment);

  function MyDay({
    date,
    localizer,
    onDayClick,
    scrollToTime = localizer.startOf(new Date(), "day"),
    ...props
  }) {
    return (
      <SmallEvent
        date={date}
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

  MyDay.navigate = (date, action, { localizer }) => {
    switch (action) {
      case Navigate.PREVIOUS:
        return localizer.add(date, -3, "day");

      case Navigate.NEXT:
        return localizer.add(date, 3, "day");

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

  const calculateTotalAmount = (events, type) => {
    return events.reduce((total, event) => {
      if (type === "EXPENDITURE") {
        return event.title === "EXPENDITURE" ? total + event.amount : total;
      } else if (type === "INCOME") {
        return event.title === "INCOME" ? total + event.amount : total;
      }
      return total;
    }, 0);
  };

  const totalExpense = event?.calculateTotalAmount(event, "EXPENDITURE");
  const totalIncome = event?.calculateTotalAmount(event, "INCOME");

  // const [selectedEvent, setSelectedEvent] = useState(null);
  // const [modalIsOpen, setModalIsOpen] = useState(false);

  // const eventStyleGetter = (event, start, end, isSelected) => {
  //   // const style = {
  //   //   borderRadius: "0px",
  //   //   opacity: 0.8,
  //   //   color: "white",
  //   //   border: "0px",
  //   //   display: "block",
  //   //   backgroundColor: "transparent", // Set background color to transparent
  //   //   width: "100%", // Make the event fill the entire day cell
  //   //   cursor: "pointer", // Enable cursor for events
  //   // };

  //   const content = (
  //     <div>
  //       {event.title === "INCOME" ? (
  //         <div style={{ color: "red" }}>{event.amount}</div>
  //       ) : (
  //         <div>-{event.amount}</div>
  //       )}
  //     </div>
  //   );

  //   return {
  //     // style,
  //     content,
  //   };
  // };

  const eventPropGetter = useCallback(
    (event, isSelected) => ({
      ...(event.title === "INCOME" && {
        className: "income",
      }),
      ...(event.title === "EXPENDITURE" && {
        className: "expend",
      }),
      ...(isSelected && {
        style: {
          backgroundColor: "transparent",
        },
      }),
    }),
    []
  );
  return (
    <div className={styled.chartContainer}>
      <div className={styled.expense}>
        <span>총 지출 </span>
        <span>{totalExpense}비드</span>
      </div>
      <div className={styled.income}>
        <span>총 수입 </span>
        <span className={styled.incomeBid}>{totalIncome}비드</span>
      </div>
      <Calendar
        localizer={localizer}
        events={event}
        defaultDate={moment().toDate()}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 350 }}
        views={views}
        components={{
          toolbar: Toolbar,
          event: CustomEvent,
          month: {
            header: monthHeader,
          },
        }}
        eventPropGetter={eventPropGetter}
      />
    </div>
  );
}

export default CalendarChart;
