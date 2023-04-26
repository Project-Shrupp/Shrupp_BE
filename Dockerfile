FROM azul/zulu-openjdk:17-latest
WORKDIR /app

COPY build/libs/shrupp-0.0.1-SNAPSHOT.jar .

USER nobody
ENTRYPOINT [\
    "java",\
    "-jar",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-Dsun.net.inetaddr.ttl=0",\
    "-Dspring.profiles.active=dev",\
    "shrupp-0.0.1-SNAPSHOT.jar"\
]