# dataflow-core

Core library for building composable and extensible data transformation pipelines.

## Features

- Type-safe, composable pipeline stages
- Fluent builder API for pipeline construction
- Detailed execution results with timing information
- Lightweight with minimal dependencies

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Build

```bash
mvn clean install
```

## Usage

```java
Pipeline<String, Integer> pipeline = Pipeline.from(String.class)
    .addStage("parse", Integer::parseInt)
    .addStage("double", n -> n * 2)
    .build();

int result = pipeline.execute("21");
```

## License

This project is licensed under the [MIT License](LICENSE).
