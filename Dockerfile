# APP
FROM azul/zulu-openjdk:17-latest
WORKDIR /app

COPY build/libs/shrupp-0.0.1-SNAPSHOT.jar .

ARG DB_USERNAME
ARG DB_PASSWORD
ARG KAKAO_CLIENT_ID
ARG KAKAO_CLIENT_SECRET

ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD
ENV KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID
ENV KAKAO_CLIENT_SECRET=$KAKAO_CLIENT_SECRET

RUN useradd shrupp
USER shrupp
ENTRYPOINT [\
    "java",\
    "-jar",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-Dsun.net.inetaddr.ttl=0",\
    "-Dspring.profiles.active=dev",\
    "shrupp-0.0.1-SNAPSHOT.jar"\
]