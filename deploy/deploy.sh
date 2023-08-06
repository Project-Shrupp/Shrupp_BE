REPOSITORY=/home/ec2-user/

cd $REPOSITORY

docker stop shrupp
docker rm shrupp
docker rmi shrupp-image

docker build -t shrupp-image . \
--build-arg DB_USERNAME=$DB_USERNAME \
--build-arg DB_PASSWORD=$DB_PASSWORD \
--build-arg KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
--build-arg KAKAO_CLIENT_SECRET=$KAKAO_CLIENT_SECRET

docker run -d -p 8080:8080 --name shrupp shrupp-image
