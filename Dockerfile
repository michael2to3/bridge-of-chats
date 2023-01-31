FROM openjdk:19-alpine

WORKDIR /app

COPY app/build/install/app /app/app

ENTRYPOINT ["/bin/sh", "app/bin/app"]
