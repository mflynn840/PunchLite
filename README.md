A simple Timeclock program to help me learn full stack development frameworks


PunchLiteDemo:
    -A Java Spring backend that exposes the database api
    -CRUD operations for users, managers and time entries
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
