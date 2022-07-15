package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class DijkstraShortestPathComputerTest {

    @Test
    final void testException() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class,
                () -> new DijkstraShortestPathComputer<String>(null)); // Grafo nullo
        assertThrows(IllegalArgumentException.class,
                () -> new DijkstraShortestPathComputer<String>(g)); // Grafo vuoto
        Graph<String> u = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(IllegalArgumentException.class,
                () -> new DijkstraShortestPathComputer<String>(u)); // Grafo non diretto
        //a rispetto a GraphNode, è un oggetto della classe GraphNode. GraphNode definisce un tipo di dato
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        assertThrows(IllegalArgumentException.class,
                () -> new DijkstraShortestPathComputer<String>(g)); // Grafo che contiene almeno un arco senza peso
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true, -7);
        g.addEdge(e2);
        assertThrows(IllegalArgumentException.class,
                () -> new DijkstraShortestPathComputer<String>(g)); // Grafo con almeno un peso negativo
        // Controllo delle eccezioni di computeShortestPath
        Graph<String> gr = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> n1 = new GraphNode<String>("n1");
        gr.addNode(n1);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        gr.addNode(n2);
        GraphEdge<String> e4 = new GraphEdge<String>(n1, n2, true, 7);
        gr.addEdge(e4);
        DijkstraShortestPathComputer<String> d = new DijkstraShortestPathComputer<String>(gr);
        assertThrows(NullPointerException.class, () -> d.computeShortestPathsFrom(null)); // Nodo passato == null
        assertThrows(IllegalArgumentException.class, () -> d.computeShortestPathsFrom(a)); // Nodo non associato al grafo
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
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(g);
        assertThrows(IllegalStateException.class, () -> c.getLastSource());
        c.computeShortestPathsFrom(a);
        // Controllo dell'ultima sorgente
        assertTrue(c.getLastSource().equals(a));
        c.computeShortestPathsFrom(b);
        assertTrue(c.getLastSource().equals(b));
        assertFalse(c.getLastSource().equals(a));
    }

    @Test
    final void testIsComputed() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(g);
        // Non è ancora stato eseguito il calcolo dei cammini minimi con sorgente singola, computed = false
        assertTrue(c.isComputed() == false);
        c.computeShortestPathsFrom(a);
        // -> computed = true perchè è stato eseguito il calcolo dei cammini minimi con sorgente singola
        assertTrue(c.isComputed() == true);
        c.computeShortestPathsFrom(b);
        assertTrue(c.isComputed() == true);
    }

    @Test
    final void testGetShortestPathTo1() {
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
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(
                g);
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

    @Test
    final void testGetShortestPathTo2() {
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
        GraphNode<String> np = new GraphNode<String>("p");
        g.addNode(np);
        GraphEdge<String> epv = new GraphEdge<String>(np, nv, true, 1.0);
        g.addEdge(epv);
        // p è collegato a v, ma non è raggiungibile da s
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(
                g);
        GraphNode<String> nsTest = new GraphNode<String>("s");
        c.computeShortestPathsFrom(nsTest);
        GraphNode<String> npTest = new GraphNode<String>("p");
        assertTrue(c.getShortestPathTo(npTest) == null);
    }
}
