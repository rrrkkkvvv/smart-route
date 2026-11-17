# SmartRoute

SmartRoute is a web application for planning, comparing, and visualizing routes using multiple routing providers.

## Features

- Route planning between selected locations.
- Multiple routing providers.
- Parallel route calculation.
- Route comparison by distance and duration.
- Interactive map with route visualization.
- Support for different transport modes.
  
Note: Address search is currently restricted to Poland. Locations outside Poland are not supported.
## Demo

![SmartRoute Demo](docs/demo.gif)

## Tech Stack

**Backend**

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Maven
- SQLite

**Frontend**

- React
- TypeScript
- Tailwind CSS
- Leaflet

**External Services**

- [OpenStreetMap Nominatim](https://nominatim.org/) — geocoding.
- [ODT](https://odt.org.pl/) — routing.
- [HeiGIT OpenRouteService](https://openrouteservice.org/) — routing.

## Architecture

The project follows a **Feature-Based Layered Architecture**.

The backend is organized by features, with each feature separated into API, application, domain, and infrastructure layers.

The frontend uses a feature-based structure with reusable components.

## Getting Started

### Prerequisites

- Java 25
- Node.js and npm

### 1. Clone the repository

```bash
git clone https://github.com/rrrkkkvvv/smart-route.git
cd smart-route
```

### 2. Backend configuration

Navigate to the backend directory:

```bash
cd backend
```

Create a `.env` file in the `backend` directory:

```env
JWT_SECRET=your_base64_encoded_jwt_secret

# Optional: HeiGIT API key
# HEIGIT_API_KEY=your_heigit_api_key
```

The JWT secret is required.

The HeiGIT API key is optional. To obtain one, register at:

[https://account.heigit.org/login](https://account.heigit.org/login)

After obtaining the key, uncomment the `HEIGIT_API_KEY` line and replace the placeholder with your key.

**Important:** If `HEIGIT_API_KEY` is not configured, the HeiGIT provider will not be available. Selecting this provider in a route request will result in an HTTP 400 Bad Request response. Other configured routing provider can still be used(Will be available only Transit transport mode).

### 3. Run the backend

From the `backend` directory:

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

The backend will start at:

`http://localhost:8080`

### 4. Frontend configuration

Open another terminal and navigate to the frontend directory:

```bash
cd frontend
```

Create a `.env` file:

```env
VITE_API_URL=http://localhost:8080/api/
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend will be available at:

`http://localhost:5173`

## AI Tools Usage

AI tools used during development:

- **ChatGPT** — assistance with backend implementation, test improvements, debugging, and architectural decisions.
- **GitHub Copilot** — assistance with code completion, Tailwind CSS styling, and frontend component structure.

AI-generated suggestions were reviewed and adapted during development.
