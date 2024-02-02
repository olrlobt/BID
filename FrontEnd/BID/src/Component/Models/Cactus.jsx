
import { MeshWobbleMaterial, useGLTF } from '@react-three/drei'

export function Cactus() {
    const { nodes, materials } = useGLTF('/models/Cactus.glb')
    return (
      <mesh 
      geometry={nodes.Cactus.geometry} 
      position={[1.284, 0.456, -4.645]}
      rotation={[Math.PI / 2, 0, 0]}
      scale={0.351}
      >
        <MeshWobbleMaterial 
        factor={0.4} 
        map={materials.Cactus.map} />
      </mesh>
    )
  }