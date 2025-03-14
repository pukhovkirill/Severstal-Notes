#!/bin/bash

generate_random_string() {
    local length=$1
    tr -dc 'a-zA-Z0-9' </dev/urandom | head -c "$length"
}

DATABASE_HOST=postgres
DATABASE_PORT=6380
DATABASE_NAME=svrstlnotesdb
DATABASE_USER=$(generate_random_string 5)
DATABASE_PASSWORD=$(generate_random_string 20)
DATABASE_DATASOURCE_URL="jdbc:postgresql://${DATABASE_HOST}:\${DATABASE_PORT}/\${DATABASE_NAME}"

MINIO_URL=minio
MINIO_PORT=9000
MINIO_ACCESS_KEY=$(generate_random_string 32)
MINIO_SECRET_KEY=$(generate_random_string 64)
MINIO_BUCKET=storage

# Создание файла с переменными
cat <<EOF > .env
DATABASE_HOST=${DATABASE_HOST}
DATABASE_PORT=${DATABASE_PORT}
DATABASE_NAME=${DATABASE_NAME}
DATABASE_USER=${DATABASE_USER}
DATABASE_PASSWORD=${DATABASE_PASSWORD}
DATABASE_DATASOURCE_URL=${DATABASE_DATASOURCE_URL}

MINIO_URL=${MINIO_URL}
MINIO_PORT=${MINIO_PORT}
MINIO_ACCESS_KEY=${MINIO_ACCESS_KEY}
MINIO_SECRET_KEY=${MINIO_SECRET_KEY}
MINIO_BUCKET=${MINIO_BUCKET}
EOF

echo "The .env file has been created successfully"