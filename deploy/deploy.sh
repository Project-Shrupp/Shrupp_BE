REPOSITORY=/home/ec2-user/

cd $REPOSITORY

docker stop shrupp
docker rm shrupp
docker rmi shrupp-image

docker build -t shrupp-image .
docker run -d -p 80:443 --name shrupp shrupp-image