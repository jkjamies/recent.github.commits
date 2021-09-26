# recent.github.commits
Recent GitHub commits in a simple list from the GitHub api.
\
Enter owner of repository into the owner editText\
Enter name of repository into the repository editText\
\
Default owner is jkjamies, and default repository is recent.github.commits\
\
When you start the application for the first time, you will see the following:\
\
![First Load](readme_resources/first_load.png)\
\
Following searching the default values using the Floating Action Button, you will see the following:\
\
![First Search](readme_resources/after_searching.png)\
\
Following searching another repository, the list will load as long as the repository and owner value are valid.\
\
![Second Search](readme_resources/second_search.png)\
\
If the application is closed and reopened, the database is used to initially load data. The owner and repository, however,
are set back to the default values to demonstrate that local database values were utilized.\
\
![Relaunched](readme_resources/relaunched_database_used.png)\