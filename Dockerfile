# APP
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
    "-DKAKAO_CLIENT_ID=1b8ff0d13c094175ce585c92e1fafac6",\
    "-DKAKAO_CLIENT_SECRET=JTcr1zK63BVeiwCImZmQciLqY58Lw2wF",\
    "shrupp-0.0.1-SNAPSHOT.jar"\
]