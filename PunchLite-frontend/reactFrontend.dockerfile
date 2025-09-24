# Step 1: Build the frontend application
FROM node:16 AS build

# Set working directory for the frontend app
WORKDIR /frontend

# Copy package.json and package-lock.json to install dependencies
COPY PunchLite-frontend/package.json PunchLite-frontend/package-lock.json ./
RUN npm install

# Copy the rest of the frontend code and build the app
COPY PunchLite-frontend/src ./src    
RUN npm run build

# Step 2: Serve the React app using nginx
FROM nginx:alpine

# Copy the build files from the build stage to nginx's web directory
COPY --from=build /frontend/build /usr/share/nginx/html

# Expose the port that nginx will use
EXPOSE 80

# Start nginx to serve the React app
CMD ["nginx", "-g", "daemon off;"]
