package minesweeper;

import javax.swing.JButton;

public class Cell extends JButton
{
    //public static final String[] CELL_TYPES = {"Mined", "Adjacent", "Empty"};
    public static int numMinesLeft = 10;
    private int randInt;  // Stores a random number from [0, 2]
    private String cellType;
    private int numAdj = 0;
    private int row;
    private int col;
    
    public Cell(String identifier, int row, int col)
    {
        super(identifier);
        this.row = row;
        this.col = col;
        randInt = (int)(Math.random()*7);
        if (randInt == 0)
        {
            if (numMinesLeft > 0)
            {
                cellType = "Mined";
                numMinesLeft--;
                //System.out.println("Num mines left: " + numMinesLeft);
            }
        }
        else
        {
            cellType = "Empty";
        }
    }
    
    public String getType()
    {
        return cellType;
    }
    
    public int getAdj()
    {
        return numAdj;
    }

    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public void setType(String type)
    {
        cellType = type;
    }
    
    public void incrementAdj()
    {
        numAdj++;
    }

    void setIcon(int i)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
