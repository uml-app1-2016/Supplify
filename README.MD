Yusuf:

Added several new functionalities to the scraper.
These include the capability to pass in a list of types
which are used to scrape different parts of the page.
In the past we were reloading the page every time we wanted
to scrape a different piece of information from it and
this got to be very network intensive.

Other features added included:  
1. Getting the ratings of drugs  
2. Retrieving the dosage information  
3. Getting the pros of a drug  
4. Getting the cons of a drug  

George:

Worked on the database portion, which we're currently using in the Search History view. Added icon to the menu bar for a way navigate to the History View. History View is finally functional. Immediate goals: Store text in the search icon, and upon hitting the "search" button, store the string as a new object in the Search History database.

Work was done on the following files
* DrugActivity.java
* HistoryActivity.java
* MainActivity.java
* activity_history.xml
* list_item.xml
* Database files
  * HistoryContract.java
  * HistoryDbHelper.java
  * HistoryProvider.java
