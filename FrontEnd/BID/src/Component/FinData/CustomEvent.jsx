import React from "react";
const CustomEvent = ({ event }) => {
  const getAmountColor = () => {
    if (event.title === "EXPENDITURE") {
      return "#A6A6A6";
    } else if (event.title === "INCOME") {
      return "#5FA1C4";
    }
  };

  return (
    <div
      style={{
        color: getAmountColor(),
        fontSize: "0.6rem",
      }}
    >
      {event.title === "EXPENDITURE" ? `-${event.amount}` : `+${event.amount}`}
    </div>
  );
};

export default CustomEvent;
