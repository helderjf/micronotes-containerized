########### BUILD ###########
FROM node:12.6-alpine AS builder
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

########### RUN ###########
FROM nginx:1.19.6
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /usr/src/app/dist/micronotes-front /usr/share/nginx/html
EXPOSE 80