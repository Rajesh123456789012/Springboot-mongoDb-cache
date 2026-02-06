**How to Verify Cache Is Working**
GET /users/123
1st call → MongoDB hit

2nd call → cache hit

Add a log inside the method to confirm
@CacheEvict is used to remove (invalidate) data from the cache.
You use it when data changes or is deleted, so stale data is not returned from cache.

**Why Do We Need It?**

Imagine this flow:

GET /users/1 → data cached

PUT /users/1 → DB updated

GET /users/1 → ❌ old data from cache

👉 This happens unless you evict the cache.


**Annotation	Purpose**
@Cacheable	Read & cache
@CachePut	Update cache with new value
@CacheEvict	Remove cache
