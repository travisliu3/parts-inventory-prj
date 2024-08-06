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
```

#### Response

{
    "accessToken": "your-access-token",
    "refreshToken": "your-refresh-token"
}

### POST `/api/v1/auth/authenticate`

This endpoint authenticate a user with the provided information.

#### Request

The `POST` request to this endpoint requires a JSON body containing the user's information.

#### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/auth/register" \
-H "Content-Type: application/json" \
-d '{
    "email": "test3@test",
    "password": "1234"
}'
```

#### Response

{
    "accessToken": "your-access-token",
    "refreshToken": "your-refresh-token"
}

### POST `/api/v1/product`

This endpoint POST a product with the provided information.

#### Request

The `POST` request to this endpoint requires a JSON body containing the product's information.

#### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/product" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-access-token" \
-d '{
    "name": "testProd",
    "price": 123,
    "stock": 23,
    "min": 1,
    "max": 10
}'
```

### POST `/api/v1/part/inHouse`

This endpoint POST an InHouse part with the provided information.

#### Request

The `POST` request to this endpoint requires a JSON body containing the inHouse part's information.

#### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/part/inHouse" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-access-token" \
-d '{
    "name": "test1",
    "price": "12",
    "stock": "1",
    "min": "1",
    "max": "3",
    "machineId": "42343"
}'
```

### POST `/api/v1/part/outsourced`

This endpoint POST an Outsourced part with the provided information.

#### Request

The `POST` request to this endpoint requires a JSON body containing the outsourced part's information.

#### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/part/inHouse" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-access-token" \
-d '{
    "name": "test",
    "price": "12",
    "stock": "1",
    "min": "1",
    "max": "3",
    "companyName": "testName"
}'
```

### PUT `/api/v1/product/{productId}/{partId}`

This endpoint assigns a part to a product.

#### Request

The `PUT` request to this endpoint requires two parameters, first is a product id and second is a part id which is to be assigned to a product.

#### Example Request

```bash
curl -X PUT "http://localhost:8080/api/v1/product/{productId}/{partId}" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-access-token" \
-d '{}'
```

### PUT `/api/part/{partId}?companyName=testName1`

This endpoint changes the type of a part from inHouse to Outsourced and vice-versa.

#### Request

The `PUT` request to this endpoint requires a partId to be edited and the query companyName if the type is changed to Outsourced or machineId if the type is changed to InHouse.

#### Example Request

```bash
curl -X PUT "http://localhost:8080/api/v1/part/1?companyName=testName1" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-access-token" \
-d '{}'
```

### GET `/api/v1/product`

This endpoint retrieves a list of products from the server.

#### Request

The `GET` request to this endpoint does not require any parameters.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/product" \
-H "Authorization: Bearer your-access-token"
```

#### Response

[
    {
        "id": 1,
        "name": "testProd",
        "price": 123.0,
        "stock": 23,
        "min": 1,
        "max": 10,
        "user": {
            "id": 1,
            "firstname": "Travis",
            "lastname": "Liu",
            "email": "test3@test",
            "password": "$2a$10$bMuM0wbggLmqQnTdLhdsMOHS33yDEpvMsehOt3vt7v1jo13mg3Uwm",
            "role": "USER",
            "enabled": true,
            "username": "test3@test",
            "authorities": [
                {
                    "authority": "USER"
                }
            ],
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "accountNonExpired": true
        }
    }
]

### GET `/api/v1/part`

This endpoint retrieves a list of parts from the server.

#### Request

The `GET` request to this endpoint does not require any parameters.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/part" \
-H "Authorization: Bearer your-access-token"
```

#### Response

[
    {
        "id": 1,
        "name": "test1",
        "price": 12.0,
        "stock": 1,
        "min": 1,
        "max": 3,
        "product": null,
        "user": {
            "id": 1,
            "firstname": "Travis",
            "lastname": "Liu",
            "email": "test3@test",
            "password": "$2a$10$bMuM0wbggLmqQnTdLhdsMOHS33yDEpvMsehOt3vt7v1jo13mg3Uwm",
            "role": "USER",
            "enabled": true,
            "username": "test3@test",
            "authorities": [
                {
                    "authority": "USER"
                }
            ],
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "accountNonExpired": true
        },
        "machineId": 42343
    }
]

### PATCH `/api/v1/users/change-password`

This endpoint updates the password for a user.

#### Request

The `PATCH` request to this endpoint require currentPassword, newPassword and conformationPassword.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/users/change-password" \
-H "Authorization: Bearer your-access-token" \
-d '{
    "currentPassword": 1234,
    "newPassword": 12345,
    "confirmationPassword":12345
}'
```

### POST `/api/v1/auth/refresh-token`

This endpoint retrieves a new access and refresh token.

#### Request

The `POST` request to this endpoint does not require any parameters.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/auth/refresh-token" \
-H "Authorization: Bearer your-access-token"
```

#### Response

{
    "accessToken": "your-access-token",
    "refreshToken": "your-refresh-token"
}

### GET `/api/v1/users/getUserName`

This endpoint retrieves the user name of the account holder.

#### Request

The `GET` request to this endpoint does not require any parameters.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/users/getUserName" \
-H "Authorization: Bearer your-access-token"
```

#### Response

Travis Liu

### DELETE `/api/v1/part/{partId}`

This endpoint deletes the part from the database.

#### Request

The `DELETE` request to this endpoint require the partId to be deleted.

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/part/{partId}" \
-H "Authorization: Bearer your-access-token"
```

### DELETE `/api/v1/product/{productId}`

This endpoint deletes the product from the database.

#### Request

The `DELETE` request to this endpoint require the productId to be deleted

#### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/product/{productId}" \
-H "Authorization: Bearer your-access-token"
```
