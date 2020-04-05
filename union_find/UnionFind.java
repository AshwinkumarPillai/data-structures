/**
 * Union Find / Disjoint Set implementation.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */
public class UnionFind {
    private int size, numOfComponenets;
    private int[] sizearr, id;

    public UnionFind(int size) {
        if (size <= 0)
            throw new IllegalArgumentException();
        this.size = numOfComponenets = size;
        sizearr = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i;
            sizearr[i] = i;
        }
    }

    // Find root of the element
    public int find(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }

        // Path compression
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    // check whether two nodes are connected
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int componentSize(int p) {
        return sizearr[find(p)];
    }

    public int size() {
        return size;
    }

    public int numOfComponenets() {
        return numOfComponenets;
    }

    public void unify(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);

        if (root1 == root2)
            return;

        if (sizearr[root1] > sizearr[root2]) {
            id[root2] = root1;
            sizearr[root1] += sizearr[root2];
        } else {
            id[root1] = root2;
            sizearr[root2] += sizearr[root1];
        }

        numOfComponenets--;
    }
}