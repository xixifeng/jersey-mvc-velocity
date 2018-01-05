## Jersey Velocity MVC Templates

```java
// 注册扫描properties
register(new CdiBinder());

// 防范XSS恶意攻击
register(XSSFilter.class);

// 注册Velocity模板引擎
register(VelocityTemplateProcessor.class);
register(MvcFeature.class);
```

