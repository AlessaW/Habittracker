package org.jap.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.ui.IUi;
import org.jap.ui.UiFactory;
import org.jap.ui.UiType;

public class App
{
    private final static Logger logger = LogManager.getLogger(App.class);
    
    private static IUi ui;
    
    public static void main(String[] args)
    {
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        
        for (String arg : args)
        {
            for (UiType type : UiType.values())
            {
                if (arg.equals(type.toString()))
                {
                    try
                    {
                        ui = UiFactory.getInstance(type);
                    } catch (Exception e)
                    {
                        logger.error(e.getStackTrace());
                    }
                }
            }
            // Check Arguments
            logger.info(arg);
        }
    }
}
