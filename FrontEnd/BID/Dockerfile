# 사용할 이미지 선택
FROM node:latest as build
# 작업 디렉토리 설정
WORKDIR /app
# 현재 디렉토리의 모든 파일을 도커 컨테이너의 워킹 디렉토리에 복사.
COPY . .
# 명령어 실행 (의존성 설치)
RUN yarn install
RUN yarn add axios
RUN yarn add @reduxjs/toolkit
RUN yarn add @nivo/core @nivo/pie @nivo/line
RUN yarn add react-big-calendar
RUN yarn add font-awesome
RUN yarn add react-hot-loader
RUN yarn add react-modal
RUN yarn add @tanstack/react-query
RUN yarn add @tanstack/react-query-devtools

# 연결할 포트번호/이건 docker
EXPOSE 3000

ENV CHOKIDAR_USEPOLLING=true
#앱 빌드
RUN yarn build

# 앱 실행
CMD ["yarn", "start"]