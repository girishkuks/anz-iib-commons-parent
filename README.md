# anz-iib-commons

Core common framework components for IIB applications. Includes
- Cache Manager
- Tranformation Utils and interfaces
- IIB Java Compute Framework
- Sample data source for cache and sample cacheable pojo objects

### How to use this framework


Refer to https://github.com/sanketsw/JsonJsonApplication/tree/master/JsonJsonApplication

##### How to create Compute Node
For JSON to JSON Tranformation computes, 
- Set the Java compute class to com.anz.common.compute.impl.CommonJsonJsonTransformCompute
- Set the name of the Java Compute to your transformer class (Let's say com.anz.MyTransformer)
- Implement a class com.anz.MyTransformer) implements com.anz.common.transform.IJsonJsonTransformer. Put this in your <App-name>AppJava project


##### How to read from Cache
```
CachingProvider provider = Caching.getCachingProvider(JCacheCachingProvider.class.getName());
CacheManager cacheManager = provider.getCacheManager();
Cache<String, String> cache = cacheManager.getCache("MyDefaultMap");
json = cache.get(key);
```
Helper Method: `CachePojoSample op = CacheHandlerFactory.getInstance().lookupIIBCache("DefaultMap", objectKey, CachePojoSample.class);`

