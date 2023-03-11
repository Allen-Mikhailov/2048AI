import java.util.*;

public class GameBoard {
    private int[][] board;
    private int size;

    private boolean addTile()
    {
        ArrayList<Integer> openSpaces = new ArrayList<Integer>();

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                if (board[y][x] == 0)
                    openSpaces.add(y);
                    openSpaces.add(x);
            }
        }

        if (openSpaces.size() > 0)
        {
            
        }

        return false;
    }

    public GameBoard()
    {
        board = new int[4][4];
        addTile();
        addTile();
    }
}
