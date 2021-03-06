import org.omg.CORBA.Any;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }

    private void clear( )
    {
        doClear( );
    }

    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }

    public boolean isEmpty( )
    {
        return size( ) == 0;
    }

    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean add( AnyType x )
    {
        add( size( ), x );
        return true;
    }

    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }

    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }


    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }

    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }

    /**
     * Checks whether the specified index is within the limits of the list
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     */
    private void checkBounds(int idx, int lower, int upper)
    {
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "index: " + idx + "; size: " + size( ) + ". Called by " + Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */
    private Node<AnyType> getNode(int idx, @SuppressWarnings("SameParameterValue") int lower, int upper )
    {
        Node<AnyType> p;

        checkBounds(idx, lower, upper);

        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        }

        return p;
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }

    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * Receives two index positions as parameters and swaps the two nodes
     * (the nodes, not just the values inside) at these positions, provided
     * both positions are within the current size
     * @param idx1 the index of the first node to swap.
     * @param idx2 the index of the second node to swap.
     */
    public void swap( int idx1, int idx2)
    {
        Node<AnyType> a = getNode(idx1);
        Node<AnyType> b = getNode(idx2);

        a.prev.next = b;
        a.next.prev = b;
        b.prev.next = a;
        b.next.prev = a;

        Node<AnyType> c = a.prev;
        a.prev = b.prev;
        b.prev = c;
        c = a.next;
        a.next = b.next;
        b.next = c;
    }

    /**
     * Returns a new MyLinkedList that has the elements in reverse order.
     * @return a reversed version of this list
     */
    public MyLinkedList<AnyType> reverse( )
    {
        MyLinkedList<AnyType> r = new MyLinkedList<>();
        for(AnyType x : this)
        {
            r.add(0, x);
        }

        return r;
    }

    /**
     * receives an index position and number of elements as parameters, and
     * removes elements beginning at the index position for the number of
     * elements specified, provided the index position is within the size
     * and together with the number of elements does not exceed the size
     * @param idx the index of the first node to be erased.
     * @param num the number of nodes to erase
     */
    public void erase(int idx, int num)
    {
        checkBounds(idx, 0, size() - 1);
        checkBounds(idx + num - 1, 0, size() - 1);

        // check if zero elements are to be erased
        if(num <= 0)
        {
            return;
        }

        Node<AnyType> s = getNode(idx);

        Node<AnyType> e = getNode( idx + num - 1 );

        e.next.prev = s.prev;
        s.prev.next = e.next;
    }

    /**
     * receives a List and an index position as parameters, and copies all of the
     * passed list into the existing list at the position specified by the parameter,
     * provided the index position does not exceed the size
     * @param idx the index where the new list should be inserted.
     * @param l the new list to be inserted.
     */
    public void insertList(int idx, MyLinkedList<AnyType> l)
    {
        checkBounds(idx, 0, size() - 1);
        for(AnyType x : l)
        {
            this.add(idx++, x);
        }
    }

    /**
     * receives an integer and shifts the list this many nodes forward or backward,
     * for example, if passed 2, the first two nodes move to the tail, or if
     * passed -3, the last three nodes move to the front.
     *
     * e.g. +2:  abcde -> cdeab       -3:  abcde ->  cdeab
     *
     * @param spaces the number of spaces by which to shift this list
     */

    public void shift(int spaces)
    {
        while(spaces < 0)
        {
            AnyType x = remove(0);
            add(x);
            spaces++;
        }
        while(spaces > 0)
        {
            AnyType x = remove(size() - 1);
            add(0, x);
            spaces--;
        }
    }

    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append(x).append(" ");
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext( )
        {
            return current != endMarker;
        }

        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( );

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );

            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }

        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }

    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );

        for( int i = 0; i < 10; i++ )
            lst.add( i );
        for( int i = 20; i < 30; i++ )
            lst.add( 0, i );

        System.out.println("Here's the initial list:");
        System.out.println( lst );

        //demonstrate reverse
        MyLinkedList<Integer> newList = lst.reverse();
        System.out.println("Here's the reversed list:");
        System.out.println( newList );

        //demonstrate swap
        lst.swap(3, 5);
        System.out.println("Here's the list with elements 3 and 5 swapped:");
        System.out.println( lst );

        // demonstrate swap with invalid index
        try
        {
            System.out.println("Swap method trying to use out of bounds index!");
            lst.swap(8, lst.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught out of bounds exception:");
            System.out.println(e.toString());
        }

        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        System.out.println("Here's the list with first and last elements removed:");
        System.out.println( lst );

        // demonstrate erase
        lst.erase(6, 3);
        System.out.println("Here's the list with elements 6-8 removed:");
        System.out.println( lst );

        // demonstrate erase with invalid index
        try
        {
            System.out.println("Erase method trying to use out of bounds index!");
            lst.erase(8, lst.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught out of bounds exception:");
            System.out.println(e.toString());
        }

        // demonstrate insertList
        newList.doClear();
        newList.add(111);
        newList.add(222);
        newList.add(333);
        System.out.println("Here's the list to be inserted:");
        System.out.println(newList);
        lst.insertList(10, newList);
        System.out.println("Here's the list with another list inserted:");
        System.out.println( lst );

        // demonstrate insertList with invalid index
        try
        {
            System.out.println("InsertList method trying to use out of bounds index!");
            lst.insertList(lst.size(), newList);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught out of bounds exception:");
            System.out.println(e.toString());
        }

        // demonstrate shift
        lst.shift(-4);
        System.out.println("Here's the list shifted 4 spaces left:");
        System.out.println( lst );
        lst.shift(6);
        System.out.println("Here's the list shifted 6 spaces right:");
        System.out.println( lst );

        // demonstrate remove
        System.out.println("Remove the first element until empty:");
        java.util.Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) )
        {
            itr.next( );
            itr.remove( );
            System.out.println( lst );
        }
    }
}