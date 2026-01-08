#!/bin/bash
set -e

echo ">>> [ORCHESTRATOR] Starting database initialization..."

if [ -d "/docker-entrypoint-migrations" ]; then
  echo ">>> [ORCHESTRATOR] Running migrations from /docker-entrypoint-migrations..."
  for f in /docker-entrypoint-migrations/*.sql; do
    echo "Executing $f"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f "$f"
  done
fi

if [ -d "/docker-entrypoint-replications" ]; then
  echo ">>> [ORCHESTRATOR] Running replication scripts from /docker-entrypoint-replications..."
  for f in /docker-entrypoint-replications/*.sql; do
    echo "Executing $f"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f "$f"
  done
fi

echo ">>> [ORCHESTRATOR] Initialization complete!"