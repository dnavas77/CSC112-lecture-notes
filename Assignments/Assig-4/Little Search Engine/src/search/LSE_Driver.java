package search;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Danilo Navas on 10/30/2015.
 * Data Structures CSC-112
 */
public class LSE_Driver
{
    public static void main(String[] args)
    {
       // Write your own driver
        LittleSearchEngine LSE = new LittleSearchEngine();

        try
        {
            LSE.makeIndex("docs.txt", "noisewords.txt");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String kw1 = "nothing";
        String kw2 = "slowly";

        ArrayList<String> result = LSE.top5search(kw1, kw2);

        for (int i = 0; i < result.size(); i++)
        {
            if (result.size() == i + 1)
                System.out.print(result.get(i));
            else
                System.out.print(result.get(i) + ", ");
        }

    }//end
}