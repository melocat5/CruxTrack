# make sure instal
- **Node.js** LTS (Recommended: v20.x or latest LTS)
- **Java** JDK 17 or newer

<pre> ``` This is indented across multiple lines ``` </pre>

# How To Run
Go into the frontend folder:
```bash
cd frontend
```

Then install dependencies:
```bash
npm install
```

Open a new terminal and go to backend:
```bash
cd backend
```

Then run this start your backend server:
```bash
.\mvnw.cmd spring-boot:run
```

Open a new terminal
```bash
try { Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/auth/login" -ContentType "application/json" -Body '{"username":"Admin","password":"admin123"}' | ConvertTo-Json -Depth 4 } catch { $_.Exception.Message }
```

Open a new terminal
```bash
cd frontend
```

Now run Angular:
```bash
npm start
```
or
```bash
ng serve --open
```

Click on: http://localhost:4200

# Optional checks:
Open a new terminal
```bash
cd frontend
```

```bash
npm run build
```

Open a new terminal
```bash
cd backend
```

```bash
.\mvnw.cmd test
```









Note that you can only open the application in the frontend.

# CruxTrack
2026 Spring Semester SWE4724 Group Project  
FRONTEND: Angular  
BACKEND: Java Spring Boot  
DATABASE: SQLite  
