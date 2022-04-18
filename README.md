# DoiT
### A native Android App designed & developed in Android Studio, which tries to ease the process of managing and scheduling your daily tasks. Written in Java, the app utilizes SQLite database equipped with custom CursorLoader to support data persistence and background Threading.

### Progrmming Language: Java
### IDE: Android Studio
### Database: SQLite

## MainActivity.java
  ### Classes
  - __MainActivity:__ A public class which contians the whole functionality of the main activity.
  ### Public Funcitons
  - **Loader<Cursor> onCreateLoader(int i, Bundle bundle)** : Define a projection to specify the columns to select from query table.
  - **void onLoadFinished(Loader<Cursor> loader, Cursor cursor)** : Update TaskCursorAdapter with this new Adapter containing updated data.
  - **public void onLoaderReset(Loader<Cursor> loader)** : Callback called when data needs to be deleted
  ### Protected Functions 
  - **void onCreate(Bundle savedInstanceState)**:  
    - Setup FAB to open EditorActivity
    - Setup an Adapter to create a list item for each row of task data in the Cursor.
    - Find and populate the ListView
    - Find and set empty view on the ListView, so that it only shows when the list has 0 items.
    - Setup the item click listener
    - Set the URI on the data field of the intent
    - Launch the {@link EditorActivity} to display the data for the current task.
    - kick-off the loader
  
<img src="https://github.com/dhnanj2/ToDo/blob/main/Screenshots/IMG_20210926_202229.jpg" width="256"> 

## EditorActivity.java
  ### Classes
  - __MainActivity:__ A public class which contians the whole functionality of the main activity.
  ### Public Funcitons
  - **Loader<Cursor> onCreateLoader(int i, Bundle bundle)** : Define a projection to specify the columns to select from query table.
  - **void onLoadFinished(Loader<Cursor> loader, Cursor cursor)** : Update TaskCursorAdapter with this new Adapter containing updated data.
  - **public void onLoaderReset(Loader<Cursor> loader)** : Callback called when data needs to be deleted
  ### Protected Functions 
  - **void onCreate(Bundle savedInstanceState)**:  
    - Setup FAB to open EditorActivity
    - Setup an Adapter to create a list item for each row of task data in the Cursor.
    - Find and populate the ListView
    - Find and set empty view on the ListView, so that it only shows when the list has 0 items.
    - Setup the item click listener
    - Set the URI on the data field of the intent
    - Launch the {@link EditorActivity} to display the data for the current task.
    - kick-off the loader
<img src="https://github.com/dhnanj2/ToDo/blob/main/Screenshots/IMG_20210926_202513.jpg" width="256"> 
<img src="https://github.com/dhnanj2/ToDo/blob/main/Screenshots/IMG_20210926_202719.jpg" width="256"> 
