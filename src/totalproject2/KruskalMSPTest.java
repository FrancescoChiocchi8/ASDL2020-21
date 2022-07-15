package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Template: Luca Tesei
 */
class KruskalMSPTest {

    @Test
    final void testExceptions() {
        // Creo un grafo non orientato; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        KruskalMSP<String> k = new KruskalMSP<String>();
        assertThrows(NullPointerException.class, () -> k.computeMSP(null));
        Graph<String> dir = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(IllegalArgumentException.class, () -> k.computeMSP(dir));
        // Vedo se il codice lancia una IllegalArgumentException passandogli un grafo con un arco con peso negativo
        Graph<String> w = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> n1 = new GraphNode<String>("n1");
        w.addNode(n1);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        w.addNode(n2);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        w.addNode(n3);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, false, -8.0);
        w.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(n1, n3, false, 7.0);
        w.addEdge(e2);
        assertThrows(IllegalArgumentException.class, () -> k.computeMSP(w));
        // Cancello tutto e vedo se il codice lancia una IllegalArgumentException passandogli 
        // un grafo con un arco senza peso
        w.clear();
        assertTrue(w.size() == 0);
        GraphNode<String> n4 = new GraphNode<String>("n4");
        w.addNode(n4);
        GraphNode<String> n5 = new GraphNode<String>("n5");
        w.addNode(n5);
        GraphNode<String> n6 = new GraphNode<String>("n6");
        w.addNode(n6);
        GraphEdge<String> e3 = new GraphEdge<String>(n4, n5, false, -8.0);
        w.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(n5, n6, false);
        w.addEdge(e4);
        assertThrows(IllegalArgumentException.class, () -> k.computeMSP(w));
    }

    @Test
    final void testComputeMSP2() {
        // Altro test per contollare il risultato
        Graph<String> u = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> n10 = new GraphNode<String>("n10");
        u.addNode(n10);
        GraphNode<String> n11 = new GraphNode<String>("n11");
        u.addNode(n11);
        GraphNode<String> n12 = new GraphNode<String>("n12");
        u.addNode(n12);
        GraphNode<String> n13 = new GraphNode<String>("n13");
        u.addNode(n13);
        GraphNode<String> n14 = new GraphNode<String>("n14");
        u.addNode(n14);
        GraphNode<String> n15 = new GraphNode<String>("n15");
        u.addNode(n15);
        GraphNode<String> n16 = new GraphNode<String>("n16");
        u.addNode(n16);
        GraphNode<String> n17 = new GraphNode<String>("n17");
        u.addNode(n17);
        GraphNode<String> n18 = new GraphNode<String>("n18");
        u.addNode(n18);
        u.addEdge(new GraphEdge<String>(n10, n11, false, 7));
        u.addEdge(new GraphEdge<String>(n10, n12, false, 8));
        u.addEdge(new GraphEdge<String>(n10, n13, false, 10));
        u.addEdge(new GraphEdge<String>(n11, n12, false, 11));
        u.addEdge(new GraphEdge<String>(n12, n13, false, 6));
        u.addEdge(new GraphEdge<String>(n12, n15, false, 12));
        u.addEdge(new GraphEdge<String>(n13, n14, false, 3));
        u.addEdge(new GraphEdge<String>(n13, n15, false, 4));
        u.addEdge(new GraphEdge<String>(n14, n15, false, 2));
        u.addEdge(new GraphEdge<String>(n14, n16, false, 8));
        u.addEdge(new GraphEdge<String>(n14, n17, false, 7));
        u.addEdge(new GraphEdge<String>(n15, n17, false, 20));
        u.addEdge(new GraphEdge<String>(n16, n17, false, 15));
        u.addEdge(new GraphEdge<String>(n16, n18, false, 1));
        u.addEdge(new GraphEdge<String>(n17, n18, false, 12));
        KruskalMSP<String> algo = new KruskalMSP<String>();
        Set<GraphEdge<String>> risultato = new HashSet<GraphEdge<String>>();
        risultato.add(new GraphEdge<String>(n10, n11, false, 7));
        risultato.add(new GraphEdge<String>(n10, n12, false, 8));
        risultato.add(new GraphEdge<String>(n12, n13, false, 6));
        risultato.add(new GraphEdge<String>(n13, n14, false, 3));
        risultato.add(new GraphEdge<String>(n14, n15, false, 2));
        risultato.add(new GraphEdge<String>(n14, n16, false, 8));
        risultato.add(new GraphEdge<String>(n14, n17, false, 7));
        risultato.add(new GraphEdge<String>(n16, n18, false, 1));
        assertTrue(algo.computeMSP(u).equals(risultato));
    }

    @Test
    final void testComputeMSP() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        GraphNode<String> e = new GraphNode<String>("e");
        gr.addNode(e);
        GraphNode<String> f = new GraphNode<String>("f");
        gr.addNode(f);
        GraphNode<String> g = new GraphNode<String>("g");
        gr.addNode(g);
        GraphNode<String> h = new GraphNode<String>("h");
        gr.addNode(h);
        GraphNode<String> i = new GraphNode<String>("i");
        gr.addNode(i);
        gr.addEdge(new GraphEdge<String>(a, b, false, 4));
        gr.addEdge(new GraphEdge<String>(a, h, false, 8.5));
        gr.addEdge(new GraphEdge<String>(b, h, false, 11));
        gr.addEdge(new GraphEdge<String>(b, c, false, 8));
        gr.addEdge(new GraphEdge<String>(c, i, false, 2));
        gr.addEdge(new GraphEdge<String>(c, d, false, 7));
        gr.addEdge(new GraphEdge<String>(c, f, false, 4));
        gr.addEdge(new GraphEdge<String>(d, f, false, 14));
        gr.addEdge(new GraphEdge<String>(d, e, false, 9));
        gr.addEdge(new GraphEdge<String>(e, f, false, 10));
        gr.addEdge(new GraphEdge<String>(f, g, false, 2));
        gr.addEdge(new GraphEdge<String>(g, i, false, 6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));
        KruskalMSP<String> alg = new KruskalMSP<String>();
        Set<GraphEdge<String>> result = new HashSet<GraphEdge<String>>();
        result.add(new GraphEdge<String>(a, b, false, 4));
        result.add(new GraphEdge<String>(b, c, false, 8));
        result.add(new GraphEdge<String>(c, i, false, 2));
        result.add(new GraphEdge<String>(c, d, false, 7));
        result.add(new GraphEdge<String>(c, f, false, 4));
        result.add(new GraphEdge<String>(d, e, false, 9));
        result.add(new GraphEdge<String>(f, g, false, 2));
        result.add(new GraphEdge<String>(g, h, false, 1));
        assertTrue(alg.computeMSP(gr).equals(result));
    }
}
