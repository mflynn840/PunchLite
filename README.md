A simple fullstack Timeclock program to help me learn full stack development frameworks


PunchLite-backend:
    -A Java Spring backend that exposes the database api and enforces security (roles, login)
        -JWT tokens for logging in
        -JPA repository for the database
        -Connected to PostgreseSQL database (see application.properties)
    -CRUD operations for the entities {users, managers and time entries}
    -Handle login using JWT
    -Block access to unauthorized or invalid transactions
    -Configured to listen on port 8080

punchlite-frontend
    -A react frontend that implements the UI
    -Fetch and post to the backend to traform user input into db CRUD
    -handle drawing and switching between UI screens
    -Configured to listen on port 5173

Roles (each level has all of the privledges of the previous plus the additional):
    -Employees (punch in, punch out)
    -Managers (set employees wages, approve employee punches)
    -Admin (assign managers to employees, set managers wages)

Synthetic demo data populating script:
    -./PunchLite-backend/src.main.java/michael/PunchLiteDemo/config/GenerateDummyDb.java
    -Create an admin (admin)
    -Create 2 managers (manager0 and manager1)
    -Create 20 employees (with names employee1...employee20) where half are managed by each manager
        -Each worker gets 10 time entries (reasonably randomly generated, picked within the last 2 weeks)
        -half of the time entires need to be approved still
    -Everyones password is password, the UI changes depending on role
        
    
