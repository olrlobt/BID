import React, { useEffect, useState } from "react";
import { Canvas } from "@react-three/fiber";
import {
  OrbitControls,
  CameraControls,
  PerspectiveCamera,
} from "@react-three/drei";
import { Classroom, Box } from "../../Component/Models/Classroom";
import BlackBoard from "../../Component/Models/BlackBoard";
import { Cactus } from "../../Component/Models/Cactus";
import Alarm from "../../Component/Models/Alarm";
import { BiddingPlace } from "../../Component/Models/BiddingPlace";
import { Bank } from "../../Component/Models/Bank";
// import { BasicBean } from "../../Component/Character/BasicBean";
import { SnowBean } from "../../Component/Character/SnowBean";
import AlarmWindow from "./AlarmWindow";
import { updateAlarms } from "../../Store/alarmSlice";

function StudentMain() {
  const [isAlarmActive, setIsAlarmActive] = useState(false);
  const eventSource = new EventSource(
    `${process.env.REACT_APP_TCH_IP}/notification/subscribe`
  );

  useEffect(() => {
    //SSE연결 로직
    eventSource.onopen = () => {
      eventSource.addEventListener("sse", (e) => {
        updateAlarms(e.data);
      });
    };

    eventSource.onerror = (error) => {
      console.error("EventSource failed: " + error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);
  return (
    <>
      <Canvas
        style={{ width: "100%", height: "100vh" }}
        camera={{ position: [12, 10, 20], fov: 20 }}
      >
        <CameraControls minPolarAngle={2} maxPolarAngle={Math.PI / 2} />
        <directionalLight position={[1, 1, 1]} intensity={2} />
        <ambientLight intensity={2} />
        <group scale={20} position={[0, 0, 0]}>
          <Classroom />
          <BlackBoard />
          <Cactus />
          <Alarm
            onClick={() => {
              setIsAlarmActive(!isAlarmActive);
            }}
          />
          <Bank />
          <BiddingPlace />
          {/* <BasicBean /> */}
          <SnowBean />
          <Box position={[3, 1.5, -2]} scale={0.15} />
        </group>
        <OrbitControls
          makeDefault
          minAzimuthAngle={2.4}
          maxAzimuthAngle={2.6}
          minPolarAngle={Math.PI / 2.5}
          maxPolarAngle={Math.PI / 2.7}
          enableZoom={true}
          enablePan={true}
          enableRotate={false} // 회전 비활성화
          zoomSpeed={0.3}
        />
        <PerspectiveCamera makeDefault position={[0, 10, 190]} />
      </Canvas>
      {isAlarmActive && <AlarmWindow />}
    </>
  );
}

export default StudentMain;
