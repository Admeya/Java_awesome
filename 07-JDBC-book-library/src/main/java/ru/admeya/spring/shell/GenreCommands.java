package ru.admeya.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.admeya.spring.dao.GenreDaoJdbc;
import ru.admeya.spring.domain.Genre;

@ShellComponent
public class GenreCommands {

    private final GenreDaoJdbc genreDao;

    public GenreCommands(GenreDaoJdbc genreDaoJdbc) {
        this.genreDao = genreDaoJdbc;
    }

    @ShellMethod(value = "Add genre")
    public String insertGenre(
            @ShellOption String genre) {
        long id = genreDao.insert(new Genre(genre));
        return String.join(" ",
                "genre add:",
                genre,
                "with id:",
                String.valueOf(id));
    }

    @ShellMethod(value = "Get all genres")
    public String getAllGenres() {
        StringBuilder sb = new StringBuilder();
        genreDao.getAllGenres().forEach(genre -> sb.append(genre));
        return sb.toString();
    }

    @ShellMethod(value = "Get genre by id")
    public String getGenreById(@ShellOption long id) {
        Genre genre = genreDao.getGenreById(id);
        return genre.toString();
    }

    @ShellMethod(value = "Delete genre by id")
    public String delGenreById(@ShellOption long id) {
        genreDao.deleteById(id);
        return "genre deleted";
    }

    @ShellMethod(value = "Get genre by FIO")
    public String getGenreByName(
            @ShellOption String genreName
    ) {
        Genre genre = genreDao.getGenreByName(genreName);
        if (genre == null) {
            return "Genre not found";
        }
        return genre.toString();
    }
}
