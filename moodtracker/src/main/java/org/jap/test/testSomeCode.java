package org.jap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
    Created by Peter
*/

/**
 *
 */
public class testSomeCode {
    private static final Logger log = LogManager.getLogger(testSomeCode.class);
    
    public static void main(String[] args) {
//        streamTest01();
        streamTest02();
//        new GFG();
    }
    
    private static void streamTest01() {
        // Get the stream
        Stream<String> stream
                = Stream.of("Geeks", "For",
                "Geeks", "A",
                "Computer",
                "Portal");
        
        // Print the stream
        // using double colon operator
        stream.forEach(log::info);
    }
    
    private static void streamTest02() {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");
    
        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }
    
    static class Test {
        String str = null;
    
        Test(String s) {
            this.str = s;
        }
    
        // instance function to be called
        void someFunction() {
            log.info(this.str);
        }
    }
    
    static class GFG {
        
        GFG() {
            
            List<Test> list = new ArrayList<Test>();
            list.add(new Test("Geeks"));
            list.add(new Test("For"));
            list.add(new Test("GEEKS"));
            
            // call the instance method
            // using double colon operator
            list.forEach(Test::someFunction);
        }
    }
}
