# Lab 5 Instructions

Winter 2025 - CMPUT 301: Intro to Software Engineering <br/>
Author(s): Bryan Kostelyk <br/>
University of Alberta <br/>
#### Summary
In this lab, you will be integrating a NoSQL database into you're android mobile app. You will learn how to:
- Store data in the cloud for persistent storage
- Organize data for fast retrieval
- Serialize objects to simplify data operations
- Add listeners to synchronize data across instances in real-time
## Step 1. Preparation
1. Clone this repository and open the `code/` folder in Android Studio. This code contains a partially implemented ListView and Button. It also contains objects we use to help change and update our database and views. (More later)
## Step 2. Creating a Firebase Project
1. Go to https://console.firebase.google.com/u/0/.
	1. if you haven't signed up for an account please do so now. (please *do not* use your UAlberta email)
2. Click **Create a Project**. Give it a name. (ex. Lab5-starter)
	1. Disable Gemini in Firebase. (We don't need that 😤)
	2. Disable Google Analytics.
3. Let it build your Project. Click continue when finished.
## Step 3.  Adding Firebase to your App
1. In the Firebase Console, under "Get started by adding Firebase to your app", click on the Android icon.
	1. For *Android Package name*, enter `com.example.<your-project-name>`. If you have cloned the repo, it should be `com.example.lab5_starter`.
	2. Download the *google-services.json* and move it to the **app level** directory of your project. 
2. In Android Studio, go to your **project level** `build.gradle.kts` file and add the following line to your plugins:
```java
plugins {
	// ...
	id("com.google.gms.google-services") version "4.4.2" apply false 
}
```
3. Then in your **app-level** `build.gradle.kts` file, add the following:
```java
plugins {
	// ... 
	id("com.google.gms.google-services")
}


dependencies {
	// ...
	implementation(platform("com.google.firebase:firebase-bom:33.8.0")) 
	implementation("com.google.firebase:firebase-firestore:25.1.1")
	// ...
} 
```
4. After you have done that, synchronize your project with your gradle files. 
## Step 4. Create a Firestore Database
1. In the firebase console, navigate to the menu of the left-hand side and under *build*, select **Firestore Database**.
	1. Click "Create Database". Leave the *id* and *location* fields to their default options. (*Location* should be `nam5 (United States)`)
	2. Click "Start in Test Mode". 
2. You should now see an empty database.
## Step 5. Interacting with the Database
Inside Android Studio, we see that our app can add to our list, but when we relaunch our app, it reverts back. We want the objects that we add to our list view to persist, and we also want other users who are using the app to be able to see updates in real-time.
### Step 5.a Documents, References, and Collections 
1. In the `MainActivity`, add the following: 
```java
public class MainActivity extends AppCompatActivity implements ... {
	//...
	private FirestoreFirebase db;
	private CollectionReference moviesRef;
	//...
}
```
- These classes allow you to interact with your database. 
- **Note**: if you are unable to access these classes, please check that you have configured your gradle files properly.

2. Inside the `onCreate()` method, after setting our views, we want to use these objects above to obtain an instance of our database, which we will then use to access the collection to store and retrieve our data. 
```java
@Override  
protected void onCreate(Bundle savedInstanceState) {
	// ...
	db = FirebaseFirestore.getInstance();  
	movieRef = db.collection("movies");
}
```

3. We now have the foundation to create, read, update, and delete documents in our NoSQL database.
### Step 5.b Adding data to your DB Instance
1. To prepare our java object to pass to the database, we must first implement `Serializable` in our data object we are uploading. This allows us to package our model class conveniently in a `<key, value>`  pair. 
```java 
public class Movie implements Serializable {
	//...
}
```

2. Now we can create the function `addMovie()` that sends our data to firebase.
```java
public class MainActivity extends AppCompatActivity implements ... {
// ...
	protected void onCreate(Bundle savedInstanceState){
		// ...
	}

	@Override
	public void addMovie(Movie movie){
		DocumentReference docRef = movieRef.document(movie.getTitle());
		docRef.set(movie);
	}
}
```
- This sets the document reference to the "Movie Title" whenever we call `addMovie()`

### Step 5.c Adding Event Listeners to Synchronize Data
1. An event listener checks the contents of the path provided to it **and** can be applied to both Documents and Collections to detect changes to them.
	1. For example, we can use the following code to *add a listener* to the `movieRef` declared earlier:
```java
moviesRef.addSnapshotListener((value, error) -> {
	if (error != null){
		Log.e("Firestore", error.toString())
	}
	if (value != null && value.exists()){
		movieArrayList.clear()
		for (QueryDocumentSnapshot snapshot : value){
			String title = snapshot.getString("title");
			String genre = snapshot.getString("genre");
			String year = snapshot.getYear("year");

			movieArrayList.add(new Movie(title, genre, year));
		}
		movieAdapter.notifiyDataSetChanged();
	}
});
```

## Conclusion
You have now seen one way to share data with your NoSQL database. After implementing the above, please complete the accompanying exercise. 
## Resources
Official Documentation:
- [Firebase Documentation](https://firebase.google.com/docs/firestore/query-data/get-data?hl=en&authuser=0&_gl=1*1kn3epd*_up*MQ..*_ga*NjIyMzg0MDMzLjE3MjUxNzEyNjE.*_ga_CW55HF8NVT*MTczODQ0MTM5MS4zNi4xLjE3Mzg0NDEzOTUuNTYuMC4w)
- [Android Documentation](https://developer.android.com/reference)
