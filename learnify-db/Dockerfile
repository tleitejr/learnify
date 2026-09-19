FROM postgres:17

HEALTHCHECK --interval=5s --retries=10 \
  CMD pg_isready -U dev -d learnify_db