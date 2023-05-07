.PHONY: dev up build down doc

dev:
	@echo "Starting development server..."
	@./gradlew quarkusDev -Dquarkus.profile=local

up:
	@echo "Starting development container..."
	@docker-compose up -d

build:
	@echo "Building application..."
	@docker build -f ./Dockerfile -t user-service .

down:
	@echo "Stopping development container..."
	@docker-compose down

doc:
	@echo "Generating documentation..."
	@./gradlew build -Dquarkus.profile=local

