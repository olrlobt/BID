import { Server } from "socket.io";

const origin ="http://localhost:3000";

const io = new Server({
    cors: {
      origin,
    },
  });

  io.listen(3001);

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
        selectedCharacter: "DefaultBody",
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

