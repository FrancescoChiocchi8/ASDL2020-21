package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Template: Luca Tesei
 */
class FloydWarshallAllPairsShortestPathComputerTest {

    @Test
    final void testFloydWarshallAllPairsShortestPathComputer() {
        // Controllo che il metodo lanci le eccezioni; inserisco nodi e archi; creo un grafo diretto;
        assertThrows(NullPointerException.class,
                () -> new FloydWarshallAllPairsShortestPathComputer<String>(null));//grafo nullo
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(IllegalArgumentException.class,
                () -> new FloydWarshallAllPairsShortestPathComputer<String>(g));//grafo vuoto
        Graph<String> u = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(IllegalArgumentException.class,
                () -> new FloydWarshallAllPairsShortestPathComputer<String>(u));//grafo non orientato
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, b, true, 9);
        g.addEdge(e2);
        assertThrows(IllegalArgumentException.class,
                () -> new FloydWarshallAllPairsShortestPathComputer<String>(u));// Grafo con un arco senza peso
        g.clear();
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(c, d, true, 6);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, c, true, 9);
        g.addEdge(e4);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        // All'inizio computed è false perchè non è stato ancora eseguito il calcolo dei cammini minimi
        assertTrue(f.isComputed() == false);
    }

    @Test
    final void testComputeShortestPaths() {
        // Creo un grafo diretto; aggiungo nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(a, c, true, 3);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, d, true, 2);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 2);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(b, c, true, 6);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, d, true, 2);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(d, b, true, 3);
        g.addEdge(e6);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        // Calcolo dei cammini minimi tra tutte le coppie, computed = true
        f.computeShortestPaths();
        // Calcolo dei costi della matrice
        assertTrue(f.getCostMatrix()[0][0] == 0);
        assertTrue(f.getCostMatrix()[0][1] == 5);
        assertTrue(f.getCostMatrix()[0][2] == 3);
        assertTrue(f.getCostMatrix()[0][3] == 2);
        assertTrue(f.getCostMatrix()[1][0] == 2);
        assertTrue(f.getCostMatrix()[1][1] == 0);
        assertTrue(f.getCostMatrix()[1][2] == 5);
        assertTrue(f.getCostMatrix()[1][3] == 4);
        assertTrue(f.getCostMatrix()[2][0] == 7);
        assertTrue(f.getCostMatrix()[2][1] == 5);
        assertTrue(f.getCostMatrix()[2][2] == 0);
        assertTrue(f.getCostMatrix()[2][3] == 2);
        assertTrue(f.getCostMatrix()[3][0] == 5);
        assertTrue(f.getCostMatrix()[3][1] == 3);
        assertTrue(f.getCostMatrix()[3][2] == 8);
        assertTrue(f.getCostMatrix()[3][3] == 0);
        // Calcolo della matrice dei predecessori, l'indice 0 corrisponde al nodo a, l'indice 1 corrisponde al nodo b...
        assertTrue(f.getPredecessorMatrix()[0][0] == -1);
        assertTrue(f.getPredecessorMatrix()[0][1] == 3);
        assertTrue(f.getPredecessorMatrix()[0][2] == 0);
        assertTrue(f.getPredecessorMatrix()[0][3] == 0);
        assertTrue(f.getPredecessorMatrix()[1][0] == 1);
        assertTrue(f.getPredecessorMatrix()[1][1] == -1);
        assertTrue(f.getPredecessorMatrix()[1][2] == 0);
        assertTrue(f.getPredecessorMatrix()[1][3] == 0);
        assertTrue(f.getPredecessorMatrix()[2][0] == 1);
        assertTrue(f.getPredecessorMatrix()[2][1] == 3);
        assertTrue(f.getPredecessorMatrix()[2][2] == -1);
        assertTrue(f.getPredecessorMatrix()[2][3] == 2);
        assertTrue(f.getPredecessorMatrix()[3][0] == 1);
        assertTrue(f.getPredecessorMatrix()[3][1] == 3);
        assertTrue(f.getPredecessorMatrix()[3][2] == 0);
        assertTrue(f.getPredecessorMatrix()[3][3] == -1);
        // Creo un altro grafo per vedere se viene lanciata l'eccezione per cicli negativi nel grafo
        Graph<String> gr = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> x = new GraphNode<String>("x");
        gr.addNode(x);
        GraphNode<String> y = new GraphNode<String>("y");
        gr.addNode(y);
        GraphNode<String> z = new GraphNode<String>("z");
        gr.addNode(z);
        GraphEdge<String> e100 = new GraphEdge<String>(x, y, true, -6);
        gr.addEdge(e100);
        GraphEdge<String> e101 = new GraphEdge<String>(y, x, true, 5);
        gr.addEdge(e101);
        GraphEdge<String> e102 = new GraphEdge<String>(y, z, true, 10);
        gr.addEdge(e102);
        FloydWarshallAllPairsShortestPathComputer<String> f66 = new FloydWarshallAllPairsShortestPathComputer<String>(gr);
        assertThrows(IllegalStateException.class, () -> f66.computeShortestPaths());// Il grafo ha cicli negativi
    }

    /*
     * Test in più solo per controllare come opera l'algoritmo con un grafo i cui archi possono essere anche negativi.
     */
    @Test
    final void testComputeShortestPath2() {
        // Creo un grafo diretto; aggiungo nodi e archi, stavolta anche negativi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(a, c, true, 8);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, d, true, -2);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 3);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(b, c, true, -5);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, d, true, 2);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(d, b, true, 6);
        g.addEdge(e6);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        // Calcolo dei cammini minimi tra tutte le coppie, computed = true
        f.computeShortestPaths();
        // Calcolo dei costi della matrice
        assertTrue(f.getCostMatrix()[0][0] == 0);
        assertTrue(f.getCostMatrix()[0][1] == 4);
        assertTrue(f.getCostMatrix()[0][2] == -1);
        assertTrue(f.getCostMatrix()[0][3] == -2);
        assertTrue(f.getCostMatrix()[1][0] == 3);
        assertTrue(f.getCostMatrix()[1][1] == 0);
        assertTrue(f.getCostMatrix()[1][2] == -5);
        assertTrue(f.getCostMatrix()[1][3] == -3);
        assertTrue(f.getCostMatrix()[2][0] == 11);
        assertTrue(f.getCostMatrix()[2][1] == 8);
        assertTrue(f.getCostMatrix()[2][2] == 0);
        assertTrue(f.getCostMatrix()[2][3] == 2);
        assertTrue(f.getCostMatrix()[3][0] == 9);
        assertTrue(f.getCostMatrix()[3][1] == 6);
        assertTrue(f.getCostMatrix()[3][2] == 1);
        assertTrue(f.getCostMatrix()[3][3] == 0);
        // Calcolo della matrice dei predecessori, l'indice 0 corrisponde al nodo a, l'indice 1 corrisponde al nodo b...
        assertTrue(f.getPredecessorMatrix()[0][0] == -1);
        assertTrue(f.getPredecessorMatrix()[0][1] == 3);
        assertTrue(f.getPredecessorMatrix()[0][2] == 1);
        assertTrue(f.getPredecessorMatrix()[0][3] == 0);
        assertTrue(f.getPredecessorMatrix()[1][0] == 1);
        assertTrue(f.getPredecessorMatrix()[1][1] == -1);
        assertTrue(f.getPredecessorMatrix()[1][2] == 1);
        assertTrue(f.getPredecessorMatrix()[1][3] == 2);
        assertTrue(f.getPredecessorMatrix()[2][0] == 1);
        assertTrue(f.getPredecessorMatrix()[2][1] == 3);
        assertTrue(f.getPredecessorMatrix()[2][2] == -1);
        assertTrue(f.getPredecessorMatrix()[2][3] == 2);
        assertTrue(f.getPredecessorMatrix()[3][0] == 1);
        assertTrue(f.getPredecessorMatrix()[3][1] == 3);
        assertTrue(f.getPredecessorMatrix()[3][2] == 1);
        assertTrue(f.getPredecessorMatrix()[3][3] == -1);
    }

    @Test
    final void testIsComputed() {
        // Creo un grafo diretto; aggiungo nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(c, d, true, 6);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, c, true, 9);
        g.addEdge(e4);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        // Non è ancora stato eseguito il calcolo dei cammini minimi tra tutte le coppie di nodi del grafo, computed = false
        assertTrue(f.isComputed() == false);
        // -> computed = true perchè è stato eseguito il calcolo dei cammini minimi tra tutte le coppie di nodi del grafo
        f.computeShortestPaths();
        assertTrue(f.isComputed() == true);
    }

    @Test
    final void testGetGraph() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(c, d, true, 6);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, c, true, 9);
        g.addEdge(e4);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        assertTrue(f.getGraph().equals(g));
    }

    @Test
    final void testGetShortestPath() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true, 5);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 7);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(c, a, true, 10);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, b, true, 2);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(c, d, true, 2);
        g.addEdge(e6);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        assertThrows(IllegalStateException.class, () -> f.getShortestPath(a, b)); // computed = false
        f.computeShortestPaths();
        // Eccezioni con nodi null
        assertThrows(NullPointerException.class, () -> f.getShortestPath(null, null));
        assertThrows(NullPointerException.class, () -> f.getShortestPath(null, b));
        assertThrows(NullPointerException.class, () -> f.getShortestPath(a, null));
        GraphNode<String> sourceNode = new GraphNode<String>("sourceNode");
        GraphNode<String> targetNode = new GraphNode<String>("targetNode");
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(sourceNode, a));
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(a, targetNode));
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(sourceNode, targetNode));
        // Eccezione con d non appartenente al grafo g
        GraphNode<String> d1 = new GraphNode<String>("d1");
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(a, d1));
        // Creazione di una lista contenente gli archi di un percorso, via via aggiornata a seconda del confronto
        GraphNode<String> aTest = new GraphNode<String>("a");
        GraphNode<String> bTest = new GraphNode<String>("b");
        GraphNode<String> cTest = new GraphNode<String>("c");
        GraphNode<String> dTest = new GraphNode<String>("d");
        List<GraphEdge<String>> pathTest = new ArrayList<GraphEdge<String>>();
        // Restituisce la lista vuota se il nodo sorgente è uguale al nodo destinazione
        assertTrue(f.getShortestPath(aTest, aTest).equals(pathTest));
        // Restituisco null se nella matrice dei costi, il nodo a non è raggiungibile da d
        assertTrue(f.getShortestPath(dTest, aTest) == null);
        // Aggiungo gli archi e vedo se il percorso dal nodo a fino al nodo b è quello aspettato
        GraphEdge<String> e2Test = new GraphEdge<String>(aTest, cTest, true, 5);
        pathTest.add(e2Test);
        assertTrue(f.getShortestPath(aTest, cTest).equals(pathTest));
        GraphEdge<String> e5Test = new GraphEdge<String>(cTest, bTest, true, 2);
        pathTest.add(e5Test);
        assertTrue(f.getShortestPath(aTest, bTest).equals(pathTest));
        // Pulisco la lista, aggiungo gli archi e vedo se il percorso dal nodo b fino al nodo d è quello aspettato
        pathTest.clear();
        GraphEdge<String> e3Test = new GraphEdge<String>(bTest, aTest, true, 7);
        pathTest.add(e3Test);
        GraphEdge<String> e2Testt = new GraphEdge<String>(aTest, cTest, true, 5);
        pathTest.add(e2Testt);
        assertTrue(f.getShortestPath(bTest, cTest).equals(pathTest));
        GraphEdge<String> e6Test = new GraphEdge<String>(cTest, dTest, true, 2);
        pathTest.add(e6Test);
        assertTrue(f.getShortestPath(bTest, dTest).equals(pathTest));
        // Pulisco la lista, aggiungo gli archi e vedo se il percorso dal nodo c fino al nodo a è quello aspettato
        pathTest.clear();
        GraphEdge<String> e5Testt = new GraphEdge<String>(c, b, true, 2);
        pathTest.add(e5Testt);
        GraphEdge<String> e3Testt = new GraphEdge<String>(bTest, aTest, true, 7);
        pathTest.add(e3Testt);
        assertTrue(f.getShortestPath(cTest, aTest).equals(pathTest));
    }

    @Test
    final void testGetShortestPathCost() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> nR = new GraphNode<String>("nonRaggiungibile");
        g.addNode(nR);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true, 5);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 7);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(c, a, true, 10);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, b, true, 2);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(c, d, true, 2);
        g.addEdge(e6);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        assertThrows(IllegalStateException.class, () -> f.getShortestPathCost(a, b)); //computed = false
        f.computeShortestPaths();
        // Eccezioni con nodi null
        assertThrows(NullPointerException.class, () -> f.getShortestPathCost(null, null));
        assertThrows(NullPointerException.class, () -> f.getShortestPathCost(null, b));
        assertThrows(NullPointerException.class, () -> f.getShortestPathCost(a, null));
        GraphNode<String> sourceNode = new GraphNode<String>("sourceNode");
        GraphNode<String> targetNode = new GraphNode<String>("targetNode");
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(sourceNode, a));
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(a, targetNode));
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPath(sourceNode, targetNode));
        // Eccezione con d non appartenente al grafo g
        GraphNode<String> d1 = new GraphNode<String>("d1");
        assertThrows(IllegalArgumentException.class, () -> f.getShortestPathCost(a, d1));
        // nodo sorgente == nodo target
        // Controllo dei costi
        assertTrue(f.getShortestPathCost(a, a) == 0);
        assertTrue(f.getCostMatrix()[2][0] == Double.POSITIVE_INFINITY);
        assertTrue(f.getShortestPathCost(a, nR) == Double.POSITIVE_INFINITY);
        assertTrue(f.getShortestPathCost(a, b) == 7);
        assertTrue(f.getShortestPathCost(a, c) == 5);
        assertTrue(f.getShortestPathCost(a, d) == 7);
        assertTrue(f.getShortestPathCost(b, a) == 7);
        assertTrue(f.getShortestPathCost(b, b) == 0);
        assertTrue(f.getShortestPathCost(b, c) == 12);
        assertTrue(f.getShortestPathCost(b, d) == 14);
        assertTrue(f.getShortestPathCost(c, a) == 9);
        assertTrue(f.getShortestPathCost(c, b) == 2);
        assertTrue(f.getShortestPathCost(c, c) == 0);
        assertTrue(f.getShortestPathCost(c, d) == 2);
        assertTrue(f.getShortestPathCost(d, a) == Double.POSITIVE_INFINITY);
        assertTrue(f.getShortestPathCost(d, b) == Double.POSITIVE_INFINITY);
        assertTrue(f.getShortestPathCost(d, c) == Double.POSITIVE_INFINITY);
        assertTrue(f.getShortestPathCost(d, d) == 0);
    }

    @Test
    final void testPrintPath() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        f.computeShortestPaths();
        // Controllo dell'eccezione: path passato nullo
        assertThrows(NullPointerException.class, () -> f.printPath(null));
        List<GraphEdge<String>> path = new ArrayList<GraphEdge<String>>();
        assertTrue(f.printPath(path) == "[ ]"); //path vuoto
        GraphNode<String> aTest = new GraphNode<String>("a");
        GraphNode<String> bTest = new GraphNode<String>("b");
        GraphEdge<String> e1Test = new GraphEdge<String>(aTest, bTest, true, 8);
        path.add(e1Test);
        assertTrue(!f.printPath(path).isEmpty());
    }

    @Test
    final void testGetCostMatrix() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true, 5);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 7);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(c, a, true, 10);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, b, true, 2);
        g.addEdge(e5);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        f.computeShortestPaths();
        // Cotrollo dei costi della matrice
        assertTrue(f.getCostMatrix()[0][0] == 0);
        assertTrue(f.getCostMatrix()[0][1] == 7);
        assertTrue(f.getCostMatrix()[0][2] == 5);
        assertTrue(f.getCostMatrix()[1][0] == 7);
        assertTrue(f.getCostMatrix()[1][1] == 0);
        assertTrue(f.getCostMatrix()[1][2] == 12);
        assertTrue(f.getCostMatrix()[2][0] == 9);
        assertTrue(f.getCostMatrix()[2][1] == 2);
        assertTrue(f.getCostMatrix()[2][2] == 0);
    }

    @Test
    final void testGetPredecessorMatrix() {
        // Creo un grafo diretto; controllo che il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true, 8);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true, 5);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(b, a, true, 7);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(c, a, true, 10);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(c, b, true, 2);
        g.addEdge(e5);
        FloydWarshallAllPairsShortestPathComputer<String> f = new FloydWarshallAllPairsShortestPathComputer<String>(g);
        f.computeShortestPaths();
        // Controllo degli indici dei predecessori; a corrisponde all'indice 0, b all'indice 1, c all'indice 2.
        assertTrue(f.getPredecessorMatrix()[0][0] == -1);
        assertTrue(f.getPredecessorMatrix()[0][1] == 2);
        assertTrue(f.getPredecessorMatrix()[0][2] == 0);
        assertTrue(f.getPredecessorMatrix()[1][0] == 1);
        assertTrue(f.getPredecessorMatrix()[1][1] == -1);
        assertTrue(f.getPredecessorMatrix()[1][2] == 0);
        assertTrue(f.getPredecessorMatrix()[2][0] == 1);
        assertTrue(f.getPredecessorMatrix()[2][1] == 2);
        assertTrue(f.getPredecessorMatrix()[2][2] == -1);
    }
}
