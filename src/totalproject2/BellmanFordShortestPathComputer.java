package totalproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementazione dell'algoritmo di Bellman-Ford per il calcolo di cammini
 * minimi a sorgente singola in un grafo pesato che pu&ograve; contenere anche pesi
 * negativi, ma non cicli di peso negativo.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class BellmanFordShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {

    // Grafo su cui svolgere le operazioni
    private final Graph<L> graph;

    // Ultima sorgente su cui si è svolto il calcolo dei cammini minimi
    private GraphNode<L> lastSource;

    // Restituisce true se è gia stato calcolato il calcolo dei cammini minimi; false altrimenti
    private boolean computed;

    // Coda che serve alla classe per inserire i nodi
    private final BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * orientato e pesato.
     *
     * @param graph il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException     se il grafo passato &egrave; nullo
     * @throws IllegalArgumentException se il grafo passato &egrave; vuoto
     * @throws IllegalArgumentException se il grafo passato non &egrave; diretto
     * @throws IllegalArgumentException se il grafo passato non &egrave; pesato,
     *                                  cio&egrave; esiste almeno un arco il cui
     *                                  peso &egrave; {@code Double.NaN}.
     */
    public BellmanFordShortestPathComputer(Graph<L> graph) {
        // Requisiti da soddifarre per creare un oggetto su cui operare; se non soddisfatti non crano l'oggetto
        // e viene lanciata un'eccezione a seconda del tipo di requisito non soddisfatto
        if (graph == null)
            throw new NullPointerException("Il grafo passato è nullo");
        if (graph.isEmpty())
            throw new IllegalArgumentException("Il grafo passato è vuoto");
        if (!graph.isDirected())
            throw new IllegalArgumentException("Il grafo passato è non diretto");
        Set<GraphEdge<L>> edges = graph.getEdges();
        for (GraphEdge<L> edge : edges) {
            if (!edge.hasWeight())
                throw new IllegalArgumentException(
                        "Tentativo di passare un grafo con almeno un arco con peso non settato");
        }
        // Inizializzazione delle variabili d'istanza
        this.graph = graph;
        this.computed = false;
        this.lastSource = null;
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e che appartenga al grafo
        if (sourceNode == null)
            throw new NullPointerException("Il nodo passato è nullo");
        Set<GraphNode<L>> nodes = this.graph.getNodes();
        if (!nodes.contains(sourceNode))
            throw new IllegalArgumentException("Il nodo non esiste in questo grafo");
        this.lastSource = sourceNode;
        this.computed = true;
        // Inizializzazione di ogni nodo
        for (GraphNode<L> node : nodes)
            // Se il nodo è la radice allora mette la distanza a 0 e il predecessore null
            if (node.equals(sourceNode)) {
                node.setFloatingPointDistance(0);
                node.setPrevious(null);
            }
            // Se invece non è la radice mette la distanza infinita e il predecessore null; poi inserisce i nodi in coda
            else {
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
                node.setPrevious(null);
                this.queue.insert(node);
            }
        // Prende tutti gli archi del grafo e li mette in un insieme; rilassa poi gli archi
        Set<GraphEdge<L>> edges = graph.getEdges();
        for (int i = 0; i < graph.nodeCount() - 1; i++) {
            for (GraphEdge<L> edge : edges)
                relax(edge);
        }
        // Controlla se il grafo contiene cicli negativi; si è già eseguito il rilassamento, quindi se si può ancora migliorare
        // il percorso tra due nodi può significare solamente che il grafo contiene almeno un ciclo negativo
        for (GraphEdge<L> e : edges) {
            double currentDistance = e.getNode2().getPriority();
            double newPossibleDistance = e.getNode1().getPriority() + e.getWeight();
            if (currentDistance > newPossibleDistance) {
                // Se il controllo è positivo aggiorna la distanza del nodo di arrivo
                throw new IllegalStateException("Il grafo contiene cicli di peso negativo");
            }
        }
    }

    @Override
    public boolean isComputed() {
        // Restituisce true se è stato eseguito il calcolo dei cammini minimi per un oggetto di tipo
        // BellmanFordShortestPathComputer, false altrimenti
        return this.computed;
    }

    @Override
    public GraphNode<L> getLastSource() {
        // Verifica delle API del metodo: controlla se è stato eseguito un calcolo dei cammini minimi e nel caso non lo
        // fosse, lancia un'eccezione di tipo IllegalArgumentException
        if (!this.computed)
            throw new IllegalStateException("Non si può ottenere il nodo sorgente dato che non è stato eseguito "
                    + "nemmeno una volta il calcolo dei cammini minimi");
        // Restituisce l'ultima sorgente su cui è stato eseguito il calcolo dei cammini minimi
        return this.lastSource;
    }

    @Override
    public Graph<L> getGraph() {
        // Restituisce il grafo su cui opera questo calcolatore
        return this.graph;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        // Verifica delle API del metodo: controlla se è stato eseguito almeno una volta il calcolo dei cammini minimi
        // da una sorgente e che il nodo passato in input sia valido
        if (!computed)
            throw new IllegalStateException(
                    "Non è stato eseguito nemmeno una volta il calcolo dei cammini minimi da una sorgente");
        if (targetNode == null)
            throw new NullPointerException("Il nodo passato è nullo");
        if (!graph.containsNode(targetNode))
            throw new IllegalArgumentException("Il nodo passato non esiste");
        // Inizializzazione del risultato
        ArrayList<GraphEdge<L>> result = new ArrayList<>();
        try {
            // Crea il percorso
            createPath(this.graph.getNodeOf(targetNode.getLabel()), result);
        } catch (IllegalArgumentException e) {
            // Restituisce null se non è possibile raggiungere il nodo passato, partendo dalla sorgente
            return null;
        }
        // Restituisce l'insieme degli archi attraversati dal percorso
        return result;
    }

    /*
     * Metodo che permette di creare il percorso tra la sorgente e il nodo target
     */
    private void createPath(GraphNode<L> currentNode, ArrayList<GraphEdge<L>> path) {
        // Verifica delle API del metodo
        if (currentNode.equals(this.lastSource))
            return;
        if (currentNode.getPrevious() == null)
            throw new IllegalArgumentException("Nodo non raggiungibile dalla sorgente");
        // Ricorsione finchè non trova lastSource
        createPath(currentNode.getPrevious(), path);
        // Crea per ogni currentNode un insieme dove ci mette tutti i nodi entranti al currentNode
        Set<GraphEdge<L>> edges = this.graph.getIngoingEdgesOf(currentNode);
        // Scorre l'insieme fin quando non si ha che il nodo sorgente dell'arco (node1) è il previous del nodo corrente
        for (GraphEdge<L> e : edges) {
            if (e.getNode1().equals(currentNode.getPrevious()))
                // Una volta trovato, aggiunge l'arco al percorso
                path.add(e);
        }
    }

    private void relax(GraphEdge<L> edge) {
        // Mette in due variabili distinte la distanza corrente associata al nodo e la
        // nuova distanza possibile, che è data dalla distanza dal nodo sorgente più il peso di questo arco
        double currentDistance = edge.getNode2().getPriority();
        double newPossibleDistance = edge.getNode1().getPriority() + edge.getWeight();
        if (currentDistance > newPossibleDistance) {
            // Aggiorna la distanza del nodo di arrivo
            edge.getNode2().setPriority(newPossibleDistance);
            // set del predecessore del nodo di destinazione con il nodo di partenza
            edge.getNode2().setPrevious(edge.getNode1());
        }
    }
}

