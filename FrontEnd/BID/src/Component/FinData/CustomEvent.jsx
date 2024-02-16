import React from "react";
const CustomEvent = ({ event }) => {
  const getAmountColor = () => {
    if (event.amount < 0) {
      return "#A6A6A6";
    } else if (event.amount > 0) {
      return "#5FA1C4";
    }
  };

  return (
    <div
      style={{
        color: getAmountColor(),
        fontSize: "0.7rem",
      }}
    >
      {event.amount < 0 ? event.amount : `+${event.amount}`}
    </div>
  );
};

export default CustomEvent;
