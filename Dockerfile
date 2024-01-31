# /home/ubuntu/jenkins-dockerfile/Dockerfile
FROM jenkins/jenkins:jdk17

USER root
RUN apt-get update &&\
    apt-get upgrade -y &&\
    apt-get install -y openssh-client
