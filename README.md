# Museum App Foundation

Monorepo with:

- `frontend`: React + Vite + TypeScript
- `backend`: Java 21 + Spring Boot + JWT authentication

## Documentation

- [Target Architecture v1](docs/TARGET_ARCHITECTURE_V1.md) — system design: modules, services, API, offline sync, roadmap B–F
- [ERD v2 + Field Dictionary](docs/ERD_museum_v2.md) — full entity model with cardinalities, fields, Flyway migration plan

## Quick start

### Backend

```bash
# PostgreSQL starten (im Projektroot)
docker compose -f docker/docker-compose.yml up -d

cd backend
mvn spring-boot:run
```

Flyway führt beim Start automatisch `V1`–`V3` aus (Lookups, Core-Entities, History & Documentation).

Datenbank: `localhost:5432/museum`, User/Passwort: `museum` / `museum_dev`.

Default test user:

- username: `admin`
- password: `admin123`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Vite proxies `/api` to `http://localhost:8080`.

## API endpoints

Auth:
- `POST /api/auth/login` -> JWT token
- `GET /api/public/health` -> public health check
- `GET /api/secure/me` -> protected (Bearer token)

Museum objects (`Authorization: Bearer <token>`):
- `GET /api/v1/objects` — list (paginated)
- `GET /api/v1/objects/search?inventarNumber=` — search by inventar (may return duplicates)
- `GET /api/v1/objects/{id}` — get by UUID
- `POST /api/v1/objects` — create (`assignSystemNumber: true` auto-assigns `MUS-YYYY-NNNNNN`)
- `PUT /api/v1/objects/{id}` — update
- `DELETE /api/v1/objects/{id}` — soft delete

Documentation (nested under object UUID):
- `GET/POST /api/v1/objects/{id}/condition-reports`
- `GET/POST /api/v1/objects/{id}/provenance`
- `GET/POST /api/v1/objects/{id}/insurance`
- `GET/POST /api/v1/objects/{id}/restorations`
- `GET/POST /api/v1/objects/{id}/loans`
- `GET/POST /api/v1/objects/{id}/research-reports`
- `GET/POST /api/v1/objects/{id}/art-historical-reports`
- `GET/POST /api/v1/objects/{id}/documents`

Each nested resource also supports `PUT` and `DELETE` where applicable.

## CI

GitHub Actions workflow in `.github/workflows/ci.yml`:

- frontend: `npm ci`, `npm run lint`, `npm run build`
- backend: `mvn -B test`

