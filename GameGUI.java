package minesweeper;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Deque;
import java.util.LinkedList;

public class GameGUI extends JFrame implements ActionListener
{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public static int gridNum = 10;

    public static final int IMAGE_WIDTH = WIDTH / gridNum;
    public static final int IMAGE_HEIGHT = HEIGHT / gridNum;

    public static int winCount = 0;  // Must == 90 for the user to win
    public static int sealCount = 0;  // Must == 10 for the user to win

    ImageIcon imageIcon = new ImageIcon("mineIcon.png"); // load the image to a imageIcon
    Image image = imageIcon.getImage(); // transform it 
    Image newimg = image.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    ImageIcon newIcon = new ImageIcon(newimg);  // transform it back

    public static Cell[][] cells = new Cell[gridNum][gridNum];

    public GameGUI()
    {
        // Set layout
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);

        JRadioButton tenByTen = new JRadioButton("10x10");
        JRadioButton fifByFif = new JRadioButton("15x15");
        JRadioButton tweByTwe = new JRadioButton("20x20");

        JMenu menuGridSize = new JMenu("Grid Size");
        menuGridSize.addActionListener(this);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(this);

        JMenu menuOptions = new JMenu("Options");
        menuOptions.add(menuExit);

        JMenuBar bar = new JMenuBar();
        bar.add(menuOptions);
        JPanel minePanel = new JPanel();
        minePanel.setLayout(new GridLayout(gridNum, gridNum));

        // Create a mouse listener to check for right clicks
        MouseListener mouseListener = new MouseAdapter()
        {
            public void mousePressed(MouseEvent mouseEvent)
            {
                int modifiers = mouseEvent.getModifiers();
                Cell rClickedCell = (Cell) mouseEvent.getSource();

                if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)
                {
                    System.out.println("Cell sealed.");
                    rClickedCell.setText("S");
                    rClickedCell.setType("Sealed");
                    rClickedCell.setActionCommand("Sealed");

                    sealCount++;
                }

                // Check for win condition
                if (winCount + sealCount >= gridNum * gridNum)
                {
                    PlayerNotification winFrame = new PlayerNotification("You win!");
                }
            }
        };

        for (int row = 0; row < gridNum; row++)
        {
            for (int col = 0; col < gridNum; col++)
            {
                Cell newCell = new Cell("", row, col);
                newCell.addActionListener(this);
                newCell.addMouseListener(mouseListener);
                cells[row][col] = newCell;
                String cellType = newCell.getType();
                //System.out.println(cellType);
                if ("Mined".equals(cellType))
                {
                    newCell.setActionCommand("Mined");

                } else
                {
                    newCell.setType("Empty");
                    newCell.setActionCommand("Empty");
                }
                minePanel.add(newCell);
            }
        }

        // Find adjacent cells
        for (int row = 0; row < gridNum; row++)
        {
            for (int col = 0; col < gridNum; col++)
            {
                Cell currentCell = cells[row][col];
                String cellType = "";
                if (currentCell.getType() != null)
                {
                    cellType = currentCell.getType();
                }

                if (cellType.equals("Mined"))
                {
                    //System.out.println("Mined: " + row + ", " + col);
                    if (row == 0)
                    {
                        // There will always be a cell below a cell in the top row
                        if (!cells[row + 1][col].getType().equals("Mined"))
                        {
                            cells[row + 1][col].incrementAdj();
                            cells[row + 1][col].setType("Adjacent");
                            cells[row + 1][col].setActionCommand("Adjacent");
                        }

                        if (col == 0)  // First column
                        {
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col + 1].incrementAdj();
                                cells[row + 1][col + 1].setType("Adjacent");
                                cells[row + 1][col + 1].setActionCommand("Adjacent");

                            }
                        } else if (col == gridNum - 1)  // Last column
                        {
                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col - 1].incrementAdj();
                                cells[row + 1][col - 1].setType("Adjacent");
                                cells[row + 1][col - 1].setActionCommand("Adjacent");
                            }
                        } else
                        {
                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }

                            if (!cells[row + 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col - 1].incrementAdj();
                                cells[row + 1][col - 1].setType("Adjacent");
                                cells[row + 1][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col + 1].incrementAdj();
                                cells[row + 1][col + 1].setType("Adjacent");
                                cells[row + 1][col + 1].setActionCommand("Adjacent");
                            }
                        }
                    } else if (row == gridNum - 1)  // Last row
                    {
                        // There will always be a cell above a cell in the bottom row
                        if (!cells[row - 1][col].getType().equals("Mined"))
                        {
                            cells[row - 1][col].incrementAdj();
                            cells[row - 1][col].setType("Adjacent");
                            cells[row - 1][col].setActionCommand("Adjacent");
                        }

                        if (col == 0)  // First column
                        {
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col + 1].incrementAdj();
                                cells[row - 1][col + 1].setType("Adjacent");
                                cells[row - 1][col + 1].setActionCommand("Adjacent");
                            }
                        } else if (col == gridNum - 1)  // Last column
                        {
                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col - 1].incrementAdj();
                                cells[row - 1][col - 1].setType("Adjacent");
                                cells[row - 1][col - 1].setActionCommand("Adjacent");
                            }
                        } else
                        {
                            if (!cells[row - 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col - 1].incrementAdj();
                                cells[row - 1][col - 1].setType("Adjacent");
                                cells[row - 1][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col + 1].incrementAdj();
                                cells[row - 1][col + 1].setType("Adjacent");
                                cells[row - 1][col + 1].setActionCommand("Adjacent");
                            }

                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }
                        }
                    } else
                    {
                        // There will always be a cell below a cell in the top row
                        if (!cells[row + 1][col].getType().equals("Mined"))
                        {
                            cells[row + 1][col].incrementAdj();
                            cells[row + 1][col].setType("Adjacent");
                            cells[row + 1][col].setActionCommand("Adjacent");
                        }

                        // There will always be a cell above a cell in a middle row
                        if (!cells[row - 1][col].getType().equals("Mined"))
                        {
                            cells[row - 1][col].incrementAdj();
                            cells[row - 1][col].setType("Adjacent");
                            cells[row - 1][col].setActionCommand("Adjacent");
                        }

                        if (col == 0)  // First column
                        {
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col + 1].incrementAdj();
                                cells[row - 1][col + 1].setType("Adjacent");
                                cells[row - 1][col + 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col + 1].incrementAdj();
                                cells[row + 1][col + 1].setType("Adjacent");
                                cells[row + 1][col + 1].setActionCommand("Adjacent");
                            }
                        } else if (col == gridNum - 1)  // Last column
                        {
                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col - 1].incrementAdj();
                                cells[row - 1][col - 1].setType("Adjacent");
                                cells[row - 1][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col - 1].incrementAdj();
                                cells[row + 1][col - 1].setType("Adjacent");
                                cells[row + 1][col - 1].setActionCommand("Adjacent");
                            }
                        } else  // Check all
                        {
                            if (!cells[row - 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col - 1].incrementAdj();
                                cells[row - 1][col - 1].setType("Adjacent");
                                cells[row - 1][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row - 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row - 1][col + 1].incrementAdj();
                                cells[row - 1][col + 1].setType("Adjacent");
                                cells[row - 1][col + 1].setActionCommand("Adjacent");
                            }

                            if (!cells[row][col - 1].getType().equals("Mined"))
                            {
                                cells[row][col - 1].incrementAdj();
                                cells[row][col - 1].setType("Adjacent");
                                cells[row][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row][col + 1].getType().equals("Mined"))
                            {
                                cells[row][col + 1].incrementAdj();
                                cells[row][col + 1].setType("Adjacent");
                                cells[row][col + 1].setActionCommand("Adjacent");
                            }

                            if (!cells[row + 1][col - 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col - 1].incrementAdj();
                                cells[row + 1][col - 1].setType("Adjacent");
                                cells[row + 1][col - 1].setActionCommand("Adjacent");
                            }
                            if (!cells[row + 1][col + 1].getType().equals("Mined"))
                            {
                                cells[row + 1][col + 1].incrementAdj();
                                cells[row + 1][col + 1].setType("Adjacent");
                                cells[row + 1][col + 1].setActionCommand("Adjacent");
                            }
                        }
                    }
                }
            }

        }

        add(bar, BorderLayout.NORTH);
        add(minePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println("Action: " + e.getActionCommand());
        if (null != e.getActionCommand())
        {
            switch (e.getActionCommand())
            {
                case "Exit":
                    System.exit(0);
                    break;
                case "Sealed":
                    break;
                case "Mined":
                    //Cell mined = (Cell) e.getSource();
                    //mined.setIcon((Icon) newIcon);
                    //mined.setBackground(Color.LIGHT_GRAY);
                    for (int row = 0; row < gridNum; row++)
                    {
                        for (int col = 0; col < gridNum; col++)
                        {
                            if (cells[row][col].getType().equals("Mined"))
                            {
                                cells[row][col].setIcon((Icon) newIcon);
                                cells[row][col].setBackground(Color.LIGHT_GRAY);
                            }
                        }
                    }
                    PlayerNotification goFrame = new PlayerNotification("Game Over!");
                    break;
                case "Adjacent":
                    Cell adjacent = (Cell) e.getSource();
                    adjacent.setType("Triggered");
                    adjacent.setEnabled(false);
                    adjacent.setBackground(Color.LIGHT_GRAY);
                    adjacent.setText(Integer.toString(adjacent.getAdj()));
                    winCount++;
                    break;
                case "Empty":
                    Cell empty = (Cell) e.getSource();
                    int emptyRow = empty.getRow();
                    int emptyCol = empty.getCol();
                    Point emptyNode = new Point();
                    emptyNode.x = emptyCol;
                    emptyNode.y = emptyRow;
                    floodFill(emptyNode);
                    break;
                default:
                    break;
            }
        }

        // Check for win condition
        if (winCount + sealCount >= gridNum * gridNum)
        {
            PlayerNotification winFrame = new PlayerNotification("You win!");
        }
    }

    // Flood fill algorithm inspired from https://rosettacode.org/wiki/Bitmap/Flood_fill#Java
    public void floodFill(Point node)
    {
        int width = gridNum;
        int height = gridNum;
        if (cells[node.y][node.x].getType().equals("Empty") || cells[node.y][node.x].getType().equals("Adjacent"))
        {
            Deque<Point> queue = new LinkedList<Point>();
            do
            {
                int x = node.x;
                int y = node.y;

                while (x > 0 && (cells[y][x - 1].getType() == "Empty"))
                {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && (cells[y][x].getType().equals("Empty")))
                {
                    winCount++;

                    cells[y][x].setType("Triggered");
                    cells[y][x].setEnabled(false);
                    cells[y][x].setBackground(Color.LIGHT_GRAY);

                    // Check for "Adjacent" cells surrounding the "Empty" cells
                    if (x - 1 > 0 && cells[y][x - 1].getType().equals("Adjacent"))
                    {
                        cells[y][x - 1].setType("Triggered");
                        cells[y][x - 1].setEnabled(false);
                        cells[y][x - 1].setBackground(Color.LIGHT_GRAY);
                        cells[y][x - 1].setText(Integer.toString(cells[y][x - 1].getAdj()));
                        winCount++;
                    }
                    if (x + 1 < gridNum - 1 && cells[y][x + 1].getType().equals("Adjacent"))
                    {
                        cells[y][x + 1].setType("Triggered");
                        cells[y][x + 1].setEnabled(false);
                        cells[y][x + 1].setBackground(Color.LIGHT_GRAY);
                        cells[y][x + 1].setText(Integer.toString(cells[y][x + 1].getAdj()));
                        winCount++;
                    }
                    if (y - 1 > 0 && cells[y - 1][x].getType().equals("Adjacent"))
                    {
                        cells[y - 1][x].setType("Triggered");
                        cells[y - 1][x].setEnabled(false);
                        cells[y - 1][x].setBackground(Color.LIGHT_GRAY);
                        cells[y - 1][x].setText(Integer.toString(cells[y - 1][x].getAdj()));
                        winCount++;
                    }
                    if (y + 1 < gridNum - 1 && cells[y + 1][x].getType().equals("Adjacent"))
                    {
                        cells[y + 1][x].setType("Triggered");
                        cells[y + 1][x].setEnabled(false);
                        cells[y + 1][x].setBackground(Color.LIGHT_GRAY);
                        cells[y + 1][x].setText(Integer.toString(cells[y + 1][x].getAdj()));
                        winCount++;
                    }

                    //System.out.println("Point: " + x + ", " + y);
                    if (!spanUp && y > 0 && (cells[y - 1][x].getType().equals("Empty")))
                    {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && (!cells[y - 1][x].equals("Empty")))
                    {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1 && (cells[y + 1][x].getType().equals("Empty")))
                    {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1 && (!cells[y + 1][x].getType().equals("Empty")))
                    {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.pollFirst()) != null);
        }
    }
}
