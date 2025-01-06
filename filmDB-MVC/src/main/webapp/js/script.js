const addButton = document.getElementById('addNewFilm');
const closeButton = document.getElementById('closeForm')
const FilmForm = document.getElementById('FilmForm');

addButton.addEventListener('click', () => {
  FilmForm.style.display = 'flex';
});

closeButton.addEventListener('click', () => {
  
    FilmForm.style.display = 'none';
  });

