import React, {useEffect, useRef, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { Canvas } from '@react-three/fiber';
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
  const [onFloor, setOnFloor] = useState(false);
  const [isAlarm, setIsAlarm] = useState(false);
  // const [selectedCharacter, setSelectedCharacter] = useState(null);
  useCursor(onFloor);
  const navigate = useNavigate(); // useNavigate hook
  const [dragging, setDragging] = useState(false); // 드래그 상태
  const dragTimeout = useRef(); // 롱 클릭 타이머
  const handleCharacterClick = (characterId) => {
    navigate(`/studentmain/${characterId}`);
  };

  const handlePointerDown = (e) => {
    // 롱 클릭을 감지하기 위한 타이머 설정
    dragTimeout.current = setTimeout(() => {
      setDragging(true); // 롱 클릭 후 드래그 시작
    }, 400); // 예: 500ms가 롱 클릭으로 가정
  };

  const handlePointerMove = (e) => {
    if (dragging) {
      // 드래그 상태일 때의 로직
      // 예: socket.emit으로 캐릭터 위치 업데이트
      const position = [e.point.x / 33, 0, e.point.z / 33];
      socket.emit('move', position, characters, myInfo.myInfo.model.no, myInfo.myInfo.model.gradeNo);
    }
  };

  const handlePointerUp = () => {
    clearTimeout(dragTimeout.current); // 롱 클릭 타이머 해제
    if (dragging) {
      setDragging(false); // 드래그 종료
    }
  };

  const handleAlarmClick = () => {
    setIsAlarm(!isAlarm);
  };

  return (
    <>
      <SocketManager />
      <Canvas
        style={{ width: "100%", height: "70vh" }}
        camera={{ position: [12, 10, 20], fov: 30 }}
      >
        <CameraControls minPolarAngle={5} maxPolarAngle={Math.PI / 1} />
        <directionalLight
          position={[1, 1, 1]}
          castShadow
          intensity={3}
        ></directionalLight>
      <OrbitControls
          enableZoom={false} // 확대/축소 비활성화
          enablePan={false} // 패닝 비활성화
        />
        <ambientLight intensity={1.7} />
        <OrbitControls />
        <group scale={20} position={[0, 0, 0]}>
          <mesh
              onPointerDown={handlePointerDown}
              onPointerMove={handlePointerMove}
              onPointerUp={handlePointerUp}
          >
            <Classroom/>
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
          <SnowBean />
        </group>
        <OrbitControls
          makeDefault
          minAzimuthAngle={2}
          maxAzimuthAngle={2}
          minPolarAngle={Math.PI / 2.55}
          maxPolarAngle={Math.PI / 2.55}
          enableZoom={false}
          enablePan={false}
          enableRotate={false} // 회전 비활성화
          zoomSpeed={1}
        />
        <PerspectiveCamera makeDefault position={[0, 10, 190]} />
      </Canvas>
      {isAlarm && <RealTimeModal handleClick={handleAlarmClick} />}
    </>
  );
}
