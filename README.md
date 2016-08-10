#Pi Test

[![Build Status](https://travis-ci.org/DVentas/piTest.svg?branch=master)](https://travis-ci.org/DVentas/piTest)
[![Coverage Status](https://coveralls.io/repos/github/DVentas/piTest/badge.svg?branch=master)](https://coveralls.io/github/DVentas/piTest?branch=master)


How to use
----------

0. System requirements
------------------------------------------------------------

- Java 8
- Maven 3

1. Setup your verticle.cfg (or use this one as a default)
------------------------------------------------------------

```Text

{
    "server": {
        "host": "localhost",
        "port": 8080,
        "base_path": "/",
        "resources": ["org.daniel.ventas"]
    },
    "key.stringArray" : "stringsToProcess"
}

```

2. Compile project
------------------------------------------------------------

```Text
  mvn package
```

3. Deploy server
------------------------------------------------------------

```Text
  java -jar target/interview-1.0-SNAPSHOT-fat.jar --conf src/main/resources/verticle.cfg
```

4. Test API
------------------------------------------------------------
### Example 1

```Text
  curl -H "Content-Type: application/json" -X POST -d '{"stringsToProcess":["stringtotest", "otherstringrandom"]}' http://localhost:8080/api/mix
```

Should return:
```Text
  {
    "result" : "1:tttt/2:rrr/2:nn/2:oo/1:ss"
  }
```

### Example 2

```Text
  curl -H "Content-Type: application/json" -X POST -d '{"stringsToProcess":["firstelementiiiiiillllll", "2 12213(( ^ seeeecondAYI32  ++ - element", "third&elementoooooo"]}' http://localhost:8080/api/mix
```

Should return:
```Text
  {
    "result" : "2:eeeeeee/1:iiiiiii/1:lllllll/3:oooooo/2:nn/1,3:tt"
  }
```


