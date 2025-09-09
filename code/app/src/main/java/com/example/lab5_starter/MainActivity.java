package com.example.lab5_starter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDialogFragment.MovieDialogListener {

    private Button addMovieButton;
    private ListView movieListView;

    private ArrayList<Movie> movieArrayList;
    private ArrayAdapter<Movie> movieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set views
        addMovieButton = findViewById(R.id.buttonAddMovie);
        movieListView = findViewById(R.id.listviewMovies);

        // create movie array
        movieArrayList = new ArrayList<>();
        movieArrayAdapter = new MovieArrayAdapter(this, movieArrayList);
        movieListView.setAdapter(movieArrayAdapter);

        addDummyData();

        // set listeners
        addMovieButton.setOnClickListener(view -> {
            MovieDialogFragment movieDialogFragment = new MovieDialogFragment();
            movieDialogFragment.show(getSupportFragmentManager(),"Add Movie");
        });

        movieListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Movie movie = movieArrayAdapter.getItem(i);
            MovieDialogFragment movieDialogFragment = MovieDialogFragment.newInstance(movie);
            movieDialogFragment.show(getSupportFragmentManager(),"Movie Details");
        });
    }

    @Override
    public void updateMovie(Movie movie, String title, String genre, String year) {
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(year);
        movieArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void addMovie(Movie movie) {
        movieArrayList.add(movie);
        movieArrayAdapter.notifyDataSetChanged();

    }

    public void addDummyData(){
        Movie m1 = new Movie("Interstellar", "Scifi", "No idea");
        Movie m2 = new Movie("Inception", "Action", "2012");
        movieArrayList.add(m1);
        movieArrayList.add(m2);
        movieArrayAdapter.notifyDataSetChanged();
    }
}