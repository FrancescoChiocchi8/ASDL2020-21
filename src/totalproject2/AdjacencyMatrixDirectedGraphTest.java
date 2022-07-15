package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class AdjacencyMatrixDirectedGraphTest {

    @Test
    final void testNodeCount() {
        // Creo un grafo diretto; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(g.nodeCount() == 0);
        g.addNode(new GraphNode<>("n1"));
        assertTrue(g.nodeCount() == 1);
        g.addNode(new GraphNode<>("n2"));
        assertTrue(g.nodeCount() == 2);
        g.addNode(new GraphNode<>("n2"));
        //Non viene conteggiato quest'ultimo nodo perchè un nodo con la stessa etichetta è già stato aggiunto in precedenza
        assertTrue(g.nodeCount() == 2);
        g.addNode(new GraphNode<>("n3"));
        // Mi assicuro che il nodeCount() abbia valore 3
        assertTrue(g.nodeCount() == 3);
        assertTrue(g.size() == 3);
    }

    @Test
    final void testEdgeCount() {
        // Creo un grafo diretto; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<>();
        assertTrue(g.edgeCount() == 0);
        GraphNode<String> n1 = new GraphNode<>("n1");
        g.addNode(n1);
        assertTrue(g.edgeCount() == 0);
        GraphNode<String> n2 = new GraphNode<>("n2");
        g.addNode(n2);
        GraphEdge<String> e1 = new GraphEdge<>(n1, n2, true);
        g.addEdge(e1);
        // Mi assicuro che il conto degli archi sia stato aggiornato 
        assertTrue(g.edgeCount() == 1);
        g.addEdge(e1);
        // Non viene aumentato perchè inserirei nel grafo un arco già presente
        assertTrue(g.edgeCount() == 1);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        GraphEdge<String> e2 = new GraphEdge<String>(n2, n3, true);
        g.addEdge(e2);
        // Mi assicuro che il conto degli archi sia stato aggiornato 
        assertTrue(g.edgeCount() == 2);
        g.clear();
        // Dopo quest'operazione il contatore degli archi dovrà essere azzerato
        assertTrue(g.edgeCount() == 0);
    }

    @Test
    final void testClear() {
        // Creo un grafo diretto
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        // Mi assicuro che il grafo appena creato sia vuoto
        assertTrue(g.isEmpty());
        // Inserisco nodi e archi
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        assertFalse(g.isEmpty());
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, true);
        g.addEdge(e1);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        assertTrue(g.size() == 4);
        // Mi accerto che il grafo non sia vuoto
        assertFalse(g.isEmpty());
        g.clear();
        // Dopo quest'operazione il grafo sarà vuoto e la size sarà 0
        assertTrue(g.isEmpty());
        assertTrue(g.size() == 0);
    }

    @Test
    final void testIsDirected() {
        // Creo un grafo diretto;
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        // Mi assicuro che il grafo creato sia diretto
        assertTrue(g.isDirected());
        assertFalse(!g.isDirected());
    }

    @Test
    final void testGetNodes() {
        // Creo un grafo diretto; aggiungo nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        // Creo un insieme contenente i nodi del grafo (in questo caso sono assenti) e vedo se è vero
        Set<GraphNode<String>> set = g.getNodes();
        assertTrue(set.isEmpty());
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        // Aggiorno l'insieme con i nuovi nodi del grafo appena inseriti
        set = g.getNodes();
        // Creo un altro insieme per confrontarlo con l'altro insieme
        Set<GraphNode<String>> testSet = new HashSet<GraphNode<String>>();
        GraphNode<String> n1Test = new GraphNode<String>("n1");
        GraphNode<String> n2Test = new GraphNode<String>("n2");
        GraphNode<String> n3Test = new GraphNode<String>("n3");
        testSet.add(n1Test);
        testSet.add(n2Test);
        testSet.add(n3Test);
        // Mi assicuro che gli insieme contengano gli stessi nodi
        assertTrue(set.equals(testSet));
        GraphNode<String> n21Test = new GraphNode<String>("n2");
        g.addNode(n21Test);
        set = g.getNodes();
        assertTrue(set.equals(testSet));
    }

    @Test
    final void testAddNode() {
        // Creo un grafo diretto; aggiungo nodi e archi; controllo che il metodo lanci le eccezioni
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.addNode(null));
        assertTrue(g.size() == 0);
        GraphNode<String> s = new GraphNode<String>("s");
        GraphNode<String> sTest = new GraphNode<String>("s");
        assertTrue(!g.containsNode(s));
        g.addNode(s);
        g.addNode(sTest);
        assertTrue(g.size() == 1);
        // Mi assicuro che ora il grafo contenga il nodo con etichetta "s"
        assertTrue(g.containsNode(sTest));
        GraphNode<String> d = new GraphNode<String>("d");
        GraphNode<String> dTest = new GraphNode<String>("d");
        g.addNode(d);
        // Mi assicuro che ora il grafo contenga il nodo con etichetta "d"
        assertTrue(g.containsNode(dTest));
        // La dimensione è 2 perchè l'aggiunta del nodo sTest viene ignorata perchè un nodo con la 
        // stessa etichetta è già stato aggiunto
        assertTrue(g.size() == 2);
    }

    @Test
    final void testRemoveNode() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.removeNode(null));
        GraphNode<String> n = new GraphNode<String>("n");
        g.addNode(n);
        assertThrows(UnsupportedOperationException.class, () -> g.removeNode(n));
    }

    @Test
    final void testContainsNode() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.containsNode(null));
        GraphNode<String> s = new GraphNode<String>("s");
        GraphNode<String> sTest = new GraphNode<String>("s");
        // Mi assicuro che il grafo non contenga il nodo con etichetta "s"
        assertTrue(!g.containsNode(sTest));
        assertTrue(g.isEmpty());
        g.addNode(s);
        // Mi assicuro che il grafo contenga il nodo con etichetta "s"
        assertTrue(g.containsNode(s));
        assertTrue(g.containsNode(sTest));
        assertTrue(!g.isEmpty());
        assertTrue(g.size() == 1);
        assertTrue(g.nodeCount() == 1);
    }

    @Test
    final void testGetNodeOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
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
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getNodeIndexOf(null));
        GraphNode<String> r = new GraphNode<String>("r");
        g.addNode(r);
        assertThrows(IllegalArgumentException.class, () -> g.getNodeIndexOf("d"));
        g.clear();
        GraphNode<String> s = new GraphNode<String>("s");
        s.setHandle(1);
        g.addNode(s);
        assertTrue(g.getNodeIndexOf("s") == 0);
        assertThrows(IllegalArgumentException.class, () -> g.getNodeIndexOf("d"));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        assertTrue(g.getNodeIndexOf("d") == 1);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        // Controllo se gli indici sono nella posizione esatta
        assertTrue(g.getNodeIndexOf("s") == 0);
        assertTrue(g.getNodeIndexOf("d") == 1);
        assertTrue(g.getNodeIndexOf("x") == 2);
    }

    @Test
    final void testGetNodeAtIndex() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(IndexOutOfBoundsException.class, () -> g.getNodeAtIndex(5));
        GraphNode<String> s = new GraphNode<String>("s");
        s.setHandle(1);
        g.addNode(s);
        assertThrows(IndexOutOfBoundsException.class, () -> g.getNodeAtIndex(1));
        GraphNode<String> sTest = new GraphNode<String>("s");
        // Mi assicuro che il nodo sTest sia nella posizione 1
        assertTrue(sTest.equals(g.getNodeAtIndex(0)));
        assertTrue(g.getNodeAtIndex(0).getHandle() == 1);
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        assertThrows(IndexOutOfBoundsException.class, () -> g.getNodeAtIndex(2));
        GraphNode<String> dTest = new GraphNode<String>("d");
        // Mi assicuro che il nodo dTest sia nella posizione 1
        assertTrue(dTest.equals(g.getNodeAtIndex(1)));

    }

    @Test
    final void testGetEdge() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> n1 = new GraphNode<String>("n1");
        GraphNode<String> n2 = new GraphNode<String>("n2");
        assertThrows(NullPointerException.class, () -> g.getEdge(null, null));
        assertThrows(NullPointerException.class, () -> g.getEdge(n1, null));
        assertThrows(NullPointerException.class, () -> g.getEdge(null, n2));
        g.addNode(n1);
        assertThrows(IllegalArgumentException.class, () -> g.getEdge(n1, n2));
        g.addNode(n2);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, true);
        g.addEdge(e1);
        assertTrue(g.getEdge(n1, n2).equals(e1));
        // System.out.println(g.getEdge(n1,  n2));
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        GraphEdge<String> e2 = new GraphEdge<String>(n2, n3, true);
        g.addEdge(e2);
        // Mi assicuro che tra n2 e n3 ci sia l'arco e2
        assertTrue(g.getEdge(n2, n3) == e2);
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(IndexOutOfBoundsException.class, () -> g.getEdgeAtNodeIndexes(2, 0));
        GraphNode<String> s = new GraphNode<String>("s");
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(s);
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, true);
        g.addEdge(e1);
        GraphEdge<String> e2 = new GraphEdge<String>(s, s, true);
        g.addEdge(e2);
        // Siccome s è il primo nodo inserito, è nella posizione 0
        assertTrue(g.getEdgeAtNodeIndexes(0, 1) == (e1));
        assertTrue(g.getEdgeAtNodeIndexes(0, 0) == (e2)); // Cappio ammesso
        // Ampliamento di nodeCount() dopo creazione e aggiunta nel grafo del nuovo nodo
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphEdge<String> e3 = new GraphEdge<String>(x, d, true);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(x, s, true);
        g.addEdge(e4);
        // Mi assicuro che nella posizione (2, 1) ci sia l'arco e3
        assertTrue(g.getEdgeAtNodeIndexes(2, 1) == e3);
        assertTrue(g.getEdgeAtNodeIndexes(2, 0).equals(e4));
        assertTrue(g.getEdgeAtNodeIndexes(1, 1) == null); // d non ha cappi
    }

    @Test
    final void testGetAdjacentNodesOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getAdjacentNodesOf(null));
        GraphNode<String> z = new GraphNode<String>("z");
        assertThrows(IllegalArgumentException.class, () -> g.getAdjacentNodesOf(z));
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphNode<String>> result = new HashSet<GraphNode<String>>();
        result.add(b);
        assertTrue(g.getAdjacentNodesOf(a).equals(result));
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e2 = new GraphEdge<String>(c, b, true);
        g.addEdge(e2);
        result.clear();
        result.add(b);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(c).equals(result));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(a, c, true);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, a, true);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(d, b, true);
        g.addEdge(e5);
        result.clear();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(b).equals(result));
        result.clear();
        result.add(b);
        result.add(c);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(a).equals(result));
        result.clear();
        result.add(a);
        result.add(b);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(d).equals(result));
        result.clear();
        result.add(b);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getAdjacentNodesOf(c).equals(result));
    }

    @Test
    final void testGetPredecessorNodesOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getPredecessorNodesOf(null));
        GraphNode<String> z = new GraphNode<String>("z");
        assertThrows(IllegalArgumentException.class, () -> g.getPredecessorNodesOf(z));
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphNode<String>> result = new HashSet<GraphNode<String>>();
        result.add(a);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(b).equals(result));
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e2 = new GraphEdge<String>(c, b, true);
        g.addEdge(e2);
        result.add(c);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(b).equals(result));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(a, c, true);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, a, true);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(d, b, true);
        g.addEdge(e5);
        result.add(d);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(b).equals(result));
        result.clear();
        result.add(d);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(a).equals(result));
        result.clear();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(d).equals(result));
        result.add(a);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi nodi
        assertTrue(g.getPredecessorNodesOf(c).equals(result));
    }

    @Test
    final void testGetEdges() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphEdge<String>> testEdges = new HashSet<GraphEdge<String>>();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, true);
        g.addEdge(e1);
        GraphEdge<String> e1Test = new GraphEdge<String>(s, d, true);
        testEdges.add(e1Test);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphNode<String> y = new GraphNode<String>("d");
        g.addNode(y);
        GraphEdge<String> e2 = new GraphEdge<String>(s, x, true);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(d, x, true);
        g.addEdge(e3);
        testEdges.add(e3);
        testEdges.add(e2);
        GraphEdge<String> e4 = new GraphEdge<String>(d, y, true);
        g.addEdge(e4);
        testEdges.add(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(y, d, true);
        g.addEdge(e5);
        testEdges.add(e5);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdges().equals(testEdges));
        g.clear();
        testEdges.clear();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stesso archi
        assertTrue(g.getEdges().equals(testEdges));
    }

    @Test
    final void testAddEdge() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.addEdge(null));
        GraphNode<String> source = new GraphNode<String>("s");
        g.addNode(source);
        GraphNode<String> destination = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(new GraphEdge<String>(source, destination, false)));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(new GraphEdge<String>(source, destination, true)));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(new GraphEdge<String>(destination, source, true)));
        g.addNode(destination);
        GraphEdge<String> e1 = new GraphEdge<String>(source, destination, true);
        assertTrue(g.addEdge(e1));
        assertTrue(g.containsEdge(new GraphEdge<String>(source, destination, true)));
        GraphEdge<String> e2 = new GraphEdge<String>(destination, source, true);
        g.addEdge(e2);
        // Mi assicuro che il grafo contenga l'arco e2
        assertTrue(g.containsEdge(e2));
        assertTrue(g.edgeCount() == 2);
        assertTrue(g.getEdgeAtNodeIndexes(0, 0) == null);
        assertTrue(g.getEdgeAtNodeIndexes(0, 1).equals(e1));
    }

    @Test
    final void testRemoveEdge() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.removeEdge(null));
        GraphNode<String> source = new GraphNode<String>("s");
        g.addNode(source);
        GraphNode<String> destination = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.removeEdge(new GraphEdge<String>(source, destination, true)));
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(destination, source, true)));
        g.addNode(destination);
        g.clear();
        GraphNode<String> n1 = new GraphNode<String>("n1");
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n1);
        g.addNode(n2);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, true);
        g.addEdge(e1);
        assertTrue(g.edgeCount() == 1);
        GraphEdge<String> e2 = new GraphEdge<String>(n2, n3, true);
        g.addEdge(e2);
        assertTrue(g.edgeCount() == 2);
        GraphEdge<String> e3 = new GraphEdge<String>(n1, n3, true);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(n1, n1, true); //cappio
        g.addEdge(e4);
        assertTrue(g.edgeCount() == 4);
        g.removeEdge(e2);
        // Mi accerto che il nodo è stato rimosso
        assertTrue(g.edgeCount() == 3);
    }

    @Test
    final void testContainsEdge() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.containsEdge(null));
        GraphNode<String> source = new GraphNode<String>("s");
        g.addNode(source);
        GraphNode<String> destination = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(source, destination, true)));
        assertThrows(IllegalArgumentException.class, () -> g.containsEdge(new GraphEdge<String>(destination, source, true)));
        g.addNode(destination);
        GraphEdge<String> e1 = new GraphEdge<String>(source, destination, true);
        assertFalse(g.containsEdge(new GraphEdge<String>(source, destination, true)));
        g.addEdge(e1);
        // Mi assicuro che l'arco è stato inserito
        assertTrue(g.containsEdge(new GraphEdge<String>(source, destination, true)));
    }

    @Test
    final void testGetEdgesOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> s = new GraphNode<String>("s");
        g.addNode(s);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphEdge<String>> testEdges = new HashSet<GraphEdge<String>>();
        assertThrows(NullPointerException.class, () -> g.getEdgesOf(null));
        GraphNode<String> d = new GraphNode<String>("d");
        assertThrows(IllegalArgumentException.class, () -> g.getEdgesOf(d));
        g.addNode(d);
        GraphEdge<String> e1 = new GraphEdge<String>(s, d, true);
        g.addEdge(e1);
        GraphNode<String> x = new GraphNode<String>("x");
        g.addNode(x);
        GraphEdge<String> e2 = new GraphEdge<String>(s, x, true);
        g.addEdge(e2);
        GraphEdge<String> e3 = new GraphEdge<String>(d, x, true);
        g.addEdge(e3);
        GraphNode<String> y = new GraphNode<String>("y");
        g.addNode(y);
        GraphEdge<String> e4 = new GraphEdge<String>(x, y, true);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(y, s, true);
        g.addEdge(e5);
        GraphNode<String> w = new GraphNode<String>("w");
        g.addNode(w);
        GraphEdge<String> e6 = new GraphEdge<String>(y, w, true);
        g.addEdge(e6);
        testEdges.add(e1);
        testEdges.add(e2);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(s).equals(testEdges));
        testEdges.clear();
        testEdges.add(e3);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(d).equals(testEdges));
        testEdges.clear();
        testEdges.add(e5);
        testEdges.add(e6);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getEdgesOf(y).equals(testEdges));
    }

    @Test
    final void testGetIngoingEdgesOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getIngoingEdgesOf(null));
        GraphNode<String> z = new GraphNode<String>("z");
        assertThrows(IllegalArgumentException.class, () -> g.getIngoingEdgesOf(z));
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        // Creo un insieme che mi serve per confrontarlo con il risultato della chiamata al metodo
        Set<GraphEdge<String>> testEdges = new HashSet<GraphEdge<String>>();
        testEdges.add(e1);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(b).equals(testEdges));
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e2 = new GraphEdge<String>(c, b, true);
        g.addEdge(e2);
        testEdges.add(e2);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(b).equals(testEdges));
        GraphNode<String> d = new GraphNode<String>("d");
        g.addNode(d);
        GraphEdge<String> e3 = new GraphEdge<String>(a, c, true);
        g.addEdge(e3);
        GraphEdge<String> e4 = new GraphEdge<String>(d, a, true);
        g.addEdge(e4);
        GraphEdge<String> e5 = new GraphEdge<String>(d, b, true);
        g.addEdge(e5);
        testEdges.add(e5);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(b).equals(testEdges));
        testEdges.clear();
        testEdges.add(e4);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(a).equals(testEdges));
        testEdges.clear();
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(d).equals(testEdges));
        testEdges.add(e3);
        // Mi assicuro che l'insieme derivato dalla chiamata al metodo e l'insieme result contengano gli stessi archi
        assertTrue(g.getIngoingEdgesOf(c).equals(testEdges));
    }

    @Test
    final void testAdjacencyMatrixDirectedGraph() {
        // Creo un grafo diretto;
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        // Controllo se è stato inizializzato correttamente
        assertTrue(g.isEmpty());
        assertFalse(!g.isEmpty());
    }

    @Test
    final void testSize() {
        // Creo un grafo diretto; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(g.size() == 0);
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        assertTrue(g.size() == 1);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        assertTrue(g.size() == 2);
        GraphEdge<String> e1 = new GraphEdge<String>(n1, n2, true);
        g.addEdge(e1);
        assertTrue(g.size() == 3);
        GraphNode<String> n3 = new GraphNode<String>("n3");
        g.addNode(n3);
        assertTrue(g.size() == 4);
        GraphEdge<String> e2 = new GraphEdge<String>(n2, n3, true);
        g.addEdge(e2);
        assertTrue(g.size() == 5);
        GraphEdge<String> e3 = new GraphEdge<String>(n1, n3, true);
        g.addEdge(e3);
        // Mi assicuro che la size sia uguale a 6
        assertTrue(g.size() == 6);
        g.addEdge(new GraphEdge<String>(n1, n3, true));
        assertTrue(g.size() == 6);
        // Dopo quest'operazione la size si azzera
        g.clear();
        assertTrue(g.size() == 0);
    }

    @Test
    final void testIsEmpty() {
        // Creo un grafo diretto; inserisco i nodi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(g.isEmpty());
        GraphNode<String> n1 = new GraphNode<String>("n1");
        g.addNode(n1);
        assertTrue(g.nodeCount() == 1);
        GraphNode<String> n2 = new GraphNode<String>("n2");
        g.addNode(n2);
        assertTrue(g.nodeCount() == 2);
        // Mi assicuro che il grafo non sia vuoto
        assertFalse(g.isEmpty());
        g.clear();
        // Ora il grafo è vuoto
        assertTrue(g.isEmpty());
    }

    @Test
    final void testGetDegreeOf() {
        // Creo un grafo diretto; controllo se il metodo lanci le eccezioni; inserisco nodi e archi
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> g.getDegreeOf(null));
        GraphNode<String> z = new GraphNode<String>("z");
        assertThrows(IllegalArgumentException.class, () -> g.getDegreeOf(z));
        GraphNode<String> a = new GraphNode<String>("a");
        g.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        g.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        g.addNode(c);
        GraphEdge<String> e1 = new GraphEdge<String>(a, b, true);
        g.addEdge(e1);
        // Mi assicuro che il grado del nodo a sia 1
        assertTrue(g.getDegreeOf(a) == 1);
        GraphEdge<String> e2 = new GraphEdge<String>(a, c, true);
        // Mi assicuro che il grado del nodo a ora sia 2
        g.addEdge(e2);
        assertTrue(g.getDegreeOf(a) == 2);
        GraphEdge<String> e3 = new GraphEdge<String>(c, a, true);
        g.addEdge(e3);
        // Mi assicuro che il grado del nodo a sia cambiato nuovamente e il suo valore sia diventato 3
        assertTrue(g.getDegreeOf(a) == 3);
    }
}