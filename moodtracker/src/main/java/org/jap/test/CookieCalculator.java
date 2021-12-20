package org.jap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 *
 */
public class CookieCalculator {
    private static final Logger log = LogManager.getLogger(CookieCalculator.class);
    
    // Variables
    private static int idCounter = 0;
    private final int id;
    private int milk;
    private int flour;
    private int chocolate;
    private int sugar;
    
    private int doe;
    private String name;
    private float tasteRating;
    private double buoyancy;
    
    private final String[] namePieces = {
        "ab","ad","af","ak","al","an","am","ao","ap","ar","as","at","au",
        "eb","ed","ef","el","en","em","ep","er","es","et",
        "ib","id","if","ig","il","in","im","ip","ir","is","it",
        "ob","od","of","og","ol","on","om","oo","op","or","os","ot","ou",
        "ub","ud","uf","ug","ul","un","um","uo","up","ur","us","ut"
    };
    
    public CookieCalculator(int milk, int flour, int chocolate, int sugar) {
        this.id = idCounter++;
        this.milk = milk;
        this.flour = flour;
        this.chocolate = chocolate;
        this.sugar = sugar;
    }
    
    public void calc() {
        doe = (int) Math.round(milk + flour + chocolate + sugar);
        float chocPerc = ((float) chocolate+sugar)/doe;
        float doePerc = ((float) flour+milk)/doe;
        tasteRating = chocPerc*chocPerc*doePerc*6.75f;
        buoyancy = (float) (flour+sugar)/doe;
        generateName();
    }
    
    private void generateName() {
        String name = "Nr."+id+": ";
        int digit = id;
        int digitCount = 0;
        while(digit>=10){
            digit/=10;
            digitCount++;
        }
        
        if (buoyancy > 0.5) {
            name += "light ";
        }
        if (tasteRating >= 9) {
            name += "delicious ";
        }
        
        if (id == 1) name+= "1st Cookie ";
        else if (id == 2) name+= "2nd Cookie ";
        else if (id == 3) name+= "3rd Cookie ";
        else if ((int)(Math.pow(10,digitCount)*digit) == id && id > 10) {
            name += id;
            name += "th Anniversary Cookie ";
        } else {
            name += namePieces[(int)(Math.random()* namePieces.length)];
            name += namePieces[(int)(Math.random()* namePieces.length)];
            name += " ";
        }
        if ((float) (milk+chocolate)/doe < 0.1) {
            name += "powder ";
        }
        
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public int getMilk() {
        return milk;
    }
    
    public int getFlour() {
        return flour;
    }
    
    public int getChocolate() {
        return chocolate;
    }
    
    public int getSugar() {
        return sugar;
    }
    
    public int getDoe() {
        return doe;
    }
    
    public String getName() {
        return name;
    }
    
    public float getTasteRating() {
        return tasteRating;
    }
    
    public double getBuoyancy() {
        return buoyancy;
    }
    
    @Override
    public String toString() {
        return "CookieCalculator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", milk=" + milk +
                ", flour=" + flour +
                ", chocolate=" + chocolate +
                ", sugar=" + sugar +
                ", doe=" + doe +
                ", tasteRating=" + tasteRating +
                ", buoyancy=" + buoyancy +
                '}';
    }
    
    public CookieCalculator average(CookieCalculator c2) {
        milk = (milk+c2.milk)/2;
        flour = (c2.flour)/2;
        chocolate = (c2.chocolate)/2;
        sugar = (c2.sugar)/2;
        calc();
        return this;
    }
}
