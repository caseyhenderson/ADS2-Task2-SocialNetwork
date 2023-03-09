public class ADS2Graph {
    private double[][] arr; // stores weights + values
    private boolean[] visited;
    double[][] friends = new double[200][200];
    private double[] tentative;
    private int[] fromList;

    ADS2Graph(int numberOfValues)
    {
        if(numberOfValues>0)
        {
            System.out.println("Build the graph.");
            arr = new double[numberOfValues][numberOfValues];
            visited = new boolean[numberOfValues];
        }
        else
        {
            System.err.println("Number of the vertices should be more than zero.");
        }
    }
    public void AddEdge(int index1, int index2, double weight){
        if (index1 == index2){
            System.err.println("two indices should be different");
            return;
        }
        arr[index1][index2] = weight;
        arr[index2][index1] = weight;
    }


    public boolean isAdjacent(int index1, int index2)
    {
        return arr[index1][index2]!=0 && arr[index2][index1]!=0;
    }
    public double getWeight(int index1, int index2)
    {
        return arr[index1][index2];
    }

    private void ResetVisitedList()
    {
        for(int i =0; i< visited.length; i++)
        {
            visited[i] = false;
        }
    }
    private void initialiseTentative(int startID)
    {
        tentative = new double[visited.length];
        for (int i = 0; i < tentative.length ; i++) {
            tentative[i] = Integer.MAX_VALUE;

        }
        tentative[startID] =0;
    }
    private void initialiseFromList(int startID)
    {
        fromList = new int[visited.length];
        for(int i=0; i<tentative.length; i++)
        {
            fromList[i] = -1;
        }
        fromList[startID]= startID;
    }
    private int FindNewCurrent(int destination) {
        // scan through tentative to find next new current node
        double min_tentative = Double.MAX_VALUE;
        int newCurrent = destination;
        for (int i =0; i<tentative.length; i++)
        {
            if(!visited[i] && min_tentative>tentative[i])
            {
                min_tentative = tentative[i];
                newCurrent = i;
            }
        }
        return newCurrent;

    }

    public double FindShortestPath(int startID, int destination)
    {
        long start = System.nanoTime();

        // reset visited to all false
        ResetVisitedList();
        // create and initialise tentative, set startID  to 0
        initialiseTentative(startID);
        // same for From List
        initialiseFromList(startID);
        // - initialise our tracker, current =start
        int current = startID;


            while(current != destination && tentative[current] != Double.MAX_VALUE)
            // if we've not found the destination basically
            {
                visited[current] = true;
                // now scan all UNVISITED neighbours.
                for (int i=0; i<arr.length; i++)
                {

                    if(!visited[i] && isAdjacent(current, i) && tentative[i]>(tentative[current]+getWeight(current, i)))
                    {
                        tentative[i] = tentative[current]+getWeight(current, i);

                        fromList[i] = current;
                    }
                }
                current = FindNewCurrent(destination);

            }
            long finish = System.nanoTime();
        System.out.println("Returning "+tentative[destination]+" in time"+(finish-start));
        return tentative[destination];
    }

    public double getLength(double totalLength)
    {
        return totalLength;
    }


    public double[][] getFriends()
    {
        return friends;
    }


}