CREATE SUBSCRIPTION my_sub
CONNECTION 'host=${DOCKER_DB_HOST} port=5432 user=${DB_USER} password=${DB_PASSWORD} dbname=traveler_db'
PUBLICATION my_pub;