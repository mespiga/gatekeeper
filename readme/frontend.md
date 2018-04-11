## Synopsis

At the top of the file there should be a short introduction and/ or overview that explains **what** the project is. This description should match descriptions added for package managers (Gemspec, package.json, etc.)

## Code Example

Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

## Motivation

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## Installation

Provide code examples and explanations of how to get the project.

## API Reference

Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## Tests

Describe and show how to run the tests with code examples.

## Contributors

Let people know how they can dive into the project, include important links to things like issue trackers, irc, twitter accounts if applicable.

## License

A short snippet describing the license (MIT, Apache, etc.)
=======
## Project ##

This project is using Angular2 as the main MVC framework, and typescript as a result.

To run the project it is necessary to install node-js, npm and angular-cli tools. It is then necessary to do a ```npm install``` on the project to prepare the remaining dependencies.

The project is divided in two main branches:

- main (version running in production deployments)
- dev (version running in staging phase for testing)
and several sandbox branches used for personal development, usually branching from the dev branch.


## Organization ##

The Frontend project is structured as a regular Angular2 project, with each component having it's own folder inside the app/ folder.
Services are contained inside _services, and all widgets are contained inside the widget folder. A more detailed explanation of each element is provided below:

```html
\ cmx-cloud
 - app/ : contains the projects source
   - _config/ : the different configuration files for prod, dev, staging and so on
   - _directives/ : 
   - _guards/ : guards to apply on requests (i.e. authentication guard to check for properly authenticated requests)
   - _helpers/ : util classes for testing
   - _models/ : domain model information for the project
   - _services/ : singleton classes that are injectable in every component to provide global functionality in an encapsulated way. Communication with the backend is done in services
   - apps/ : definition of the main component
   - dashboard/ : main dashboard component
   - header/ : definition of the header menu
   - login/ : login landing page
   - notifications/ : notifications drop down of the header menu
   - profile/ :
   - register/ : 
   - reporting/ : reporting widget dashboard
   - settings/ :
   - site/ : 
   - sites/ : definition of the component that handles site selection in the header menu
   - wifi/ : 
   - widget/ : contains each of the widget components, the definition for the generic widget component, widget interface and the widget loading service
   - app.component.* : definition of the application entry component
   - app.module.ts : definition of the application module (what components are used to create the project and so on)
   - app.routing.ts : definition of the application's routing strategy
 - template/ : contains the html/js/images necessary for the project's html template
   - (...)
 - app.css : contains the bulk of the application's custom css
 - bs-config.json : configuration file for the BrowserSync plugin
 - index.html : contains the entry page (html) of the Angular2 project
 - package.json : project definition (dependencies,...)
 - tsconfig.json : typescript compilation options
```

## Widgets ##

The widget system has 4 main elements: the widget board, widget interface, widget component and widget loading service.

To create a new widget, it is necessary to create a component that implements the widget (IWidget) interface, which forces the component to implement the required methods of the Widget lifecycle. The created component should not implement common behaviour & feel of the widgets.

 The new component implementing the Widget interface can then be displayed in a generic Widget Component. For the new component to be recognized it must be added to the app module and then to the switch case statement of the Widget Component (for class reflection purposes). 

 The widget board component allows the display of an arbitrary number of components in a grid structure just by managing a list of components, allowing for the dynamic configuration of the displayed widgets. This is made possible by loading new widgets using the widget loader service. Several examples of widgets can be found inside the widget folder

# Backend communication ##

 The frontend communicates with the backend in two distinct ways:

 - Asynchronously: in the majority of the cases the frontend intends to request a finite amount of information and then wait for the server's response, finally displaying it. These requests are handled by using a RESTlike API provided by the backend. To fetch aditional information it is necessary to create a new Controller method on the backend and extend the appropriate service in the Frontend to prepare for the request.
 - Synchronously: in some cases such as the MAC tracer the frontend expects a continuous flow of information that is not suited for a RESTlike interface. In these cases the frontend and backend can be made to communicate using the WebSockets protocol for synchronous message delivery of arbritry size. At the moment only the MAC tracer uses this kind of communication, and can be seen as an example.

 Any kind of database storage mechanism is hidden from the Frontend, for which there is no difference in a request that is fetched from CMX or the Backend's Database.

## Cache ##

 The Frontend has a local cache:

 - Images are usually automatically cached by the browser when fetched froms static locations. Since our images are loaded from the backend in JSON responses dynamically they are not automatically cached by the browser, greatly increasing page load times. 

 With this in mind, the Frontend now has a Local Cache that uses the Local Storage mechanism available in browser's to keep a few requests/responses cached, either indefinetely or for a finite amount of time.

 The local cache can be accessed through the Local Cache Service, and store any kind of data that can be transformed into a string representation. If an expiration Date() object is provided, it is also stored.

 The cache has a simple get(key), put(key, value [, expires=False, expiration_date]) interface.
