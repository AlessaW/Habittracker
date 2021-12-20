package org.jap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
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
//        streamTest02();
//        new GFG();
//        new Stresstest(10000000);
//        simpleStresstest(100000000);
    }
    
    private static void simpleStresstest(int i) {
        long ts1;
        long ts2;
        
        System.out.println("Running...");
    
        ts1 = System.nanoTime();
        IntStream rangeS1 = IntStream.rangeClosed(1, i);
        rangeS1
                .filter(a->a%10>0)
                .filter(a->a%11>0)
                .filter(a->a%12>0)
                .filter(a->a%13>0)
                .filter(a->a%14>0)
                .filter(a->a%15>0)
                .filter(a->a%16>0)
                .filter(a->a%17>0)
                .filter(a->a%18>0)
                .filter(a->a%19>0)
                .filter(a->a%20>0)
                .filter(a->a%21>0)
                .filter(a->a%22>0)
                .filter(a->a%23>0)
                .filter(a->a%24>0)
                .filter(a->a%25>0)
                .filter(a->a%26>0)
                .filter(a->a%27>0)
                .filter(a->a%28>0)
                .filter(a->a%29>0)
                .forEach(a->a++);
        ts2 = System.nanoTime();
        long s1 = (ts2-ts1);
        System.out.println("Serial1:     " + s1 + " ns");
        
        ts1 = System.nanoTime();
        IntStream rangeS2 = IntStream.rangeClosed(1, i);
        rangeS2
                .filter(a->a%29>0)
                .filter(a->a%28>0)
                .filter(a->a%27>0)
                .filter(a->a%26>0)
                .filter(a->a%25>0)
                .filter(a->a%24>0)
                .filter(a->a%23>0)
                .filter(a->a%22>0)
                .filter(a->a%21>0)
                .filter(a->a%20>0)
                .filter(a->a%19>0)
                .filter(a->a%18>0)
                .filter(a->a%17>0)
                .filter(a->a%16>0)
                .filter(a->a%15>0)
                .filter(a->a%14>0)
                .filter(a->a%13>0)
                .filter(a->a%12>0)
                .filter(a->a%11>0)
                .filter(a->a%10>0)
                .forEach(a->a++);
        ts2 = System.nanoTime();
        long s2 = (ts2-ts1);
        System.out.println("Serial2:     " + s2 + " ns");
        
        ts1 = System.nanoTime();
        IntStream rangeP = IntStream.rangeClosed(1, i);
        rangeP.parallel()
                .filter(a->a%10>0)
                .filter(a->a%11>0)
                .filter(a->a%12>0)
                .filter(a->a%13>0)
                .filter(a->a%14>0)
                .filter(a->a%15>0)
                .filter(a->a%16>0)
                .filter(a->a%17>0)
                .filter(a->a%18>0)
                .filter(a->a%19>0)
                .filter(a->a%20>0)
                .filter(a->a%21>0)
                .filter(a->a%22>0)
                .filter(a->a%23>0)
                .filter(a->a%24>0)
                .filter(a->a%25>0)
                .filter(a->a%26>0)
                .filter(a->a%27>0)
                .filter(a->a%28>0)
                .filter(a->a%29>0)
                .forEach(a->a++);
        ts2 = System.nanoTime();
        long p = (ts2-ts1);
        System.out.println("Parallel:    " + p + " ns");
        if (s1<s2)
            System.out.println("S1 is " + (double)s2/s1 + " times faster than s2");
        else
            System.out.println("S2 is " + (double)s1/s2 + " times faster than s1");
        if (p<s1)
            System.out.println("P is " + (double)s1/p + " times faster than s1");
        else
            System.out.println("S1 is " + (double)p/s1 + " times faster than p");
        if (p<s2)
            System.out.println("P is " + (double)s2/p + " times faster than s2");
        else
            System.out.println("S2 is " + (double)p/s2 + " times faster than p");
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
