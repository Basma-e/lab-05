package com.example.lab5_starter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDialogFragment.MovieDialogListener {
    private ListView listView;
    private Button addButton;

    private final ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MovieArrayAdapter movieArrayAdapter;

    private FirebaseFirestore db;
    private CollectionReference moviesRef;
    private ListenerRegistration moviesRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.buttonAddMovie);
        listView = findViewById(R.id.listviewMovies);

        movieArrayAdapter = new MovieArrayAdapter(this, movieArrayList);
        listView.setAdapter(movieArrayAdapter);

        // Firestore setup
        db = FirebaseFirestore.getInstance();
        moviesRef = db.collection("movies");

        // real-time listener
        moviesRegistration = moviesRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", "Listener error", error);
                return;
            }
            if (value == null) return;

            movieArrayList.clear();
            for (QueryDocumentSnapshot snapshot : value) {
                Movie m = snapshot.toObject(Movie.class);
                if (m.getTitle() != null) movieArrayList.add(m);
            }
            movieArrayAdapter.notifyDataSetChanged();
        });

        addButton.setOnClickListener(v ->
                MovieDialogFragment.newInstance(null)
                        .show(getSupportFragmentManager(), "Add Movie")
        );

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie selected = movieArrayList.get(position);
            MovieDialogFragment.newInstance(selected)
                    .show(getSupportFragmentManager(), "Movie Details");
        });
    }

    @Override
    public void addMovie(Movie movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().isEmpty()) return;
        moviesRef.document(movie.getTitle()).set(movie)
                .addOnFailureListener(e -> Log.e("Firestore", "addMovie failed", e));
    }

    @Override
    public void updateMovie(Movie original, String newTitle, String newGenre, String newYear) {
        if (original == null) return;
        String oldTitle = original.getTitle();
        boolean titleChanged = oldTitle != null && !oldTitle.equals(newTitle);
        Movie updated = new Movie(newTitle, newGenre, newYear);

        if (titleChanged) {
            moviesRef.document(newTitle).set(updated)
                    .addOnSuccessListener(aVoid ->
                            moviesRef.document(oldTitle).delete()
                                    .addOnFailureListener(e -> Log.e("Firestore", "delete old failed", e)))
                    .addOnFailureListener(e -> Log.e("Firestore", "update create new failed", e));
        } else {
            moviesRef.document(oldTitle).set(updated)
                    .addOnFailureListener(e -> Log.e("Firestore", "update failed", e));
        }
    }

    @Override
    public void deleteMovie(Movie movie) {
        if (movie == null || movie.getTitle() == null) return;

        String title = movie.getTitle();

        moviesRef.document(title)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Deleted movie: " + title))
                .addOnFailureListener(e -> Log.e("Firestore", "deleteMovie failed", e));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (moviesRegistration != null) moviesRegistration.remove();
    }
}
