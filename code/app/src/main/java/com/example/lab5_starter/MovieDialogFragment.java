package com.example.lab5_starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MovieDialogFragment extends DialogFragment {

    public interface MovieDialogListener {
        void updateMovie(Movie movie, String title, String genre, String year);
        void addMovie(Movie movie);
        void deleteMovie(Movie movie);
    }

    private MovieDialogListener listener;
    private Movie movie;

    public static MovieDialogFragment newInstance(@Nullable Movie movie) {
        MovieDialogFragment fragment = new MovieDialogFragment();
        Bundle args = new Bundle();
        if (movie != null) {
            args.putString("title", movie.getTitle());
            args.putString("genre", movie.getGenre());
            args.putString("year", movie.getYear());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MovieDialogListener)
            listener = (MovieDialogListener) context;
        else
            throw new IllegalStateException("Host must implement MovieDialogListener");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_movie_details, null);

        EditText editTitle = view.findViewById(R.id.edit_title);
        EditText editGenre = view.findViewById(R.id.edit_genre);
        EditText editYear = view.findViewById(R.id.edit_year);

        Bundle args = getArguments();
        String title = null, genre = null, year = null;
        if (args != null) {
            title = args.getString("title");
            genre = args.getString("genre");
            year = args.getString("year");
        }
        boolean isEdit = title != null;

        if (isEdit) {
            editTitle.setText(title);
            editGenre.setText(genre);
            editYear.setText(year);
            movie = new Movie(title, genre, year);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(isEdit ? "Movie Details" : "Add Movie")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Update" : "Add", (dialog, which) -> {
                    String newTitle = editTitle.getText().toString().trim();
                    String newGenre = editGenre.getText().toString().trim();
                    String newYear = editYear.getText().toString().trim();

                    if (isEdit)
                        listener.updateMovie(movie, newTitle, newGenre, newYear);
                    else
                        listener.addMovie(new Movie(newTitle, newGenre, newYear));
                });

        if (isEdit) {
            builder.setNeutralButton("Delete", (dialog, which) -> {
                listener.deleteMovie(movie);
            });
        }

        return builder.create();
    }
}
