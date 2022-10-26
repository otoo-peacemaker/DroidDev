# Android Pagination Library

## Usage
- One of the most common ways of displaying information to users is with lists. 
However, sometimes these lists offer just a small window into all the content available to the user. 
As the user scrolls through the information that is available, there is often the expectation that more data is fetched to supplement the information that has already been seen. 
Each time data is fetched, it needs to be efficient and seamless so that incremental loads don't detract from the user experience. 
- Incremental loads also offer a **performance benefit**, as the app **does not need to hold large amounts of data in memory** at once.

- This process of fetching information incrementally is called --pagination--, where each page corresponds to a chunk of data to be fetched. 
- To request a page, the data source being paged through often requires a query which defines the information required.

## Importance of pagination
- Performance benefit in memory usage[in-memory cache]: The incremental loads does not need to hold large amount of data in memory.
- User experience enhancement: The supplemented data fetched does not distract user experience.
- Requests data when the user is close to the end of the list.

## Core component of the Paging Library
PagingSource 
- the base class for loading chunks of data for a specific page query. 
- PagingSource defines the source of data by specifying how to retrieve data in incremental chunks
- It is part of the **data layer**, and is typically exposed from a **DataSource** class and subsequently by the **Repository** for use in the ViewModel.

PagingConfig 
- a class that defines the parameters that determine paging behavior. 
- This includes **page size**, whether placeholders are enabled, and so on.

Pager 
- a class responsible for producing the PagingData stream. 
- It depends on the PagingSource to do this and should be --created-- in the ViewModel.

PagingData [object class that models the type of data we want to return]
- a container for paginated data. 
- Each refresh of data will have a separate corresponding PagingData emission backed by its own PagingSource.

PagingDataAdapter -
- a RecyclerView.Adapter subclass that presents PagingData in a RecyclerView. 
- The PagingDataAdapter can be connected to a Kotlin Flow, a LiveData, an RxJava Flowable, an RxJava Observable, or even a static list using factory methods. 
- The PagingDataAdapter listens to internal PagingData loading events and efficiently updates the UI as pages are loaded.

## Conditions for defining the source of data when implementing pagination[Using PagingSource]
- Properly handling requests for the data from the UI, ensuring that multiple requests aren't triggered at the same time for the same query.
- Keeping a manageable amount of retrieved data in memory.
- Triggering requests to fetch more data to supplement the data we've already fetched. 
- We can achieve all this with a PagingSource. 
  - A PagingSource defines the source of data by specifying how to retrieve data in incremental chunks.
  - The PagingData object then pulls data from the PagingSource in response to loading hints that are generated as the user scrolls in a RecyclerView
  example:
```
  // the paging source model
  data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val created: LocalDateTime,
  )
```
  ### Args of PagingSource.
- The type of the **paging key** - The definition of the type of the page query we use to request more data. 
   In our case, we fetch articles after or before a certain article ID since the IDs are guaranteed to be ordered and increasing.

- The type of data loaded - Each page returns a List of articles, so the type is Article.
- Where the data is retrieved from - Typically, this would be a database, network resource, or any other source of paginated data.

  ### Implementing PagingSource
- adding dependencies
```
 // architecture components
    implementation libs.androidx.activity.ktx
    implementation libs.androidx.core.ktx
    implementation libs.androidx.lifecycle.runtime.ktx
    implementation libs.androidx.lifecycle.viewModel.ktx
    implementation libs.androidx.paging.common.ktx
    implementation libs.androidx.paging.runtime.ktx

```
- Creating Article paging source[create an ArticlePagingSource.kt]
```
package com.example.android.codelabs.paging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

//date created by the article
private val firstArticleCreatedTime = LocalDateTime.now()

class ArticlePagingSource : PagingSource<Int, Article>() { 
/**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        TODO("Not yet implemented")
    }
   override fun getRefreshKey(state: PagingState<Int, Article>): Int? { 
        TODO("Not yet implemented")
    }
}

```
- load() and getRefreshKey() functions.
- The load() function will be called by the Paging library to asynchronously fetch more data to be displayed as the user scrolls around.
  - The **LoadParams** object keeps information related to the load operation, including the following:
    - **Key** of the page to be loaded first - If this is the first time that load() is called, LoadParams.key will be null. In this case, you will have to define the initial page key.
    - **Load size** - the requested number of items to load.
      
  - LoadResult
    - LoadResult.Page, if the result was successful.
      - data: A List of the items fetched.
      - prevKey: The key used by the load() method if it needs to fetch items behind the current page.
      - nextKey: The key used by the load() method if it needs to fetch items after the current page.
      - itemsBefore(optional): The number of placeholders to show before the loaded data.
      - itemsAfter(optional) : The number of placeholders to show after the loaded data
        
    - LoadResult.Error, in case of error.
      - 
    - LoadResult.Invalid, if the PagingSource should be invalidated because it can no longer guarantee the integrity of its results.
      - Full implementation of the load() function:
```
private val firstArticleCreatedTime = LocalDateTime.now()

class ArticlePagingSource : PagingSource<Int, Article>() {
   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        // Start paging with the STARTING_KEY if this is the first load()
        val start = params.key ?: STARTING_KEY
        // Load as many items as hinted by params.loadSize
        val range = start.until(start + params.loadSize)//load size

        return LoadResult.Page(
            data = range.map { number ->
                Article(
                    // Generate consecutive increasing numbers as the article id
                    id = number,
                    title = "Article $number",
                    description = "This describes article $number",
                    created = firstArticleCreatedTime.minusDays(number.toLong())
                )
            },
           
            // Make sure we don't try to load items behind the STARTING_KEY
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1// support loading infinite items
        )
    }

    ...
}

```
- getRefreshKey()[performs invalidation]: This method is called when the Paging library needs to reload items for the UI because the data in its backing PagingSource has changed and needs to be updated in the UI.
    - When invalidated, the Paging Library creates a new **PagingSource** to reload the data, and informs the UI by emitting new **PagingData**. Invalidation in the paging library occurs for one of two reasons:
        - You called refresh() on the PagingAdapter.
        - You called invalidate() on the PagingSource.
  
        - To prevent items from jumping around after invalidation, we need to make sure the key returned will load enough items to fill the screen.
        - This increases the possibility that the new set of items includes items that were present in the invalidated data, which helps maintain the current scroll position.
    - When loading[load from the last known key] from a new **PagingSource**, **getRefreshKey()** is called to provide the key the new PagingSource should start loading with to make sure the user does not lose their current place in the list after the refresh.
    - PagingState.anchorPosition: This is how paging library knows to fetch more items:
      - When the UI tries to read items from PagingData, it tries to read at a certain index.
      - If data was read, then that data is displayed in the UI.
      - If there is no data, however, then the paging library knows it needs to fetch data to fulfill the failed read request. 
      - The last index that successfully fetched data when read is the __anchorPosition_.
- Full implementation of the getRefreshKey() function:
```
private val firstArticleCreatedTime = LocalDateTime.now()

class ArticlePagingSource : PagingSource<Int, Article>() {
   // The refresh key is used for the initial load of the next PagingSource, after invalidation
   override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }
}

```

##  Produce PagingData for the UI[Should be inside the repository]
### PagingData Parameters
- PagingConfig. This class sets options regarding how to load content from a PagingSource such: 
  - as how far ahead to load, 
  - the size request for the initial load, and others. 
- The only required parameter you have to define is the **page size**:
  - how many items should be loaded in each page. 
  - By default, Paging will keep all of the pages you load in memory. 
- To ensure that you're not wasting memory as the user scrolls, 
  - set the maxSize parameter in PagingConfig. 
  - By default, Paging will return null items as a placeholder for content that is not yet loaded if Paging can count the unloaded items and if the enablePlaceholders config flag is true. 
  - That way, you will be able to display a placeholder view in your adapter. 
  - To simplify the work, let's disable the placeholders by passing enablePlaceholders = false.
- A function that defines how to create the PagingSource. 
  - In our case, we'll be creating an ArticlePagingSource, so we need a function that tells the Paging library how to do that.

### Pager: Parameters[Should be inside the viewmodel]
- A PagingConfig with a pageSize of ITEMS_PER_PAGE and placeholders disabled
- A PagingSourceFactory that provides an instance of the ArticlePagingSource we just created inside the repository
- Note:
  - to maintain paging state through configuration or navigation changes, we use the **cachedIn()** method by passing it viewModelScope as an argument.
  - if you're doing any operations on the Flow, like map or filter, make sure you call cachedIn after you execute these operations to ensure you don't need to trigger them again. 
  - In other words, cachedIn should be used as a terminal operator.
  - Do not use the stateIn() or sharedIn() operators with PagingData Flows as PagingData Flows are not cold.
  - Do not mix or combine the PagingData Flow with other Flows, as each emission of PagingData should be consumed independently.
```
private const val ITEMS_PER_PAGE = 50

class ArticleViewModel(
    private val repository: ArticleRepository,
) : ViewModel() {

    val items: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.articlePagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}

```
### Display load states in the UI
- When the Paging library is fetching more items to display in the UI, it is best practice to indicate to the user that more data is on the way. 
Fortunately, the Paging library offers a convenient way to access its loading status with the **CombinedLoadStates** type.
**CombinedLoadStates*** instances describe the loading status of all components in the Paging library that load data. 
In our case, we're interested in the **LoadState** of just the ArticlePagingSource, so we will be working primarily with the LoadStates type in the CombinedLoadStates.source field. 
You can access CombinedLoadStates through the PagingDataAdapter via PagingDataAdapter.loadStateFlow
- There are 3 distinct classes that help describe loading states: 
  - CombinedLoadStates, LoadStates and LoadState. 
  - CombinedLoadStates houses LoadStates instances, and LoadStates provides LoadState instances :)
  - CombinedLoadStates.source is a LoadStates type, with fields for three different types of LoadState:
    - LoadStates.append: For the LoadState of items being fetched after the user's current position. 
    - LoadStates.prepend: For the LoadState of items being fetched before the user's current position. 
    - LoadStates.refresh: For the LoadState of the initial load. 
    
  - Each LoadState itself can be one of the following:
    - LoadState.Loading: Items are being loaded.
    - LoadState.NotLoading: Items are not being loaded.
    - LoadState.Error: There was a loading error

References: [android colabs](https://developer.android.com/codelabs/android-paging-basics#0)
