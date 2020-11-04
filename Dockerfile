FROM openjdk:11.0.7-jdk

## Copy jar into the container
ADD build/libs/rig-api-*-*.jar /opt/app/app.jar

# Command to run when container starts
ENTRYPOINT java ${JAVA_OPTS} \
    -jar /opt/app/app.jar