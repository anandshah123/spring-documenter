spring-documenter
=================

Documents All spring annotations with their javadocs links

All the annotations within ```org.springframework``` package are scanned with reflection api.

```java
Reflections reflections = new Reflections("org.springframework");
		List<Class<?>> clazzes = new ArrayList<Class<?>>(
				reflections.getTypesAnnotatedWith(Documented.class));
```

Then we crawl bing (The simplest search engine to crawl.) using Jsoup api

```java
Document doc = Jsoup.connect(
					"http://www.bing.com/search?q="
							+ URLEncoder.encode(class1.getName(), "UTF-8"))
					.get();
			int ctr = 1;
			for (Element elem : doc.select("h2 a")) {
				ctr++;
				System.out.println(elem.attr("href"));
				out.append("\"" + class1.getSimpleName() + "\",\""
						+ class1.getName() + "\",\"" + elem.attr("href")
						+ "\"\n");
				if (ctr > 2)
					break;
			}
```

And finally we put everything to csv which looks something similar to

Class Simple Name  | Class Name | URL
------------- | ------------- | --------
AbstractAsyncConfiguration | org.springframework.scheduling.annotation.AbstractAsyncConfiguration | http://docs.spring.io/spring/docs/4.0.7.RELEASE/javadoc-api/org/springframework/scheduling/annotation/ProxyAsyncConfiguration.html

And we are done. Ready to learn all spring annotations with their use from documentation.
