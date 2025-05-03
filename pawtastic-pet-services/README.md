# 🐾 Pawtastic Pet Services

A full-stack encrypted appointment scheduling system for pet services.

## 📁 Project Structure
pawtastic-pet-services/
├── backend/ → Java Spring Boot API
├── frontend/ → Angular client
└── README.md → Project overview

- Normally, there would be two separate repositories for frontend and backend source code. For simplicity, included both in one repository.

## 🛠 Technologies

- Frontend: Angular 16, Bootstrap 5, Node v22.15.0
- Backend: Spring Boot 3.2, Java 17
- Crypto: AES-GCM (client-side), JWT (temporary static token)

🧪 Test the App
Open http://localhost:4200 (Angular)

Fill and submit the appointment form

Encrypted data is sent to the API at http://localhost:8080/api/form/submit

📌 Notes
AES-GCM encryption is handled on the client.

JWT is currently hardcoded for demo/testing.

# See backend/README.md for detailed setup
# See frontend/README.md for detailed setup
