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

  const roomId = []
  const characters = []
  const generateRandomPosition = () => {
    const minX = 1.4;
    const maxX = 2.224 + 3;

    const minZ = -1.781 - 1.5 ;
    const maxZ = -1.781 + 1.5;

    const x = minX + Math.random() * (maxX - minX);
    const z = minZ + Math.random() * (maxZ - minZ);

    return [x, 0, z];
  };

  const generateRandomHexColor = () => {
    return "#" + Math.floor(Math.random() * 16777215).toString(16);
  };

  
  const createCharacter = (model) => {
    const [baseX, baseY, baseZ] = generateRandomPosition(); // 랜덤한 기준 포지션 생성
    const deltaX = (Math.random() * 0.3) - 0.6; // x 값에 더해줄 랜덤한 값 생성 (0.3 ~ 0.65)
    const deltaZ = (Math.random() * 0.3) + 0.6; // z 값에 더해줄 랜덤한 값 생성 (0.3 ~ 0.65)
  
    // 기준 포지션에 더해진 값을 사용하여 캐릭터의 위치를 설정
    const position = [baseX + deltaX, baseY, baseZ + deltaZ];
  
    const selectedCharacter = model.profileImgUrl.split('/').pop().replace('.png', '');
    const id = model.no;
    const gradeNo = model.gradeNo;
    const name = model.name;
    
    return {
      id,
      name,
      gradeNo,
      position,
      selectedCharacter
    };
  };

  io.on("connection", (socket) => {
    console.log("user", socket.id);


    socket.on("characters", (data) => {
      // 여기서 models 이벤트를 수신하여 원하는 작업을 수행합니다.
      // console.log("Received models data:", data);
      data.forEach(model => {
        // 중복 추가 방지
        const gradeNo = model.gradeNo;
        if (!roomId[gradeNo]) {
          roomId[gradeNo] = [];
        }
        if (!roomId[gradeNo].some(character => character.id === model.no)) {
          const character = createCharacter(model);
          roomId[gradeNo].push(character);
        }
        Object.keys(roomId).forEach(gradeNo => {
          io.emit(`characters-${gradeNo}`, roomId[gradeNo]);
        });
      });
    });

    socket.on("move", (position, characters, userId, gradeNo) => {
      characters.forEach((character) => {

        if (character.id === userId) {
          character.position = position
        }
      })
      io.emit(`characters-${gradeNo}`, characters);
    });

    socket.on("chatMessage", (message, userId) => {
      console.log(userId)
      console.log(message)
      // Assuming the message object has a sender and content property
      io.emit("playerChatMessage", {
        id: userId,
        message,
      });
    });
  
    socket.on("disconnect", () => {
      console.log("user disconnected");
      const characters = []

      // Broadcast the updated characters array to all connected clients
      io.emit("characters", characters);
    });
  });

