package totalproject2;

import java.util.Set;

/**
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * <p>
 * L'algoritmo usa una coda di min priorit&agrave; tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo &egrave; necessario utilizzare l'operazione di
 * decreasePriority.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class PrimMSP<L> {

    /*
     * Coda di priorità che va usata dall'algoritmo. La variabile istanza è
     * protected solo per scopi di testing JUnit.
     */
    protected BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    /**
     * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non negativi.
     * Dopo l'esecuzione del metodo nei nodi del grafo il campo previous deve
     * contenere un puntatore a un nodo in accordo all'albero di copertura
     * minimo calcolato, la cui radice &egrave; il nodo sorgente passato.
     *
     * @param g un grafo non orientato, pesato, con pesi non negativi
     * @param s il nodo del grafo g sorgente, cio&egrave; da cui parte il calcolo
     *          dell'albero di copertura minimo. Tale nodo sar&agrave; la radice
     *          dell'albero di copertura trovato
     * @throw NullPointerException         se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException     se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException     se il grafo g &egrave; orientato, non pesato o
     * con pesi negativi
     */
    @SuppressWarnings("unchecked")
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        // Verifica delle API del metodo: delega tutto il da farsi al metodo checkExceptions(Graph<L> g)
        checkExceptions(g, s);
        // Con questo metodo, colora i nodi di grigio e li mette in coda
        colorNode(g, s);
        // Inizialmente il nodo corrente è la sorgente
        GraphNode<L> currentNode = s;
        while (!queue.isEmpty()) {
            // Mette i nodi adiacenti al nodo corrente, di volta in volta aggiornato, in un insieme e poi scorre il set
            Set<GraphNode<L>> adjNodesCurrentNodes = g.getAdjacentNodesOf(currentNode);
            for (GraphNode<L> n : adjNodesCurrentNodes) {
                if (n.getColor() == 1) {
                    // Controllo della priorità attuale con un'altra possibile priorità
                    double priority = n.getPriority(); //metto la priorità attuale in una variabile
                    // Associa la priorità del nodo con il peso dell'arco che collega i due nodi
                    if (!n.equals(currentNode)) {
                        double newPossiblePriority = g.getEdge(n, currentNode).getWeight();
                        if (priority > newPossiblePriority) {
                            this.queue.decreasePriority(n, newPossiblePriority);
                            n.setPrevious(currentNode);
                        }
                    }
                }
                // Se il nodo ha impostato il colore black(2) allora non fa niente
            }
            currentNode.setColor(2);
            // Aggiornamento del nodo corrente
            currentNode = (GraphNode<L>) this.queue.extractMinimum();
        }
    }

    /*
     * Controllo del grafo passato
     */
    private void checkExceptions(Graph<L> g, GraphNode<L> s) {
        // Verifica delle API del metodo: controlla se il grafo e il nodo passati sono validi, se il nodo è contenuto nel grafo
        // se il grafo è pesato e che il peso degli archi sia positivo
        if (g == null)
            throw new NullPointerException("Il grafo passato è nullo");
        if (s == null)
            throw new NullPointerException("La sorgente passata è nulla");
        if (g.isDirected())
            throw new IllegalArgumentException("Il grafo è orientato");
        if (!g.containsNode(s))
            throw new IllegalArgumentException("Il nodo s non appartiene all'insieme dei nodi del grafo g");
        for (GraphEdge<L> e : g.getEdges()) {
            if (!e.hasWeight())
                throw new IllegalArgumentException("Il grafo contiene almeno un arco non pesato");
            if (e.getWeight() < 0)
                throw new IllegalArgumentException("Il grafo contiene almeno un arco con peso negativo");
        }
    }

    /*
     * Scorro tutti nodi del grafo, li coloro di grigio e li metto in coda
     */
    private void colorNode(Graph<L> g, GraphNode<L> s) {
        // Scorre tutti i nodi del grafo
        for (GraphNode<L> node : g.getNodes()) {
            // Se il nodo è la radice allora mette la distanza a 0 e il predecessore null
            if (node.equals(s)) {
                node.setPriority(0);
                node.setPrevious(null);
            }
            // Se invece non è la radice mette la distanza a infinito e il predecessore null; poi inserisce i nodi in coda
            else {
                node.setPriority(Double.POSITIVE_INFINITY);
                node.setPrevious(null);
                this.queue.insert(node);
            }
            // Colora tutti i nodi del grafo di grigio
            node.setColor(1);
        }
    }
}
