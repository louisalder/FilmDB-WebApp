//modal for add form
$('#addButton').click(function() {
	clearTbody();
	$('#addModal').modal('show')

});


//shows the modal when the helpbutton is clicked
$('#helpButton').click(function() {

	$('#helpModal').modal('show')
});

//TODO need to implement
function sanatize(data) {

	var sanatizedData = data.replace();
	
	

	return sanatizedData;

}


var spinner = '<tr><td colspan="6" class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div></td></tr>';

function loading(status) {


	// if the status of the function is set to 1, the spinner is populated
	if (status == 1) {
		$('#films-table tbody').append(spinner);
	}
	// if the status of the function is set to 0, the spinner is removed 
	else if (status == 0) {
		clearTbody();

	}

}

//when the settings button is clicked, it shows the div containing the selects
$('#settingsButton').click(function() {

	console.log("button clicked");
	$('#settings').toggleClass('d-none');
});


//when the modify modal is hidden for modifyfilms, it makes a new get request
$('#modifyModal').on('hidden.bs.modal', function() {

	getFilms();
})
//when the add modal is hidden for modifyfilms, it makes a new get request
$('#addModal').on('hidden.bs.modal', function() {

	getFilms();

})


//adds a filter onto the table based off what is typed in the search bar
//it sees if the contents of the search bar matches the string after converting them both to lowercase
// this is limited, as it won't search specifically for the title or year, just anything within that row.
$(document).ready(function() {


	$('.form-control').on('keyup', function() {
		//sets the variable search input equal to whatever is in the input area in lowercase
		var searchInput = $(this).val().toLowerCase();


		//hides any row that doesnt contain the search input string
		$('tbody tr').filter(function() {
			return $(this).text().toLowerCase().indexOf(searchInput) === -1;
		}).hide();

		//shows any row containing a match to the string
		$('tbody tr').filter(function() {
			return $(this).text().toLowerCase().indexOf(searchInput) !== -1;
		}).show();
	});
});




//creating a film class:
class Film {
	constructor(id, title, year, director, stars, review) {
		this.id = parseInt(id);
		this.title = title;
		this.year = parseInt(year);
		this.director = director;
		this.stars = stars;
		this.review = review;
	}
}




//create a toString with delimiters so this can be separated out on server side
//it prints out the toString to the console, this was used for testing purposes
function toString(film) {

	//adds the film elements to variables
	var id = film.id;
	var title = film.title;
	var year = film.year;
	var director = film.director;
	var stars = film.stars;
	var review = film.review;

	//creates a toStrint
	var stringValue = "ID: " + id + "%%\nTitle: " + title + "%%\nYear: " + year + "%%\nDirector: " + director + "%%\nStars: " + stars + "%%\nReview: " + review + "\n***";

	//logs to console for testing purposes
	console.log(stringValue);
	return stringValue;
}


//create a JSON file from the created object
//it prints out the Json string produced to the console, this was used for testing purposes
function toJSON(film) {

	var stringValue = JSON.stringify(film);
	console.log(JSON.stringify(film));

	return stringValue;
}


//creates a XML string from the object
// it prints a copy of the xml string to the console for testing purposes
function toXML(film) {

	//creates a 'skeleton' or structure for the xml
	const xml = `
      <film>
        <director>${film.director}</director>
        <id>${film.id}</id>
        <review>${film.review}</review>
        <stars>${film.stars}</stars>
        <title>${film.title}</title>
        <year>${film.year}</year>
      </film>
    `;

	//logs to console for testing purposes
	console.log(xml);

	return xml;
}


//film API URL
const url = 'http://localhost:8080/FilmRESTFulAPI/filmapi'


//takes in the format in from the select option picker
var formatIn = $('#format').val();

//takes in the second input value from the selec t option picker
var formatOut = $('#formatOut').val();


//changes the Accept format based off user input. defaults to json
if (formatIn == "JSON") {
	var format = "application/json";
	console.log("format in= " + format);
}
else if (formatIn == "XML") {
	var format = "application/xml";
	console.log("format in= " + format);
}
else if (formatIn == "Text") {
	var format = "text/plain";
	console.log("format in= " + format);
}
else if (formatIn = null) {
	var format = "application/json";
	console.log("No format, setting to: " + format);
}


//changes the ContentType format based off user input. defaults to json
if (formatOut == "JSON") {
	var format2 = "application/json";
	console.log("format Out= " + format2);
}
else if (formatOut == "XML") {
	var format2 = "application/xml";
	console.log("format Out= " + format2);
}
else if (formatOut == "Text") {
	var format2 = "text/plain";
	console.log("format Out= " + format2);
}
else if (formatOut = null) {
	var format = "application/json";
	console.log("No format, setting to: " + format2);
}


//clear tbody
function clearTbody() {

	//clear films table if required
	var tbody = document.querySelector('#films-table tbody');
	tbody.innerHTML = '';

}


//function to do a GET request
function getFilms() {

	//clears body of table before populating it with data
	clearTbody();

	//populates loading animation
	loading(1);

	//JQuery GET request
	$.ajax({
		//passes in URL, type of request and selected format
		url: url,
		type: "GET",
		headers: { Accept: format },
		success: function(data) {



			console.log(data);

			//passes the data into the relevent method /function to parse it correctly into the tbody

			if (format == "application/json") {

				dataInJSON(data);

			}
			if (format == "application/xml") {
				dataInXML(data);

			}

			if (format == "text/plain") {
				dataInText(data);
			}
		},
		error: function(xhr, textStatus, errorThrown) {
			console.error("Error:", xhr, textStatus, errorThrown);
		},
	});



}


//search function 
function search(searchInput) {
	//grabs the type of search, dependent on the button whih is currently active
	var searchType = $('input[name="options"]:checked').attr('id');

	// shows the type selected for testing purposes
	console.log(searchType);

	//changes the url based on the 
	if (searchType == "yearSearch") {

		var url = `http://localhost:8080/FilmRESTFulAPI/filmapi?year=${searchInput}`;
	}

	else if (searchType == "titleSearch") {
		var url = `http://localhost:8080/FilmRESTFulAPI/filmapi?title=${searchInput}`;
	}


	//JQuery GET request for the search
	$.ajax({
		url: url,
		type: "GET",
		headers: { Accept: "application/json" },
		success: function(data) {

			console.log(data);


			var tbody = document.querySelector('#search-table tbody');
			tbody.innerHTML = '';
			var tableContent = "";

			for (var i = 0; i < data.length; i++) {
				tableContent += "<tr>";
				tableContent += "<td>" + data[i].title + "</td>";
				tableContent += "<td>" + data[i].year + "</td>";
				tableContent += "<td>" + data[i].director + "</td>";
				tableContent += "<td>" + data[i].stars + "</td>";
				tableContent += "<td>" + data[i].review + "</td>";
				tableContent += "<td></td>";
				tableContent += "</tr>";
			}

			//Adds the data into the searchContent table
			$("#searchContent").html(tableContent);

		},
		error: function(xhr, textStatus, errorThrown) {
			console.error("Error:", xhr, textStatus, errorThrown);
			alert('Error: Unable to search film');
		},
	});
}

//on click of the search button
$("#searchButton").click(function(event) {
	//prevents the page from refreshing
	event.preventDefault();

	// gets the contents of the search input
	var searchInput = $("input[type='search']").val().toLowerCase();

	// passes it into the search input method
	search(searchInput);

	//shows the modal for the search results table
	$('#searchModal').modal('show')
});





//on the load of the page, it will send a AJAX JQuery GET request to the API. It uses the format of the 'format' variable, set from the select option or uses JSON as default 
$(document).ready(function() {
	//calls the getfilms method which does the GET request
	getFilms();
});


//doDELETE
//when triggered, the ID is sent to the API of the film that needs to be deleted. It will prompt an alert with the result of the delete request.
function deleteFilm(id) {


	//build the url
	const deleteurl = `http://localhost:8080/FilmRESTFulAPI/filmapi?id=${id}`;

	console.log(deleteurl);

	//jQuery AJAX request for a delete
	$.ajax({
		url: deleteurl,
		type: 'DELETE',
		Accept: "text/plain",
		success: function(result) {

			console.log(result);
			//creates an alert to make user aware film is deleted
			alert('Film Successfully Deleted');

			//clear table and reload request
			getFilms();

		},
		error: function(xhr, textStatus, errorThrown) {

			console.log(xhr);
			console.log(textStatus);
			console.log(errorThrown);

			// alert if the delete failed
			alert('Unable to delete film');
		}
	});



}

//modify
// function to modify exisiting film, and format the data based on the format2 parameter
function modifyFilm(id) {


	//displays the ID of the film which is being modified in the console
	//this was done for testing purposes
	console.log("modifyFilm called with id " + id);

	//clears tbody so it doesn't glitch while filling in form
	clearTbody();

	//shows the modal
	$('#modifyModal').modal('show')

	let data = null;


	//creates an event listener on the modify form modal. When the submit button is clicked, it sends the request data to the relevent format method
	var modifyFilmForm = document.getElementById('modifyFilm');
	modifyFilmForm.addEventListener('submit', function(event) {

		//prevents the page from refreshing
		event.preventDefault();

		//creates an instance of the formData, and adds the modify form contents
		var formData = new FormData(modifyFilmForm);

		//grabs the ID and adds it into the form data
		formData.append('id', id);

		//creates film with the data from the form
		var film = new Film(formData.get('id'), formData.get('title'), parseInt(formData.get('year')), formData.get('director'), formData.get('stars'), formData.get('review'));


		//depending on the format type the format 2 is set to, it will send the film object to the correct method
		// sets the data equal to the string returned
		if (format2 == "application/json") {

			data = toJSON(film);
		}
		else if (format2 == "application/xml") {
			data = toXML(film);
		}
		else if (format2 == "text/plain") {
			data = toString(film);
		}


		//resets the contents of the form once submitted
		modifyFilmForm.reset();


		//hides modal
		$('#modifyModal').modal('hide')

		//sends the doPUT method in the specified format
		$.ajax({
			type: "PUT",
			url: url,
			data: data,
			contentType: format2,
			success: function(result) {
				console.log(result);
				alert('Film Successfully Modified');
				//once retrieved, it refreshes the table and gets the updated films.
				getFilms();

			},
			error: function(xhr, textStatus, errorThrown) {
				console.log(xhr);
				console.log(textStatus);
				console.log(errorThrown);
				alert('Unable to Modify film');
			}
		});


	});

}

//adds an event listener onto the add form modal
var addFilmForm = document.getElementById('addFilm');
addFilmForm.addEventListener('submit', function(event) {



	//prevents the page from refreshing
	event.preventDefault();

	// uses 10, as the film class requires a value. This is not used 
	var id = 10;
	var title = document.getElementById('title').value;
	var year = document.getElementById('year').value;
	var director = document.getElementById('director').value;
	var stars = document.getElementById('stars').value;
	var review = document.getElementById('review').value;

	// Do something with the values (for example, log them to the console)
	console.log(title, year, director, stars, review);

	// Reset the form
	addFilmForm.reset();



	//creates new film instance
	f = new Film(id, title, year, director, stars, review);

	//inserts film into the addFilm method, which sends the POST request
	addFilm(f);

	//hides the modal
	$('#addModal').modal('hide')




});



//add film function when the button is clicked
function addFilm(film) {

	let data = null;

	if (format2 == "application/json") {

		data = toJSON(film);

	}
	else if (format2 == "application/xml") {

		data = toXML(film);

	}
	else if (format2 == "text/plain") {

		data = toString(film);
		console.log(film)

	}


	// jQuery POST request using AJAX
	// adds in new films
	//uses format 2 to send in correct format
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		contentType: format2,
		success: function(response) {
			console.log(response);
			alert('Film Successfully Inserted');

			//once retrieved, it refreshes the table and gets the updated films.
			getFilms();
		},
		error: function(xhr, textStatus, errorThrown) {

			console.log(xhr);
			console.log(textStatus);
			console.log(errorThrown);
			alert('Unable to Insert film');
		}
	});
}


const id = document.getElementById('id')

//button to get film by id

const idButton = document.getElementById('getFilmByID');
const filmid = document.getElementById('id');


getFilmByID.addEventListener('click', () => {

	//grab film using AJAX and JQuery

});




//gets the data from the GET request if it is in JSON format.
// It adds each data point of the JSON, and inputs it into the table body
function dataInJSON(data) {

	//makes table content an empty string
	var tableContent = '';


	//the data recieved is the 
	for (var i = 0; i < data.length; i++) {
		tableContent += '<tr>';
		tableContent += '<td>' + data[i].title + '</td>';
		tableContent += '<td>' + data[i].year + '</td>';
		tableContent += '<td>' + data[i].director + '</td>';
		tableContent += '<td>' + data[i].stars + '</td>';
		tableContent += '<td>' + data[i].review + '</td>';
		tableContent += '<td class="buttons"> <button class="btn btn-outline-dark modify" data-id="' + data[i].id + '"><span class="material-symbols-outlined">edit</span></button><button class="btn btn-outline-dark delete" data-id="' + data[i].id + '"><span class="material-symbols-outlined">delete</span></button></td>';
		tableContent += '</tr>';
	}

	//removes loading spinner
	loading(0);

	//adds contents to the table body
	document.getElementById('tableContent').innerHTML = tableContent;

	//event listeners are added to every button which is dynamically added. 

	//when the user clicks the delete button, it calls the deleteFilm method and inputs the film ID, taken from the data-id
	var deleteButtons = document.getElementsByClassName('delete');
	for (var d = 0; d < deleteButtons.length; d++) {
		deleteButtons[d].addEventListener('click', function() {
			var filmId = this.getAttribute('data-id');
			deleteFilm(filmId);
		});
	}

	//when the user clicks the modify button, it calls the modifyFilm method and inputs the film ID, taken from the data-id
	var modifyButtons = document.getElementsByClassName('modify');
	for (var m = 0; m < modifyButtons.length; m++) {
		modifyButtons[m].addEventListener('click', function() {
			var filmId = this.getAttribute('data-id');
			modifyFilm(filmId);
		});
	}

}

//gets the data from the GET request if it is in XML format.
// It adds each data point of the XML, and inputs it into the table body
function dataInXML(data) {
	var xmlString = new XMLSerializer().serializeToString(data);
	var xmlDoc = $.parseXML(xmlString);
	var $xml = $(xmlDoc);

	// creates an empty variable for the table content
	var tableContent = '';


	//for each film, in the xml of films, it will add the data into the table using jquery
	$xml.find('film').each(function() {


		var id = $(this).find('id').text();
		var title = $(this).find('title').text();
		var year = $(this).find('year').text();
		var director = $(this).find('director').text();
		var stars = $(this).find('stars').text();
		var review = $(this).find('review').text();

		tableContent += '<tr>';
		tableContent += '<td>' + title + '</td>';
		tableContent += '<td>' + year + '</td>';
		tableContent += '<td>' + director + '</td>';
		tableContent += '<td>' + stars + '</td>';
		tableContent += '<td>' + review + '</td>';
		tableContent += '<td class="buttons"> <button class="btn btn-outline-dark modify" data-id="' + id + '"><span class="material-symbols-outlined">edit</span></button><button class="btn btn-outline-dark delete" data-id="' + id + '"><span class="material-symbols-outlined">delete</span></button></td>';
		tableContent += '</tr>';
	});

	// removes loading spinner
	loading(0);

	//loads data into the table
	document.getElementById('tableContent').innerHTML = tableContent;


	//event listeners are added to every button which is dynamically added. 

	//when the user clicks the delete button, it calls the deleteFilm method and inputs the film ID, taken from the data-id
	//event listeners are added to every button which is dynamically added. 
	var deleteButtons = document.getElementsByClassName('delete');
	for (var d = 0; d < deleteButtons.length; d++) {
		deleteButtons[d].addEventListener('click', function() {
			var filmId = this.getAttribute('data-id');
			deleteFilm(filmId);
		});
	}

	//when the user clicks the modify button, it calls the modifyFilm method and inputs the film ID, taken from the data-id
	var modifyButtons = document.getElementsByClassName('modify');
	for (var m = 0; m< modifyButtons.length; m++) {
		modifyButtons[m].addEventListener('click', function() {
			var filmId = this.getAttribute('data-id');
			modifyFilm(filmId);
		});

	}

}


//gets the data from the GET request if it is in JSON format.
// It uses '***,' to split up the films, from each other, and then and '%%' to split up each data point for the films.
//data is then dynamically inputted into the table
function dataInText(data) {

	// removing out '[' and ']' characters. 
	//Also removes the space which comes before the first ID
	var data = data.replace(']', '').replace('[', '').replace(' ID:', 'ID:');

	// create a string array
	const filmsArray = [];

	// splits up the array using the '***,'
	const filmsArraySplit = data.split('***,');

	filmsArraySplit.forEach(item => {
		filmsArray.push(item);
	});

	// creates an empty string for table content
	var tableContent = ""

	console.log(filmsArray[0]);
	console.log(filmsArray[1]);


	for (let i = 0; i < filmsArray.length; i++) {
		const film = [];
		const filmElement = filmsArray[i].split("%%");

		filmElement.forEach(item => {
			film.push(item.trim());
		});

		//add it to variables
		// removes out the start of each element of the string, e.g. goes from 'ID: 11312' to '11312'
		const id = film[0].replace("ID: ", "");
		const title = film[1].replace("Title: ", "");
		const year = film[2].replace("Year: ", "");
		const director = film[3].replace("Director: ", "");
		const stars = film[4].replace("Stars: ", "");
		const review = film[5].replace("Review: ", "");

		tableContent += '<tr>';
		tableContent += '<td>' + title + '</td>';
		tableContent += '<td>' + year + '</td>';
		tableContent += '<td>' + director + '</td>';
		tableContent += '<td>' + stars + '</td>';
		tableContent += '<td>' + review + '</td>';
		tableContent += '<td class="buttons"> <button class="btn btn-outline-dark modify" data-id="' + id + '"><span class="material-symbols-outlined">edit</span></button><button class="btn btn-outline-dark delete" data-id="' + id + '"><span class="material-symbols-outlined">delete</span></button></td>';

		tableContent += '</tr>';
	}
	loading(0);
	document.getElementById('tableContent').innerHTML = tableContent;

	//event listeners are added to every button which is dynamically added. 
	var deleteButtons = document.getElementsByClassName('delete');
	for (var d = 0; d < deleteButtons.length; d++) {
		deleteButtons[d].addEventListener('click', function() {
			//grabs film id from button
			var filmId = this.getAttribute('data-id');
			deleteFilm(filmId);
		});
	}

	//when the user clicks the modify button, it calls the modifyFilm method and inputs the film ID, taken from the data-id
	var modifyButtons = document.getElementsByClassName('modify');
	for (var m = 0; m < modifyButtons.length; m++) {
		modifyButtons[m].addEventListener('click', function() {
			var filmId = this.getAttribute('data-id');
			modifyFilm(filmId);
		});

	}

}
