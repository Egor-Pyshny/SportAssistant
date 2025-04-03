FROM mobiledevops/android-sdk-image:latest

WORKDIR /app

USER root

COPY . .

CMD ["./docker-entrypoint.sh"]