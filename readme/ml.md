# Machine Learning Repository

This Repository contains the backend server code used for machine learning and several scripts for extracting, processing and analyzing data

## Necessary libraries ##

The code in this repository uses the following non-std libraries:
* pandas
* matplotlib
* flask
* dataset
* scikit-learn (sklearn)
* statsmodels
* numpy
* scipy
* (...)

The full list of packages and it's dependencies can be found in environment.yml at the root of the repository
The .yml can be used to install a fully working environment for this repository using the conda tool (python environment management tool)
The environment file differs from environment to environment.


#### Windows ####
`environment_file = environment.yml`
#### Linux ####
`environment_file = environment_ubuntu.yml`

Then create the environment with:

`conda env create -f <path/to/environment_file> -n <environment_name>`

To activate the environment:

#### Windows ####
`activate <environment_name>`
#### Linux ####
`source activate <environment_name>`

## Organization ##

```html
\ machine-learning
 - scripts/
 |- data/ 	   : contains extracted datasets
 |- analyze.py : contains code to test different models in structured .csv data
 |- extract.py : contains code to extract data with different requests from CMX API
 |- parse.py   : contains code to parse the extracted (raw) data to .csv format
 |- weather.py : contains code to augment extracted data with weather data
 - server/
 |- models/        : contains serialized models to be served by the backend
 |- config.py      : contains abstraction for configuration files
 |- crossdomain.py : contains necessary annotation for flask to support cross-site requests in it's response
 |- database.py    : contains database creation and helper methods
 |- model.py       : contains abstraction for serialized machine learning models and a ModelServer to retrieve and load each model
 |- server.py      : contains the main flask backend server code
```

## Configuring and running ##
### Database ###
The PostgreSQL database can be configured for now in database.py. After the configuration abstraction is properly tested the database configuration will be extracted from the current configuration profile

The current defaults are: 

```
DB_USERNAME = 'ml_backend'
DB_PASSWORD = 'learning'
DB_NAME 	= 'ml_db'
DB_IP 		= 'localhost'
DB_PORT 	= '5432'
```

### Flask (RUNNING THE SERVER) ###
A flask application requires the environment variable FLASK_APP=... to be set in order to find the server entry point.
The variable FLASK_DEBUG=1 can also be used while in development for more thorough errors and automatic reloading of code
To run the server from the server/ folder:
#### Linux ####
`export FLASK_APP=server.py && flask run`
#### Windows ####
`set FLASK_APP=server.py && flask run`

#### Models ####

Currently there are two trained models with 3-month data from the Elvas production site.

- SVR (Stave Vector Regression) model with input features: day of the week, day of the month, holiday, and several weather parameters (windspeed predictions, min and max temperature predictions, etc...)
- ARIMA statistical model trained on the visitor count, with a few exogenous variables: day of the week, (possible should test weather features here as well, altough not as useful)
