package com.example.lab5_starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private final ArrayList<Movie> movies;

    public MovieArrayAdapter(@NonNull Context context, @NonNull ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_movie, parent, false);

        Movie movie = movies.get(position);
        ((TextView) view.findViewById(R.id.textMovieName)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.textMovieGenre)).setText(movie.getGenre());
        ((TextView) view.findViewById(R.id.textMovieYear)).setText(movie.getYear());

        return view;
    }
}
