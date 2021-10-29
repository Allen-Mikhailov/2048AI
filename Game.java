import java.util.Random;

public class Game {
    public boolean Idle;
    public int Score = 0;
    private int[][] board;
    public Random rand;

    public boolean GameOver()
    {
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                if (board[x][y] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void Spawn()
    {
        int x = rand.nextInt(4);
        int y = rand.nextInt(4);

        if (board[x][y] != 0)
        {
            Spawn();
            return;
        }
            

        if (rand.nextInt(10) == 0)
        {
            board[x][y] = 4;
        } else {
            board[x][y] = 2;
        }
    }

    public Game(int Seed)
    {
        board = new int[][] {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };

        rand = new Random(Seed);

        Spawn();
        Spawn();
    }

    public Game(Game g)
    {
        board = new int[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                board[i][j] = g.board[i][j];

        Score = g.Score;
        rand = g.rand.clone();
    }

    public int[][] GetBoard()
    {
        return board;
    }

    public int[][] Turn(int Direction)
    {
        boolean Changed = false;

        //Right
        if (Direction == 0)
        {
            for (int y = 0; y < 4; y++)
            {
                for (int x = 3; x >= 0; x--)
                {
                    if (board[x][y] != 0)
                    {
                        int switchx = -1;
                        int value = board[x][y];
                        boolean CanCombine = true;
                        for (int ax = x+1; ax < 4; ax++)
                        {
                            if (board[ax][y] == 0)
                            {
                                switchx = ax;
                            } else if (CanCombine && board[x][y] == board[ax][y]) {
                                switchx = ax;
                                value = board[x][y]*2;
                                Score += value;
                                CanCombine = false;
                            } else {
                                break;
                            }
                        }

                        if (switchx != -1)
                        {
                            board[x][y] = 0;
                            board[switchx][y] = value;
                        }
                    }
                }
            }
        } else if (Direction == 1) {
            //Left
            for (int y = 0; y < 4; y++)
            {
                for (int x = 1; x < 4; x++)
                {
                    if (board[x][y] != 0)
                    {
                        int switchx = -1;
                        int value = board[x][y];
                        boolean CanCombine = true;
                        for (int ax = x-1; ax > -1; ax--)
                        {
                            if (board[ax][y] == 0)
                            {
                                switchx = ax;
                            } else if (CanCombine && board[x][y] == board[ax][y]) {
                                switchx = ax;
                                value = board[x][y]*2;
                                Score += value;
                                CanCombine = false;
                            } else {
                                break;
                            }
                        }

                        if (switchx != -1)
                        {
                            board[x][y] = 0;
                            board[switchx][y] = value;
                        }
                    }
                }
            }
        } else if (Direction == 2) {
            //Down
            for (int x = 0; x < 4; x++)
            {
                for (int y = 3; y >= 0; y--)
                {
                    if (board[x][y] != 0)
                    {
                        int switchy = -1;
                        int value = board[x][y];
                        boolean CanCombine = true;
                        for (int ay = y+1; ay < 4; ay++)
                        {
                            if (board[x][ay] == 0)
                            {
                                switchy = ay;
                            } else if (CanCombine && board[x][y] == board[x][ay]) {
                                switchy = ay;
                                value = board[x][y]*2;
                                Score += value;
                                CanCombine = false;
                            } else {
                                break;
                            }
                        }

                        if (switchy != -1)
                        {
                            board[x][y] = 0;
                            board[x][switchy] = value;
                        }
                    }
                }
            }
        } else if (Direction == 3) {
            //Up
            for (int x = 0; x < 4; x++)
            {
                for (int y = 1; y < 4; y++)
                {
                    if (board[x][y] != 0)
                    {
                        int switchy = -1;
                        int value = board[x][y];
                        boolean CanCombine = true;
                        for (int ay = y-1; ay > -1; ay--)
                        {
                            if (board[x][ay] == 0)
                            {
                                switchy = ay;
                            } else if (CanCombine && board[x][y] == board[x][ay]) {
                                switchy = ay;
                                value = board[x][y]*2;
                                Score += value;
                                CanCombine = false;
                            } else {
                                break;
                            }
                        }

                        if (switchy != -1)
                        {
                            board[x][y] = 0;
                            board[x][switchy] = value;
                        }
                    }
                }
            }
        }

        if (!GameOver() && Changed)
        {
            Idle = false;
            Spawn();   
        } else {
            Idle = true;
        }

        return board;
    }

    public void Display()
    {
        for (int y = 0; y < 4; y++)
        {
            System.out.print("\n");
            for (int x = 0; x < 4; x++)
            {
                System.out.printf("%d, ", board[x][y]);
            }
        
        }
        System.out.print("\n");
    }
}
