package org.jap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

/*
    Created by Peter
*/

/**
 *
 */
public class Stresstest {
    private static final Logger log = LogManager.getLogger(Stresstest.class);
    
    
    // Constructor
    public Stresstest(int amount) {
        log.info("Stresstest running with "+amount+" Samples");
        
        List<CookieCalculator> cookies = cookieGenerator(amount);
        
        serialProcessing(new ArrayList<>(cookies));
        parallelProcessing(new ArrayList<>(cookies));
    }
    
    // Methods
    private List<CookieCalculator> cookieGenerator(int amount) {
        long ts1 = System.nanoTime();
        
        List<CookieCalculator> cookies = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            cookies.add(new CookieCalculator((int) (Math.random() * 10000), (int) (Math.random() * 10000), (int) (Math.random() * 10000), (int) (Math.random() * 10000)));
        }
        
        long ts2 = System.nanoTime();
        log.info("Generating: " + (ts2-ts1) + "ns");
        
        return cookies;
    }
    
    private void serialProcessing(List<CookieCalculator> cookies) {
        long ts1 = System.nanoTime();
    
        CookieCalculator best = process(cookies.stream());
        
        long ts2 = System.nanoTime();
        log.info("Best: "+best);
        log.info("Serial:     " + (ts2-ts1) + "ns");
    }
    
    private void parallelProcessing(List<CookieCalculator> cookies) {
        long ts1 = System.nanoTime();
        
        CookieCalculator best = process(cookies.stream().parallel());
        
        long ts2 = System.nanoTime();
        log.info("Best: "+best);
        log.info("Parallel:   " + (ts2-ts1) + "ns");
    }
    
    private CookieCalculator process(Stream<CookieCalculator> s) {
        return s.distinct()
                .peek(CookieCalculator::calc)
                .filter(c -> c.getDoe()>500)
                .filter(c -> c.getName().contains("a"))
                .filter(c -> c.getBuoyancy()<0.1)
                .filter(c -> c.getTasteRating()>0.8f)
//                .peek(c->{if(c.getName().toLowerCase(Locale.ROOT).contains("cookie")) log.debug(c.getName());})
                .sorted(Comparator.comparing(CookieCalculator::getName).thenComparing(CookieCalculator::getId))
                .reduce(new CookieCalculator(0,0,0,0), CookieCalculator::average);
//                .collect(Collectors.toList());
    }
}
