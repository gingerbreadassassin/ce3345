import java.util.Arrays;
import java.util.Collections;

public class Selection {

    private Integer[] list = {-4598,-5679,-3231,-9457,-2822,-3457,-6371,-8743};
    private BinaryHeap<Integer> heap = new BinaryHeap<>(list);

    public Selection(int n) {
        printList();
        findIt(n);
    }

    public Selection(int n, int m){
        list = new Integer[m];
        for(int i = 0; i< m; i++){
            list[i] = -i;
        }
        Collections.shuffle(Arrays.asList(list));
        heap = new BinaryHeap<>(list);
        printList();
        findIt(n);
    }

    private void printList(){
        System.out.println("Printing the list: ");
        for(int e : list) {
            System.out.println(-e);
        }
        System.out.println();
    }

    private void findIt(int n) {
        int i = 0;
        int l = n;
        while(n-- > 0){
            i = heap.deleteMin();
        }
        System.out.println(String.format("The %d largest element is: %d", l, -i));
    }

    public static void main (String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java Selection [n] [m]");
            System.out.println("This program selects the nth largest number from a list.");
            System.out.println("A default list is used if only \"n\" is specified.");
            System.out.println("Otherwise, you may specify a list of size 1-9999 for \"m\"");
            System.out.println("which will randomly generate a list of that length.");
        } else {
            if(args.length == 1) {
                try {
                    int n = Integer.parseInt(args[0]);
                    if(n >= 1 && n <= 8){
                        new Selection(n);
                    } else {
                        System.out.println("Please choose n between 1 and 8 for default run!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("You didn't enter a number I could understand.");
                }
            } else if(args.length == 2) {
                try {
                    int n = Integer.parseInt(args[0]);
                    int m = Integer.parseInt(args[1]);
                    if((n >= 1 && n <= m) && (m <= 9999)){
                        new Selection(n, m);
                    } else {
                        System.out.println("Please choose n between 1 and m, and m between n and 9999 for random run!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("You didn't enter a number I could understand.");
                }
            }
        }
    }
}
