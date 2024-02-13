# Node.js 공식 이미지를 기반으로 합니다.
FROM node:16-alpine as build

# 앱 디렉터리 생성
WORKDIR /app

# 앱 의존성 설치
# package.json과 package-lock.json을 모두 복사합니다.
COPY package*.json ./

RUN yarn install --network-timeout 1000000

COPY ./ ./

# 앱이 3001 포트에서 실행됨을 알립니다.
EXPOSE 3001

# 앱 실행
CMD ["node", "index.js"]
