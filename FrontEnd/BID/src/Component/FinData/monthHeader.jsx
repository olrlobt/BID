import React from 'react';
import moment from 'moment';

const monthHeader = (props) => {
  const dateFormat = '일월화수목금토';

  const date = moment(props.date).format('YYYY-MM-DD');
  const dayIndex = new Date(date).getDay();
  const dayOfWeek = dateFormat[dayIndex];

  return (
    <div style={{ textAlign: 'center', padding: '10px', border: 'none' }}>
      <span>{dayOfWeek}</span>
    </div>
  );
};

export default monthHeader;
