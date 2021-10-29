public class runner {
    private static int ScoreNetwork(Network network, int Seed)
    {
        int IdleTurns = 0;

        Game game = new Game(Seed);
        while (!game.GameOver())
        {
            //Converting Board to inputs
            float[] Inputs = new float[16];
            int[][] Board = game.GetBoard();

            for (int x = 0; x < 4; x ++)
            {
                for (int y = 0; y < 4; y ++)
                {
                    Inputs[x*4 + y] = Board[x][y]/2048;
                }
            }

            //Running Network
            float[] Outputs = network.Run(Inputs);

            for (int attempts = 0; attempts < 4; attempts++) {
                //Dealing with outputs
                int Direction = 0;
                float TopValue = -1000;
                for (int i = 0; i < Outputs.length; i++)
                {
                    if (Outputs[i] > TopValue)
                    {
                        TopValue = Outputs[i];
                        Direction = i;
                    }
                }

                game.Turn(Direction);

                if (game.Idle)
                {
                    if (attempts == 3)
                    {
                        return game.Score;
                    } else {
                        Outputs[Direction] = -1000;
                    }
                } else {
                    break;
                }
            }
        }
        return game.Score;
    }

    public static void main(String[] args) {
        // Training
        int Generations = 1000000;
        int NetworkCount = 2; // Must be even
        int GamesPerGen = 10000;
        float changeWeight = .001f;

        // Network
        int InputCount = 16;
        int LayerCount = 2;
        int NodesPerLayer = 16;
        int OutputCount = 4;

        // Variables
        Network[] Networks = new Network[NetworkCount];

        for (int i = 0; i < NetworkCount; i++) {
            Networks[i] = new Network(LayerCount, NodesPerLayer, InputCount, OutputCount);
        }

        double[] TopScores = new double[NetworkCount / 2];
        int[] TopNetworks = new int[NetworkCount / 2];

        // Generation Progress
        double BestScore = 0;
        long TotalScore = 0;

        for (int g = 0; g < Generations; g++) {

            // Filling top scores
            for (int j = 0; j < TopScores.length; j++)
                TopScores[j] = 0;

            for (int j = 0; j < NetworkCount; j++) {
                int score = 0;
                for (int h = 0; h < GamesPerGen; h++)
                    score += ScoreNetwork(Networks[j], (int) (Math.random() * 10000));
                ;
                TotalScore += score;

                if (score > BestScore)
                    BestScore = score;

                // Looking for place to insert
                for (int k = 0; k < TopScores.length; k++) {
                    if (score > TopScores[k]) {
                        int PointerNetwork = j;
                        double PointerScore = score;

                        for (int l = k; l < TopScores.length; l++) {
                            int newNetworkId = TopNetworks[l];
                            double newScore = TopScores[l];

                            TopNetworks[l] = PointerNetwork;
                            TopScores[l] = PointerScore;

                            PointerNetwork = newNetworkId;
                            PointerScore = newScore;
                        }

                        break;
                    }
                }
            }

            // Creating New Generation
            Network[] newNetworks = new Network[NetworkCount];
            for (int j = 0; j < TopNetworks.length; j++) {
                newNetworks[j * 2] = Networks[TopNetworks[j]];
                newNetworks[j * 2 + 1] = new Network(newNetworks[j * 2], changeWeight);
            }

            Networks = newNetworks;

            System.out.printf("Generation : %d, Best Score : %f, Average Score :%f, \n", g, BestScore,
                    (float) ((double) TotalScore / (((g + 1) * NetworkCount))));
        }
    }
}