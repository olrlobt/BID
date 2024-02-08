import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Canvas } from '@react-three/fiber';
import { OrbitControls, CameraControls, PerspectiveCamera, useCursor} from '@react-three/drei';
import {Classroom,Box} from "../../Component/Models/Classroom";
import BlackBoard from "../../Component/Models/BlackBoard";
import { Cactus } from "../../Component/Models/Cactus";
import Alarm from "../../Component/Models/Alarm";
import { BiddingPlace } from "../../Component/Models/BiddingPlace";
import { Bank } from "../../Component/Models/Bank";
import { SnowBean } from "../../Component/Character/SnowBean";
import { CharacterModel } from "../../Component/Character/CharacterModel";
import { SocketManager,socket, charactersAtom } from "../../Component/Models/SocketManager";
import { useAtom } from "jotai";
import * as THREE from "three";

export default function Models() {
    const [characters] = useAtom(charactersAtom)
    const [onFloor, setOnFloor] = useState(false);
    // const [selectedCharacter, setSelectedCharacter] = useState(null); 

    useCursor(onFloor);
    const navigate = useNavigate(); // useNavigate hook


    const handleCharacterClick = (characterId) => {
        navigate(`/studentmain/${characterId}`);
    };

    return (
        <>
        <SocketManager/>
      <Canvas 
       style={{ width: '100%', height: '80vh' }}
       camera={{ position: [12, 10, 20], fov: 20 }} 
       >
        <CameraControls minPolarAngle={2} maxPolarAngle={Math.PI / 2} />
        <directionalLight position={[1, 1, 1]} intensity={2} />
        <ambientLight intensity={2} />
        <OrbitControls />
            <group scale={20} position={[0, 0, 0]}>
            <mesh
                onClick={(e) => socket.emit("move", [e.point.x/45, 0, e.point.z/45])}
                onPointerEnter={() => setOnFloor(true)}
                onPointerLeave={() => setOnFloor(false)}
            >
                <Classroom  />
            </mesh>
            {characters.map((character) => (
                <CharacterModel
                    key={character.id}
                    id={character.id}
                    position={
                        new THREE.Vector3(
                            character.position[0],
                            character.position[1],
                            character.position[2]
                        )
                    }
                    bodyColor={character.bodyColor}
                    selectedCharacter={character.selectedCharacter}
                    onClick={() => handleCharacterClick(character.id)}
                />
            ))}

            <BlackBoard />
            <Cactus />
            <Alarm />
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
            <Box position={[3, 1.5, -2]} scale={0.15} />
            </group>
            <OrbitControls
                makeDefault
                minAzimuthAngle={2}
                maxAzimuthAngle={2.6}
                minPolarAngle={Math.PI / 2.5}
                maxPolarAngle={Math.PI / 2.7}
                enableZoom={true}
                enablePan={true}
                enableRotate={false} // 회전 비활성화
                zoomSpeed={1}
            />
            <PerspectiveCamera makeDefault position={[0, 10, 190]} />
        </Canvas>
        </>
    )
}

