# Option 1
Step 1: Clean and verify compilation
cd backend
Remove-Item -Recurse -Force -Path "target"
.\mvnw clean
.\mvnw compile
# Result: BUILD SUCCESS

Step 2: Kill the process blocking port 8080
# Find what's using port 8080:
Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue | Select-Object OwningProcess

# Kill the process (in this case it was PID 39236):
Stop-Process -Id 39236 -Force

# Wait a moment:
Start-Sleep -Seconds 2

Step 3: Start the backend
cd backend
.\mvnw spring-boot:run
# Result: "Started BackendApplication in 8.524 seconds"

Step 4: In a NEW terminal, start the frontend
cd frontend
npm install  # if not already done
npm start

Quick Start (For Next Time):
If you're starting fresh after closing terminals:
# Terminal 1 - Backend:
cd backend
.\mvnw spring-boot:run

# Terminal 2 - Frontend:
cd frontend
npm start


# Option 2
# How To Run
Go into the frontend folder:
```bash
cd frontend
```

Then install dependencies:
```bash
npm install
```

```bash
cd ..
```

Open a new terminal and go to backend:
```bash
cd backend
```

Then run this start your backend server:
```bash
.\mvnw spring-boot:run
```

```bash
cd ..
```

Return to frontend folder:
```bash
cd frontend
```

Now run Angular:
```bash
ng serve --open
```
or
```bash
npm start
```

Click on: http://localhost:4200

Return to frontend folder:
```bash
cd frontend
```

Note that you can only open the application in the frontend.

# CruxTrack
2026 Spring Semester SWE4724 Group Project  
FRONTEND: Angular  
BACKEND: Java Spring Boot  
DATABASE: SQLite  
