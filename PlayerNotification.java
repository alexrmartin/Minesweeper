package minesweeper;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerNotification extends JFrame
{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    NotificationListener ExitGame = new NotificationListener();

    public PlayerNotification(String message)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize((int) (WIDTH / 2), HEIGHT / 4);

        JButton goButton = new JButton("Exit");
        goButton.addActionListener(ExitGame);

        JLabel goLabel = new JLabel(message, SwingConstants.CENTER);

        JPanel goPanel = new JPanel();
        goPanel.setLayout(new BorderLayout());
        goPanel.add(goButton, BorderLayout.SOUTH);
        goPanel.add(goLabel, BorderLayout.CENTER);

        add(goPanel, BorderLayout.CENTER);
        setVisible(true);
    }

}
