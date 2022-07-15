package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class MapAdjacentListUndirectedGraphTest {

    @Test
    final void testNodeCount() {
        // Creo un grafo non orientato; inserisco i nodi
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertTrue(g.nodeCount() == 0);
        g.addNode(new GraphNode<String>("n1"));
        assertTrue(g.nodeCount() == 1);
        g.addNode(new GraphNode<String>("n2"));
        assertTrue(g.nodeCount() == 2);
        g.addNode(new GraphNode<String>("n2"));
        //Non viene conteggiato quest'ultimo nodo perchè un nodo con la stessa etichetta è già stato aggiunto in precedenza
        assertTrue(g.nodeCount() == 2);
        g.addNode(new GraphNode<String>("n3"));
        // Mi assicuro che il nodeCount() abbia valore 3
        assertTrue(g.nodeCount() == 3);
        assertTrue(g.size() == 3);

    }

    @Test
    final void testEdgeCount() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertTrue(g.edgeCount() == 0);
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        assertTrue(g.edgeCount() == 0);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false, 8.9);
        g.addEdge(e1);
        // Mi assicuro che il conto degli archi sia stato aggiornato 
        assertTrue(g.edgeCount() == 1);
        g.addEdge(e1);
        // Non viene aumentato perchè inserirei nel grafo un arco già presente
        assertTrue(g.edgeCount() == 1);
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphEdge<String> e2 = new GraphEdge<String>(s, a, false, 2.7);
        g.addEdge(e2);
        assertTrue(g.edgeCount() == 2);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e6 = new GraphEdge<String>(s, b, false, 7.62);
        g.addEdge(e6);
        assertTrue(g.edgeCount() == 3);
        GraphEdge<String> e3 = new GraphEdge<String>(d, b, false, 7.62);
        g.addEdge(e3);
        assertTrue(g.edgeCount() == 4);
        GraphEdge<String> e4 = new GraphEdge<String>(d, b, false, 8.92);
        g.addEdge(e4);
        assertTrue(g.edgeCount() == 4);
        GraphEdge<String> e5 = new GraphEdge<String>(b, d, false, 89.62);
        g.addEdge(e5);
        assertTrue(g.edgeCount() == 4);
        GraphEdge<String> e7 = new GraphEdge<String>(b, b, false, 8); // Cappio
        g.addEdge(e7);
        // Mi assicuro che il conto degli archi sia stato aggiornato 
        assertTrue(g.edgeCount() == 5);
        GraphEdge<String> e8 = new GraphEdge<String>(b, b, false, 8); // Cappio
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(e8));
        assertTrue(g.edgeCount() == 5);
    }

    @Test
    final void testClear() {
        // Creo un grafo non orientato; inserisco nodi e archi
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertTrue(g.isEmpty());
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        assertFalse(g.isEmpty());
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, false, 8.0);
        g.addEdge(e1);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        assertTrue(g.size() == 4);
        // Mi assicuro che il grafo non sia vuoto
        assertFalse(g.isEmpty());
        g.clear();
        // Dopo quest'operazione il grafo è vuoto
        assertTrue(g.isEmpty());
    }

    @Test
    final void testIsDirected() {
        // Creo un grafo non orientato; controllo se è non orientato
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertTrue(!g.isDirected());
        assertFalse(g.isDirected());
    }

    @Test
    final void testGetNodes() {
        // Creo un grafo non orientato; inserisco nodi e archi
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphNode<String>> set = g.getNodes();
        assertTrue(set.isEmpty());
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        assertTrue(g.edgeCount() == 0);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        set = g.getNodes();
        Set<GraphNode<String>> testSet = new HashSet<GraphNode<String>>();
        GraphNode<String> sTest = new GraphNode<String>("n1");
        GraphNode<String> dTest = new GraphNode<String>("n2");
        GraphNode<String> tTest = new GraphNode<String>("n3");
        testSet.add(dTest);
        testSet.add(sTest);
        testSet.add(tTest);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(set.equals(testSet));
        GraphNode<String> d2Test = new GraphNode<String>("n2");
        g.addNode(d2Test);
        set = g.getNodes();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(set.equals(testSet));
    }

    @Test
    final void testAddNode() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.addNode(null));
        assertTrue(g.size() == 0);
        GraphNode<String> s = new GraphNode<String>("s");
        GraphNode<String> sTest = new GraphNode<String>("s");
        assertTrue(!g.containsNode(s));
        g.addNode(s);
        g.addNode(sTest);
        assertTrue(g.size() == 1);
        assertTrue(g.containsNode(sTest));
        GraphNode<String> d = new GraphNode<String>("d");
        GraphNode<String> dTest = new GraphNode<String>("d");
        g.addNode(d);
        // Mi assicuro che il nodo con l'etichetta d è stato aggiunto
        assertTrue(g.containsNode(dTest));
        assertTrue(g.size() == 2);
    }

    @Test
    final void testRemoveNode() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.removeNode(null));
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        assertTrue(g.nodeCount() == 1);
        g.removeNode(s);
        assertTrue(g.nodeCount() == 0);
        assertTrue(g.size() == 0);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        // Significa che restituisce vero perchè lo rimuove
        assertTrue(g.removeNode(d));
        // Restituisce falso perchè il nodo non è più presente nel grafo
        assertFalse(g.removeNode(s));
        assertTrue(g.nodeCount() == 1);
        assertTrue(g.size() == 1);

    }

    @Test
    final void testContainsNode() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.containsNode(null));
        GraphNode<String> s = new GraphNode<String>("s");
        GraphNode<String> sTest = new GraphNode<String>("s");
        assertTrue(!g.containsNode(sTest));
        assertTrue(g.isEmpty());
        g.addNode(s);
        // Mi assicuro che il grafo contenga il nodo con etichetta "s"
        assertFalse(!g.containsNode(sTest));
        assertTrue(!g.isEmpty());
    }

    @Test
    final void testGetNodeOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getNodeOf(null));
        GraphNode<String> s = new GraphNode<String>("s");
        s.setHandle(1);
        g.addNode(s);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphNode<String> n1 = g.getNodeOf("s");
        assertEquals("s", n1.getLabel());
        n1 = g.getNodeOf("d");
        assertEquals("d", n1.getLabel());
        assertTrue(n1.getLabel() == "d");
        assertTrue(n1.getHandle() == 0);
        assertTrue(g.getNodeOf("p") == null);
    }

    @Test
    final void testGetNodeIndexOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getNodeIndexOf(null));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        assertThrows(UnsupportedOperationException.class, () -> g.getNodeIndexOf("d"));
    }

    @Test
    final void testGetNodeAtIndex() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        assertThrows(UnsupportedOperationException.class, () -> g.getNodeAtIndex(1));
    }

    @Test
    final void testGetEdge() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> n1 = new GraphNode<String>("n1");
        GraphNode<String> n2 = new GraphNode<String>("n2");
        assertThrows(NullPointerException.class, () -> g.getEdge(null, n1));
        assertThrows(NullPointerException.class, () -> g.getEdge(n2, null));
        assertThrows(IllegalArgumentException.class, () -> g.getEdge(n1, n2));
        g.addNode(n1);
        g.addNode(n2);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, false);
        g.addEdge(e1);
        // Mi assicuro che l'arco che collega n1 con n2 sia e1
        assertTrue(g.getEdge(n1, n2).equals(e1));
        // Mi assicuro che l'arco che collega n2 con n1 sia sempre e1
        assertTrue(g.getEdge(n2, n1).equals(e1));
        GraphNode<String> n3 = new GraphNode<String>("n3");
        GraphNode<String> n4 = new GraphNode<String>("n4");
        g.addNode(n3);
        g.addNode(n4);
        GraphEdge<String> e2 = new GraphEdge<String>(n1, n3, false);
        g.addEdge(e2);
        // Mi assicuro che l'arco che collega n1 con n3 sia e2
        assertTrue(g.getEdge(n1, n3).equals(e2));
        // Mi assicuro che l'arco che collega n3 con n1 sia sempre e2
        assertTrue(g.getEdge(n3, n1).equals(e2));
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> d1 = new GraphNode<String>("d1");
        GraphNode<String> d2 = new GraphNode<String>("d2");
        g.addNode(d1);
        g.addNode(d2);
        assertThrows(UnsupportedOperationException.class, () -> g.getEdgeAtNodeIndexes(1, 2));
    }

    @Test
    final void testGetAdjacentNodesOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getAdjacentNodesOf(null));
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphNode<String>> adjNodes = new HashSet<GraphNode<String>>();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(a).equals(adjNodes));
        GraphNode<String> aTest = new GraphNode<String>("a");
        GraphNode<String> b = new GraphNode<String>("b");
        GraphNode<String> bTest = new GraphNode<String>("b");
        assertThrows(IllegalArgumentException.class, () -> g.getAdjacentNodesOf(b));
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, false);
        g.addEdge(e1);
        GraphNode<String> c = new GraphNode<String>("c");
        GraphNode<String> cTest = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, false);
        g.addEdge(e2);
        adjNodes.add(bTest);
        adjNodes.add(cTest);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(aTest).equals(adjNodes));
        adjNodes.clear();
        adjNodes.add(aTest);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(bTest).equals(adjNodes));
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(cTest).equals(adjNodes));
    }

    @Test
    final void testGetPredecessorNodesOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        assertThrows(UnsupportedOperationException.class, () -> g.getPredecessorNodesOf(d));
    }

    @Test
    final void testGetEdges() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphEdge<String>> testEdges = new HashSet<GraphEdge<String>>();
        assertTrue(g.getEdges().equals(testEdges));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false, 8.01);
        g.addEdge(e1);
        testEdges.add(e1);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphEdge<String> e2 = new GraphEdge<String>(s, x, false, 8.6);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(d, x, false, 43.2);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(x, d, false, 86.2);
        g.addEdge(e4);
        testEdges.add(e2);
        testEdges.add(e3);
        testEdges.add(e4);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        GraphNode<String> y = new GraphNode<String>("y");
        g.addNode(y);
        GraphEdge<String> e5 = new GraphEdge<String>(x, y, false, 12.30);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(y, s, false, 17.40);
        g.addEdge(e6);
        testEdges.add(e5);
        testEdges.add(e6);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        g.clear();
        testEdges.clear();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
    }

    @Test
    final void testAddEdge() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.addEdge(null));
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        GraphNode<String> d = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(new GraphEdge<String>(s, d, true)));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(new GraphEdge<String>(s, d, false)));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(new GraphEdge<String>(d, s, false)));
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false, 8.0);
        assertTrue(g.addEdge(e1));
        assertTrue(g.edgeCount() == 1);
        assertTrue(g.containsEdge(new GraphEdge<String>(s, d, false, 9.0)));
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e2 = new GraphEdge<String>(s, b, false, 7.62);
        g.addEdge(e2);
        assertTrue(g.edgeCount() == 2);
        assertTrue(g.containsEdge(e2));
        GraphEdge<String> e3 = new GraphEdge<String>(d, b, false, 7.62);
        g.addEdge(e3);
        assertTrue(g.edgeCount() == 3);
        assertTrue(g.containsEdge(e3));
        GraphEdge<String> e4 = new GraphEdge<String>(d, b, false, 8.92);
        g.addEdge(e4);
        assertTrue(g.edgeCount() == 3);
        GraphEdge<String> e5 = new GraphEdge<String>(b, d, false, 89.62);
        g.addEdge(e5);
        assertTrue(g.edgeCount() == 3);
        GraphEdge<String> e7 = new GraphEdge<String>(b, b, false, 8); // Cappio
        g.addEdge(e7);
        // Mi assicuro che l'arco è stato inserito correttamente
        assertTrue(g.edgeCount() == 4);
        assertTrue(g.containsEdge(e7));
        GraphEdge<String> e8 = new GraphEdge<String>(b, b, false, 8); // Tentativo di inserire un cappio due volte
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(e8));
        assertTrue(g.edgeCount() == 4);
    }

    @Test
    final void testRemoveEdge() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.containsEdge(null));
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        GraphNode<String> d = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(s, d, false)));
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(d, s, false)));
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false, 89.0);
        g.addEdge(e1);
        assertTrue(g.edgeCount() == 1);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphEdge<String> e2 = new GraphEdge<String>(s, x, false, 5.0);
        g.addEdge(e2);
        assertTrue(g.edgeCount() == 2);
        g.removeEdge(e1);
        assertTrue(g.edgeCount() == 1);
        assertTrue(!g.containsEdge(e1));
        // Sono rimasti solo i 3 nodi e l'arco e2
        assertTrue(g.size() == 4);
    }

    @Test
    final void testContainsEdge() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.containsEdge(null));
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        GraphNode<String> d = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(s, d, false)));
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(d, s, false)));
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false);
        assertTrue(!g.containsEdge(new GraphEdge<String>(s, d, false)));
        g.addEdge(e1);
        // Mi assicuro che esiste già un arco che va da s a d e viceversa
        assertTrue(g.containsEdge(new GraphEdge<String>(s, d, false)));
        assertTrue(g.containsEdge(new GraphEdge<String>(d, s, false)));
    }

    @Test
    final void testGetEdgesOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphEdge<String>> testEdges = new HashSet<GraphEdge<String>>();
        assertThrows(NullPointerException.class, () -> g.getEdgesOf(null));
        GraphNode<String> d = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.getEdgesOf(d));
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, false);
        g.addEdge(e1);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphEdge<String> e2 = new GraphEdge<String>(s, x, false);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(d, x, false);
        g.addEdge(e3);
        testEdges.add(e1);
        testEdges.add(e2);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(s).equals(testEdges));
        testEdges.add(e3);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertFalse(g.getEdgesOf(s).equals(testEdges));
        testEdges.clear();
        testEdges.add(e2);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertFalse(g.getEdgesOf(x).equals(testEdges));
        testEdges.add(e3);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(x).equals(testEdges));
        testEdges.clear();
        assertFalse(g.getEdgesOf(s).equals(testEdges));
        GraphEdge<String> e4 = new GraphEdge<String>(x, d, false);
        g.addEdge(e4);
        assertTrue(g.edgeCount() == 3);
        GraphNode<String> y = new GraphNode<String>("y");
        g.addNode(y);
        GraphEdge<String> e5 = new GraphEdge<String>(x, y, false);
        g.addEdge(e5);
        GraphEdge<String> e6 = new GraphEdge<String>(y, s, false);
        g.addEdge(e6);
        testEdges.add(e1);
        testEdges.add(e2);
        testEdges.add(e6);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(s).equals(testEdges));
        testEdges.clear();
        testEdges.add(e5);
        testEdges.add(e6);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(y).equals(testEdges));
        testEdges.clear();
        testEdges.add(e3);
        testEdges.add(e1);
        testEdges.add(e4);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(d).equals(testEdges));

    }

    @Test
    final void testGetIngoingEdgesOf() {
        // Creo un grafo non orientato; inserisco nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> d1 = new GraphNode<String>("d1");
        GraphNode<String> d2 = new GraphNode<String>("d2");
        g.addNode(d1);
        g.addNode(d2);
        assertThrows(UnsupportedOperationException.class, () -> g.getIngoingEdgesOf(d1));
    }

    @Test
    final void testMapAdjacentListUndirectedGraph() {
        // Creo un grafo non orientato;
        Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
        // Controllo che il grafo sia stato inizializzato correttamente
        assertTrue(g.isEmpty());
    }

}
