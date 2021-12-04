# Sketch [![Build](https://github.com/reugn/sketch/actions/workflows/build.yml/badge.svg)](https://github.com/reugn/sketch/actions/workflows/build.yml)

An image manipulation library for Kotlin.

`Sketch` doesn't require any external installation like OpenCV or OCR and can be used right away. It's actually a
zero-dependency library designed for simplicity that wraps the Java AWT BufferedImage. The library provides an
asynchronous interface for all image operations.

## Requirements

* Java 8+

## Installation

> The library is intended to be published to Maven Central.

In the meantime, it can be installed locally:

```sh
./gradlew publishToMavenLocal
```

Add the library as a dependency to your project:

```kotlin
dependencies {
    implementation("io.github.reugn:sketch:0.1.0")
}
```

## Features

Below is a list of the supported transformations/effects with the resulting image.  
Click the heading to get the test code that generated the example.

| Original Image |
|----------------|
|![dstImage](docs/images/hummingbird_original.jpg)|

### Transformations

| [Rotate][rotate] | [Resize][resize] | [Crop rectangle][crop] | [Crop circle][crop] |
|------------------|------------------|------------------------|---------------------|
|![dstImage](docs/images/hummingbird_rotate.png)|![dstImage](docs/images/hummingbird_resized.jpg)|![dstImage](docs/images/hummingbird_crop_rect.jpg)|![dstImage](docs/images/hummingbird_crop_circle.png)|

[rotate]: src/test/kotlin/io/github/reugn/sketch/SketchRotateTest.kt

[resize]: src/test/kotlin/io/github/reugn/sketch/SketchResizeTest.kt

[crop]: src/test/kotlin/io/github/reugn/sketch/SketchCropTest.kt

| [Border radius][border] | [Add border][border] | [Add border with radius][border] |
|-------------------------|----------------------|----------------------------------|
|![dstImage](docs/images/hummingbird_border_radius.png)|![dstImage](docs/images/hummingbird_border.png)|![dstImage](docs/images/hummingbird_border_with_radius.png)|

[border]: src/test/kotlin/io/github/reugn/sketch/SketchBorderTest.kt

| [Inline text][inline] | [Inline image][inline] | [Set pixels][pixels] |
|-----------------------|------------------------|----------------------|
|![dstImage](docs/images/hummingbird_inline_text.jpg)|![dstImage](docs/images/hummingbird_inline_image.jpg)|![dstImage](docs/images/hummingbird_set_pixels.jpg)|

[inline]: src/test/kotlin/io/github/reugn/sketch/SketchInlineTest.kt

[pixels]: src/test/kotlin/io/github/reugn/sketch/SketchSetPixelsTest.kt

### Effects

| [Brightness][filter] | [Contrast][filter] | [Opacity][filter] |
|--------------------------|----------------------|--------------------|
|![dstImage](docs/images/hummingbird_brightness_filter.jpg)|![dstImage](docs/images/hummingbird_contrast_filter.jpg)|![dstImage](docs/images/hummingbird_opacity_filter.jpg)|

| [Color Mask][filter]  | [Grayscale][filter]  | [Invert Colors][filter] |
|-----------------------|----------------------|-------------------------|
|![dstImage](docs/images/hummingbird_color_mask_filter.jpg)|![dstImage](docs/images/hummingbird_grayscale_filter.jpg)|![dstImage](docs/images/hummingbird_invert_filter.jpg)|

[filter]: src/test/kotlin/io/github/reugn/sketch/SketchFiltersTest.kt

| [Blur][blur] | [Blur partial][blur] |
|--------------|----------------------|
|![dstImage](docs/images/hummingbird_blur_full.jpg)|![dstImage](docs/images/hummingbird_blur_partial.jpg)|

[blur]: src/test/kotlin/io/github/reugn/sketch/SketchBlurTest.kt

| [Pixelate][pixelate] | [Pixelate partial][pixelate] |
|----------------------|------------------------------|
|![dstImage](docs/images/hummingbird_pixelate_full.jpg)|![dstImage](docs/images/hummingbird_pixelate_partial.jpg)|

[pixelate]: src/test/kotlin/io/github/reugn/sketch/SketchPixelateTest.kt

## License

Licensed under the [Apache 2.0 License](./LICENSE).
