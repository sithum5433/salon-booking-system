# Salon Booking System

Professional salon booking website built with **Spring Boot, Thymeleaf, Spring Security, JPA, and MySQL**.

## Features

- Customer booking for **30 minutes** or **1 hour**
- Prevents **double booking** and overlapping appointments
- Admin approval flow
- Package and price management
- Salon open / close status
- Closed-date blocking
- Customer reviews with star ratings and admin approval
- Live digital clock and salon closing countdown
- Automatic end-of-day deletion of today's bookings
- Demo salon gallery and professional UI
- Favicon included

## Default admin accounts

- Owner admin  
  Username: `owner`  
  Password: `owner123`

- Developer admin  
  Username: `developer`  
  Password: `developer123`

## Before running

Edit `src/main/resources/application.properties` and update:

- `spring.datasource.username`
- `spring.datasource.password`

Optional for email notifications:
- `spring.mail.username`
- `spring.mail.password`

If you do not configure SMTP, the system still runs. Email sending failures are safely ignored.

## Run

```bash
mvn clean install
mvn spring-boot:run
```

Open:
`http://localhost:8080`

## Notes

- Daily cleanup runs at **11:59 PM Asia/Colombo**
- This project deletes today's bookings at the end of the day, exactly as requested
- For a real production system, booking history is usually better than permanent delete
