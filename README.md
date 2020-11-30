# SampleApp

Features Implemented
- Display list of restaurants sorted by if that restaurant is favorite, its status(Open, Order ahead, Close) and the type of sort selection selected by user.
- Add or remove favorite restaurants
- Search for restaurant using restaurant name

# Assumptions while sorting restaurant list
- Restaurant added to favorite is always on top(sorted by status open, order ahead and close) if there are favorites from different statuses
- Restaurants are always sorted by status ie open followed by order ahead followed bu closed restaurants(this sort always applicable irrespective of the independent sort type)
- Rating average and Popularity of restaurants are always in descending order(restaurant with average rating/popularity higher are shown before restaurants with lower values)
- Rest all filters i assumed should follow ascending order(Distance, Average Product Price, Minimim Cost, Delivery Cost, Newest, Best Match)

# Unit Test Cases
Unit test cases are included for the following:
- ViewModel (RestaurantsViewModelTest)
- UseCases (GetRestaurantsUseCaseTest, FilterRestaurantsUseCaseTest, AddToFavoritesUseCaseTest)
- Repository (RestaurantsRepositoryImplTest): Covers test cases for different sort types
- Data Models to Domain Mappers (RestaurantTest, SortingValuesTest) covers test for mapping from data to domain models
- DomainModel tests cases(RestaurantDomainModelTest)

# Running application and Unit test cases
- Repository can be forked or code can be dowloaded as zip and Android studio should be used to run the app and unit test cases

# Project Characteristics 
- 100% Kotlin<br />
- Architecture (Clean Architecture, MVVM)<br />
- Dependency Injection(Using HILT)<br />
- Coroutines<br />
- Kotlin Flow<br />
- Architecture Components(LiveData, ViewModel, Room)<br />
- GSON for parsing<br />
- Navigation<br />
- Extension Functions
- Unit Testing(Junit, Mockito)<br />

# App Architecture Details:
![app_architecture](https://user-images.githubusercontent.com/16702310/100535703-446bf980-31e9-11eb-8c17-ffb6982e2cd5.png)
 # View
 This layer mainly deals with the UI of the application.
 
 Components
 - **Activity/Fragment**: Presents data on the screen and pass user interaction to viewmodel
 - **ViewModel**: Executes use cases based on user interaction and updates ui using LiveData
 
 # Domain
 Contains all the business logic for the application.Domain layers is independent of other layers, has its own models, so that changes in other layers will have no effect on domain layer.
 
 Components
 - **UseCase** : Handles business logic
 - **Domain Models**: Represents structure of response data
 - **Repository Interface**: To keep domain layer independent from data layer.
 
 # Data
 Manages application data and exposes data to domain layer
 
 components
 - **Repository** : Exposes data to domain layer. For current project as no live service is used this layers uses a local API parser to read and parse json from assets and map it to response object.
 - **Mapper** : Used to map data models to domains models to keep domain independent of data layer.
 - **Database** : Used for saving local data (in current project favorite restaurants)
 - **DataModel** : Structure for data retrieved from remote/local data source. 
 
