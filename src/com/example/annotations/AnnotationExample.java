package com.example.annotations;

@TypeAnnotation(
        priority = TypeAnnotation.Priority.HIGH,
        author = "47",
        tags = {"java", "com/example/annotations"}
)
public class AnnotationExample {

    @MethodAnnotation
    String shouldAlwaysBeProcessed() {
        return "This string should always be processed";
    }

    @MethodAnnotation
    void willThrowException() {
        throw new RuntimeException("This will throw Exception");
    }

    @MethodAnnotation(enabled = false)
    void shouldNotBeProcessed() {
        throw new RuntimeException("This should never be processed");
    }
}
