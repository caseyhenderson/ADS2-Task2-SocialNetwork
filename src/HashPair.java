public class HashPair {
    String name;
    int index;
    // formerly previous when using a chained approach
    HashPair(String name, int index)
    {
        this.name=name;
        this.index=index;
    }
    String getName()
    {
        return name;
    }

    int getIndex()
    {
        return index;
    }
}
