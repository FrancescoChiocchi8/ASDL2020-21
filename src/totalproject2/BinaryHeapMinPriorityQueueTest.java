package totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class BinaryHeapMinPriorityQueueTest {

    @Test
    final void testBinaryHeapMinPriorityQueue() {
        // Creo un heap binario;
        BinaryHeapMinPriorityQueue h = new BinaryHeapMinPriorityQueue();
        // Controllo che sia stato creato correttamente
        assertTrue(h.isEmpty());
    }

    @Test
    final void testInsert() {
        // Creo un heap binario; Inserisco i nodi e relativa priorità
        BinaryHeapMinPriorityQueue g = new BinaryHeapMinPriorityQueue();
        assertThrows(NullPointerException.class, () -> g.insert(null));
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(5);
        g.insert(n1);
        assertTrue(n1.getHandle() == 0);
        // Controllo che ci sia qualcosa (nodo n1)
        assertTrue(!g.isEmpty());
        ArrayList<PriorityQueueElement> a = g.getBinaryHeap();
        assertTrue(a.get(0) == n1);
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        n2.setPriority(6);
        g.insert(n2);
        // Controllo la posizione e la handle ogni volta che inserisco un nodo
        assertTrue(g.size() == 2);
        assertTrue(a.get(0) == n1);
        assertTrue(a.get(1) == n2);
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setPriority(4);
        g.insert(n3);
        assertTrue(g.size() == 3);
        assertTrue(a.get(0) == n3);
        assertTrue(a.get(1) == n2);
        assertTrue(a.get(2) == n1);
        assertTrue(n3.getHandle() == 0);
        assertTrue(n2.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        PriorityQueueElement n4 = new GraphNode<String>("n4");
        n4.setPriority(2);
        g.insert(n4);
        assertTrue(g.size() == 4);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(n4.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        PriorityQueueElement n5 = new GraphNode<String>("n5");
        n5.setPriority(8);
        g.insert(n5);
        assertTrue(g.size() == 5);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(a.get(4) == n5);
        assertTrue(n4.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        assertTrue(n5.getHandle() == 4);
        PriorityQueueElement n6 = new GraphNode<String>("n6");
        n6.setPriority(1);
        g.insert(n6);
        assertTrue(g.size() == 6);
        assertTrue(a.get(0) == n6);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n4);
        assertTrue(a.get(3) == n2);
        assertTrue(a.get(4) == n5);
        assertTrue(a.get(5) == n1);
        assertTrue(n6.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n4.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        assertTrue(n5.getHandle() == 4);
        assertTrue(n1.getHandle() == 5);

    }

    @Test
    final void testMinimum() {
        // Creo un heap binario; controllo che il metodo lanci le eccezioni; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue h = new BinaryHeapMinPriorityQueue();
        assertThrows(NoSuchElementException.class, () -> h.minimum());
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(2);
        n1.setHandle(10);
        h.insert(n1);
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        n2.setPriority(6);
        n1.setHandle(0);
        h.insert(n2);
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setPriority(5);
        h.insert(n3);
        PriorityQueueElement n4 = new GraphNode<String>("n4");
        n4.setPriority(1);
        h.insert(n4);
        PriorityQueueElement min = h.minimum();
        // Controllo che il minimo sia il nodo n4, che sta nella radice e che quindi abbia priorità 1
        assertTrue(min.getPriority() == 1);
        assertTrue(min == n4);
        assertTrue(min.getHandle() == 0);
    }

    @Test
    final void testExtractMinimum() {
        // Creo un heap binario; controllo che il metodo lanci le eccezioni; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue g = new BinaryHeapMinPriorityQueue();
        assertThrows(NoSuchElementException.class, () -> g.extractMinimum());
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(5);
        g.insert(n1);
        assertTrue(n1.getHandle() == 0);
        // Controllo che ci sia qualcosa (nodo n1)
        assertTrue(!g.isEmpty());
        ArrayList<PriorityQueueElement> a = g.getBinaryHeap();
        assertTrue(a.get(0) == n1);
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        n2.setPriority(6);
        g.insert(n2);
        assertTrue(g.size() == 2);
        assertTrue(a.get(0) == n1);
        assertTrue(a.get(1) == n2);
        /*
        g.extractMinimum();
        assertTrue(a.get(0) == n2);
        assertTrue(n2.getHandle() == 0);    
        assertTrue(g.size() == 1);
        *///OK
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setPriority(4);
        g.insert(n3);
        assertTrue(g.size() == 3);
        assertTrue(a.get(0) == n3);
        assertTrue(a.get(1) == n2);
        assertTrue(a.get(2) == n1);
        assertTrue(n3.getHandle() == 0);
        assertTrue(n2.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);  
        /* PriorityQueueElement min = g.extractMinimum();
        assertTrue(min == n3);
        assertTrue(g.size() == 2);
        assertTrue(a.get(0) == n1);
        assertTrue(a.get(1) == n2);
        assertTrue(n1.getHandle() == 0);
        assertTrue(n2.getHandle() == 1);*/// OK
        PriorityQueueElement n4 = new GraphNode<String>("n4");
        n4.setPriority(2);
        g.insert(n4);
        assertTrue(g.size() == 4);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(n4.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        /*
        PriorityQueueElement min = g.extractMinimum();
        assertTrue(min == n4);
        assertTrue(g.size() == 3);
        assertTrue(a.get(0) == n3);
        assertTrue(a.get(1) == n2);
        assertTrue(a.get(2) == n1);
        assertTrue(n3.getHandle() == 0);
        assertTrue(n2.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        *////OK
        PriorityQueueElement n5 = new GraphNode<String>("n5");
        n5.setPriority(8);
        g.insert(n5);
        PriorityQueueElement n6 = new GraphNode<String>("n6");
        n6.setPriority(1);
        g.insert(n6);
        PriorityQueueElement min = g.extractMinimum();
        // Controllo che il minimo estratto sia n6 e poi controllo che l'heap sia stato rimesso in ordine 
        // secondo la proprietà di minHeap
        assertTrue(min == n6);
        assertTrue(g.size() == 5);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(a.get(4) == n5);
        assertTrue(n4.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        assertTrue(n5.getHandle() == 4);
    }

    @Test
    final void testDecreasePriority() {
        // Creo un heap binario; controllo che il metodo lanci le eccezioni; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue g = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement n10 = new GraphNode<String>("n10");
        assertThrows(NoSuchElementException.class, () -> g.decreasePriority(n10, 0));
        PriorityQueueElement n7 = new GraphNode<String>("n7");
        g.insert(n10); //caso di lista non vuota
        //g.insert(n7); usata per far fallire l'assertThrows()
        assertThrows(NoSuchElementException.class, () -> g.decreasePriority(n7, 0));
        g.clear();
        n7.setPriority(10);
        g.insert(n7);
        assertThrows(IllegalArgumentException.class, () -> g.decreasePriority(n7, 15));
        /*
         * fallisce
         * assertThrows(IllegalArgumentException.class, () -> g.decreasePriority(n7, 1));
         */
        assertTrue(n7.getPriority() == 10);
        g.decreasePriority(n7, 4);
        assertTrue(n7.getPriority() == 4);
        g.clear();
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(5);
        assertTrue(n1.getHandle() == 0);
        g.insert(n1);
        // Controllo che ci sia qualcosa (nodo n1)
        assertTrue(!g.isEmpty());
        ArrayList<PriorityQueueElement> a = g.getBinaryHeap();
        assertTrue(a.get(0) == n1);
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        n2.setPriority(6);
        g.insert(n2);
        assertTrue(g.size() == 2);
        assertTrue(a.get(0) == n1);
        assertTrue(a.get(1) == n2);
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setPriority(4);
        g.insert(n3);
        assertTrue(g.size() == 3);
        assertTrue(a.get(0) == n3);
        assertTrue(a.get(1) == n2);
        assertTrue(a.get(2) == n1);
        PriorityQueueElement n4 = new GraphNode<String>("n4");
        n4.setPriority(2);
        g.insert(n4);
        assertTrue(g.size() == 4);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(n4.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n1.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        PriorityQueueElement n5 = new GraphNode<String>("n5");
        n5.setPriority(8);
        g.insert(n5);
        assertTrue(g.size() == 5);
        assertTrue(a.get(0) == n4);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n1);
        assertTrue(a.get(3) == n2);
        assertTrue(a.get(4) == n5);
        PriorityQueueElement n6 = new GraphNode<String>("n6");
        n6.setPriority(1);
        g.insert(n6);
        assertTrue(g.size() == 6);
        assertTrue(a.get(0) == n6);
        assertTrue(a.get(1) == n3);
        assertTrue(a.get(2) == n4);
        assertTrue(a.get(3) == n2);
        assertTrue(a.get(4) == n5);
        assertTrue(a.get(5) == n1);
        assertTrue(n6.getHandle() == 0);
        assertTrue(n3.getHandle() == 1);
        assertTrue(n4.getHandle() == 2);
        assertTrue(n2.getHandle() == 3);
        assertTrue(n5.getHandle() == 4);
        assertTrue(n1.getHandle() == 5);
        g.decreasePriority(n2, 1);
        // Dopo la chiamata di decremento di priorità controllo se tutti i nodi sono nelle posizioni che gli competono
        // in accordo con la proprietà di minHeap
        assertTrue(g.size() == 6);
        assertTrue(a.get(0).getPriority() == 1);
        assertTrue(a.get(1) == n2);
        assertTrue(a.get(2) == n4);
        assertTrue(a.get(3) == n3);
        assertTrue(a.get(4) == n5);
        assertTrue(a.get(5) == n1);
        assertTrue(n6.getHandle() == 0);
        assertTrue(n2.getHandle() == 1);
        assertTrue(n4.getHandle() == 2);
        assertTrue(n3.getHandle() == 3);
        assertTrue(n5.getHandle() == 4);
        assertTrue(n1.getHandle() == 5);
    }

    @Test
    final void testIsEmpty() {
        // Creo un heap binario; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue h = new BinaryHeapMinPriorityQueue();
        assertTrue(h.isEmpty());
        PriorityQueueElement n1 = new GraphNode<Integer>(5);
        h.insert(n1);
        assertFalse(h.isEmpty());
        assertTrue(h.size() == 1);
        PriorityQueueElement n2 = new GraphNode<Integer>(7);
        h.insert(n2);
        assertTrue(h.size() == 2);
        h.extractMinimum();
        assertFalse(h.isEmpty());
        assertTrue(h.size() == 1);
        h.extractMinimum();
        // Assumo che l'heap sia vuoto
        assertTrue(h.isEmpty());
    }

    @Test
    final void testSize() {
        // Creo un heap binario; controllo che il metodo lanci le eccezioni; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue h = new BinaryHeapMinPriorityQueue();
        assertTrue(h.size() == 0);
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(4);
        h.insert(n1);
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        n2.setPriority(3);
        h.insert(n2);
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setPriority(6);
        h.insert(n3);
        PriorityQueueElement n4 = new GraphNode<String>("n3");
        n4.setPriority(1);
        h.insert(n4);
        // Mi assicuro che l'heap abbia dimensione 4
        assertTrue(h.size() == 4);
        h.extractMinimum();
        // Dopo l'estrazione del minimo, mi aspetto che la dimensione dell'heap venga diminuita di 1, quindi diventa 3
        assertTrue(h.size() == 3);
    }


    @Test
    final void testClear() {
        // Creo un heap binario; controllo che il metodo lanci le eccezioni; aggiungo i nodi con le relative priorità
        BinaryHeapMinPriorityQueue h = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement n1 = new GraphNode<String>("n1");
        n1.setPriority(3);
        n1.setHandle(0);
        assertTrue(h.size() == 0);
        h.insert(n1);
        assertTrue(h.size() == 1);
        h.clear();
        PriorityQueueElement n2 = new GraphNode<String>("n2");
        assertTrue(h.size() == 0);
        n2.setPriority(1);
        n2.setHandle(0);
        h.insert(n2);
        assertTrue(h.size() == 1);
        PriorityQueueElement n3 = new GraphNode<String>("n3");
        n3.setHandle(1);
        n3.setPriority(6);
        h.insert(n3);
        assertTrue(h.size() == 2);
        PriorityQueueElement n4 = new GraphNode<String>("n4");
        n3.setHandle(1);
        n3.setPriority(6);
        h.insert(n4);
        assertTrue(h.size() == 3);
        h.clear();
        // Dopo quest'operazione la dimensione dell'heap sarà 0
        assertTrue(h.size() == 0);
        ArrayList<PriorityQueueElement> a = h.getBinaryHeap();
        assertTrue(a.size() == 0);
    }
}
