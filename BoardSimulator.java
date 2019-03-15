/* *****************************************************************************
 * Simulates acquisition of property on a Monopoly board.
 * Makes use of algs4 library used in COS226 at Princeton University.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.HashSet;

public class BoardSimulator {
    public static void main(String[] args) {
        StdOut.print("Number of players: ");
        int numPlayers = StdIn.readInt();
        StdOut.print("Number of simulations: ");
        int numSim = StdIn.readInt();
        int[] propertiesBoughtStats = new int[numSim];

        for (int s = 0; s < numSim; s++) {
            int[] playerPos = new int[numPlayers];

            HashSet<Integer> availProperties = new HashSet<Integer>();

            availProperties.add(1);
            availProperties.add(3);
            availProperties.add(5);
            availProperties.add(6);
            availProperties.add(8);
            availProperties.add(9);

            availProperties.add(11);
            availProperties.add(12);
            availProperties.add(13);
            availProperties.add(14);
            availProperties.add(15);
            availProperties.add(16);
            availProperties.add(18);
            availProperties.add(19);

            availProperties.add(21);
            availProperties.add(23);
            availProperties.add(24);
            availProperties.add(25);
            availProperties.add(26);
            availProperties.add(27);
            availProperties.add(28);
            availProperties.add(29);

            availProperties.add(31);
            availProperties.add(32);
            availProperties.add(34);
            availProperties.add(35);
            availProperties.add(37);
            availProperties.add(39);

            for (int roll = 0; roll < 20; roll++) {
                for (int i = 0; i < numPlayers; i++) {
                    playerPos[i] =
                            (playerPos[i] + StdRandom.uniform(6) + 1) % 40;
                    playerPos[i] =
                            (playerPos[i] + StdRandom.uniform(6) + 1) % 40;
                    availProperties.remove(playerPos[i]);
                }
            }
            propertiesBoughtStats[s] = 28 - availProperties.size();
        }
        StdOut.println("STATS:");
        StdOut.println("Sample mean of properties bought after 20 rounds: " +
                               StdStats.mean(propertiesBoughtStats));
        StdOut.println("Sample standard deviation of properties bought: " +
                               StdStats.stddev(propertiesBoughtStats));
    }
}
