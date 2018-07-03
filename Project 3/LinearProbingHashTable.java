import java.io.Serializable;

/**
 * @param <K>
 * @param <V>
 */
public class LinearProbingHashTable<K,V> {
    /**
     * Construct the hash table
     */
    public LinearProbingHashTable( ) {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size
     */
    public LinearProbingHashTable( int size ) {
        allocateTable( size );
        makeEmpty();
    }

    /**
     * Make the hash table logically empty
     */
    public void makeEmpty( ) {
        currentSize = 0;
        occupied = 0;
        for( int i = 0; i < table.length; i++ )
            table[ i ] = null;
    }



    private static class Entry<K,V>{
        public K key;
        public V value;
        public boolean isActive;

        public Entry( K key, V value ) {
            this(key, value, true );
        }

        public Entry( K k, V v, boolean i ) {
            key = k; value = v; isActive = i;
        }

    }

    /**
     * inserts entry, rehashes if half full, can re-use deleted entries, throws
     * exception if key is null, returns true if inserted, false if duplicate.
     * @param key the key to insert
     * @param value the value to insert
     * @return true if inserted, false if duplicate
     */
    public boolean insert(K key, V value) {
        if( key == null )
            throw new IllegalArgumentException("Key is null!");
        int currentPos = getLocation( key );
        if( isActive( currentPos ))
            return false;

        if( table[ currentPos ] == null )
            ++occupied;
        table[ currentPos ] = new Entry<>( key, value );
        currentSize++;

        if( occupied > table.length /2 )
            rehash( );

        return true;
    }

    /**
     * Find an item in the hash table.
     * @param key the item to earch for
     * @return value for key, or null if not found
     */
    public V find(K key) {
        int currentPos = getLocation( key );
        return table[ currentPos ].value;
    }

    /**
     * marks the entry deleted but leaves it there
     * @param key
     * @return true if deleted, false if not found
     */
    public boolean delete(K key) {
        int currentPos = getLocation( key );
        if( isActive( currentPos )) {
            table[ currentPos ].isActive = false;
            currentSize--;
            return true;
        }
        return false;
    }


    /**
     * @param key
     * @return the hash value for the given key. (this is the value before probing occurs)
     */
    public int getHashValue(K key) {
        return myhash( key );
    }

    /**
     * @return a formatted string of the hash table,
     *           where key, value is the key and value at this location:
     *                0  key, value
     *                1
     *                2  key, value   deleted
     *                ...
     */
    public String toString() {
        StringBuilder t = new StringBuilder();
        int i = 0;
        for( Entry<K, V> entry : table) {
            if( entry == null )
                t.append(String.format("%d%n", i));
            else if( entry.isActive )
                t.append(String.format("%d  %s  %s  %n",
                        i, entry.key.toString(), entry.value.toString()));
            else
                t.append(String.format("%d  %s  %s  %s%n",
                        i, entry.key.toString(), entry.value.toString(), "deleted"));
            i++;
        }
        return t.toString();
    }

    private static final int DEFAULT_TABLE_SIZE = 11;

    private Entry<K, V> [ ] table;  // The table of elements
    private int occupied;           // The number of occupied cells
    private int currentSize;        // Current size

    /**
     * Internal method to allocate table.
     * @param tableSize the size of the table.
     */
    private void allocateTable( int tableSize ) {
        table = new Entry[ nextPrime( tableSize )];
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to getLocation.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos ) {
        return table[ currentPos ] != null && table[ currentPos ].isActive;
    }

    /**
     * @param key
     * @return returns the location for the given key, or -1 if not found.
     */
    public int getLocation(K key) {
        int currentPos = myhash( key );

        while ( table[ currentPos ] != null &&
                !table[ currentPos ].key.equals( key )) {
            currentPos++;
            if( currentPos >= table.length )
                currentPos -= table.length;
        }
        return currentPos;
    }

    /**
     * doubles the table size, hashes everything to the new table, omitting
     * items marked deleted
     */
    private void rehash( ){
        Entry<K, V> [ ] oldTable = table;

        allocateTable( 2 * oldTable.length );
        occupied = 0;
        currentSize = 0;

        for( Entry<K, V> entry : oldTable )
            if( entry != null && entry.isActive )
                insert( entry.key, entry.value );

    }

    private int myhash( K x ) {

        int hashVal = x.hashCode();

        hashVal %= table.length;
        if( hashVal < 0 )
            hashVal += table.length;

        return hashVal;
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive)
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n ) {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * @param n the number to test
     * @return the result of the test
     */
    private static boolean isPrime( int n ) {
        if( n ==2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i < n; i+= 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    /**
     * demonstrate each of your methods
     * @param args
     */
    public static void main(String args[]) {
        LinearProbingHashTable<String, Serializable> H = new LinearProbingHashTable<>( );
        H.insert("First Name", "Connor");
        H.insert("Last Name", "Ness");
        System.out.print("Trying insertion of \"Employee ID, 10\": ");
        System.out.println(H.insert("Employee ID", 10));
        System.out.print("Trying insertion of \"Employee ID, 12.2\": ");
        System.out.println(H.insert("Employee ID", 12.2));
        System.out.print("Trying deletion of non-existent key, \"test\": ");
        System.out.println(H.delete("test"));
        System.out.print("Trying deletion of \"Employee ID\": ");
        System.out.println(H.delete("Employee ID"));
        System.out.print("Trying insertion of \"Employee ID, 12.2\": ");
        System.out.println(H.insert("Employee ID", 12.2));
        System.out.println("Printing table: ");
        System.out.println(H);
        System.out.print("Hash value of \"Employee ID\": ");
        System.out.println(H.getHashValue("Employee ID"));
        System.out.print("Location of \"Employee ID\": ");
        System.out.println(H.getLocation("Employee ID"));
        System.out.println();

        System.out.println("Inserting lots of values:");
        for( int i = 0; i < 30; i++)
            H.insert(String.format("%d", i), String.format("Key %d", i));

        System.out.println("Printing new table: ");
        System.out.println(H);
        System.out.print("Hash value of \"Employee ID\": ");
        System.out.println(H.getHashValue("Employee ID"));
        System.out.print("Location of \"Employee ID\": ");
        System.out.println(H.getLocation("Employee ID"));

        System.out.print("Trying insertion of null key: ");
        try {H.insert(null, 10);} catch (IllegalArgumentException x) {
            System.out.println(x.getMessage());
        }

    }
}


