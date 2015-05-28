import java.util.Scanner;
import java.util.Calendar;
/*
 * Features to add: 
 * Chooseable start and end points
 * See how speed / average speed to solve changes with dimension. Graph average time vs dimension
 * See if we can expand past 175
 * Turn on off delay on solve and draw (1000 and 30 milliseconds)
 * Test Cory's algorithm for speed and compare
 * Possibly test broad search for speed and compare
 */
public class Maze2
{
    private int N;                  // dimension of maze, N by N
    private boolean[][] north;      // is there a wall to the north of cell i, j
    private boolean[][] east;       // is there a wall to the east of cell i,j
    private boolean[][] south;      // is there a wall to the south of cell i,j
    private boolean[][] west;       // is there a wall to the west of cell i,j
    private boolean[][] visited;    // has the cell i,j been visited
    private boolean done = false;   // the maze has not been solved
    private int[] start;            // the coordinates of the start of the maze
    private int[] end;              // the coordinates of the end of the maze
    private int delay;              // the delay between drawing points
    public Maze2(int N)
    {
        // set variable N to parameter N
        this.N = N;
        this.start = new int[2];
        this.start[0] = 1;
        this.start[1] = 1;
        this.end = new int[2];
        this.end[0] = (N + 1) / 2;
        this.end[1] = (N + 1) / 2;
        this.delay = 30;
        // set the size of the image to N + 2 by N + 2
        StdDraw.setXscale(0, N+2);
        StdDraw.setYscale(0, N+2);
        // initialize the maze values
        init();
        // generate the maze, drawing not included
        generate();
    }

    public Maze2(int N, int delayTime)
    {
        // set variable N to parameter N
        this.N = N;
        this.start = new int[2];
        this.start[0] = 1;
        this.start[1] = 1;
        this.end = new int[2];
        this.end[0] = (N + 1) / 2;
        this.end[1] = (N + 1) / 2;
        this.delay = delayTime;
        // set the size of the image to N + 2 by N + 2
        StdDraw.setXscale(0, N+2);
        StdDraw.setYscale(0, N+2);
        // initialize the maze values
        init();
        // generate the maze, drawing not included
        generate();
    }

    public Maze2(int N, int delayTime, int startX, int startY, int endX, int endY)
    {
        // set variable N to parameter N
        this.N = N;
        this.start = new int[2];
        this.start[0] = startX;
        this.start[1] = startY;
        this.end = new int[2];
        this.end[0] = endX;
        this.end[1] = endY;
        this.delay = delayTime;
        // set the size of the image to N + 2 by N + 2
        StdDraw.setXscale(0, N+2);
        StdDraw.setYscale(0, N+2);
        // initialize the maze values
        init();
        // generate the maze, drawing not included
        generate();
    }

    private void init()
    {
        // initialize border cells as already visited
        visited = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++)
        {
            visited[x][0] = visited[x][N+1] = true;
        }
        for (int y = 0; y < N+2; y++)
        {
            visited[0][y] = visited[N+1][y] = true;
        }
        // initialize all walls as present
        north = new boolean[N+2][N+2];
        east  = new boolean[N+2][N+2];
        south = new boolean[N+2][N+2];
        west  = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++)
        {
            for (int y = 0; y < N+2; y++)
            {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }
    }

    // generate the maze
    private void generate(int x, int y)
    {
        visited[x][y] = true;
        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y])
        {
            // pick random neighbor
            while (true)
            {
                double r = Math.random();
                //knock down a random wall, continue from that cell
                if (r < 0.25 && !visited[x][y+1])
                {
                    north[x][y] = south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r < 0.50 && !visited[x+1][y])
                {
                    east[x][y] = west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r < 0.75 && !visited[x][y-1])
                {
                    south[x][y] = north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (!visited[x-1][y])
                {
                    west[x][y] = east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate()
    {
        generate(1, 1);
        // not mine idk wat up
        /*
        // delete some random walls
        for (int i = 0; i < N; i++) {
        int x = (int) (1 + Math.random() * (N-1));
        int y = (int) (1 + Math.random() * (N-1));
        north[x][y] = south[x][y+1] = false;
        }

        // add some random walls
        for (int i = 0; i < 10; i++) {
        int x = (int) (N / 2 + Math.random() * (N / 2));
        int y = (int) (N / 2 + Math.random() * (N / 2));
        east[x][y] = west[x+1][y] = true;
        }
         */

    }

    // solve the maze using depth-first search
    private void solve(int x, int y)
    {
        //if parameters aren't in the maze
        if (x == 0 || y == 0 || x == N+1 || y == N+1)
        {
            return;
        }
        // if you have found the center or you have already visited the cell don't do anything
        if (done || visited[x][y])
        {
            return;
        }
        // mark this cell as visited
        visited[x][y] = true;
        // set the drawing color to blue
        StdDraw.setPenColor(StdDraw.BLUE);
        // draw a circle where you are with radius 0.25
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        // wait a certain number of milliseconds before doing anything else
        StdDraw.show(delay); //default was 30
        // reached middle which is the solution
        if (x == end[0] && y == end[1]) 
        {
            done = true;
        }
        // go to a random neighboring cell and solve from there
        while(true)
        {
            // pick a random number between 0 and 1
            double temp = Math.random();
            if (temp < 0.25 && !north[x][y])
            {
                solve(x, y + 1);
            }
            else if (temp < 0.50 && !east[x][y])
            {
                solve(x + 1, y);
            }
            else if (temp < 0.75 && !south[x][y])
            {
                solve(x, y - 1);
            }
            else if (!west[x][y])
            {
                solve(x - 1, y);
            }
        }
        // if this is the solution (you have reached the center), you are done
        if (done)
        {
            return;
        }
        // if you come back to this cirle, draw a gray circle over it because it isn't in the solution
        // set the drawing color to gray
        StdDraw.setPenColor(StdDraw.GRAY);
        // draw a gray circle at your position with radius 0.25
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        // wait a certain number of milliseconds before doing anything else
        StdDraw.show(delay); //30 was the default
    }

    // solve the maze starting from the start state
    public void solve()
    {
        // reset the maze
        // set all cells to unvisited
        for (int x = 1; x <= N; x++)
        {
            for (int y = 1; y <= N; y++)
            {
                visited[x][y] = false;
            }
        }
        // have not reached the center yet
        done = false;
        // solve starting from the input location left
        solve(start[0], start[1]);
    }

    // draw the maze
    public void draw()
    {
        // set the drawing color to red
        StdDraw.setPenColor(StdDraw.RED);
        // draw a red circle in the center of the maze, the end
        StdDraw.filledCircle(end[0]+ 0.5, end[1] + 0.5, 0.375);
        // draw another red circle at the bottom left, the start
        StdDraw.filledCircle(start[0] + 0.5, start[1] + 0.5, 0.375);
        // set the drawing color to black
        StdDraw.setPenColor(StdDraw.BLACK);
        // draw the walls of the maze
        for (int x = 1; x <= N; x++)
        {
            for (int y = 1; y <= N; y++)
            {
                // if there is a wall between this cell and it south, draw a wall there
                if (south[x][y])
                {
                    StdDraw.line(x, y, x + 1, y);
                }
                // if there is a wall between this cell and it north, draw a wall there
                if (north[x][y])
                {
                    StdDraw.line(x, y + 1, x + 1, y + 1);
                }
                // if there is a wall between this cell and it west, draw a wall there
                if (west[x][y])
                {
                    StdDraw.line(x, y, x, y + 1);
                }
                // if there is a wall between this cell and it east, draw a wall there
                if (east[x][y])
                {
                    StdDraw.line(x + 1, y, x + 1, y + 1);
                }
            }
        }
        // wait one millisecond before doing anything else
        StdDraw.show(1);
    }

    // a test client
    public static void main(String[] args)
    {
        // store the input number as N
        int N = Integer.parseInt(args[0]);
        // make a new maze that is n rows by n columns
        Maze2 maze = new Maze2(N);
        // draw the initial array of walls
        StdDraw.show(1);
        // draws the actual maze
        maze.draw();
        // solve the maze, including drawing (within solve)
        maze.solve();
    }

    // second test client
    public static void main2(String[] args)
    {
        // make a new Scanner to read inputs
        Scanner in = new Scanner(System.in);
        // a variable to store the side length of the maze
        int N;
        // ask what side length the user would like
        System.out.println("What dimensions would you like your maze to have?");
        // tell them to enter to a positive number
        do
        {
            System.out.println("Please enter a positive number. ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            N = in.nextInt();
        } while(N < 1);
        // ^ if the input is invalid, repeat
        // a variable to store the delaytime of the maze
        int delayTime;
        // ask what delay time the user wants
        System.out.println("What delay would you like to have between iterations (in milliseconds)?");
        // tell them to enter to a positive number
        do
        {
            System.out.println("Please enter a positive number. ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            delayTime = in.nextInt();
        } while(delayTime < 1);
        // ^ if the input is invalid, repeat
        // a variable to store the starting x location of the maze
        int startXLoc;
        // ask where the user would like the maze to start
        System.out.println("In what row would you like to have your maze start?");
        // tell them to enter to a positive number
        do
        {
            System.out.println("Please enter a positive number. ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            startXLoc = in.nextInt();
        } while(startXLoc < 1 && startXLoc > N);
        // ^ if the input is invalid, repeat
        // add other inputs
        // add other inputs
        // add other inputs
        // add other inputs
        // set time1 to current time
        long time1 = System.currentTimeMillis();
        // make a new maze that is n rows by n columns
        Maze2 maze = new Maze2(N, delayTime, startXLoc, startYLoc, endXLoc, endYLoc);
        // draw the initial array of walls
        StdDraw.show(1);
        // draws the actual maze
        maze.draw();
        // solve the maze, including drawing (within solve)
        maze.solve();
        // set time2 to current time
        long time2 = System.currentTimeMillis();
        // set timeDiff to the the difference in the two times
        long timeDiff = time2 - time1;
        System.out.println(timeDiff/1000.0);
    }

}
