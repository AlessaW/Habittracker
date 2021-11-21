package org.jap.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.ui.IUi;
import org.jap.ui.UiFactory;
import org.jap.ui.EUiType;

public class App
{
    private final static Logger logger = LogManager.getLogger(App.class);
    
    private static IUi ui;
    
    private static boolean loop = false;
    
    private static String input = "";
    private static String output = "";
    
    public static void main(String[] args) {
        init(args);
        
        while(loop) {
            input();
            processing();
            output();
        }
    }
    
    private static void init(String[] args) {
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        
        EUiType eUiType = EUiType.console;
        for (String arg : args)
        {
            for (EUiType type : EUiType.values())
                if (arg.equals(type.toString()))
                    eUiType = type;
            // Add other Argument Checks
            
            logger.info(arg);
        }
        
        try
        {
            ui = UiFactory.getInstance(eUiType);
        } catch (Exception e)
        {
            logger.error(e.getStackTrace());
        }
        
        loop = true;
        output = "Init Complete!";
        logger.info(output);
        
        output();
    }
    
    private static void input() {
        input = ui.getInput();
    }
    
    private static void processing() {
        output = "Your Input is: "+input;
    }
    
    private static void output() {
        ui.sendOutput(output);
    }
}
