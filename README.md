# FilmDB-WebApp

## What is it?

This repository contains the code for a film rating web application. There is two variants of the app, an MVC app and a JavaScript Web app. Server side code can also be found for the REST API. The application allows users to perform CRUD (Create, update and delete) operations on a film database which sits server-side in a variety of formats.

The user can GET request and send data (with PUT and DELETE) in .xml, .txt, .json formats to perform these CRUD operations. The applications demonstrates my ability to interact with various data formats from a client machine and push it to a SQL DB without hibernate or spring frameworks. The application can be deployed to a cloud provider, such as Azure or AWS.

The database login details have been redacted from the FilmDAO.class in both server side apps, for security purposes.


### MVC

Architecture overview:

- Utilises the MVC design model.
- Uses JSTL and EL on the JSP pages. A series of requests are sent from the JSP to the Film controller, and update controller which communicates with the Data Access Object (DAO).
- A film object can be created by calling the Film() method. This is what is used in the DAO when communicating with the SQL server.
- A simple graphical user interface is used, using some bootstrap elements such as a bootstrap table and icons.


### RESTful API

Architecture overview:

- A class to create film objects.
- A class to create film lists.
- Uses a single RESTFulController to handle communications from the client to the DAO.
- The DAO uses a series of SQL commands, to send film data to the DB.

### User Interface of JS Web app📱:

The UI of the JavaScript Web Application is more complex than that of the MVC Application. 
![UI](https://github.com/user-attachments/assets/2c008761-c8be-4511-9547-a0214765d75c)

Inbuilt help:
![UI_Help](https://github.com/user-attachments/assets/c5da45a4-ba50-4334-a6db-3b14518fa0eb)


## Data handling📮
Examples of CRUD functionality with different data formats:

### 1. GET Request in JSON Format
![GET_JSON](https://github.com/user-attachments/assets/fbf01186-c769-40bc-a4d2-e6c769e5b2bc)

### 2. POST Request in XML Format
![POST_XML](https://github.com/user-attachments/assets/4e453947-5a7b-48c0-a4bd-184dade80c7f)
![POST_XML_Success](https://github.com/user-attachments/assets/130bfea0-9dee-4260-a40b-5a16efc0491b)

### 3. PUT Request in TXT:
![PUT_TXT](https://github.com/user-attachments/assets/72ce2032-6a63-430d-84a6-0aca9d2b9295)
![PUT_TXT_devtools](https://github.com/user-attachments/assets/e443af52-78b1-4314-82e8-530f2fd867d9)


### Search Functionality🔎:

The JS web app can use search functionality within the table, and also make GET requests for films with specific years or titles
![Search](https://github.com/user-attachments/assets/24a271ef-c431-4c76-a66e-01c8ee1289ac)

NOTE: A design choice was made to retrieve films for search results in JSON format. The project demonstrates the ability to retrieve data in all formats for the other GET requests. With this successfully demonstrated the decision was made to just use JSON exclusively as it is the quickest retrieval method in comparison to text or XML.

# Requirements✅
HTML 5 compatible browser
Tomcat 9 runtime environment
Internet connection for connection to Mudfoot SQL database


### To start the WEBSVC App:
1. Firstly, start the MVC application in tomcat 9
2. To initialise the MVC web application, start the film controller

### To start the REST APIApp:
1. To initialise the JavaScript client web application, start the film web client app & RESTful API in tomcat 9. 
2. The main page is called index.html for the JS web application.

 
