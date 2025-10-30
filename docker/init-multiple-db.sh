#!/bin/bash
set -e

# Variable que contiene las bases de datos separadas por coma (productsdb,inventorydb)
IFS=',' read -r -a DATABASES <<< "$POSTGRES_MULTIPLE_DATABASES"

echo "Creating multiple databases: ${DATABASES[*]}"

for DB in "${DATABASES[@]}"; do
    DB=$(echo "$DB" | xargs) # Limpia espacios en blanco
    if [ -n "$DB" ]; then
        echo "  Creating database '$DB'..."
        # ❗ CRUCIAL: Conecta primero a la BD 'postgres' (que siempre existe)
        psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
            CREATE DATABASE "$DB";
            GRANT ALL PRIVILEGES ON DATABASE "$DB" TO "$POSTGRES_USER";
EOSQL
    fi
done