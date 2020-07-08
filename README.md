# DecadeOfMovies

<a href="https://codebeat.co/projects/github-com-mohnage7-decadeofmovies-master"><img alt="codebeat badge" src="https://codebeat.co/badges/73447a9e-6690-4887-bd2f-ec8469cd38f4" /></a>

## Preview 

<img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/movies_listing.png"  width="300" height="622" /> <img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/details_screen.png"   width="300" height="622" />

<img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/search.png"   width="300" height="622" /><img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/search_listing.png"  width="300" height="622" />

<img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/tablet.png"  width="640" height="400" />


## The Challenge
The past decade held a lot of movies, some left a mark and some were just a set of 24-60
pictures per second. We would like you to create a Master - Detail Application to showcase
those movies and the signature they left behind.

You will have a local list of movies that should be displayed in any order. The list is searchable
and the search results will be categorized by Year.

Each search result category will hold at most the top rated 5 movies of this category (year).

Once a movie is selected from the search results, you will switch to a detailed view to unveil the
following:
- Movie Title
- Movie Year
- Movie Genres (if any)
- Movie Cast (if any)
- A two column list of pictures fetched from flickr that matches the movie title as the search
query

## Project Overview

* Fetch Movies list from .json file and display them in Master-Detail flow.
* Show every movie details in separate screen 
* Cache data offline for the movie's photos. 
* Search for movies by its title.
* Search data is categorized by year and it shows only top 5 movies in that category.


## Project Architecture 
The project is following [clean architucte](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) , It’s a group of practices and decisions that makes the code testable with independable components.

The following diagram shows how all the modules will interact with one another.

<img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/clean_arch_diagram.png"  width=600 height=690  />


### Dependency Flow
As illustrated in the image above each component depends only on the component below it. the higher layers will request the needed data from the layers below it
and the data is being provided by the lower layers by a reactive paradigm. 

#### Dependency Injection 
Allows classes to define their dependencies without constructing them. 
At runtime, another class is responsible for providing these dependencies
* For DI we are using Koin 


### Presentation layer
The layer that interacts with the UI. for this layer we are applying MVVM architecture pattern

### Framework layer 
Implements interaction with the Android SDK and provides concrete implementations for the data layer (Room implementation). 

### UseCase layer 
Sometimes called interactors. Defines actions the user can trigger

### Data Layer
Abstract definition of all the data sources. (Network / Local )

### Network layer
Contain abstract and concrete implementation for any logic that is related to network calls. 

### Local Layer
Abstract definition for the local data persistence.

### Domain Layer
Contains business logic and entities. 

## How to Run it
* The project is only dependant on flicker api. You can create a key from [here](https://www.flickr.com/services/api/misc.api_keys.html)
* Once you have created the key insert it build.gradle(Module: pp) for both debug and release variants in API_KEY field. 


## Libraries 
* [Coroutine](https://github.com/Kotlin/kotlinx.coroutines) for threading
* [RxJava/RxAndroid](https://github.com/ReactiveX/RxAndroid) for reactive programming
* [Retrofit](https://square.github.io/retrofit/) for consuming REST APIs
* [Koin](https://insert-koin.io/) for dependency injection 
* [Glide](https://github.com/bumptech/glide) for loading images from remote servers
* [Facebook Shimmer](https://github.com/facebook/shimmer-android) library as loading animation
* [Room](https://developer.android.com/topic/libraries/architecture/room) as presistance library. 
* [Gson](https://github.com/google/gson) as data parser.
