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



