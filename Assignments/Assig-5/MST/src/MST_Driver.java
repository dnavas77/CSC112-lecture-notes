/**
 * Student: Danilo Navas on 11/22/2015.
 * Rutgers University New Brunswick New Jersey
 * Data Structures CSC-112
 * Assignment 5: Minimum Spanning Tree
 */

import apps.PartialTree;
import apps.PartialTreeList;
import structures.Graph;
import apps.MST;

import java.io.IOException;
import java.util.ArrayList;


public class MST_Driver
{
    public static void main(String[] args)
    {
        Graph myGraph = null;
        try
        {
            myGraph = new Graph("graph2.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        PartialTreeList ptlist = MST.initialize(myGraph);

        for (PartialTree pt : ptlist)
        {
           System.out.println(pt.toString());
        }

        ArrayList<PartialTree.Arc> mstArcs = MST.execute(myGraph, ptlist);

        for (PartialTree.Arc pta : mstArcs)
        {
            System.out.println(pta.toString());
        }

    }// end main
}
