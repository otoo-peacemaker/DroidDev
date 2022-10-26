# Android pagination.
The Paging library makes it easier for you to load data incrementally and gracefully within your app's UI. The Paging API provides support for many of the functionalities that you would otherwise need to implement manually when you need to load data in pages.
## Benefits
- Keeps track of the keys to be used for retrieving the next and previous page.
- Automatically requests the correct page when the user has scrolled to the end of the list.
- Ensures that multiple requests aren't triggered at the same time.
- Allows you to cache data: if you're using Kotlin, this is done in a CoroutineScope; if you're using Java, this can be done with LiveData.
- Tracks loading state and allows you to display it in a RecyclerView list item or elsewhere in your UI, and easily retry failed loads.
- Allows you to execute common operations like map or filter on the list that will be displayed, independently of whether you're using Flow, LiveData, or RxJava Flowable or Observable.
- Provides an easy way of implementing list separators.
