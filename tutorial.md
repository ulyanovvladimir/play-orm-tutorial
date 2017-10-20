# Инструкции

См. документацию по Playframework https://www.playframework.com/documentation/latest/Home для подробной информации.

## Подключение базы данных 
В build.sbt добавьте следующие строки

```scala
libraryDependencies += javaJdbc
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
```

Первая строка подключает драйвер JDBC. Вторая строка подключает необходимый драйвер СУБД H2. 

В файле conf/application.conf раскомментируйте следующие строки:
```
db {
  # https://www.playframework.com/documentation/latest/Developing-with-the-H2-Database
  default.driver = org.h2.Driver
  default.url = "jdbc:h2:mem:play"
  default.username = sa
  default.password = ""
}

```
Решетка # - символ комментария. Здесь мы подключаем драйвер и прописываем настройки для БД с именем default.

# Ebean
В build.sbt добавьте плагин PlayEbean

```scala
lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
```
В project/plugins.sbt добавьте плагин

```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.0.2")
```
это даст инструкцию сборщику разрешить зависимости и скачать необходимые библиотеки и подключить их к проекту.

## Зависимости
Зайдите в консоль sbt 

```bash
sbt
```
и введите команды
```sbtshell
update
compile
```
Первая команда обновит зависимости. Вторая команда компилирует. После этого у вас будут в проекте необходимые библиотеки. 

# Модели и настройка ORM
Создайте пакет models  и в нем создайте класс Feature
```java
import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Feature extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String title;

    public String description;

    public static final Finder<Long, Feature> find = new Finder<>(Feature.class);
}

```
Для того чтобы указать ORM, что мы хотим все наши классы из пакета models отображать в таблицы БД H2 с помощью Ebean, 
укажем это в конфигурации.

В файле conf/application.conf добавьте следующие строки:
```properties
ebean.default = ["models.*"]
```

Теперь можно запустить проект. При этом вам предложат создать БД, если она создается впервые.

## Запуск
Запуск с помощью [sbt](http://www.scala-sbt.org/).  

```
sbt run
```

В компьютерных классах ИГУ, используйте файл с настройками прокси-сервера для запуска

```
sbt-isu run
``` 

И затем откройте http://localhost:9000 в браузере.

