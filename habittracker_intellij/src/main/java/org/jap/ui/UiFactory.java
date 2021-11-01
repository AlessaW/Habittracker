package org.jap.ui;

import org.jap.ui.console.ConsoleManager;
import org.jap.ui.gui.GuiManager;

public class UiFactory
{
    public static IUi getInstance(UiType uiType) throws Exception
    {
        if (uiType.equals(UiType.console))
        {
            return new ConsoleManager();
        }
        else if (uiType.equals(UiType.gui))
        {
            return new GuiManager();
        }
        else
        {
            throw new Exception("Type " + uiType + " is not supported");
        }
    }
}
