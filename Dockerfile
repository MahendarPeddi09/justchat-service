From tomcat:latest 
#RUN /target/mkdir /usr/local/tomcat/webapps/justchat
COPY /target/justchat-2.1.6.RELEASE.war /usr/local/tomcat/webapps/
#EXPOSE 9119
