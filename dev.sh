#!/bin/bash
./mvnw clean install -DskipTests && cd trimlight-app && ../mvnw quarkus:dev
