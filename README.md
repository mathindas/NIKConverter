# NIK Converter Library (FULL OFFLINE)

Welcome to the NIK Converter Library, your go-to solution for parsing Indonesian National Identification Numbers (NIK) and extracting insightful data from them!

## Overview

The NIK Converter Library allows you to effortlessly convert NIK strings into structured `NIKData` objects. With this library, you can extract valuable information such as province, city, district, gender, birth date, age, zodiac sign, and more from Indonesian NIKs.

## Features

- **NIK Parsing**: Parse Indonesian National Identification Numbers (NIK) effortlessly.
- **Detailed Data Extraction**: Extract detailed information including province, city, district, gender, birth date, age, zodiac sign, and more from NIKs.
- **Customizable Translations**: Translate to Indonesian for localized data extraction.
- **Easy Integration**: Simple integration into your Android projects.

## Installation

To integrate the NIK Converter Library into your Android project, follow these steps:

1. Add the JitPack repository to your build file:

    ```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2. Add the dependency:

    ```gradle
    dependencies {
        implementation 'com.github.mathindas:nik-converter:1.0.0'
    }
    ```

## Usage

Here's a quick example of how to use the NIK Converter Library in your Android project:

## Examples

### Basic Conversion

```kotlin
val nikConverter = NIKConverter()
val nik = "your_nik_string_here"
val context = applicationContext // or any valid context

val nikData = nikConverter.convert(nik, context)

if (nikData != null) {
    // Access NIK data fields here
    println("Province: ${nikData.province}")
    println("City: ${nikData.city}")
    // Add more data fields as needed
} else {
    println("Invalid NIK.")
}
```

### Custom Translation

```kotlin
val nikConverter = NIKConverter()
val nik = "your_nik_string_here"
val context = applicationContext // or any valid context

val nikData = nikConverter.convert(nik, context, translateToId = true) // translate to indonesian

if (nikData != null) {
    // Access NIK data fields here with translated values
    println("Province: ${nikData.province}")
    println("City: ${nikData.city}")
    // Add more data fields as needed
} else {
    println("Invalid NIK.")
}
```

## Contribution

Contributions are welcome! If you encounter any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

## License

This library is available under the MIT License. See the [LICENSE](LICENSE) file for more information.
