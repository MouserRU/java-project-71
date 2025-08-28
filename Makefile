#Makefile
rebuild: clean build

clean:
	JAVA_OPTS="--enable-native-access=ALL-UNNAMED" ./gradlew clean



.PHONY: build
build:
	JAVA_OPTS="--enable-native-access=ALL-UNNAMED" ./gradlew build

.PHONY: installDist
installDist:
	JAVA_OPTS="--enable-native-access=ALL-UNNAMED" ./gradlew installDist

# Переменные
# Директория для скомпилированных файлов
BIN_DIR = ./build/install/app/bin/
ARGS_1 = ./build/resources/main/file1.json
ARGS_2 = ./build/resources/main/file2.json

# Цель для запуска программы с параметрами
.PHONY: run
run: clean installDist
	@echo "Запуск программы..."
	@$(BIN_DIR)app $(ARGS_1) $(ARGS_2)