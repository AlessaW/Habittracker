package org.jap.ui;

import org.jap.ui.console.ConsoleManager;
import org.jap.ui.gui.GuiManager;

public class UiFactory
{
    public static IUi getInstance(EUiType type) throws Exception
    {
        if (type.equals(EUiType.console))
        {
            return new ConsoleManager();
        }
        else if (type.equals(EUiType.gui))
        {
            return new GuiManager();
        }
        else
        {
            throw new Exception("Type " + type + " is not supported");
        }
    }
}
