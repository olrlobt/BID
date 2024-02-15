import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Canvas } from "@react-three/fiber";
import {
  OrbitControls,
  CameraControls,
  PerspectiveCamera,
  useCursor,
} from "@react-three/drei";
import { Classroom, Box } from "../../Component/Models/Classroom";
import BlackBoard from "../../Component/Models/BlackBoard";
import { Cactus } from "../../Component/Models/Cactus";
import Alarm from "../../Component/Models/Alarm";
import { BiddingPlace } from "../../Component/Models/BiddingPlace";
import { Bank } from "../../Component/Models/Bank";
import { SnowBean } from "../../Component/Character/SnowBean";
import { CharacterModel } from "../../Component/Character/CharacterModel";
import {
  SocketManager,
  socket,
  charactersAtom,
} from "../../Component/Models/SocketManager";
import { useAtom } from "jotai";
import * as THREE from "three";
import RealTimeModal from "../../Component/User/RealTimeModal";

export default function Models(myInfo) {
  const [characters] = useAtom(charactersAtom);
  console.log(characters);
  const [onFloor, setOnFloor] = useState(false);
  const [isAlarm, setIsAlarm] = useState(true);
  // const [selectedCharacter, setSelectedCharacter] = useState(null);
  useCursor(onFloor);
  const navigate = useNavigate(); // useNavigate hook

  const handleCharacterClick = (characterId) => {
    navigate(`/studentmain/${characterId}`);
  };

  const handleAlarmClick = () => {
    setIsAlarm(!isAlarm);
  };
  return (
    <>
      <SocketManager />
      <Canvas
        style={{ width: "100%", height: "70vh" }}
        camera={{ position: [12, 10, 20], fov: 20 }}
      >
        <CameraControls minPolarAngle={2} maxPolarAngle={Math.PI / 2} />
        <directionalLight
          position={[1, 1, 1]}
          castShadow
          intensity={2}
        ></directionalLight>
        <ambientLight intensity={1.7} />
        <OrbitControls />
        <group scale={20} position={[0, 0, 0]}>
          <mesh
            onClick={(e) =>
              socket.emit(
                "move",
                [e.point.x / 40, 0, e.point.z / 40],
                characters,
                myInfo.myInfo.model.no,
                myInfo.myInfo.model.gradeNo
              )
            }
            onPointerEnter={() => setOnFloor(true)}
            onPointerLeave={() => setOnFloor(false)}
          >
            <Classroom />
          </mesh>
          {characters.map((character) => (
            <CharacterModel
              key={character.id}
              id={character.id}
              name={character.name}
              position={
                new THREE.Vector3(
                  character.position[0],
                  character.position[1],
                  character.position[2]
                )
              }
              selectedCharacter={character.selectedCharacter}
              myModelNo={myInfo.myInfo.model.no} // 내 캐릭터의 번호 전달
            />
          ))}
          <BlackBoard />
          <Cactus />
          <Alarm onClick={handleAlarmClick} />
          <Bank />
          <BiddingPlace />
          {/* <CharacterModel selectedCharacter="SnowmanBody" bodyColor="green"/> */}
          {/* <CharacterModel 
            selectedCharacter="DefaultBody" 
             position-x={1} 
             
            /> */}
          {/* <BasicBean /> */}
          {/* <CharacterModel position-x={1} /> */}
          <SnowBean />
        </group>
        <OrbitControls
          makeDefault
          minAzimuthAngle={2}
          maxAzimuthAngle={2}
          minPolarAngle={Math.PI / 2.55}
          maxPolarAngle={Math.PI / 2.55}
          enableZoom={true}
          enablePan={true}
          enableRotate={false} // 회전 비활성화
          zoomSpeed={1}
        />
        <PerspectiveCamera makeDefault position={[0, 10, 190]} />
      </Canvas>
      {isAlarm && <RealTimeModal handleClick={handleAlarmClick} />}
    </>
  );
}
