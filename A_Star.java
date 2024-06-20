import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class A_Star {
    private MinHeap openList;
    private MinHeap closedList;
    private int startX = 0, startY = 0, goalX, goalY;
    private Node maze[][];

    /**
     * Constructs and initializes the maze.
     */
    public A_Star()
    {
        maze = new Node[15][15];
        openList = new MinHeap();
        closedList = new MinHeap();
        setup();
        System.out.println("This is your maze: ");
        print();
    }

    /**
     * Sets walls within the maze.
     */
    public void setup()
    {
        Random random = new Random();
        int rand = 0;
        int randOut;
        for (int row = 0; row < 15; row++)
        {

            {

                for (int col = 0; col < 15; col++){
                    randOut = random.nextInt(100);
                    if (randOut >= 90)
                    {
                        rand = 1; // set as wall
                    }
                    maze[row][col] = new Node(row, col, rand);
                    rand = 0; // set as open
                }   
            }
        }
    }

    /**
     * This method prints maze to stdout.
     */
    public void print()
    {
        Node current;
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                current = maze[i][j];
                System.out.print(current.getType() +", ");
            }
            System.out.println();
        }
    }

    public void a_StarSearch()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Where would you like your starting points row coordinate? (0-14) ");
        startX = input.nextInt(); 
        System.out.println("Where would you like your starting points column coordinate? (0-14) ");
        startY = input.nextInt(); 
        System.out.println("Where would you like your goal points row coordinate? (0-14) ");
        goalX = input.nextInt(); 
        System.out.println("Where would you like your goal points column coordinate? (0-14) ");        
        goalY = input.nextInt(); 
        Node current = maze[startX][startY];
        current.setH(heuristicScore(current));
        current.setG(0);
        current.setF();
        openList.add(current);
        Node goal = maze[goalX][goalY];
        Node temp;
        boolean solutionFound = false;

        //performs a* search
        while(openList.getSize() != 0)
        {
            current = openList.pop();
            if (current.equals(goal)) // generate path
            {
                solutionFound = true;
                ArrayList<Node> path = new ArrayList<>();
                System.out.println("Solution Found!");
                while (current.getParent() != null)
                {
                    path.add(current);
                    current = current.getParent();
                }
                for (int i = path.size() - 1; i > 0; i--)
                {
                    System.out.println(path.get(i).toString());
                }
                System.out.println(goal.toString());
                break;
            } else // generate neighbors and add to openList
            {
                if (current.getRow() != 0) //check top
                {
                    if (maze[current.getRow() - 1][current.getCol()].getType() == 0 && !closedList.contains(maze[current.getRow() - 1][current.getCol()]))
                    {
                        temp = maze[current.getRow() - 1][current.getCol()];
                        heuristicScore(temp);
                        temp.setG(calculateG(temp));                        
                        temp.setF();
                        temp.setParent(current);
                        openList.add(maze[current.getRow() - 1][current.getCol()]);
                    }
                }
                if (current.getRow() != 14) //check bottom
                {
                    if (maze[current.getRow() + 1][current.getCol()].getType() == 0 && !closedList.contains(maze[current.getRow() + 1][current.getCol()]))
                    {
                        temp = maze[current.getRow() + 1][current.getCol()];
                        heuristicScore(temp);
                        temp.setG(calculateG(temp));                        
                        temp.setF();
                        temp.setParent(current);
                        openList.add(maze[current.getRow() + 1][current.getCol()]);
                    }
                }
                if (current.getCol() != 0) // check left
                {
                    if (maze[current.getRow()][current.getCol() - 1].getType() == 0 && !closedList.contains(maze[current.getRow()][current.getCol() - 1]))
                    {
                        temp = maze[current.getRow()][current.getCol() - 1];
                        heuristicScore(temp);
                        temp.setG(calculateG(temp));                        
                        temp.setF();
                        temp.setParent(current);
                        openList.add(maze[current.getRow()][current.getCol() - 1]);
                    }
                }
                if (current.getCol() != 14) // check right
                {
                    if (maze[current.getRow()][current.getCol() + 1].getType() == 0 && !closedList.contains(maze[current.getRow()][current.getCol() + 1]))
                    {
                        temp = maze[current.getRow()][current.getCol() + 1];
                        heuristicScore(temp);
                        temp.setG(calculateG(temp));                        
                        temp.setF();
                        temp.setParent(current);
                        openList.add(maze[current.getRow()][current.getCol() + 1]);
                    }
                }
                openList.remove(current);                
                closedList.add(current);

            }
        }
        if (solutionFound == false)
            System.out.println("There was no solution.");
    }


    /**
     * Calculates g.
     * @param neighbor point you wish to go to
     * @return the distance
     */
    private int calculateG(Node neighbor) {
        Node current = maze[startX][startY];
        int dx = Math.abs(current.getCol() - neighbor.getCol());
        int dy = Math.abs(current.getRow() - neighbor.getRow());

        if (dx == 1 && dy == 1) {
            // Diagonal move
            return current.getG() + 14;
        } else {
            // Horizontal or vertical move
            return current.getG() + 10;
        }
    }

    /**
     * Calculate heuristic score for a node using the Manhattan method(each traversal counts as 10).
     * @return int representing distance from goal
     */
    public Integer heuristicScore(Node node)
    {
        Integer row = node.getRow();
        Integer col = node.getCol();
        Integer dif = row - col;
        Integer goalDif = goalX - goalY;
        Integer score = dif - goalDif;
        node.setH(Math.abs(score) * 10);
        return Math.abs(score) * 10;
    }

    public static void main(String args[])
    {
        A_Star search = new A_Star();
        search.a_StarSearch();
    }
}
