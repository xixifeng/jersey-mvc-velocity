## Maven

```xml
<dependency>
    <groupId>org.fastquery</groupId>
    <artifactId>jersey-mvc-velocity</artifactId>
    <version>1.1</version>
</dependency>
```

## Gradle/Grails

```xml
compile 'org.fastquery:jersey-mvc-velocity:1.1'
```

## Jersey Velocity MVC Templates

```java
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;

public class Application extends ResourceConfig {

	public Application() throws IOException {
		// 注册Velocity模板引擎
		register(org.fastquery.jersey.mvc.VelocityTemplateProcessor.class);
		register(MvcFeature.class);
	}
	
}
```



