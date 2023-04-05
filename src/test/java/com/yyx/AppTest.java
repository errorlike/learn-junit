package com.yyx;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

class AppTest {

    private final Calculator calculator = new Calculator();
    Person person = new Person("F", "feee");

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }

    @Test
    void firstNameStartWithF() {
        assertTrue(person.getFirstName().startsWith("F"));
    }

    @Test
    void personHasName() {
        assertNotNull(person.getFirstName());
    }

    @Test
    void IterableEqual() {
        List<String> listA = Arrays.asList("A", "B", "C");
        List<String> listB = Arrays.asList("B", "A", "C");
        Collections.sort(listB);
        assertIterableEquals(listA, listB);
        // assertEquals(List.of("a","b"),List.of("a"));
        assertIterableEquals(List.of(), List.of());
        assertIterableEquals(null, null);
    }

    @Test
    void arraysEqual() {
        int[] a1 = { 1, 2, 3 };
        int[] a2 = { 1, 2, 3 };
        assertArrayEquals(a1, a2);
    }

    @Test
    void personAreSame() {
        Person john = new Person("John", "Doe");
        Person doe = new Person("John", "Doe");
        assertEquals(john, doe);
    }

    @Test
    void personsAreNotSameInstance() {
        Person john = new Person("John", "Doe");
        Person doe = new Person("John", "Doe");
        assertNotSame(john, doe);
    }

    @Test
    void divideByZeroThrowsIllegalArgumentException() {
        Throwable thrown = assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
        assertEquals("/ by zero", thrown.getMessage());
    }

    @Test
    void returnValueBeforeTimeoutExceeded() {
        var message = assertTimeout(Duration.ofMillis(50L), () -> {
            Thread.sleep(200L);
            return "message";
        });
        assertEquals("message", message);
    }

    @Test
    void abortWhenTimeoutExceeded() {
        var message = assertTimeoutPreemptively(Duration.ofMillis(50L), () -> {
            Thread.sleep(10L);
            return "another message";
        });
        assertEquals("another message", message);
    }

    @Test
    void addNumbers() {
        assertEquals(3, calculator.add(2, 2), "1+2 should equal 3");
    }

    @Test
    void addingEmployeesToPersonnel() {
        Person employee = new Person("test", "name");
        HashSet<Person> personnel = new HashSet<>();
        assertTrue(personnel.contains(employee), () -> {
            return String.format("Personnel file for %s was not found", employee);
        });
    }

    @Test
    void firstNameAndSecondNameMatchs() {
        Person person = new Person("John", "Doe");
        assertAll("person",
                () -> {
                    assertEquals("haha", person.getFirstName());
                },
                () -> {
                    assertEquals("sdf", person.getSecondName());
                },
                ()->{
                    assertEquals("John", person.getFirstName());
                }
                );
    }
}