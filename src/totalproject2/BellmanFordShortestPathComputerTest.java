package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Template: Luca Tesei
 */
class BellmanFordShortestPathComputerTest {

    @Test
    final void testBellmanFordShortestPathComputer() {
        // Creo un grafo diretto; controllo che il costruttore lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class,
                () -> new BellmanFordShortestPathComputer<String>(null)); // Grafo nullo
        assertThrows(IllegalArgumentException.class,
                () -> new BellmanFordShortestPathComputer<String>(g)); // Grafo vuoto
        Graph<String> u = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(IllegalArgumentException.class,
                () -> new BellmanFordShortestPathComputer<String>(u)); // Grafo non diretto
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(b, a, true, 7);
        g.addEdge(e2);
        assertThrows(IllegalArgumentException.class,
                () -> new BellmanFordShortestPathComputer<String>(g)); // Grafo che contiene almeno un arco senza peso
    }

    @Test
    final void testComputeShortestPathsFrom() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e100 = new GraphEdge<String>(b, c, true, 8);
        g.addEdge(e100);
        BellmanFordShortestPathComputer<String> d = new BellmanFordShortestPathComputer<String>(g);
        assertThrows(NullPointerException.class, () -> d.computeShortestPathsFrom(null));
        assertThrows(IllegalArgumentException.class, () -> d.computeShortestPathsFrom(a));
        g.clear();
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        GraphNode<String> t = new GraphNode<String>("t");
        g.addNode(t);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphNode<String> y = new GraphNode<String>("y");
        g.addNode(y);
        GraphNode<String> z = new GraphNode<String>("z");
        g.addNode(z);
        GraphEdge<String> e1 = new GraphEdge<String>(s, t, true, 6);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(s, y, true, 7);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(t, y, true, 8);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(t, x, true, 5);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(t, z, true, -4);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(y, z, true, 9);
        g.addEdge(e6);
        GraphEdge<String> e7 = new GraphEdge<String>(y, x, true, -3);
        g.addEdge(e7);
        GraphEdge<String> e8 = new GraphEdge<String>(x, t, true, -2);
        g.addEdge(e8);
        GraphEdge<String> e9 = new GraphEdge<String>(z, x, true, 7);
        g.addEdge(e9);
        GraphEdge<String> e10 = new GraphEdge<String>(z, s, true, 2);
        g.addEdge(e10);
        // Creo un oggetto di tipo BellmanFordShortestPathComputer per effettuare le operazioni sul grafo
        BellmanFordShortestPathComputer<String> bel = new BellmanFordShortestPathComputer<String>(g);
        // Calcolo dei cammini minimi
        bel.computeShortestPathsFrom(s);
        // Controllo delle priorità e dei predecessori
        assertTrue(s.getPriority() == 0);
        assertTrue(s.getPrevious() == null);
        assertTrue(y.getPriority() == 7);
        assertTrue(y.getPrevious() == s);
        assertTrue(t.getPriority() == 2);
        assertTrue(t.getPrevious() == x);
        assertTrue(x.getPriority() == 4);
        assertTrue(x.getPrevious() == y);
        assertTrue(z.getPriority() == -2);
        assertTrue(z.getPrevious() == t);
        GraphEdge<String> e11 = new GraphEdge<String>(x, y, true, -7);
        g.addEdge(e11);
        // Grafo con ciclo negativo, lancio un'eccezione di tipo IllegalStateException
        assertThrows(IllegalStateException.class, () -> bel.computeShortestPathsFrom(s));
    }

    @Test
    final void testIsComputed() {
        // Creo un grafo diretto; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        // Creo un oggetto di tipo BellmanFordShortestPathComputer per effettuare le operazioni sul grafo
        BellmanFordShortestPathComputer<String> c = new BellmanFordShortestPathComputer<String>(g);
        // Controllo che ancora non sia stato effettuato il calcolo dei cammini minimi da una sorgente singola
        assertTrue(c.isComputed() == false);
        // Ora viene effettuato il calcolo dei cammini minimi, quindi isComputed() restituirà true
        c.computeShortestPathsFrom(a);
        assertTrue(c.isComputed() == true);
        c.computeShortestPathsFrom(b);
        assertTrue(c.isComputed() == true);
    }

    @Test
    final void testGetLastSource() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        // Creo un oggetto di tipo BellmanFordShortestPathComputer per effettuare le operazioni sul grafo
        BellmanFordShortestPathComputer<String> c = new BellmanFordShortestPathComputer<String>(g);
        assertThrows(IllegalStateException.class, () -> c.getLastSource());
        c.computeShortestPathsFrom(a);
        // Controllo che l'ultima sorgente su cui è stato effettuato il calcolo dei cammini minimi sia a
        assertTrue(c.getLastSource().equals(a));
        c.computeShortestPathsFrom(b);
        // Controllo che l'ultima sorgente su cui è stato effettuato il calcolo dei cammini minimi sia b
        assertTrue(c.getLastSource().equals(b));
    }

    @Test
    final void testGetGraph() {
        // Creo un grafo diretto; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        // Creo un oggetto di tipo BellmanFordShortestPathComputer per effettuare le operazioni sul grafo
        BellmanFordShortestPathComputer<String> c = new BellmanFordShortestPathComputer<String>(g);
        // Controllo che il grafo preso in considerazione dal'oggetto sia il grafo g
        assertTrue(c.getGraph() == g);
    }

    @Test
    final void testGetShortestPathTo() {
        // Preso dalla classe di DjikstraShortestPathComputerTest visto che il metodo di funzionamento è simile
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ns = new GraphNode<String>("s");
        g.addNode(ns);
        GraphNode<String> nu = new GraphNode<String>("u");
        g.addNode(nu);
        GraphEdge<String> esu = new GraphEdge<String>(ns, nu, true, 10.1);
        g.addEdge(esu);
        GraphNode<String> nx = new GraphNode<String>("x");
        g.addNode(nx);
        GraphEdge<String> esx = new GraphEdge<String>(ns, nx, true, 5.12);
        g.addEdge(esx);
        GraphEdge<String> eux = new GraphEdge<String>(nu, nx, true, 2.05);
        g.addEdge(eux);
        GraphEdge<String> exu = new GraphEdge<String>(nx, nu, true, 3.04);
        g.addEdge(exu);
        GraphNode<String> ny = new GraphNode<String>("y");
        g.addNode(ny);
        GraphEdge<String> exy = new GraphEdge<String>(nx, ny, true, 2.0);
        g.addEdge(exy);
        GraphEdge<String> eys = new GraphEdge<String>(ny, ns, true, 7.03);
        g.addEdge(eys);
        GraphNode<String> nv = new GraphNode<String>("v");
        g.addNode(nv);
        GraphEdge<String> euv = new GraphEdge<String>(nu, nv, true, 1.0);
        g.addEdge(euv);
        GraphEdge<String> exv = new GraphEdge<String>(nx, nv, true, 9.05);
        g.addEdge(exv);
        GraphEdge<String> eyv = new GraphEdge<String>(ny, nv, true, 6.0);
        g.addEdge(eyv);
        GraphEdge<String> evy = new GraphEdge<String>(nv, ny, true, 4.07);
        g.addEdge(evy);
        BellmanFordShortestPathComputer<String> c = new BellmanFordShortestPathComputer<String>(g);
        GraphNode<String> nsTest = new GraphNode<String>("s");
        c.computeShortestPathsFrom(nsTest);
        List<GraphEdge<String>> pathTest = new ArrayList<GraphEdge<String>>();
        assertTrue(c.getShortestPathTo(nsTest).equals(pathTest));
        GraphNode<String> nuTest = new GraphNode<String>("u");
        GraphNode<String> nxTest = new GraphNode<String>("x");
        GraphEdge<String> esxTest = new GraphEdge<String>(nsTest, nxTest, true,
                5.12);
        pathTest.add(esxTest);
        assertTrue(c.getShortestPathTo(nxTest).equals(pathTest));
        GraphEdge<String> exuTest = new GraphEdge<String>(nxTest, nuTest, true,
                3.04);
        pathTest.add(exuTest);
        assertTrue(c.getShortestPathTo(nuTest).equals(pathTest));
        GraphNode<String> nvTest = new GraphNode<String>("v");
        GraphEdge<String> euvTest = new GraphEdge<String>(nuTest, nvTest, true,
                1.0);
        pathTest.add(euvTest);
        assertTrue(c.getShortestPathTo(nvTest).equals(pathTest));
        pathTest.clear();
        pathTest.add(esxTest);
        GraphNode<String> nyTest = new GraphNode<String>("y");
        GraphEdge<String> exyTest = new GraphEdge<String>(nxTest, nyTest, true,
                2.0);
        pathTest.add(exyTest);
        assertTrue(c.getShortestPathTo(nyTest).equals(pathTest));
    }
}
