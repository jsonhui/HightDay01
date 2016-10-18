package com.yuanke.liwushuo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        Set<Integer> intents = new HashSet<>();
        intents.add(1);
        intents.add(1);
        intents.add(1);
        intents.add(3);
        intents.add(2);
        for (int i :
                intents) {
            System.out.println(i);
        }
    }

    @Test
    public void test2() throws Exception {
        List<Integer> intents = new ArrayList<>();
        intents.add(1);
        intents.add(1);
        intents.add(1);
        intents.add(3);
        intents.add(2);
        intents.add(3);
        for (int i :
                intents) {
            System.out.println(i);
        }
    }
}