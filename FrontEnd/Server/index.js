import { Server } from "socket.io";

const origin ="http://localhost:3000";

const io = new Server({
    cors: {
      origin,
    },
  });

  io.listen(3001);

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
    console.log("user connected:", socket.id);

    socket.on("joinRoom", (gradeNo) => {
      socket.join(gradeNo);
      console.log(`User ${socket.id} joined room ${gradeNo}`);
    });

    socket.on("characters", (data) => {
      // 여기서 models 이벤트를 수신하여 원하는 작업을 수행합니다.
      console.log("Received models data:", data);
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
        // Object.keys(roomId).forEach(gradeNo => {
        //   io.emit(`characters-${gradeNo}`, roomId[gradeNo]);
        // });

        io.to(gradeNo).emit(`characters-${gradeNo}`, roomId[gradeNo]);
      });
    });

    socket.on("leaveRoom", (gradeNo) => {
      roomId[gradeNo] = []
    }) 
    socket.on("move", (position, characters, userId, gradeNo) => {
      characters.forEach((character) => {

        if (character.id === userId) {
          character.position = position
        }
      })
      io.to(gradeNo).emit(`characters-${gradeNo}`, characters);
    });

    socket.on("chatMessage", (message, userId, gradeNo) => {
      console.log(userId)
      console.log(message)
      // Assuming the message object has a sender and content property
      io.to(gradeNo).emit("playerChatMessage", {
        id: userId,
        message,
      });
    });
  
    socket.on("disconnect", () => {
      console.log("user disconnected");
      const characters = []
      console.log
      // Broadcast the updated characters array to all connected clients
      io.emit("characters", characters);
    });
  });

