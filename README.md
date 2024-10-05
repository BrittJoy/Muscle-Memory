"Muscle-Memory" 

Promineo Tech Back End Developer | Final Spring Boot Project Requirements:
 
o Database design which contains at least 3 entities and 3 tables 

o Contains all CRUD operations (Create, Read, Update & Delete)

o Each entity should have at least one CRUD operation AND one or more entities needs to have all 4 CRUD operations (Create, Read, Update & Delete).

o Contains at least one (1) one-to-many relationship

o Contains at least one (1) many-to-many relationship with one or more CRUD operations on this relationship

o Required:  REST Web API Server tested through Swagger, Postman or AdvancedRestClient (ARC) or a front-end client.
 
____________________________________________________________________________________________________________________

Title: Muscle-Memory

Executive Summary:

This application is designed to simplify your weekly workout routines, making fitness a seamless part of your life. 
Assign a specific routine to any day of the week, and effortlessly access your exercise plan each day. 
With full customization options, you can easily add, edit, or remove exercises and routines, ensuring a workout experience tailored just for you.

Initial Features:

• Database Design: ERD
• 3 Entities: Weekday, Routine, Exercises:
	o Weekday has a one-to-many relationship with Routine
	o Routine and Exercise have a many-to-many relationship
• Features:
	o View, create, edit and delete Exercises within an identified Routine
	o View, create and edit Weekdays
	o View, create, edit, and delete Routines
	o View your weekdays with each of their routines, plus each routine’s set of exercises for a holistic view
	o View each routine and the exercises associated to the routine
