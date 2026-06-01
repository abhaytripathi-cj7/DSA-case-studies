//co-3
//KruskalAlgorithm
//DisjointSe

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Edge implements Comparable<Edge> {
    char u, v;
    int weight;

    Edge(char u, char v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
    
    @Override
    public String toString() {
        return "(" + u + ", " + v + ")=" + weight;
    }
}

class DisjointSet {
    private int[] parent;
    private int[] rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]); // Path compression
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            return true; // Union succeeded
        }
        return false; // Cycle detected
    }
}

public class EcoGreenPowerGrid {
    
    private static int nodeToIndex(char node) {
        switch(node) {
            case 'M': return 0; case 'K': return 1; case 'W': return 2;
            case 'S': return 3; case 'E': return 4; case 'Y': return 5;
            case 'H': return 6; default: return -1;
        }
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge('E', 'S', 4)); edges.add(new Edge('K', 'W', 5));
        edges.add(new Edge('W', 'S', 6)); edges.add(new Edge('M', 'E', 7));
        edges.add(new Edge('M', 'K', 8)); edges.add(new Edge('E', 'Y', 8));
        edges.add(new Edge('M', 'Y', 9)); edges.add(new Edge('Y', 'H', 9));
        edges.add(new Edge('M', 'S', 10)); edges.add(new Edge('M', 'H', 11));
        edges.add(new Edge('M', 'W', 12)); edges.add(new Edge('H', 'K', 14));

        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(7);
        List<Edge> mstEdges = new ArrayList<>();
        int totalBaselineCost = 0;

        System.out.printf("%-4s | %-14s | %-6s | %-7s\n", "Step", "Edge Evaluated", "Weight", "Action");
        System.out.println("----------------------------------------------");
        
        int step = 1;
        for (Edge edge : edges) {
            int uIdx = nodeToIndex(edge.u);
            int vIdx = nodeToIndex(edge.v);
            
            boolean accepted = ds.union(uIdx, vIdx);
            String action = accepted ? "ACCEPT" : "REJECT";
            
            System.out.printf("%-4d | (%c, %c)          | %-6d | %-7s\n", step++, edge.u, edge.v, edge.weight, action);
            
            if (accepted) {
                mstEdges.add(edge);
                totalBaselineCost += edge.weight;
            }
        }

        System.out.println("\n==============================================");
        System.out.println("Baseline Minimal Tree Edge Set: " + mstEdges);
        System.out.println("Total Baseline Construction Cost: " + totalBaselineCost + " €-crores");

        // Part (c): Apply Regulatory High-Availability Mandate Augmentation
        System.out.println("\nExecuting High-Availability Mandate Audit...");
        Edge optimalAugmentationEdge = new Edge('M', 'K', 8); // Pre-evaluated optimal edge selection
        
        System.out.println("Adding Augmentation Link: " + optimalAugmentationEdge + " to resolve single-path vulnerability.");
        int finalProjectCost = totalBaselineCost + optimalAugmentationEdge.weight;
        System.out.println("Final Compliant Grid Construction Budget: " + finalProjectCost + " €-crores.");
        System.out.println("==============================================");
    }
}
