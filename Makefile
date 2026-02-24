## Default target: show help
.PHONY: help record_screenshot verify_screenshot
.DEFAULT_GOAL := help

help: ## Show this help message
	@echo "Available make commands:"
	@grep -E '^[a-zA-Z0-9_-]+:.*## ' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*## "}; {printf "  make %-18s - %s\n", $$1, $$2}'

record_screenshot: ## Record Paparazzi screenshots for Debug build
	./gradlew :app:recordPaparazziDebug

verify_screenshot: ## Verify Paparazzi screenshots against recorded baselines
	./gradlew :app:verifyPaparazziDebug