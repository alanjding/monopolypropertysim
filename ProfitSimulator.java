/* *****************************************************************************
 * Simulates potential profit on a Monopoly board.
 * Makes use of algs4 library used in COS226 at Princeton University.
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;

public class ProfitSimulator {

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

    // makes all possible trades between player1 and player2 that give both
    // players new monopolies
    // nice code, but algorithm is slooowwwwww
    private static void tradeForMonopoly(
            HashSet<Integer> player1, HashSet<Integer> player2) {
        HashSet<Integer> temp1 = (HashSet<Integer>) player1.clone();
        HashSet<Integer> temp2 = (HashSet<Integer>) player1.clone();
        // parallel queues
        Queue<Integer> trades1 = new Queue<Integer>();
        Queue<Integer> trades2 = new Queue<Integer>();
        for (int i : player1) {
            for (int j : player2) {
                temp1.remove(i);
                temp1.add(j);
                temp2.remove(j);
                temp2.add(i);

                if (hasMonopoly(temp1) != 0 && hasMonopoly(temp2) != 0) {
                    trades1.enqueue(i);
                    trades2.enqueue(j);
                }

                temp1.remove(j);
                temp1.add(i);
                temp2.remove(i);
                temp2.add(j);
            }
        }
        while (!trades1.isEmpty()) {
            int player1trade = trades1.dequeue();
            int player2trade = trades2.dequeue();
            player1.remove(player1trade);
            player1.add(player2trade);
            player2.remove(player2trade);
            player2.add(player1trade);
        }
    }

    private static double epp(HashSet<Integer> player, double coh) {
        double epp = 0;
        // monopolies
        int monopolies = hasMonopoly(player);
        if ((monopolies & 0x1) != 0) {
            epp += 31.2 * (0.45 * 52 +
                    0.1 * 0.05 * 248 +
                    0.45 * 100 * Math.exp(-(coh - 500) * (coh - 500) / 1000000));
            player.remove(1);
            player.remove(3);
        }
        if ((monopolies & 0x2) != 0) {
            epp += 31.2 * (0.45 * 88 +
                    0.1 * 0.05 * 566 +
                    0.45 * 100 * Math.exp(-(coh - 750) * (coh - 750) / 1000000));
            player.remove(6);
            player.remove(8);
            player.remove(9);
        }
        if ((monopolies & 0x4) != 0) {
            epp += 31.2 * (0.45 * 68 +
                    0.1 * 0.05 * 808 +
                    0.45 * 100 * Math.exp(-(coh - 1500) * (coh - 1500) / 1000000));
            player.remove(11);
            player.remove(13);
            player.remove(14);
        }
        if ((monopolies & 0x8) != 0) {
            epp += 31.2 * (0.45 * 96 +
                    0.1 * 0.05 * 954 +
                    0.45 * 100 * Math.exp(-(coh - 1500) * (coh - 1500) / 1000000));
            player.remove(16);
            player.remove(18);
            player.remove(19);
        }
        if ((monopolies & 0x10) != 0) {
            epp += 31.2 * (0.45 * 72 +
                    0.1 * 0.05 * 1058 +
                    0.45 * 100 * Math.exp(-(coh - 2250) * (coh - 2250) / 1000000));
            player.remove(21);
            player.remove(23);
            player.remove(24);
        }
        if ((monopolies & 0x20) != 0) {
            epp += 31.2 * (0.45 * 68 +
                    0.1 * 0.05 * 1165 +
                    0.45 * 100 * Math.exp(-(coh - 2250) * (coh - 2250) / 1000000));
            player.remove(26);
            player.remove(27);
            player.remove(29);
        }
        if ((monopolies & 0x40) != 0) {
            epp += 31.2 * (0.45 * 60 +
                    0.1 * 0.05 * 1314 +
                    0.45 * 100 * Math.exp(-(coh - 3000) * (coh - 3000) / 1000000));
            player.remove(31);
            player.remove(32);
            player.remove(34);
        }
        if ((monopolies & 0x80) != 0) {
            epp += 31.2 * (0.45 * 68 +
                    0.1 * 0.05 * 1780 +
                    0.45 * 100 * Math.exp(-(coh - 2000) * (coh - 2000) / 1000000));
            player.remove(37);
            player.remove(39);
        }
        // railroads - no cash factor
        int numRailroads = 0;
        for (int i = 0; i < 4; i++) {
            if (player.contains(5 + 10 * i)) {
                numRailroads++;
                player.remove(5 + 10 * i);
            }
        }
        int se;
        switch (numRailroads) {
            case 1:
                se = 8;
                break;
            case 2:
                se = 18;
                break;
            case 3:
                se = 39;
                break;
            case 4:
                se = 84;
                break;
            default:
                se = 0;
        }
        epp += 31.2 * (0.45 * se + 0.1 * 0.05 * 200);

        // utilities - no cash factor
        int numUtilities = 0;
        if (player.contains(12)) numUtilities++;
        if (player.contains(28)) numUtilities++;
        switch (numUtilities) {
            case 1:
                se = 13;
                break;
            case 2:
                se = 35;
                break;
            default:
                se = 0;
        }
        epp += 31.2 * (0.45 * se + 0.1 * 0.05 * 200);

        // other single properties
        epp += 31.2 * (player.size());

        return epp;
    }

    public static void main(String[] args) {
        StdOut.print("Number of players: ");
        int numPlayers = StdIn.readInt();
        StdOut.print("Number of simulations for each given p: ");
        int numSim = StdIn.readInt();
        StdOut.print("Number of intervals for p: ");
        int intervals = StdIn.readInt();
        double initCash = 1500 + 29.81 * 80 / numPlayers;
        double minCash = 120.0;
        double delta = (initCash - minCash) / (intervals - 1);
        // these two arrays are parallel
        double[] cashSpent = new double[numSim * intervals];
        double[] epp = new double[numSim * intervals];

        int statArrayCounter = 0;
        for (double p = minCash; p <= initCash; p += delta) {
            for (int sim = 0; sim < numSim; sim++) {
                // playerProp[0] is the player we focus on
                HashSet<Integer>[] playerProp = new HashSet[numPlayers];
                for (int i = 0; i < numPlayers; i++)
                    playerProp[i] = new HashSet<Integer>();

                int[] availProperties = {
                        1, 3, 5, 6, 8, 9,
                        11, 12, 13, 14, 15, 16, 18, 19,
                        21, 23, 24, 25, 26, 27, 28, 29,
                        31, 32, 34, 35, 37, 39
                };
                // cost[i] is the cost of property i
                int[] cost = {
                        0, 60, 0, 60, 0, 200, 100, 0, 100, 120,
                        0, 140, 150, 140, 160, 200, 180, 0, 180, 200,
                        0, 220, 0, 220, 240, 200, 260, 260, 150, 280,
                        0, 300, 300, 0, 320, 200, 0, 350, 0, 400
                };
                StdRandom.shuffle(availProperties);
                int i = 0;
                while (cashSpent[statArrayCounter] < p) {
                    playerProp[0].add(availProperties[i]);
                    cashSpent[statArrayCounter] += cost[availProperties[i]];
                    i++;
                }
                // if spending exceeds initCash, remove most recently added property
                if (cashSpent[statArrayCounter] > initCash) {
                    playerProp[0].remove(availProperties[i]);
                    cashSpent[statArrayCounter] -= cost[availProperties[i]];
                    i--;
                }

                // split remaining properties among other players
                while (i < 28) {
                    for (int j = 1; j < numPlayers; j++) {
                        if (i < 28)
                            playerProp[j].add(availProperties[i++]);
                    }
                }

                // make trades
                for (int j = 1; j < numPlayers; j++)
                    tradeForMonopoly(playerProp[0], playerProp[j]);

                // calculate EPP
                epp[statArrayCounter] = epp(playerProp[0],
                                            initCash - cashSpent[statArrayCounter]);

                statArrayCounter++;
            }
        }

        StdOut.println("STATS: (cashSpent epp)");
        for (int i = 0; i < numSim * intervals; i++) {
            StdOut.println(cashSpent[i] + " " + epp[i]);
        }
    }
}

