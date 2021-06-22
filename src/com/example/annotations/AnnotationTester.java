package com.example.annotations;

import java.lang.reflect.Method;

public class AnnotationTester {

    static int success = 0, failed = 0, total = 0, disabled = 0;

    public static void main(String[] args) {
        Class<AnnotationExample> annotationExampleClass = AnnotationExample.class;

        if (!annotationExampleClass.isAnnotationPresent(TypeAnnotation.class)) {
            println("No class annotated with TypeAnnotation");
            return;
        }

        println(annotationExampleClass.getSimpleName() + " is annotated with TypeAnnotation");

        println("Process class-level annotations...");
        processClassLevelAnnotations(annotationExampleClass);
        println("");


        println("Process method-level annotations...");
        processMethodLevelAnnotations(annotationExampleClass);
    }

    private static void processClassLevelAnnotations(Class<AnnotationExample> annotationExampleClass) {
        TypeAnnotation custom = annotationExampleClass.getAnnotation(TypeAnnotation.class);

        println("Priority: " + custom.priority());
        println("Author: " + custom.author());
        print("Tags: ");
        for (String tag : custom.tags()) {
            print(tag + ", ");
        }
        println("\nLastModified: " + custom.lastModified());
    }

    private static void processMethodLevelAnnotations(Class<AnnotationExample> annotationExampleClass) {
        for (Method method : annotationExampleClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MethodAnnotation.class)) {
                total++;
                MethodAnnotation custom = method.getAnnotation(MethodAnnotation.class);

                if (!custom.enabled()) {
                    disabled++;
                    println(method.getName() + " rightfully NOT processed.\nReason: Enabled = " + false);
                    break;
                }

                try {
                    String result = (String) method.invoke(annotationExampleClass.newInstance());
                    success++;
                    println(method.getName() + " processed.\nResult: " + result);
                } catch (Throwable throwable) {
                    failed++;
                    println(method.getName() + " NOT processed.\nReason: " + throwable.getCause());
                }
            }
        }
        println("");
        println("\nResult: " + "\nTotal: " + total + "\nSucceeded: " + success + "\nFailed: " + failed + "\nDisabled: " + disabled);
    }


    static void println(String str) {
        print(str + "\n");
    }

    static void print(String str) {
        System.out.print(str);
    }
}
