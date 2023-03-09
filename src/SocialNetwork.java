import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
/**
 * You can also develop help functions and new classes for your system. You
 * can change the skeleton code if you need but you do not allow to remove the
 * methods provided in this class.
 */
public class SocialNetwork {
    // formerly private
    ADS2List userNames = new ADS2List();//You may use different container for the user name list
    ADS2Graph userNetwork = new ADS2Graph(101);//You may use different container for the social network data

    String[] myFriends = new String[200];

    public SocialNetwork() {

    }

    /**
     * Loading social network data from files.
     * The dataset contains two separate files for user names (NameList.csv) and
     * the network distributions (SocialNetworkData.csv).
     * Use file I/O functions to load the data.You need to choose suitable data
     * structure and algorithms for an effective loading function
     */
    public void Load() throws FileNotFoundException {
        String currentLine[];
        int counter = 1;

        try (Scanner scanner = new Scanner(new File("NameList.csv"))) {
            scanner.useDelimiter("\r\n");
            while (scanner.hasNext()) {
                currentLine = scanner.next().split(",");
                String userName = currentLine[0];
                HashPair newData = new HashPair(userName, counter++);
                userNames.addData(newData);
                System.out.println("" + newData.name + "," + newData.index);

            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }

        // GRAPH CREATION

        try (Scanner scannerTwo = new Scanner(new File("SocialNetworkData.csv"))) {
            scannerTwo.useDelimiter("\r\n");
            while (scannerTwo.hasNext()) {
                currentLine = scannerTwo.next().split(",");
                //System.out.println("added");
                userNetwork.AddEdge(Integer.parseInt(currentLine[1]), Integer.parseInt(currentLine[0]), Double.parseDouble(currentLine[2]));

            }

        } catch (FileNotFoundException e) {
            System.err.println("Error with file.");
        }


    }

    /**
     * Locating a user from the network
     *
     * @param fullName users full name as a String
     * @return return the ID based on the user list. return -1 if the user do not exist
     * based on your algorithm, you may also need to locate the reference
     * of the node from the graph.
     */
    public int FindUserID(String fullName) {
        int search = userNames.SearchItem(fullName);
        if (search == 0) {
            return -1;
        } else {
            return search;
        }
    }


    /**
     * Listing ALL the friends belongs to the user
     * You need to retrieval all the directly linked nodes and return their full names.
     * Current Skeleton only have some dummy data. You need to replace the output
     * by using your own algorithms.
     *
     * @param currentUserName use FindUserID or other help functions to locate
     *                        the user in the graph first.
     * @return You need to return all the user names in a String Array directly
     * linked to the users node.
     */
    public String[] GetMyFriends(String currentUserName) {
        String[] myFriends = new String[20];
        int person = FindUserID(currentUserName);
        int j = 0;
        for (int i = 0; i < 100; i++) {
            if (userNetwork.isAdjacent(person, i)) {
                //System.out.println(person);
                //System.out.println(i); // just need the name for I
                myFriends[j++] = userNames.SearchByID(i);
            }
        }
        return myFriends;

    }

    /**
     * Listing the top 10 recommended friends for the user
     * In the task, you need to calculate the shortest distance between the
     * current user and all other non-directly linked users. Pick up the top three
     * closest candidates and return their full names.
     * Use some help functions for sorting and shortest distance algorithms
     *
     / @param currentUserName use FindUserID or other help functions to locate
     * the user in the graph first.
     * @return You need to return all the user names in a String Array containing
     * top 3 closest candidates.
     *     // helper functions
     */


    static int partition(double[] array, int begin, int end)
    {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (array[i] < array[pivot]) {
                double temp = array[counter];
                array[counter] = array[i];
                array[i] = temp;
                counter++;
            }
        }
        double temp = array[pivot];
        array[pivot] = array[counter];
        array[counter] = temp;

        return counter;

    }

    public static void quickSort(double[] array, int begin, int end)
    {
        if(end<=begin)return;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot-1);
        quickSort(array, pivot+1, end);
    }
    public String[] GetRecommended(String currentUserName) {
        // must ignore friends.

        String[] recommended = new String[10];
        int[] recInts = new int[10];
        double[] recDoubles = new double[101];

        for (int i = 1; i < 101; i++) {
            if (!userNetwork.isAdjacent(FindUserID(currentUserName), i)) {
                recDoubles[i] = userNetwork.FindShortestPath(FindUserID(currentUserName), i);
                System.out.println(i + " and"+recDoubles[i]);
            }
        }
        int k = 0;
        // sorting here
        // QS approach
//        quickSort(recDoubles, 1, 100);
//        for(int i = 0; i<recDoubles.length; i++)
//        {
//            System.out.println(recDoubles[i]);
//        }
//        for(int j =0; j<10; j++)
//        {
//            Double min = Double.MAX_VALUE;
//            for (int i = 1; i < 101; i++) {
//                if (recDoubles[i] < min && recDoubles[i] != 0) {
//                    min = recDoubles[i];
//                    k = i;
//                }
//
//            }
//            recDoubles[k] = Double.MAX_VALUE;
//            recInts[j] = k;
//            recommended[j] = "No."+userNames.SearchItem(userNames.SearchByID(recInts[j]))+" "+userNames.SearchByID(recInts[j]);
//
//
//        }

        for (int j = 0; j < 10; j++) {
            Double min = Double.MAX_VALUE;
            for (int i = 1; i < 101; i++) {
                if (recDoubles[i] < min && recDoubles[i] != 0) {
                    min = recDoubles[i];
                    k = i;
                }

            }
            recDoubles[k] = Double.MAX_VALUE;
            recInts[j] = k;
            recommended[j] = "No."+userNames.SearchItem(userNames.SearchByID(recInts[j]))+" "+userNames.SearchByID(recInts[j]);
            System.out.println("Name "+recommended[j]);

        }
        return recommended;
    }
}

