# 사용할 이미지 선택
FROM node:16-alpine as build

# 작업 디렉토리 설정
WORKDIR /app

# 컨테이너 내부로 package.json 파일들을 복사
COPY package*.json ./

# 명령어 실행 (의존성 설치)
RUN yarn install --network-timeout 1000000

COPY . .

#yarn build
RUN yarn build

# prod environment
FROM nginx:stable-alpine

# 이전 빌드 단계에서 빌드한 결과물을 /usr/share/ngnix/html으로 복사
COPY --from=build /app/build /usr/share/nginx/html

# 기본 nginx 설정 파일을 삭제
RUN rm /etc/nginx/conf.d/default.conf

# custom 설정파일을 컨테이너 내부로 복사
COPY nginx.conf /etc/nginx/conf.d

# 연결할 포트번호
EXPOSE 443

# 앱 실행
CMD ["nginx","-g", "daemon off;"]