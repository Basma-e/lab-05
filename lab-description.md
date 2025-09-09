# Lab 5: Firebase Integration

## 1. Getting Started
1. If you haven't already, please clone this repository and open the `code/` folder in Android Studio.
2. Much of the provided code should appear familiar from some of your previous labs. This is done to emphasize our focus on implementing our database with existing concepts we've applied in our app.
## 2. Demo Instructions
During this demo, we are going to implement the necessary methods to be able to store data in a NoSQL database. 
1. Together, we will briefly go through [these slides](https://eclass.srv.ualberta.ca/pluginfile.php/11688735/mod_label/intro/Firestore.pdf) highlighting what Firestore is and its key features.
2. Next, follow along with the [Lab Instructions](https://eclass.srv.ualberta.ca/pluginfile.php/11688735/mod_label/intro/lab-instructions.pdf?time=1738460926453).
3. By the end of the demo, you should have a basic understanding of how to interact with your configured Firestore Database. We will show that this works by demonstrating on separate Android devices.
## 3. Participation Exercise
### Tasks
Now that you have seen how to interact with your database, **you will be tasked with the following**:
#### Implement method to update existing documents
- This is so if we edit something in our list, our changes propagate to our database and other devices using your app.
#### Implement the ability to delete documents
- If we want to remove an object from our list, we also want our changes to be reflected beyond just our device.

**Note**: The design of the delete functionality is up to you.

If you have performed these tasks and the demo properly, your changes should stay persistent even when closing the app.