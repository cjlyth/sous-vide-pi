#!/bin/bash
CARD_READER_SERIAL_HOME=
JAVA_HOME=/home/pi/bin/java
JAR_FILE_NAME=cardReaderSerial.jar
PI4J_CORE_DIR=/home/hesparza/.m2/repository/com/pi4j/pi4j-core/0.0.5
LOG_DIR=${CARD_READER_SERIAL_HOME}/logs
CARD_READER_SERIAL_MAIN_CLASS=com.micerrada.cardreaderserial.main.CardReaderSerial

${JAVA_HOME}/bin/java -cp "${CARD_READER_SERIAL_HOME}/bin/${JAR_FILE_NAME}:${PI4J_CORE_DIR}/*" -Dlog.dir=${LOG_DIR} ${CARD_READER_SERIAL_MAIN_CLASS} &
