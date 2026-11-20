### Hexlet tests and linter status:
[![Java CI](https://github.com/mxmrbkv/java-project-78/actions/workflows/build.yml/badge.svg)](https://github.com/mxmrbkv/java-project-78/actions)

# Валидатор данных (Data Validator)

Библиотека на Java для проверки корректности данных (валидации) с использованием паттерна Fluent Interface.

## Описание

«Валидатор данных» позволяет декларативно описывать правила валидации данных различных типов:
- Строки (`StringSchema`)
- Числа (`NumberSchema`)
- Структуры / Map (`MapSchema`)

## Требования

- OpenJDK 21
- Gradle не ниже 8.7

## Структура проекта

```text
├── README.md
└── app
    ├── build.gradle.kts
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradlew
    ├── gradlew.bat
    ├── settings.gradle.kts
    └── src
        ├── main
        │   ├── java
        │   └── resources
        └── test
            ├── java
            └── resources
```

## Команды

Все команды сборки выполняются из каталога `app` (или через `make` в корне):

- Сборка: `cd app && ./gradlew build`
- Запуск тестов: `cd app && ./gradlew test`
- Проверка линтерами: `cd app && ./gradlew check`
- Применение автоформатирования Spotless: `cd app && ./gradlew spotlessApply`
- Генерация отчета о покрытии: `cd app && ./gradlew jacocoTestReport`