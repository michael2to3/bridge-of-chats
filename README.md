# Forwarder

This Java application uses the VK API and Telegram Bot API to forward messages from a VK group to a Telegram channel.

## Prerequisites
- A VK token with access to a group
- A Telegram token for a bot
- Telegram channel ID

## Usage

- Clone the repository
- Build the project using maven
- Run the application 
```sh
git clone https://github.com/your-username/forwarder.git
cd forwarder
mvn package
java -jar target/forwarder.jar
```

- Set the environment variable before running the application:
```sh
VK_TOKEN=your_vk_token TELEGRAM_TOKEN=your_telegram_token TELEGRAM_CHANNEL_ID=your_telegram_channel_id VK_GROUP_ID=123456 java -jar target/forwarder.jar
```

## Deployment
The application can be deployed using Docker and Docker Compose.
- Build the image using the following command:
```sh
docker build -t forwarder .
```
- Run the container using the following command:
```sh
docker run -it forwarder
```
- Or you can use the `docker-compose.yml` file to run the service:
```sh
docker-compose up
```
