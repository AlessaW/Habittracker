package org.jap.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.ui.IUi;
import org.jap.ui.UiFactory;
import org.jap.ui.EUiType;

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
