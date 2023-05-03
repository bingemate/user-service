.PHONY: dev docker-dev build docker-down

dev:
	@echo "Starting development server..."
	@./gradlew quarkusDev --profile local

docker-dev:
	@echo "Starting development container..."
	@docker-compose up -d -f ../dev-manager/docker-compose.yml

build:
	@echo "Building application..."
	@./gradlew build

docker-down:
	@echo "Stopping development container..."
	@docker-compose down -f ../dev-manager/docker-compose.yml

