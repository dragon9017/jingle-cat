FROM openjdk:8

MAINTAINER dosion@mail.com

RUN cp /etc/hosts /tmp/hosts

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

RUN mkdir -p /internet-phone

WORKDIR /internet-phone

EXPOSE 8099

ADD ./target/internet-phone-1.0.0-SNAPSHOT.war ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar internet-phone-1.0.0-SNAPSHOT.war