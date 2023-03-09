import java.util.*;

public class ADS2List {
    public HashPair[] database;
    int capacity = 100;
    //private int[] hits = new int[100];

    ADS2List() {
        database = new HashPair[capacity];
        //Arrays.fill(hits, 0);
    }

    public HashPair[] getDatabase() {
        return database;
    }

    public void addData(HashPair pair) {
        long start = System.nanoTime();

        if (pair == null) {
            return;
        } else {
            int index = 0;
            for (int i = 0; i < pair.getName().length(); i++) {
                index += pair.getName().charAt(i);
            }
            index = HashFunction(index);
            while (database[index] != null) {
                index = OpenAddressing(index);
            }
            if (database[index] != null) {
                index = OpenAddressing(index);
            }
            database[index] = pair;

        }
        long finish = System.nanoTime();

        System.out.println("added " + pair.getName() + " " + pair.getIndex()+ " in time "+(finish-start));

    }


    private int HashFunction(int key) {
        int hash = 0;
        hash = (key % 100);
        hash = (Math.abs(hash) % database.length);
        return hash;
    }

    public int SearchItem(String name) {
        // could also make this binary for profiling improvements
        int index = 0, j = 0;
        for (int i = 0; i < name.length(); i++) {
            index += name.charAt(i);
        }
        index = HashFunction(index);
        while (!database[index].getName().equals(name)) {
            index = OpenAddressing(index);
            j++;
            if (j == 100) {
                return -1;
            }
        }
        return database[index].getIndex();

    }

    private int OpenAddressing(int key) {
        if (key == 99) {
            key = 0;
        }
        key++;
        return key;

    }


    public String SearchByID(int index) {

        // could make this binary for profiling improvements
        long start = System.nanoTime();
        for(int i = 0; i<database.length; i++)
        {
            if(database[i] != null && database[i].getIndex() == index)
            {
                long finish = System.nanoTime();
                System.out.println("Done in "+(finish-start)+" nanoseconds.");
                return database[i].getName();
            }

        }
        return null;
    }


}
        // BINARY SEARCH

//        int bIndex = 0;
//        int hIndex = database.length - 1;
//        int elementPos = -1;
//        // might need to compare the strings
//        while (bIndex <= hIndex) {
//            int midIndex = (bIndex + hIndex) / 2;
//            if (index == database[midIndex].getIndex()) {
//                elementPos = midIndex;
//                break;
//            } else if (index < database[midIndex].getIndex()) {
//                hIndex = midIndex-1;
//            } else if (index > database[midIndex].getIndex()) {
//                bIndex = midIndex+1;
//            }
//        }
//        // so we don't get out of the while??
//        if(elementPos==-1)
//        {
//            System.out.println("bad");
//        }
//        System.out.println(database[elementPos].getName());
//        return database[elementPos].getName();
//    }
//}


//        }
//
//
//
//        for(int i = 0; i<database.length; i++)
//        {
//            if(database[i] != null && database[i].getIndex() == index)
//            {
//                long finish = System.nanoTime();
//                System.out.println("Done in "+(finish-start)+" nanoseconds.");
//                return database[i].getName();
//            }
//
//        }
//        return null;
//    }


//}
