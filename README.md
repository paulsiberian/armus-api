# ARMUS API

Чтобы использовать API в своём проекте, в файле pom.xml подключите репозиторий:
```xml
<repositories>
	<repository>
		<id>paulsiberian-mvn-repo</id>
		<url>https://rowIndex.github.com/paulsiberian/armus-api/mvn-repo</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
```
И подключите зависимость:
```xml
<dependency>
	<groupId>io.github.paulsiberian.armus</groupId>
	<artifactId>armus-api</artifactId>
	<version>1.0.6</version>
</dependency>
```

[API Documentation](http://paulsiberian.me/armus-api/ "ARMUS API Documentation")
