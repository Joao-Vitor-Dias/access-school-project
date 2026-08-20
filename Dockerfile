FROM ubuntu:latest

RUN apt update && apt upgrade -y

RUN apt install openjdk-21-jdk -y
RUN apt install unzip -y
RUN apt install curl -y

RUN curl -o /tmp/google-chrome-stable_current_amd64.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt install /tmp/google-chrome-stable_current_amd64.deb -y

RUN curl -o /tmp/chromedriver-linux64.zip https://storage.googleapis.com/chrome-for-testing-public/150.0.7871.124/linux64/chromedriver-linux64.zip

RUN unzip /tmp/chromedriver-linux64.zip -d /tmp
RUN mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/
RUN chmod +x /usr/local/bin/chromedriver


RUN mkdir -p /app/images

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

CMD ["java", "-jar", "target/adminstrative-system.jar"]