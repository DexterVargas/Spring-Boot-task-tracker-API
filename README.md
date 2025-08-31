# Task Tracking App Backend API

A backend API for managing task lists and tasks, built to power a modern and functional task tracker application.

## Features

- **Task Lists**
    - Create, Read, Update, Delete (CRUD)

- **Tasks**
    - Create, Read, Update, Delete (CRUD)
    - Priority levels: HIGH / MEDIUM / LOW
    - Status management: OPEN / CLOSED

## Tech Stack

- **Java Spring Boot** – REST API framework
- **PostgreSQL (Dockerized)** – Database
- **Spring Data JPA** – ORM and database interaction

## Project Structure

```
src/main/java/com/example/tasktracker
│
├── controllers # REST controllers (Task, TaskList)
├── domain
│ ├── dto # Data Transfer Objects
│ ├── entities # JPA entities
│
├── mappers # Entity <-> DTO mappers
├── repositories # JPA repositories
├── services # Business logic layer
```



## End points


```
### Task List
GET     /task-lists                                      List Task Lists
POST    /task-lists                                      Create Task Lists
GET     /task-lists/{task_list_id}                       Get Task List by ID
PUT     /task-lists/{task_list_id}                       Update Task list
DELETE  /task-lists/{task_list_id}                       Delete Task List

### Task
GET     /task-lists/{task_list_id}/tasks                 List Tasks
POST    /task-lists/{task_list_id}/tasks                 Create Task
GET     /task-lists/{task_list_id}/tasks/{task_id}       Get Task by ID
PUT     /task-lists/{task_list_id}/tasks/{task_id}       Update Task
DELETE  /task-lists/{task_list_id}/task/{task_id}        Delete Task
```

### Base URL

http://localhost:8585/task-lists/

---

⚡ This backend API is designed to serve as the foundation for a **Task Tracker app** with a clean and professional UI.