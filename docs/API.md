# Documentación de la API

Esta sección describe los endpoints disponibles para interactuar con la API.

> **NOTA:** Todos los endpoints comienzan con `${URL}/api/` antes de cada endpoint.

## Autenticación

| Method | Endpoint    | Descripción               | Response                                               |
| ------ | ----------- | ------------------------- | ------------------------------------------------------ |
| POST   | `/login`    | Autentica un usuario      | `{ success: boolean, token: string, message: string }` |
| POST   | `/register` | Registra un nuevo usuario | `{ success: boolean, message: string }`                |

### `/login`

Permite a los usuarios autenticarse en la aplicación. Devuelve un token JWT si la autenticación es exitosa.

- **Request Body**:

  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```

- **Response**:
  ```json
  {
    "success": true,
    "token": "jwt_token",
    "message": "Login successful"
  }
  ```

### `/register`

Permite registrar un nuevo usuario en el sistema.

- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "username": "string",
    "password": "string",
    "avatar": "string | null"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "message": "Registration successful"
  }
  ```

## Mensajes

| Method | Endpoint             | Descripción                                       | Response              |
| ------ | -------------------- | ------------------------------------------------- | --------------------- |
| GET    | `/messages/:chat_id` | Obtiene todos los mensajes de un chat por chat ID | `{ messages: array }` |

### `/messages/:chat_id`

Recupera los mensajes utilizando el chat ID.

- **Request Parameters**:
  - `chat_id` (string): UUID del chat.
- **Response**:
  ```json
  {
    "messages": [
      {
        "id": "uuid",
        "chat_id": "uuid",
        "sender_id": "uuid",
        "body": "string",
        "status": "string",
        "created_at": "timestamp"
      }
    ]
  }
  ```

## Usuarios

| Method | Endpoint           | Descripción                                      | Response                                                                             |
| ------ | ------------------ | ------------------------------------------------ | ------------------------------------------------------------------------------------ |
| GET    | `/users/:username` | Obtiene un usuario por nombre de usuario (sin @) | `{ id: string, name: string, email: string, username: string, avatar: string null }` |
| DELETE | `/users/:id`       | Elimina un usuario por ID si existe              | `{ success: boolean, message: string }`                                              |
| PATCH  | `/users/:id`       | Actualiza un usuario por ID                      | `{ success: boolean, message: string }`                                              |

### `/users/:username`

Obtiene un usuario utilizando el nombre de usuario (sin `@`).

- **Request Parameters**:
  - `username` (string): Nombre de usuario sin `@`.
- **Response**:
  ```json
  {
    "id": "uuid",
    "name": "string",
    "email": "string",
    "username": "string",
    "avatar": "string | null"
  }
  ```

### `/users/:id` (DELETE)

Elimina un usuario utilizando el ID. Retorna un mensaje si el usuario no se encuentra.

- **Request Parameters**:
  - `id` (string): UUID del usuario a eliminar.
- **Response**:
  ```json
  {
    "success": true,
    "message": "User successfully deleted"
  }
  ```
  o
  ```json
  {
    "success": false,
    "message": "User not found"
  }
  ```

### `/users/:id` (PATCH)

Actualiza un usuario utilizando el ID.

- **Request Parameters**:
  - `id` (string): UUID del usuario a actualizar.
- **Request Body**:
  ```json
  {
    "name": "string | null",
    "email": "string | null",
    "username": "string | null",
    "password": "string | null",
    "avatar": "string | null"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "message": "User successfully updated"
  }
  ```
  o
  ```json
  {
    "success": false,
    "message": "Update failed"
  }
  ```
