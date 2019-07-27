package minesweeper;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationListener implements ActionListener
{
    public NotificationListener()
    {
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        System.exit(0);
    }
}
