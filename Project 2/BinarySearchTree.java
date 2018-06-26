// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import org.omg.CORBA.Any;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
@SuppressWarnings({"WeakerAccess", "StatementWithEmptyBody"})
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }

    /**
     * returns an int of the number of nodes in the tree.  Use recursion.
     * @param t the node that roots the subtree.
     */
    private int size( BinaryNode<AnyType> t )
    {
        if( t == null ){ return 0; }
        return 1 + size( t.left ) + size( t.right );
    }
    public int size() {return size(this.root);}

    /**
     * Returns the number of nodes that have no children.  Use recursion.
     * @param t the node that roots the subtree.
     */
    private int numLeaves( BinaryNode<AnyType> t )
    {
        if( t == null ){ return 0; }
        else if( t.left == null && t.right == null ){ return 1; }
        else return numLeaves( t.left ) + numLeaves( t.right );
    }
    public int numLeaves(){return numLeaves( this.root );}

    /**
     * Returns the number of nodes that have a left child.  Use recursion.
     * @param t the node that roots the subtree.
     */
    private int numLeftChildren( BinaryNode<AnyType> t )
    {
        if( t == null ){ return 0; }
        else if ( t.left == null ){return numLeftChildren( t.right ); }
        else return 1 + numLeftChildren( t.left ) + numLeftChildren( t.right );
    }
    public int numLeftChildren(){ return numLeftChildren( this.root ); }

    /**
     * Returns true if every node has either two children or no children.
     * (Assume an empty tree is full.)  Use recursion.
     * @param t the node that roots the subtree.
     */
    private boolean isFull ( BinaryNode<AnyType> t )
    {
        if ( t == null){ return true; }
        else if ( t.left == null && t.right == null ){ return true; }
        else if ( t.left != null && t.right != null ) {
            return isFull(t.left) && isFull(t.right);
        }
        else return false;
    }
    public boolean isFull(){ return isFull( this.root ); }

    /**
     * Returns true if every level of its height is filled.
     */
    public boolean isPerfect ( )
    {
        return Math.pow(2, height(this.root)+1)-1 ==size();
    }

    /**
     * @param x The value of an element to find in the tree
     * @param t The root of the Tree
     * @return The depth, if found, of the element
     */
    private int depth( AnyType x, BinaryNode<AnyType> t)
    {
        if( t == null )
            return -1;

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            return 1 + depth( x, t.left );
        else if( compareResult > 0 )
            return 1 + depth( x, t.right );
        else
            return 1;    // Match
    }
    public int depth( AnyType x){ return depth( x, this.root ); }

    /**
     * Print the root, then its children, then their children, etc.
     * This can be done using a queue.  Enqueue the root, then while the queue
     * is not empty, dequeue and print, and enqueue its children.
     */
    public void printByLevels( )
    {
        MyLinkedList<BinaryNode<AnyType>> q = new MyLinkedList<>();
        if( this.root == null ){ return; }
        else {
            q.add(this.root);
        }

        while ( !q.isEmpty() )
        {
            BinaryNode<AnyType> x = q.remove(0);
            if ( x.left != null ){ q.add( x.left ); }
            if ( x.right != null ){ q.add( x.right ); }
            System.out.print( x.element );
            System.out.print(" ");
            if(q.size() > 0) {
                if (depth(x.element) < depth(q.get(0).element)) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }
    
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;


        // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );

        class PrintStuff {
            public void doit()
            {
                t.printByLevels();
                System.out.print("Tree has this many nodes: ");
                System.out.println(t.size());
                System.out.print("Tree has this many leaves: ");
                System.out.println(t.numLeaves());
                System.out.print("Tree has this many left children: ");
                System.out.println(t.numLeftChildren());
                System.out.print("The tree is full: ");
                System.out.println(t.isFull());
                System.out.print("The tree is perfect: ");
                System.out.println(t.isPerfect());
            }
        }

        PrintStuff printer = new PrintStuff();

        printer.doit();

        t.insert(50);
        t.insert(25);
        t.insert(75);
        t.insert(20);
        t.insert(30);
        t.insert(70);
        t.insert(80);

        printer.doit();

        t.insert(15);

        printer.doit();

        t.insert(65);

        printer.doit();

        t.insert(21);
        t.insert(71);

        printer.doit();

        t.insert(27);
        t.insert(35);
        t.insert(77);
        t.insert(85);

        printer.doit();
    }
}
