import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    public int[] maxRoot;

    /**
     * Initializes an empty union-find data structure with
     * {@code n} elements {@code 0} through {@code n-1}.
     * Initially, each element is in its own set.
     *
     * @param  n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        maxRoot = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            maxRoot[i] = i;
        }
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param  p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public static int findMax(int x,int y)
    {
        if (x > y) return x;
        return y;
    }

    private void findCananiccalElement(int p,int q)
    {
        maxRoot[p]=findMax(p,q);
        maxRoot[q]=findMax(p,q);
        while (p != parent[p]) {
            maxRoot[p] = findMax(maxRoot[p],parent[p]);
            maxRoot[p] = findMax(maxRoot[q],parent[p]);
            maxRoot[parent[p]] = findMax(maxRoot[parent[p]],maxRoot[p]);
            maxRoot[parent[p]] = findMax(maxRoot[parent[p]],maxRoot[q]);

            p = parent[p];
        }
    }

    private int getMaxCanonocalElement(int i)
    {

        while (i != maxRoot[i]) {
            i = maxRoot[i];
        }
        return i;
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns true if the two elements are in the same set.
     *
     * @param  p one element
     * @param  q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     * @deprecated Replace with two calls to {@link #find(int)}.
     */
    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the set containing element {@code p} with the set
     * containing element {@code q}.
     *
     * @param  p one element
     * @param  q the other element
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
        findCananiccalElement(p,q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /**
     * Reads an integer {@code n} and a sequence of pairs of integers
     * (between {@code 0} and {@code n-1}) from standard input, where each integer
     * in the pair represents some element;
     * if the elements are in different sets, merge the two sets
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("uf\\log.txt");
        Scanner sc = new Scanner(file);

        int n = StdIn.readInt();
        UF uf = new UF(n);

        while (sc.hasNextLine())
        {
            String[] data = sc.nextLine().split(" ");
            int p = Integer.parseInt(data[0]);
            int q = Integer.parseInt(data[1]);
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);

        }






//        while (!StdIn.isEmpty()) {
//            int p = StdIn.readInt();
//            int q = StdIn.readInt();
//
//            if (uf.find(p) == uf.find(q)) continue;
//            uf.union(p, q);
//            StdOut.println(p + " " + q);
//        }
        StdOut.println(uf.count() + " components");
        int i = (StdIn.readInt());


        System.out.println("max component::"+uf.getMaxCanonocalElement(i));
    }


}

