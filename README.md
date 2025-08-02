# Bank System (Java EE Project)

A full-featured **Bank Management System** developed with **Java EE (EJB)** and deployed using **GlassFish Server**. This system supports secure, scalable banking operations for both admins and customers through a clean architecture powered by EJB, Servlets, and JSP.

## 🔧 Technologies Used
- Java EE (EJB, Servlet, JSP)
- NetBeans 21
- GlassFish Server 4.1
- JDK 8 or 21
- MailTrap (for email simulation)

---

## ✨ Key Features

- **Admin Dashboard**: Dashboard with stats, user/account management, audit & transaction logs  
- **Customer Dashboard**: Fund transfers, view history, download PDF, income/expense charts  
- **Scheduled Transactions**: Executed using EJB programmatic timers  
- **Monthly Interest**: Automatically added via `@Schedule` EJB timers  
- **Role-Based Access Control**: Secured via `@RolesAllowed`, `web.xml`, and JAAS  
- **Email Notifications**: Auto-generated credentials sent via MailTrap  
- **Audit Logging**: Cross-cutting auditing using EJB Interceptors  
- **Error Pages**: Custom JSP error handlers (401, 403, 404, 500)
---

## ⚙️ How to Run the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/DewangaTheekshana/bank_system.git
   
- Open in NetBeans 21

- Deploy to GlassFish Server 4.1

- Run the project and access it via your web browser

## 👤 Author
Dewanga Theekshana - 
Student at Java Institute for Advanced Technology

📧 theakshanadewanga10@gmail.com
