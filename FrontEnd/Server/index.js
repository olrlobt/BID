import { Server as SocketIOServer } from "socket.io";
import { createServer as createHttpsServer } from "https";
import fs from "fs";

const origin = "https://i10a306.p.ssafy.io";

let httpsOptions;
try {
  httpsOptions = {
    key: fs.readFileSync('/etc/letsencrypt/live/i10a306.p.ssafy.io/privkey.pem', 'utf8'),
    cert: fs.readFileSync('/etc/letsencrypt/live/i10a306.p.ssafy.io/fullchain.pem', 'utf8'),
  };
  // 인증서 로드 성공 메시지
  console.log('SSL/TLS 인증서가 성공적으로 로드되었습니다.');
} catch (error) {
  // 에러 로깅
  console.error('SSL/TLS 인증서 로드 중 에러 발생:', error);
}

const httpsServer = createHttpsServer(httpsOptions);

const io = new SocketIOServer(httpsServer, {
  cors: {
    origin, // 허용할 CORS origin
    methods: ["GET", "POST"] // 허용할 HTTP 메소드
  },
});

httpsServer.listen(3001, () => {
  console.log('HTTPS 서버가 포트 3001에서 시작되었습니다.');
});

  console.log("Server started on port 3000, allowed cors origin: " + origin);

  const characters = [];

  const generateRandomPosition = () => {
    const minX = 2.824 - 1;
    const maxX = 2.824 + 1;

    const minZ = -1.781 - 1;
    const maxZ = -1.781 + 1;

    const x = minX + Math.random() * (maxX - minX);
    const z = minZ + Math.random() * (maxZ - minZ);

    return [x, 0, z];
  };

  const generateRandomHexColor = () => {
    return "#" + Math.floor(Math.random() * 16777215).toString(16);
  };

  io.on("connection", (socket) => {
    console.log("user", socket.id);

    const existingCharacter = characters.find(
      (character) => character.id === socket.id
    );

    if (!existingCharacter) {
      characters.push({
        id: socket.id,
        position: generateRandomPosition(),
        bodyColor: generateRandomHexColor(),
        selectedCharacter: "AvocadoBody",
      });
    }

    socket.emit("hello", {
      characters,
      id: socket.id,
    });

    io.emit("characters", characters);

    socket.on("move", (position) => {
      const character = characters.find(
        (character) => character.id === socket.id
      );
      character.position = position;
      io.emit("characters", characters);
    });

    socket.on("chatMessage", (message) => {
      // Assuming the message object has a sender and content property
      io.emit("playerChatMessage", {
        id: socket.id,
        message,
      });
    });

    socket.on("disconnect", () => {
      console.log("user disconnected");

      characters.splice(
        characters.findIndex((character) => character.id === socket.id),
        1
      );

      // Broadcast the updated characters array to all connected clients
      io.emit("characters", characters);
    });
  });

