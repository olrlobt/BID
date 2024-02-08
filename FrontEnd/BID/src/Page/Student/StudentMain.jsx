import React from "react";
import Models from "./Models";
import { Link } from "react-router-dom";
// import styled from "./StudentMain.module.css";
import { socket } from "../../Component/Models/SocketManager";
import { useState } from "react";

function StudentMain() {

    const [chatMessage, setChatMessage] = useState("");
    const sendChatMessage = () => {
        if (chatMessage.length > 0) {
        socket.emit("chatMessage", chatMessage);
        setChatMessage("");
        }
    };
    
    return (
        <>
        
        <Link to="/studentmain/:studentId/">
            <button>캐릭터 이미지</button>
          </Link>
        <div>
            안녕하세요!
        </div>
        <button>
            출석
        </button>
        <Models />
        <input type="text" 
        className=""
        placeholder="Message..."
        onKeyDown={(e) => {
            if (e.key === "Enter") {
                sendChatMessage()
            } 
        }}
        value={chatMessage}
        onChange={(e) => setChatMessage(e.target.value)} 
        />
        <button
        onClick={sendChatMessage}
        >
        <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5"
              />
            </svg>
        </button>
        </>
    );
}

export default StudentMain;