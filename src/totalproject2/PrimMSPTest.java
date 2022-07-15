package totalproject2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Template: Luca Tesei
 */
class PrimMSPTest {

    @Test
    final void testExceptions() {
        // Creo un grafo non orientato; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        PrimMSP<String> alg = new PrimMSP<String>();
        assertThrows(NullPointerException.class, () -> alg.computeMSP(null, null));
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        assertThrows(NullPointerException.class, () -> alg.computeMSP(gr, null));
        assertThrows(NullPointerException.class, () -> alg.computeMSP(null, a));
        GraphNode<String> b = new GraphNode<String>("b");
        assertThrows(IllegalArgumentException.class, () -> alg.computeMSP(gr, b));
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
        gr.addEdge(new GraphEdge<String>(d, e, false));
        gr.addEdge(new GraphEdge<String>(e, f, false, 10));
        gr.addEdge(new GraphEdge<String>(f, g, false, 2));
        gr.addEdge(new GraphEdge<String>(g, i, false, 6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));
        assertThrows(IllegalArgumentException.class, () -> alg.computeMSP(gr, b)); // C'è un arco senza peso
        gr.clear();
        gr.addNode(a);
        gr.addNode(b);
        gr.addNode(c);
        gr.addNode(d);
        gr.addNode(e);
        gr.addNode(f);
        gr.addNode(g);
        gr.addNode(h);
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
        gr.addEdge(new GraphEdge<String>(g, i, false, -6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));
        assertThrows(IllegalArgumentException.class, () -> alg.computeMSP(gr, b)); // C'è un arco con peso negativo
        Graph<String> gr1 = new AdjacencyMatrixDirectedGraph<String>();
        gr1.addNode(a);
        // Si sta provando a calcolare i cammini minimi su un grafo orientato
        assertThrows(IllegalArgumentException.class, () -> alg.computeMSP(gr1, a));
    }


    @Test
    final void testFindMSP() {
        // Aggiungo un ulteriore test per controllare il risultato
        // Creo un grafo non orientato; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
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
        gr.addEdge(new GraphEdge<String>(a, b, false, 17));
        gr.addEdge(new GraphEdge<String>(a, d, false, 6));
        gr.addEdge(new GraphEdge<String>(b, c, false, 3));
        gr.addEdge(new GraphEdge<String>(b, e, false, 9));
        gr.addEdge(new GraphEdge<String>(b, d, false, 5));
        gr.addEdge(new GraphEdge<String>(c, e, false, 10));
        gr.addEdge(new GraphEdge<String>(d, e, false, 1));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, a);
        // Controllo della priorità e dei predecessori di ciascun nodo dopo il calcolo dei cammini minimi
        assertTrue(a.getPriority() == 0);
        assertTrue(a.getPrevious() == null);
        assertTrue(d.getPriority() == 6);
        assertTrue(d.getPrevious() == a);
        assertTrue(e.getPriority() == 1);
        assertTrue(e.getPrevious() == d);
        assertTrue(b.getPriority() == 5);
        assertTrue(b.getPrevious() == d);
        assertTrue(c.getPriority() == 3);
        assertTrue(c.getPrevious() == b);
    }

    @Test
    final void testFindMSP1() {
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
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, a);
        assertTrue(a.getPrevious() == null);
        assertTrue(b.getPrevious() == a);
        assertTrue(c.getPrevious() == b);
        assertTrue(d.getPrevious() == c);
        assertTrue(e.getPrevious() == d);
        assertTrue(f.getPrevious() == c);
        assertTrue(g.getPrevious() == f);
        assertTrue(h.getPrevious() == g);
        assertTrue(i.getPrevious() == c);
    }

    @Test
    final void testFindMSP2() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<String>(a, b, false, 1));
        gr.addEdge(new GraphEdge<String>(a, c, false, 5));
        gr.addEdge(new GraphEdge<String>(b, d, false, 2));
        gr.addEdge(new GraphEdge<String>(b, c, false, 3));
        gr.addEdge(new GraphEdge<String>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, a);
        assertTrue(a.getPrevious() == null);
        assertTrue(a.getPriority() == 0);
        assertTrue(b.getPrevious() == a);
        assertTrue(b.getPriority() == 1);
        assertTrue(c.getPrevious() == b);
        assertTrue(c.getPriority() == 3);
        assertTrue(d.getPrevious() == b);
        assertTrue(d.getPriority() == 2);
    }

    @Test
    final void testFindMSP3() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<String>(a, b, false, 1));
        gr.addEdge(new GraphEdge<String>(a, c, false, 5));
        gr.addEdge(new GraphEdge<String>(b, d, false, 2));
        gr.addEdge(new GraphEdge<String>(b, c, false, 3));
        gr.addEdge(new GraphEdge<String>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, b);
        assertTrue(b.getPrevious() == null);
        assertTrue(a.getPrevious() == b);
        assertTrue(c.getPrevious() == b);
        assertTrue(d.getPrevious() == b);
    }

    @Test
    final void testFindMSP4() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<String>(a, b, false, 1));
        gr.addEdge(new GraphEdge<String>(a, c, false, 5));
        gr.addEdge(new GraphEdge<String>(b, d, false, 2));
        gr.addEdge(new GraphEdge<String>(b, c, false, 3));
        gr.addEdge(new GraphEdge<String>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, c);
        assertTrue(c.getPrevious() == null);
        assertTrue(a.getPrevious() == b);
        assertTrue(b.getPrevious() == c);

        assertTrue(d.getPrevious() == b);
    }

    @Test
    final void testFindMSP5() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<String>(a, b, false, 1));
        gr.addEdge(new GraphEdge<String>(a, c, false, 5));
        gr.addEdge(new GraphEdge<String>(b, d, false, 2));
        gr.addEdge(new GraphEdge<String>(b, c, false, 3));
        gr.addEdge(new GraphEdge<String>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, d);
        assertTrue(d.getPrevious() == null);
        assertTrue(c.getPrevious() == b);
        assertTrue(b.getPrevious() == d);

        assertTrue(a.getPrevious() == b);

    }
}
