#!/usr/bin/env sh
set -eu

ENV_DIR="learnify-db/.env"

mkdir -p "$ENV_DIR"

for name in api db web; do
  target="$ENV_DIR/.env.$name"
  example="$ENV_DIR/.env.$name.example"

  if [ -f "$target" ]; then
    printf 'Keeping existing %s\n' "$target"
  else
    cp "$example" "$target"
    printf 'Created %s\n' "$target"
  fi
done

api_env="$ENV_DIR/.env.api"
if grep -q '^JWT_SECRET=replace-with-' "$api_env"; then
  if command -v openssl >/dev/null 2>&1; then
    secret=$(openssl rand -hex 32)
  else
    secret=$(od -An -N32 -tx1 /dev/urandom | tr -d ' \n')
  fi
  sed "s|^JWT_SECRET=.*|JWT_SECRET=$secret|" "$api_env" > "$api_env.tmp"
  mv "$api_env.tmp" "$api_env"
  printf 'Generated a local JWT secret in %s\n' "$api_env"
fi

printf '%s\n' 'Local environment is ready. Start the stack with:'
printf '%s\n' '  docker compose up --build'
