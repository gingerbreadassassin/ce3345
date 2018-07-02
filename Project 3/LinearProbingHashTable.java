/**
 * @param <K>
 * @param <V>
 */
public class LinearProbingHashTable<K,V> {
    private static class Entry<K,V>{
        private K k;
        private V v;

        public void setK(K k) { this.k = k; }
        public void setV(V v) { this.v = v; }

        public K getK() { return k; }
        public V getV() { return v; }
    }

    Entry<K,V> table[];
    table = new Entry[10];

    /**
     * inserts entry, rehashes if half full, can re-use deleted entries, throws
     * exception if key is null, returns true if inserted, false if duplicate.
     * @param key
     * @param value
     * @return
     */
    public boolean insert(K key, V value) {
        return true;
    }

    /**
     * @param key
     * @return value for key, or null if not found
     */
    public V find(K key) {
        return V;
    }

    /**
     * marks the entry deleted but leaves it there
     * @param key
     * @return true if deleted, false if not found
     */
    public boolean delete(K key) {
        return false;
    }

    /**
     * doubles the table size, hashes everything to the new table, omitting
     * items marked deleted
     */
    private void rehash( ){

    }

    /**
     * @param key
     * @return the hash value for the given key. (this is the value before probing occurs)
     */
    public int getHashValue(K key) {
        return 0;
    }

    /**
     * @param key
     * @return returns the location for the given key, or -1 if not found.
     */
    public int getLocation(K key) {
        return 0;
    }

    /**
     * @return a formatted string of the hash table,
     *           where k, v is the key and value at this location:
     *                0  k, v
     *                1
     *                2  k, v   deleted
     *                ...
     */
    public String toString() {
        return "";
    }

    /**
     * demonstrate each of your methods
     * @param args
     */
    public static void main(String args[]) {

    }
}


