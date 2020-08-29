#!/usr/bin/env sh

curl -X POST http://localhost:8080/test
echo "\n"

curl -X GET http://localhost:8080/test
echo "\n"

curl -X GET http://localhost:8080/test/123
echo "\n"
