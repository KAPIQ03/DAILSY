# 🌼 Dailsy

Dailsy is a modern, full-stack social platform designed with a microservices-ready architecture. It features a robust **Spring Boot** backend and a high-performance **Next.js** frontend, fully containerized with **Docker**.

---

## 🚀 Overview

Dailsy provides a seamless social experience allowing users to share posts, interact through comments and reactions, and build a network by following other users. The project emphasizes security, scalability, and a clean user interface.

## ✨ Core Features

- **🔐 Authentication & Security:** Secure registration and login using JWT (JSON Web Tokens) and BCrypt password hashing.
- **📝 Post Management:** Create and view posts with ease.
- **👥 Social Connectivity:** Comprehensive follow/unfollow system to build your network.
- **💬 Engagement:** Real-time-ready comment system for every post.
- **❤️ Reactions:** Simple and intuitive post like/unlike functionality.
- **👤 Profile Customization:** Update personal information including username and email.
- **📱 Responsive UI:** A modern, mobile-first frontend built with React 19 and Tailwind CSS 4.

## 🛠️ Tech Stack

### Backend
- **Language:** Java 21
- **Framework:** Spring Boot 3.5.9
- **Security:** Spring Security 6 + JJWT
- **Persistence:** Spring Data JPA + PostgreSQL 14
- **Build Tool:** Gradle

### Frontend
- **Framework:** Next.js 16.1.1 (App Router)
- **Library:** React 19.2.3
- **Language:** TypeScript
- **Styling:** Tailwind CSS 4
- **State Management:** React Hooks & Context API

### Infrastructure
- **Containerization:** Docker & Docker Compose
- **Database:** PostgreSQL 14

---

## 📂 Project Structure

```text
/DAILSY
├── backend/                # Spring Boot Application
│   ├── src/main/java/      # Core logic (Controller, Service, Repository, DTO)
│   ├── build.gradle        # Gradle dependencies
│   └── Dockerfile          # Backend container definition
├── frontend/               # Next.js Application
│   ├── app/                # App Router (Pages, Layouts)
│   ├── components/         # Reusable UI components
│   ├── lib/                # API client and utility functions
│   └── Dockerfile          # Frontend container definition
├── docker-compose.yaml     # Service orchestration
└── .env.template           # Environment variables template
```

---

## 🚦 Getting Started

### 1. Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Node.js & npm](https://nodejs.org/) (for local frontend development)
- [Java 21 JDK](https://www.oracle.com/java/technologies/downloads/) (for local backend development)

### 2. Configuration
Create a `.env` file in the root directory:
```bash
cp .env.template .env
```
Update `.env` with your database credentials and a secure `JWT_SECRET_KEY`.

### 3. Running with Docker (Recommended)
Launch the entire stack (Database, Backend, Frontend) with a single command:
```bash
docker-compose up --build
```
- **Frontend:** [http://localhost:3001](http://localhost:3001)
- **Backend API:** [http://localhost:8080](http://localhost:8080)

---

## 📡 API Endpoints (Highlights)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT |
| `GET` | `/api/posts` | Retrieve all posts |
| `POST` | `/api/posts` | Create a new post |
| `POST` | `/api/follows/{id}` | Follow a specific user |
| `PUT` | `/api/users/me` | Update personal profile |

---

## 🛠️ Development

### Local Backend
```bash
cd backend
./gradlew bootRun
```

### Local Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## 🔮 Roadmap
- [ ] Post mood indicators
- [ ] Image and media uploads
- [ ] Global search for users and posts
- [ ] Pagination for all lists
- [ ] Notification system

---
*Created as part of the PAW Project.*
