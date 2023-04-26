FROM gradle:7.6.1-jdk17-alpine as builder
WORKDIR /build

COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel

# APP
FROM azul/zulu-openjdk:17-latest
WORKDIR /app

COPY --from=builder build/build/libs/shrupp-0.0.1-SNAPSHOT.jar .

USER nobody
ENTRYPOINT [\
    "java",\
    "-jar",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-Dsun.net.inetaddr.ttl=0",\
    "-Dspring.profiles.active=dev",\
    "shrupp-0.0.1-SNAPSHOT.jar"\
]