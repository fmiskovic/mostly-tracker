FROM openjdk:11

ARG spring_profile
ENV spring_profiles_active=$spring_profile

COPY  service/build/libs /opt/service

ENTRYPOINT ["java"]
CMD ["-jar", "/opt/service/*-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080