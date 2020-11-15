FROM openjdk:11

ARG spring_profile
ENV spring_profiles_active=$spring_profile

COPY  target /opt/service

ENTRYPOINT ["java"]
CMD ["-jar", "/opt/service/mostly-tracker-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080