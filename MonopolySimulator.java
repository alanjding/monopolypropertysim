/* *****************************************************************************
 * Simulates formation of Monopolies on a Monopoly board.
 * Makes use of algs4 library used in COS226 at Princeton University.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;

public class MonopolySimulator {

    // returns 0 iff the player has no monopoly
    // returns an on bit in the ith position from the right
    // if ith monopoly is owned
    // 1st monopoly - purple
    // 2nd monopoly - light blue
    // 3rd monopoly - magenta
    // 4th monopoly - orange
    // 5th monopoly - red
    // 6th monopoly - yellow
    // 7th monopoly - green
    // 8th monopoly - blue
    private static int hasMonopoly(HashSet<Integer> propOwned) {
        int ret = 0;
        if (propOwned.contains(1) && propOwned.contains(3))
            ret |= 0x1;
        if (propOwned.contains(6) && propOwned.contains(8) &&
                propOwned.contains(9))
            ret |= 0x2;
        if (propOwned.contains(11) && propOwned.contains(13) &&
                propOwned.contains(14))
            ret |= 0x4;
        if (propOwned.contains(16) && propOwned.contains(18) &&
                propOwned.contains(19))
            ret |= 0x8;
        if (propOwned.contains(21) && propOwned.contains(23) &&
                propOwned.contains(24))
            ret |= 0x10;
        if (propOwned.contains(26) && propOwned.contains(27) &&
                propOwned.contains(29))
            ret |= 0x20;
        if (propOwned.contains(31) && propOwned.contains(32) &&
                propOwned.contains(34))
            ret |= 0x40;
        if (propOwned.contains(37) && propOwned.contains(39))
            ret |= 0x80;
        return ret;
    }

    // can player1 and player2 trade so that each gets a new monopoly?
    // nice code, but algorithm is slooowwwwww
    private static boolean tradeForMonopoly(HashSet<Integer> player1,
                                            HashSet<Integer> player2) {
        HashSet<Integer> temp1 = (HashSet<Integer>) player1.clone();
        HashSet<Integer> temp2 = (HashSet<Integer>) player1.clone();
        for (int i : player1) {
            for (int j : player2) {
                temp1.remove(i);
                temp1.add(j);
                temp2.remove(j);
                temp2.add(i);

                if (hasMonopoly(temp1) != 0 && hasMonopoly(temp2) != 0)
                    return true;

                temp1.remove(j);
                temp1.add(i);
                temp2.remove(i);
                temp2.add(j);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        StdOut.print("Number of players: ");
        int numPlayers = StdIn.readInt();
        StdOut.print("Number of simulations: ");
        int numSim = StdIn.readInt();
        int numMonopoly = 0;
        int numMonopolyAfterTrade = 0;
        int numNoMonopoly = 0;

        for (int s = 0; s < numSim; s++) {
            int[] playerPos = new int[numPlayers];
            HashSet<Integer>[] playerProp = new HashSet[numPlayers];
            for (int i = 0; i < numPlayers; i++)
                playerProp[i] = new HashSet<Integer>();

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

            while (!availProperties.isEmpty()) {
                for (int i = 0; i < numPlayers; i++) {
                    playerPos[i] =
                            (playerPos[i] + StdRandom.uniform(6) + 1) % 40;
                    playerPos[i] =
                            (playerPos[i] + StdRandom.uniform(6) + 1) % 40;
                    if (availProperties.contains(playerPos[i])) {
                        playerProp[i].add(playerPos[i]);
                        availProperties.remove(playerPos[i]);
                    }
                }
            }
            int stat = 0;
            // any monopolies?
            for (int i = 0; i < numPlayers; i++) {
                if (hasMonopoly(playerProp[i]) != 0) {
                    stat = 2;
                    break;
                }
            }
            // no monopolies but trade creates two new monopolies?
            if (stat != 2) {
                for (int i = 0; i < numPlayers; i++) {
                    for (int j = i + 1; j < numPlayers; j++) {
                        if (tradeForMonopoly(playerProp[i], playerProp[j])) {
                            stat = 1;
                            break;
                        }
                    }
                }
            }
            // no monopolies or trades? stat is already 0.
            switch (stat) {
                case 0:
                    numNoMonopoly++;
                    break;
                case 1:
                    numMonopolyAfterTrade++;
                    break;
                case 2:
                    numMonopoly++;
                    break;
                default:
                    break;
            }
        }

        StdOut.println("STATS:");
        StdOut.println("% of rounds where player had monopoly w/o trade: " +
                               (double) numMonopoly / numSim * 100 + "%");
        StdOut.println("% of rounds where a trade can lead to a monopoly: " +
                               (double) numMonopolyAfterTrade / numSim * 100 +
                               "%");
        StdOut.println("% of rounds where NO trade can lead to a monopoly: " +
                               (double) numNoMonopoly / numSim * 100 + "%");
    }
}
