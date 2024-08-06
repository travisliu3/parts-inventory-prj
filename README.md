# Admin Product API Documentation

This document provides an overview of all the API routes.

## API Endpoint

### POST `/api/v1/auth/register`

This endpoint registers a new user with the provided information.

#### Request

The `POST` request to this endpoint requires a JSON body containing the user's information.

#### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/auth/register" \
-H "Content-Type: application/json" \
-d '{
  "firstname": "Travis",
  "lastname": "Liu",
  "email": "test3@test",
  "password": "1234"
}'
