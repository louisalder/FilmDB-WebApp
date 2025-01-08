# FilmDB-WebApp

## What is it?

This repository contains full stack code for a film rating web application. There is two variants of the app, an MVC app and a JavaScript Web app. Server side code can also be found for the REST API. The application allows users to perform CRUD (Create, update and delete) operations on a film database which sits server-side in a variety of formats.

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
![UI](https://github.com/user-attachments/assets/fc4d6e98-bd2e-4075-9280-60339355f38d)


### Inbuilt help:
![UI_Help](https://github.com/user-attachments/assets/f360dc6f-f878-41b3-b856-3e0a74b41fca)



## Data handling📮
Examples of CRUD functionality with different data formats:

### 1. GET Request in JSON Format
![GET_JSON](https://github.com/user-attachments/assets/f14ed47f-5c2d-4d4e-b3c4-012c59585daa)


### 2. POST Request in XML Format
![POST_XML](https://github.com/user-attachments/assets/851139d1-b65a-4204-82c8-70f3f4bbfc42)
![POST_XML_Success](https://github.com/user-attachments/assets/1b66f8c2-4ce7-4790-9935-b6ec6247a414)


### 3. PUT Request in TXT:
![PUT_TXT](https://github.com/user-attachments/assets/94c43ba7-3cd9-49d5-8b4f-dbd30a1c81aa)
![PUT_TXT_devtools](https://github.com/user-attachments/assets/b8bbcc5f-51b5-483b-acd2-7f71ea30868e)



### Search Functionality🔎:

The JS web app can use search functionality within the table, and also make GET requests for films with specific years or titles
![Search](https://github.com/user-attachments/assets/9ec8771a-3f3c-4a43-8bef-281bf9e3acc4)


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

 
