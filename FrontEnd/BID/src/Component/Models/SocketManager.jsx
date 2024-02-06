import { useEffect } from "react"
import {io} from "socket.io-client"
import {useAtom, atom} from 'jotai'

export const socket = io("http://localhost:3000");
export const charactersAtom = atom([])
export const userAtom = atom(null);

export const SocketManager = () => {
    
    const [, setCharacters] = useAtom(charactersAtom)
    const [, setUser] = useAtom(userAtom);
    useEffect(() => {
        function onConnect() {
            console.log("connected", socket.id);
            console.log(socket)
        }
        function onDisconnect() {
            console.log('disconnected')
        }
        function onHello(value) {
            setUser(value.id);
            console.log(value)
            setCharacters(value);
        }
        function onCharacters(value) {
            setCharacters(value)
            console.log("character", value)
            console.log("Received characters:", value);
        }

    
        socket.on("connect", onConnect);
        socket.on("disconnect", onDisconnect);
        socket.on("hello", onHello);
        socket.on("characters", onCharacters)
        return () => {
            socket.off("connect", onConnect);
            socket.off("disconnect", onDisconnect);
            socket.off("hello", onHello);
            socket.off("characters", onCharacters)
            
        };
        
    },[setCharacters, setUser])
}