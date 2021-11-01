package org.jap.ui.console;

import org.jap.ui.IUi;

import java.util.Scanner;

public class ConsoleManager implements IUi
{
    private final static Scanner scan = new Scanner(System.in);
    
    @Override
    public String getInput()
    {
        return scan.nextLine();
    }
    
    @Override
    public void sendOutput(String output)
    {
        System.out.println(output);
    }
}
