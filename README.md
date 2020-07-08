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


### App architecture 
The following diagram shows how all the modules will interact with one another.

<img src="https://github.com/MohNage7/DecadeOfMovies/blob/master/screenshots/clean_arch_diagram.png"  width=600 height=690  />

Each component depends only on the component one level below it.
