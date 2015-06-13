import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
/* AP Computer Science A - Mr. Shorr - Period 3 - SPHS 2014-2015
 * Post AP Test Project: Maze generation and solving in java
 *  by
 *      Cory Atwater
 *      Amador Lagunas
 *      Sebastien Whetsel
 * 
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
    private int stepsTaken;  a       // the number of steps that the solver took to solve the maze
    private int pathLength;         // the length of the path between the start and end points
    
    public Maze2(int N)
    {
        // set variable N to parameter N
        this.N = N;
        // set the variable start to an array of 2 ints
        this.start = new int[2];
        // in this case the start location is the bottom left
        // set the starting X location to 1
        this.start[0] = 1;
        // set the starting Y location to 1
        this.start[1] = 1;
        // set the variable end to an array of 2 ints
        this.end = new int[2];
        // in this case the end location is the middle of the maze
        // set the ending X location to the middle of the maze
        this.end[0] = (N + 1) / 2;
        // set the ending Y location to the middle of the maze
        this.end[1] = (N + 1) / 2;
        // set the delay in between the steps to 30 milliseconds (was the default)
        this.delay = 30;
        // set the total number of steps taken in the maze to zero
        this.stepsTaken = 0;
        // set the length of the path from start to end to zero
        this.pathLength = 0;
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
        // set the variable start to an array of 2 ints
        this.start = new int[2];
        // in this case the start location is the bottom left
        // set the starting X location to the inputted value
        this.start[0] = startX;
        // set the starting Y location to the inputted value
        this.start[1] = startY;
        // set the variable end to an array of 2 ints
        this.end = new int[2];
        // in this case the end location is the middle of the maze
        // set the ending X location to the inputted value
        this.end[0] = endX;
        // set the ending Y location to the inputted value
        this.end[1] = endY;
        // set the delay in between the steps to the inputted value
        this.delay = delayTime;
        // set the total number of steps taken in the maze to zero
        this.stepsTaken = 0;
        // set the length of the path from start to end to zero
        this.pathLength = 0;
        // set the size of the image to N + 2 by N + 2
        StdDraw.setXscale(0, N+2);
        StdDraw.setYscale(0, N+2);
        // initialize the maze values
        init();
        // generate the maze, drawing not included
        generate();
    }

    // initialize all of the arrays of the maze
    private void init()
    {
        // initialize an array of cells to represnt the maze
        visited = new boolean[N+2][N+2];
        // mark all border cells as already visited, so that the maze doesn't go out of bounds
        for (int x = 0; x < N+2; x++)
        {
            visited[x][0] = visited[x][N+1] = true;
        }
        for (int y = 0; y < N+2; y++)
        {
            visited[0][y] = visited[N+1][y] = true;
        }
        // initialize four arrays that represent whether there is a wall next to each cell
        // stores whether there is a wall to the north of [x][y]
        north = new boolean[N+2][N+2];
        // stores whether there is a wall to the east of [x][y]
        east  = new boolean[N+2][N+2];
        // stores whether there is a wall to the south of [x][y]
        south = new boolean[N+2][N+2];
        // stores whether there is a wall to the west of [x][y]
        west  = new boolean[N+2][N+2];
        // set all walls as present, the whole maze is filled
        for (int x = 0; x < N+2; x++)
        {
            for (int y = 0; y < N+2; y++)
            {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }
    }

    // generate the maze from a specific cell
    private void generate(int x, int y)
    {
        // mark this cell as visited
        visited[x][y] = true;
        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y])
        {
            // pick random neighbor
            while (true)
            {
                double r = Math.random();
                // knock down a random wall, keep generating from that cell
                if (r < 0.25 && !visited[x][y+1])
                {
                    /* This cell doesn't have a wall to its north
                     * The cell above this one doesn't have a wall to its south
                     */
                    north[x][y] = south[x][y+1] = false;
                    // generate starting from the cell above this one
                    generate(x, y + 1);
                    // stop the program
                    break;
                }
                else if (r >= 0.25 && r < 0.50 && !visited[x+1][y])
                {
                    /* This cell doesn't have a wall to its east
                     * The cell to the right of this one doesn't have a wall to its west
                     */
                    east[x][y] = west[x+1][y] = false;
                    // generate starting from the cell to the right of this one
                    generate(x+1, y);
                    // stop the program
                    break;
                }
                else if (r >= 0.5 && r < 0.75 && !visited[x][y-1])
                {
                    /* This cell doesn't have a wall to its south
                     * The cell below this one doesn't have a wall to its north
                     */
                    south[x][y] = north[x][y-1] = false;
                    // generate starting from the cell below this one
                    generate(x, y-1);
                    // stop the program
                    break;
                }
                else if (r >= 0.75 && r < 1.00 && !visited[x-1][y])
                {
                    /* This cell doesn't have a wall to its west
                     * The cell to the left of this one doesn't have a wall to its east
                     */
                    west[x][y] = east[x-1][y] = false;
                    // generate starting from the cell to the left of this one
                    generate(x-1, y);
                    // stop the program
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate()
    {
        /* This is just an overloaded method
         * In all our code, we always generate starting from the lower left regardless of the start point
         * Maybe we should fix that ^
         * EDIT: Nah, too lazy.
         */
        generate(1, 1);
    }

    // solve the maze using depth-first search (recursion)
    private void solve(int x, int y)
    {
        //if the parameters aren't in the maze
        if (x == 0 || y == 0 || x == N+1 || y == N+1)
        {
            // you done messed up. leave this cell
            return;
        }
        // if you have found the center or you have already visited the cell don't do anything
        if (done || visited[x][y])
        {
            // this is an old safety check, its obsolete now
            System.out.println("Reached visited cell at " + x + ", " + y + ".");
            // you are done, leave this cell
            return;
        }
        // mark this cell as visited
        visited[x][y] = true;
        // increment the number of steps taken
        stepsTaken++;
        // incement the total path length
        pathLength++;
        // set the drawing color to blue
        StdDraw.setPenColor(StdDraw.BLUE);
        // draw a circle where you are with radius 0.25
        // this is purely visual, for the user (maybe we could make it faster with no visuals??)
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        // wait a certain number of milliseconds before doing anything else
        StdDraw.show(delay);
        // if you have reached the solution point
        if (x == end[0] && y == end[1]) 
        {
            // set done to true, you have solved the maze
            done = true;
        }
        if (done)
        {
            // you have finished, leave the cell
            return;
        }
        // store a variable for how many neighbors this cell has
        int neighbors = getNeighbors(x,y);
        // while there are still more neighbors to go to
        // go to a random neighboring cell and solve from there
        while(neighbors > 0)
        {
            
            /* stores the next neighbor to go to in the form:
             *      N = (0, 1)
             *      E = (1, 0)
             *      S = (0, -1)
             *      W = (-1, 0)
             */
            int[] exit = randomNeighbor(x, y);
            // I'm pretty sure this code is redundant, but I don't want to find out
            if(done)
            {
                break;
            }
            // solve from the cell you have randomly chosen
            solve(x + exit[0], y + exit[1]);
            // update the number of neighbors left
            neighbors = getNeighbors(x, y);
        }
        // if this cell is the solution, you are done
        // if you come back to this cirle, draw a gray circle over it because it isn't in the solution
        if(!done)
        {
            // set the drawing color to gray
            StdDraw.setPenColor(StdDraw.GRAY);
            // draw a gray circle at your position with radius 0.25
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
            // wait a certain number of milliseconds before doing anything else
            StdDraw.show(delay);
            // increment the number of total steps taken (even though this is a step backward, it counts)
            stepsTaken++;
            // decrement the path length counter because this cell isn't on the solution
            pathLength--;
        }
    }

    // gets the number of valid neighbors that a given [x][y] cell has
    public int getNeighbors(int xLoc,int yLoc)
    {
        // i'm SURE there's a way to do this with like 3 lines, but this works so its good enough
        // intialize an ArrayList that stores a list of possible ways to exit
        ArrayList<String> exits = new ArrayList<String>();
        /* if there is no wall above this cell AND
         * the cell above this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!north[xLoc][yLoc] && !visited[xLoc][yLoc + 1])
        {
            exits.add("north");
        }
        /* if there is no wall to the right of this cell AND
         * the cell to the right of this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!east[xLoc][yLoc] && !visited[xLoc + 1][yLoc])
        {
            exits.add("east");
        }
        /* if there is no wall below this cell AND
         * the cell below this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!south[xLoc][yLoc] && !visited[xLoc][yLoc - 1])
        {
            exits.add("south");
        }
        /* if there is no wall to the left of this cell AND
         * the cell to the left of this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!west[xLoc][yLoc] && !visited[xLoc - 1][yLoc])
        {
            exits.add("west");
        }
        // return how many exits are possible
        return exits.size();
    }

    public int[] randomNeighbor(int xLoc, int yLoc)
    {
        // intialize an ArrayList that stores a list of possible ways to exit
        ArrayList<String> exits = new ArrayList<String>();
        /* if there is no wall above this cell AND
         * the cell above this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!north[xLoc][yLoc] && !visited[xLoc][yLoc + 1])
        {
            exits.add("north");
        }
        /* if there is no wall to the right of this cell AND
         * the cell to the right of this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!east[xLoc][yLoc] && !visited[xLoc + 1][yLoc])
        {
            exits.add("east");
        }
        /* if there is no wall below this cell AND
         * the cell below this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!south[xLoc][yLoc] && !visited[xLoc][yLoc - 1])
        {
            exits.add("south");
        }
        /* if there is no wall to the left of this cell AND
         * the cell to the left of this one hasn't been visited THEN
         * this is a possible exit, add it to the list
         */
        if(!west[xLoc][yLoc] && !visited[xLoc - 1][yLoc])
        {
            exits.add("west");
        }
        // randomly pick one of the items in the list
        int index = (int)(exits.size() * Math.random());
        /* we will return in the form of the numbers where
         *      (0, 1) = N
         *      (1, 0) = E
         *      (0, -1) = S
         *      (-1, 0) = W
         */
        int[] returnArray = {0, 0};
        // temporary variable to store the exit location
        String exit = exits.get(index);
        // if the exit is north, set the return Array to (0, 1)
        if(exit.equals("north"))
        {
            returnArray[1]++;
        }
        // if the exit is east, set the return Array to (1, 0)
        if(exit.equals("east"))
        {
            returnArray[0]++;
        }
        // if the exit is south, set the return Array to (0, -1)
        if(exit.equals("south"))
        {
            returnArray[1]--;
        }
        // if the exit is west, set the return Array to (-1, 0)
        if(exit.equals("west"))
        {
            returnArray[0]--;
        }
        // return the stored value
        return returnArray;
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
        // the maze is not yet solved
        done = false;
        // solve starting from the input location
        solve(start[0], start[1]);
    }

    // draw the maze
    public void draw()
    {
        // set the drawing color to red
        StdDraw.setPenColor(StdDraw.RED);
        // draw a red circle at the ending location
        StdDraw.filledCircle(end[0]+ 0.5, end[1] + 0.5, 0.375);
        // draw another red circle at the the starting location
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
        // display the screen (with the image), then wait one millisecond before doing anything else
        StdDraw.show(1);
    }

    /* a very simple test program
     * the starting location is always the bottom left
     * the solution is always in the center
     * there is a 30 millisecond delay on every step
     */
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

    /* a more complicated test program
     * uses a Scanner to take user input for the size, delay, starting location and ending location
     * NOTE: to avoid stack overflow errors from large mazes (125+), rename this to main, and run from terminal
     * Quick notes on terminal:
     *      type "ls" to see files on your computer
     *      type "cd" to change directory to wherever the file is on your computer
     *      type "Java -Xss1G Maze2" to run your program from terminal
     *      this allows for supa big mazes (at least 1000x1000 but they take supa supa long to solve)
     */
    public static void main2(String[] args)
    {
        // make a new Scanner to read inputs
        Scanner in = new Scanner(System.in);
        // a variable to store the side length of the maze
        int N;
        // ask what side length the user would like, warn about size limits
        System.out.println("What dimensions would you like your maze to have?");
        System.out.println("WARNING: Mazes larger than 125 by 125 are unlikely to generate due to stack overflow.");
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
            System.out.println("Please enter a non-negative number. ");
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
        } while(delayTime < 0);
        // ^ if the input is invalid, repeat
        // a variable to store the starting x location of the maze
        int startXLoc;
        // ask where the user would like the maze to start
        System.out.println("In what column would you like to have your maze start?");
        // tell them to enter to a valid number
        do
        {
            System.out.println("Please enter a positive number less than the maze size ");
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
        // a variable to store the starting y location of the maze
        int startYLoc;
        // ask where the user would like the maze to start
        System.out.println("In what row would you like to have your maze start?");
        // tell them to enter to a valid number
        do
        {
            System.out.println("Please enter a positive number less than the maze size ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            startYLoc = in.nextInt();
        } while(startYLoc < 1 && startYLoc > N);
        // ^ if the input is invalid, repeat
        // a variable to store the starting y location of the maze
        int endXLoc;
        // ask where the user would like the maze to start
        System.out.println("In what column would you like to have your maze end?");
        // tell them to enter to a valid number
        do
        {
            System.out.println("Please enter a positive number less than the maze size ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            endXLoc = in.nextInt();
        } while(endXLoc < 1 && endXLoc > N);
        // ^ if the input is invalid, repeat
        // a variable to store the starting y location of the maze
        int endYLoc;
        // ask where the user would like the maze to start
        System.out.println("In what row would you like to have your maze end?");
        // tell them to enter to a valid number
        do
        {
            System.out.println("Please enter a positive number less than the maze size ");
            // if the input isn't a number
            while(!in.hasNextInt())
            {
                // tell the user to stop being an idiot
                System.out.println("That's not a number, dumb***. ");
                // clear the input
                in.next();
            }
            // store the input
            endYLoc = in.nextInt();
        } while(endYLoc < 1 && endYLoc > N);
        // ^ if the input is invalid, repeat
        // make a new maze that is n rows by n columns
        Maze2 maze = new Maze2(N, delayTime, startXLoc, startYLoc, endXLoc, endYLoc);
        // draw the initial array of walls
        StdDraw.show(1);
        // draws the actual maze
        maze.draw();
        // set time1 to current time
        long time1 = System.currentTimeMillis();
        // solve the maze, including drawing (within solve)
        maze.solve();
        // set time2 to current time
        long time2 = System.currentTimeMillis();
        // set timeDiff to the the difference in the two times
        long timeDiff = time2 - time1;
        // print all the info about how long the maze took to solve, number of steps, path length
        System.out.println("This maze was solved in " + timeDiff/1000.0 + " seconds.");
        System.out.println("This maze was solved in " + (maze.stepsTaken - 1) + " steps.");
        System.out.println("The length of the solution was " + (maze.pathLength - 1) + " steps.");
    }

    /* main3 and main4 are for a personal project studying the effects of maze size on:
     *      time to solve
     *      number of steps taken to solve
     *      solution length
     *      solving efficiency (length / steps)
     * because of this, the code isn't commented and I don't plan on going back and doing so
     */
    public static double[][] main3(int[] args)
    {
        int N = args[0];
        int times = args[1];
        Maze2 test;
        double[][] returnArray = new double[times][4];
        System.out.print(N + " ... ");
        for(int i  = 0; i < times; i++)
        {
            test = new Maze2(N, 0, 1, 1, N, N);
            test.draw();
            long time1 = System.currentTimeMillis();
            test.solve();
            long time2 = System.currentTimeMillis();
            long timeDiff = time2 - time1;
            returnArray[i][0] = timeDiff/ 1000.0;
            returnArray[i][1] = test.stepsTaken - 1;
            returnArray[i][2] = test.pathLength - 1;
            returnArray[i][3] = returnArray[i][2] / returnArray[i][1];
            System.out.print((i + 1) + " ");
        }
        System.out.println("Complete");
        for(int i = 0; i < times; i++)
        {
            //System.out.println(Arrays.toString(returnArray[i]));
        }
        return returnArray;
    }
    
    public static void main4(String[] args)
    {
        long time1 = System.currentTimeMillis();
        int min = 5;
        int max = 200;
        int change = 5;
        int times = 50;
        int sizes = (max - min) / change + 1;
        double[][][] complicated = new double[sizes][times][4];
        int[] temp = new int[2];
        double[][] data = new double[sizes][4];
        temp[1] = times;
        double superTotalTime = 0;
        int superTotalSteps = 0;
        for(int i = min; i <= max; i+= change)
        {
            temp[0] = i;
            complicated[(i - min) / change] = main3(temp);
        }
        for(int i = min; i <= max; i += change)
        {
            double totalTime = 0;
            for(int j = 0; j < times; j++)
            {
                totalTime += complicated[(i - min) / change][j][0];
            }
            double totalSteps = 0;
            for(int k = 0; k < times; k++)
            {
                totalSteps += complicated[(i - min) / change][k][1];
            }
            double totalPath = 0;
            for(int l = 0; l < times; l++)
            {
                totalPath += complicated[(i - min) / change][l][2];
            }
            double totalEfficiency = 0;
            for(int m = 0; m < times; m++)
            {
                totalEfficiency += complicated[(i - min) / change][m][3];
            }
            superTotalTime += totalTime;
            superTotalSteps += totalSteps;
            data[(i - min) / change][0] = Math.round(totalTime / times * Math.pow(10,10)) / Math.pow(10,10);
            data[(i - min) / change][1] = Math.round(totalSteps / times * Math.pow(10,10)) / Math.pow(10,10);
            data[(i - min) / change][2] = Math.round(totalPath / times * Math.pow(10,10)) / Math.pow(10,10);
            data[(i - min) / change][3] = Math.round(totalEfficiency / times * Math.pow(10,10)) / Math.pow(10,10);
            
        }
        for(int i = min; i <= max; i += change)
        {
            System.out.println(Arrays.toString(data[(i - min) / change]));
            
            /*System.out.println("Mazes of size " + i + " took on average " + data[(i - min) / change][0]
            + " seconds to solve, " + data[(i - min) / change][1] + " steps to solve " + " and had an average "
            + "path length of " + data[(i - min) / change][2] + " steps. Their solving efficiency was, on average "
            + (100.0 * data[(i - min) / change][3]) + "%.");*/
        }
        double clockSpeed = Math.round(superTotalTime/superTotalSteps * Math.pow(10, 8)) / Math.pow(10, 5); 
        System.out.println("One maze solving step takes about " + clockSpeed + " milliseconds");
        long time2 = System.currentTimeMillis();
        long timeDiff = time2 - time1;
        System.out.println("This entire program took " + (timeDiff / 1000.0) + " seconds."); 
    }
}
