package org.jap.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumHelper<T> // Experimentell & WIP
{
    public boolean Contains( T e, String one) // Experimentell & WIP
    {
        try
        {
            Method m = e.getClass().getMethod("values");
            m.invoke(m);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex)
        {
            System.out.println(""+ex);
            ex.printStackTrace();
        }
        return false;
    }
}
