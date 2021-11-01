package org.jap.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.ui.IUi;
import org.jap.ui.UiFactory;
import org.jap.ui.UiType;
import org.jap.ui.console.ConsoleManager;

public class App
{
    private final static Logger logger = LogManager.getLogger(App.class);
    
    private static IUi ui;
    
    private static boolean loop = true;
    
    public static void main(String[] args)
    {
        init(args);
        
        while(loop)
        {
            input();
            processing();
            output();
        }
    }
    
    private static void init(String[] args)
    {
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        
        UiType uiType = UiType.console;
        for (String arg : args)
        {
            for (UiType type : UiType.values())
                if (arg.equals(type.toString()))
                    uiType = type;
            // Add other Argument Checks
            
            logger.info(arg);
        }
        
        try
        {
            ui = UiFactory.getInstance(uiType);
        } catch (Exception e)
        {
            logger.error(e.getStackTrace());
        }
    }
    
    private static void input()
    {
        ui.getInput();
    }
    
    private static void processing()
    {
    
    }
    
    private static void output()
    {
        ui.sendOutput("Test");
    }
}
