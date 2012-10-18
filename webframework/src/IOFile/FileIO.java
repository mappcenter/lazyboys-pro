/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IOFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class FileIO
{
    public static void main(String[] args) 
    {
        FileIO file=new FileIO();
        ArrayList bob = file.loadFile("xcall.proxy-1.00.html");
        for (int i=0; i<bob.size(); i++) {
            System.out.println("\t" + bob.get(i));
        }
    }
    public ArrayList loadFile(String fileName)
    {
        if ((fileName == null) || ("".equals(fileName))) {
            throw new IllegalArgumentException();
        }
        
        String line;
        ArrayList file = new ArrayList();
        try
        {    
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            if (!in.ready()) {
                throw new IOException();
            }
            while ((line = in.readLine()) != null) 
                file.add(line);
            in.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
        }
        return file;
    }
}

