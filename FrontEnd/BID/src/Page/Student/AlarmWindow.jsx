import React, { useEffect, useState } from 'react';

export default function Alarm() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const connect = () => {
      //SSE연결 로직
      const eventSource = new EventSource(
        'http://i10a306.p.ssafy.io:8081/notification/subscribe'
      );

      eventSource.onopen = (event) => {
        console.log(event);
      };

      eventSource.onmessage = (event) => {
        console.log(event);
      };

      eventSource.onerror = () => {
        //에러 발생시 할 동작
        // eventSource.close(); //연결 끊기
      };

      return () => {
        eventSource.close();
      };
    };

    return connect();
  }, []);
}
