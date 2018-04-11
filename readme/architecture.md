## General ##

The project at the current state serves mostly as a segmentation and authentication mechanism of the data provided by the CMX API, version 1 and 2. It also provides analytical tools for the fetched data and storage capabilites on the backend. The backend must be configurable per client, including the data that is reported to each user, what each account can do (permissions) and so on.

## Dependencies ##

As of right now, the project has a single component dependency: CMX and it's API, both version 1 and 2

## General Architecture ##

The project is divided in several modules that interact, each with it's own responsabilities and they are dependent on each other. The modules are: backend, frontend and machine learning backend.

### Backend ###

The backend module is used to communicate with the CMX API while keeping some of the information in a structured Database created from the CMX. The backend should be the only source of communication in CMX since it is the only module that can verify, for authentication purposes of each user, if the information they are request is within their segment of the cloud. 

The backend interacts with a Postgresql database that stores a domain model of the cloud organization, users and permissions, and everything else necessary to properly authenticate requests.
It also interacts with a Redis database that stores information for short-medium term purposes (either for immediate delivery to the frontend or for storage and then analysis of the data)

This module depends on the CMX API running.

### Machine Learning Backend ###

The machine learning backend keeps a simplified but replicated database of the Backend to keep track of users and their permissions. It also keeps track of all available models to be served to the frontend and to what client it belongs, how many arguments it takes, etc...

The Machine Learning backend answers to requests in the same way as the regular Backend, except the queries are not forwarded to the CMX API but to seralized models trained from our data that can make predictions or analysis from the features provided. 

This module does not depend on any other module.

### Frontend ###

The frontend is the module responsible for providing an interface to the clients to access and visualize the functionalities provided by the backend and machine learning backend.

The frontend requires that the Backend is running to allow a user to login and access any kind of information.
The machine learning backend is only necessary to access the prediction and analysis functionalities usualy in the form of widgets.
