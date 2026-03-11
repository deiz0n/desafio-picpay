#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"

if [[ ! -f "$COMPOSE_FILE" ]]; then
  echo "ERROR: docker-compose.yml not found at root: $COMPOSE_FILE" >&2
  exit 1
fi

env_args=()

# Keep filesystem discovery order by iterating find output without sorting.
while IFS= read -r -d '' dir; do
  service_name="$(basename "$dir")"

  if [[ "$service_name" != *service* ]]; then
    continue
  fi

  env_file="$dir/.env"
  if [[ -f "$env_file" ]]; then
    env_args+=(--env-file "$env_file")
  else
    echo "WARNING: '$service_name' matches '*service*' but has no .env file" >&2
  fi
done < <(find "$SCRIPT_DIR" -mindepth 1 -maxdepth 1 -type d -print0)

if (( ${#env_args[@]} == 0 )); then
  echo "ERROR: No .env files found in root-level directories containing 'service'" >&2
  exit 1
fi

exec docker compose "${env_args[@]}" -f "$COMPOSE_FILE" up -d

