/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/
import React, { useRef, useMemo, useState, useEffect } from "react";
import { Html, useGLTF } from "@react-three/drei";
import { useFrame, useGraph } from "@react-three/fiber";
import { SkeletonUtils } from "three-stdlib";
import { socket, userAtom } from "../Models/SocketManager";
import { useAtom } from "jotai"
import styled from "./CharacterModel.module.css";

const MOVEMENT_SPEED = 0.002;

export function CharacterModel({ 
  selectedCharacter,
  id,
  name,
  myModelNo,
  ...props
  }) {
    const [chatMessage, setChatMessage] = useState("");
    const group = useRef()
    const { scene, materials } = useGLTF("/characters/CharacterModel.glb");
    const [showChatBubble, setShowChatBubble] = useState(false);
    const clone = useMemo(() => SkeletonUtils.clone(scene),[scene])
    const {nodes} = useGraph(clone)
    useEffect(() => {
      clone.traverse((child) => {
        if (child.isMesh) {
          child.castShadow = true;
          child.receiveShadow = true;
        }
      });
    }, [clone]);
    useEffect(() => {
      let chatMessageBubbleTimeout;
      function onPlayerChatMessage(value) {
        if (value.id === id) {
          setChatMessage(value.message);
          clearTimeout(chatMessageBubbleTimeout);
          setShowChatBubble(true);
          chatMessageBubbleTimeout = setTimeout(() => {
            setShowChatBubble(false);
          }, 3500);
        }
      }
  
      socket.on("playerChatMessage", onPlayerChatMessage)
      return() => {
        socket.off("playerChatMessage", onPlayerChatMessage)
      }
    },[id])

    const [user] = useAtom(userAtom);

    // useFrame((state, delta) => {
    //   if (id === myModelNo) { // 내 캐릭터인 경우에만 움직임 제어
    //     if (group.current.position.distanceTo(props.position) > 0.1) {
    //       const direction = group.current.position
    //         .clone()
    //         .sub(props.position)
    //         .normalize()
    //         .multiplyScalar(MOVEMENT_SPEED * delta);
    //       group.current.position.sub(direction);
    //       group.current.lookAt(props.position);
    //     }
    //     if (id === user) {
    //       state.camera.lookAt(group.current.position);
    //     }
    //   }
    // });
    
    // useFrame((state) => {
    //   const classroomXMin = -10;
    //   const classroomXMax = 1.003 +  3;
    //   const classroomZMin = -3.131 -  2;
    //   const classroomZMax = -3.131 + 2;
    
    //   const newPosition = group.current.position.clone();
    //   const direction = newPosition.
    //   sub(props.position).
    //   normalize()
    //   .multiplyScalar(MOVEMENT_SPEED);
    
    //   // Check if the new position is within the boundaries
    //   const newX = Math.min(Math.max(newPosition.x, classroomXMin), classroomXMax);
    //   const newZ = Math.min(Math.max(newPosition.z, classroomZMin), classroomZMax);
    
    //   // Update the position only if it's within the boundaries
    //   // if (newX !== newPosition.x || newZ !== newPosition.z) {
    //   //   group.current.position.set(newX, newPosition.y, newZ);
    //   // }
    //   // group.current.lookAt(props.position);
    //   if (id===user) {
    //     state.camera.position.x = group.current.position.x + 1
    //     state.camera.position.y = group.current.position.y + 1
    //     state.camera.position.z = group.current.position.z + 1
    //     state.camera.lookAt(group.current.position)
    //   }
    // });

    return (

    <group 
    ref={group}
    {...props} 
    position={props.position}
    dispose={null}
    name={name}
    >
    <Html position-y={1}>
      <div className={styled.characterBubble}>
        <p
           className={`${styled.chatMessage} ${
            showChatBubble ? styled.chatMessageVisible : styled.chatMessageHidden
          }`}
        >
         {name} : {chatMessage}
        </p>
      </div>
    </Html>
      {/* 얼굴 */}
      <mesh 
        castShadow
        receiveShadow
        geometry={nodes.Face.geometry}
        material={materials["Material.012"]}
        position={[2.892, 0.585, -1.864]}
        rotation={[0, Math.PI / 2, 0]}
        scale={0.014}
      />
      {/* 기본 몸 */}
        {selectedCharacter === "DefaultBody" && (
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.DefaultBody.geometry}
          material={materials["Material.015"]}
          position={[2.824, 0.553, -1.781]}
          rotation={[0, Math.PI / 2, 0]}
          scale={0.029}
        >
      </mesh>
      )}
      {/* 리본 */}
      {selectedCharacter === "RibbonBody" && (
      <group
        position={[2.944, 0.503, -1.885]}
        rotation={[-1.261, 1.41, 1.043]}
        scale={0.067}
      >
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube030.geometry}
          material={materials["Material.014"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube030_1.geometry}
          material={materials["Material.015"]}
        />
      </group>
        )}
      {/* 도넛 */}
      {selectedCharacter === "DonutBody" && (
      <group
        position={[2.817, 0.485, -1.879]}
        rotation={[Math.PI, 0, Math.PI]}
        scale={[0.173, 0.233, 0.233]}
      >
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013.geometry}
          material={materials["Material.015"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013_1.geometry}
          material={materials["Material.005"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013_2.geometry}
          material={materials["Material.002"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013_3.geometry}
          material={materials["Material.010"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013_4.geometry}
          material={materials["Material.009"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube013_5.geometry}
          material={materials["Material.011"]}
        />
      </group>
        )}
      {/* 눈사람 */}
      {selectedCharacter === "SnowmanBody" && (
      <group
        position={[2.824, 0.553, -1.784]}
        rotation={[0, Math.PI / 2, 0]}
        scale={0.029}
      >
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048.geometry}
          material={materials["Snow.002"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048_1.geometry}
          material={materials["Material.019"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048_2.geometry}
          material={materials["Material.020"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048_3.geometry}
          material={materials["Material.021"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048_4.geometry}
          material={materials["Material.023"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.Cube048_5.geometry}
          material={materials["Material.024"]}
        />
      </group>
      )}
      {/* 도라에몽 */}
      {selectedCharacter === "DoraemonBody" && (
      <group
        position={[2.949, 0.614, -1.915]}
        rotation={[0.847, 1.478, -1.094]}
        scale={[0.0015, 0.021, 0.003]}
      >
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015.geometry}
          material={materials["วัสดุ.002"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_1.geometry}
          material={materials["Material.050"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_2.geometry}
          material={materials["วัสดุ.001"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_3.geometry}
          material={materials["วัสดุ.007"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_4.geometry}
          material={materials["วัสดุ.008"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_5.geometry}
          material={materials["วัสดุ.015"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_6.geometry}
          material={materials["วัสดุ.003"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_7.geometry}
          material={materials["วัสดุ.004"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_8.geometry}
          material={materials["วัสดุ.013"]}
        />
        <mesh
          castShadow
          receiveShadow
          geometry={nodes.ลูกบาศก์015_9.geometry}
          material={materials["วัสดุ.009"]}
        />
      </group>
    )}

    {/* 마리오 */}
    {selectedCharacter === "MarioBody" && (
      <group 
      position={[2.84, 0.43, -1.897]}
      rotation={[Math.PI / 2, 0, -Math.PI / 2]} 
      scale={0.001}
      >
        <mesh geometry={nodes.Mesh040.geometry} material={materials['08 - Default.004']} />
        <mesh geometry={nodes.Mesh040_1.geometry} material={materials['Material.026']} />
        <mesh geometry={nodes.Mesh040_2.geometry} material={materials['03 - Default']} />
        <mesh geometry={nodes.Mesh040_3.geometry} material={materials['09 - Default.001']} />
        <mesh geometry={nodes.Mesh040_4.geometry} material={materials['14 - Default.001']} />
        <mesh geometry={nodes.Mesh040_5.geometry} material={materials['07 - Default.003']} />
        <mesh geometry={nodes.Mesh040_6.geometry} material={materials['08 - Default.003']} />
      </group>
        )}

      {/* 아이언맨 */}
    {selectedCharacter === "IronManBody" && (
      <group 
      position={[2.824, 0.553, -1.781]} 
      rotation={[0, Math.PI / 2, 0]} 
      scale={0.029}>
        <mesh geometry={nodes.Cube012.geometry} material={materials['gold.002']} />
        <mesh geometry={nodes.Cube012_1.geometry} material={materials['gold.001']} />
        <mesh geometry={nodes.Cube012_2.geometry} material={materials['Iron_man_leg:red.001']} />
        <mesh geometry={nodes.Cube012_3.geometry} material={materials['red.006']} />
        <mesh geometry={nodes.Cube012_4.geometry} material={materials['Iron_man_leg:red.006']} />
        <mesh geometry={nodes.Cube012_5.geometry} material={materials['red.007']} />
        <mesh geometry={nodes.Cube012_6.geometry} material={materials['Material.029']} />
        <mesh geometry={nodes.Cube012_7.geometry} material={materials['08 - Default.001']} />
      </group>
        )}

      {/* 미니언 */}

    {selectedCharacter === "MinionBody" && (
    <group position={[2.909, 0.57, -1.893]} rotation={[Math.PI / 2, 0.194, -Math.PI / 2]} scale={[0.066, 0.058, 0.058]}>
        <mesh geometry={nodes.Torus003.geometry} material={materials['silver.007']} />
        <mesh geometry={nodes.Torus003_1.geometry} material={materials.GLOVE} />
        <mesh geometry={nodes.Torus003_2.geometry} material={materials.final} />
        <mesh geometry={nodes.Torus003_3.geometry} material={materials['silver.001']} />
        <mesh geometry={nodes.Torus003_4.geometry} material={materials['Material.011']} />
        <mesh geometry={nodes.Torus003_6.geometry} material={materials['GLOVE.002']} scale={0.8}/>
      </group>
        )}

      {/* 뚱이 */}
    {selectedCharacter === "PatrikBody" && (
    <group position={[2.84, 0.57, -1.914]} rotation={[2.167, -0.112, -1.701]} scale={[0.001, 0.0013,0.00029]}>
        <mesh geometry={nodes.Mesh005_1.geometry} material={materials.Skin} />
        <mesh geometry={nodes.Mesh005_2.geometry} material={materials.Pants} />
        <mesh geometry={nodes.Mesh005_3.geometry} material={materials['lambert1.007']} />
      </group>
      )}
      
      {/* 아보카도 */}
    {selectedCharacter === "AvocadoBody" && (
    <group position={[2.84, 0.573, -1.781]} rotation={[0, Math.PI / 2, 0]} scale={0.029}>
      <mesh geometry={nodes.Cube016.geometry} material={materials.light} />
      <mesh geometry={nodes.Cube016_1.geometry} material={materials.dark} />
      <mesh geometry={nodes.Cube016_2.geometry} material={materials['pit.001']} />
    </group>
      )}
      {myModelNo === id && <Box position={[2.892, 0.9, -1.964]} />}
    </group>
  );
}

export function Box({ scale = 0.15, ...props }) {
  const ref = useRef();
  const [hovered, hover] = useState(false);
  const [clicked, click] = useState(false);

  useFrame((state, delta) => {
    ref.current.rotation.x += delta;
    ref.current.rotation.y += delta;
  });

  const handleBoxClick = () => {
    // 여기에서 모달을 열거나 다른 동작을 수행할 수 있습니다.
  };

  return (
    <mesh
      {...props}
      ref={ref}
      scale={(clicked ? 1.5 : 1) * scale}
      onClick={() => {
        click(!clicked);
        handleBoxClick();
      }}
      onPointerOver={(event) => { event.stopPropagation(); hover(true); }}
      onPointerOut={(event) => hover(false)}>
      <boxGeometry />
      <meshStandardMaterial color={hovered ? 'hotpink' : 'orange'} />
    </mesh>
  );
}

useGLTF.preload("/CharacterModel.glb");
