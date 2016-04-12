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


##### How to use Cache (In progress)
CacheProvider provider = CacheProvider.getCachingProvider();
CacheManager cacheManager = provider.getCacheManager("StaticCacheManager");
Cache cache = cacheManager.getCache("default");
CachePojo myPojo = (CachePojo) cache.get("myKey");


