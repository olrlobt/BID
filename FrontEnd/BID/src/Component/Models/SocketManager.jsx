import { useEffect } from "react";
import { io } from "socket.io-client";
import { useAtom, atom } from "jotai";
import { useSelector } from "react-redux";
import { modelSelector } from "../../Store/modelSlice";

// export const socket = io("https://i10a306.p.ssafy.io:3001");
export const socket = io("http://localhost:3001");
export const charactersAtom = atom([]);
export const userAtom = atom(null);

export const SocketManager = () => {
  const myInfo = useSelector(modelSelector);
  const gradeNo = myInfo.model.gradeNo;

  const [, setCharacters] = useAtom(charactersAtom);
  const [, setUser] = useAtom(userAtom);
  useEffect(() => {
    const eventKey = `characters-${gradeNo}`;
    const onCharacters = (newCharacters) => {
      setCharacters((currentCharacters) => {
        // 현재 상태와 새로운 데이터가 동일한지 검사
        if (
          JSON.stringify(currentCharacters) === JSON.stringify(newCharacters)
        ) {
          // 데이터가 변경되지 않았다면 상태 업데이트 없이 현재 상태를 유지
          return currentCharacters;
        }
        // 데이터가 변경되었다면 새로운 상태로 업데이트
        return newCharacters;
      });
    };

    socket.on(eventKey, onCharacters);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      socket.off(eventKey, onCharacters);
    };
  }, [gradeNo]); // 의존성 배열에 gradeNo 추가
};
