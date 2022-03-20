package dndJava;
import com.wwu.graphics.*;
import java.util.Scanner;

public class GraphicsTestDriver implements BoardGraphicsInf
{
    public static void main(String[] args)
    {
        GraphicsTestDriver driver = new GraphicsTestDriver();
        GameGraphics gg = new GameGraphics(driver);

        //gg.changeTileImage(18,"START1");

        Scanner scan = new Scanner(System.in);

        while (true)
        {
            System.out.println("-----------------------------------------------");
            System.out.println("Set Wall Graphic with Row and Column - src");
            System.out.println("Set Wall Graphic with Tile Number - stn");
            System.out.println("Toggle Wall Graphic with Row and Column - trc");
            System.out.println("Toggle Wall Graphic with Tile Number - ttn");
            System.out.println("Change Tile Graphic with Tile Number - ctn");
            System.out.println("Change Tile Graphic with Row and Column - crc");
            System.out.println("Add a String to the Information Area - ais");
            System.out.println("To Quit the Driver program - q");
            System.out.println("-----------------------------------------------");
            System.out.print("Choice: ");
            String input = scan.next();

            if (input.equals("q"))
            {
                break;
            }
            else if (input.equals("ais"))
            {
                System.out.print("Enter a string to add (max 20 chars): ");
                String line = scan.nextLine();

                gg.addTextToInfoArea(line);
            }
            else if ((input.equals("ctn")) || (input.equals("crc")))
            {
                System.out.println("-----------------------------------------------");

                System.out.println("TILE - t");
                System.out.println("HERO1 - h1");
                System.out.println("HERO2 - h2");
                System.out.println("START1 - s1");
                System.out.println("START2 - s2");
                System.out.println("ANTAGONIST - a");
                System.out.println("GOAL - g");
                System.out.println("-----------------------------------------------");
                System.out.print("Choose an image: ");
                String image = scan.next();

                String img = "TILE";
                if (image.equals("t"))
                {
                    img = "TILE";
                }
                else  if (image.equals("h1"))
                {
                    img = "HERO1";
                }
                else  if (image.equals("h2"))
                {
                    img = "HERO2";
                }
                else  if (image.equals("s1"))
                {
                    img = "START1";
                }
                else  if (image.equals("s2"))
                {
                    img = "START2";
                }
                else  if (image.equals("a"))
                {
                    img = "ANTAGONIST";
                }
                else  if (image.equals("g"))
                {
                    img = "GOAL";
                }

                if (input.equals("crc"))
                {
                    System.out.print("Enter the row: ");
                    int row = scan.nextInt();
                    System.out.print("Enter the column: ");
                    int column = scan.nextInt();

                    gg.changeTileImage(row, column, img);
                }
                else
                {
                    System.out.print("Enter the tile number: ");
                    int tileNum = scan.nextInt();

                    gg.changeTileImage(tileNum, img);
                }
            }
            else if ((input.equals("trc")) || (input.equals("ttn")) ||
                    (input.equals("src")) || (input.equals("stn")) )
            {
                System.out.println("-----------------------------------------------");
                System.out.println("North - n");
                System.out.println("South - s");
                System.out.println("East - e");
                System.out.println("West - w");
                System.out.println("-----------------------------------------------");
                System.out.print("Which direction: ");
                String direction = scan.next();

                GraphicsWallDirections dir = GraphicsWallDirections.NORTH;

                if (direction.equals("n"))
                {
                    dir = GraphicsWallDirections.NORTH;
                }
                else if (direction.equals("s"))
                {
                    dir = GraphicsWallDirections.SOUTH;
                }
                else if (direction.equals("e"))
                {
                    dir = GraphicsWallDirections.EAST;
                }
                else if (direction.equals("w"))
                {
                    dir = GraphicsWallDirections.WEST;
                }

                if ((input.equals("trc")) || (input.equals("src")))
                {
                    System.out.print("Enter the row: ");
                    int row = scan.nextInt();
                    System.out.print("Enter the column: ");
                    int column = scan.nextInt();

                    if (input.equals("trc"))
                    {
                        gg.toggleWallGraphic(row, column, dir);
                    }
                    else
                    {
                        System.out.print("Turn the wall 'on' or 'off': ");
                        String onoff = scan.next();
                        boolean value = onoff.equals("on");
                        gg.wallGraphicSetVisible(row, column, dir, value);
                    }
                }
                else
                {
                    System.out.print("Enter the tile number: ");
                    int tileNum = scan.nextInt();

                    if (input.equals("ttn"))
                    {
                        gg.toggleWallGraphic(tileNum, dir);
                    }
                    else
                    {
                        System.out.print("Turn the wall 'on' or 'off': ");
                        String onoff = scan.next();
                        boolean value = onoff.equals("on");
                        gg.wallGraphicSetVisible(tileNum, dir, value);
                    }
                }
            }
        }
    }

    @Override
    public void buttonPressed(GraphicsClickTypes type) {

        if (type == GraphicsClickTypes.START)
        {

        }


        System.out.println("buttonPressed: " + type);
    }

    @Override
    public void tilePressed(int tileNumber) {
        System.out.println("tilePressed: " + tileNumber);
    }

    @Override
    public void tilePressed(int column, int row) {
        System.out.println("tilePressed: " + column + ":" + row);
    }
}
