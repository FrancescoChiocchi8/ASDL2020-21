package totalproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Gli oggetti di questa classe sono calcolatori di cammini minimi con sorgente
 * singola su un certo grafo orientato e pesato dato. Il grafo su cui lavorare
 * deve essere passato quando l'oggetto calcolatore viene costruito e non
 * pu&ograve; contenere archi con pesi negativi. Il calcolatore implementa il
 * classico algoritmo di Dijkstra per i cammini minimi con sorgente singola
 * utilizzando una coda con priorit&agrave; che estrae l'elemento con
 * priorit&agrave; minima e aggiorna le priorit&agrave; con l'operazione
 * decreasePriority in tempo logaritmico (coda realizzata con uno heap binario).
 * In questo caso il tempo di esecuzione dell'algoritmo di Dijkstra &egrave;
 * {@code O(n log m)} dove {@code n} &egrave; il numero di nodi del grafo e
 * {@code m} &egrave; il numero di archi.
 *
 * @param <L> il tipo delle etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class DijkstraShortestPathComputer<L> implements SingleSourceShortestPathComputer<L> {

    //grafo su cui svolgere le operazioni
    private final Graph<L> g;

    //ultima sorgente su cui si è svolto il calcolo dei cammini minimi
    private GraphNode<L> lastSource;

    //restituisce true se è gia stato calcolato il calcolo dei cammini minimi; false altrimenti
    private boolean computed;

    //coda che serve alla classe per inserire i nodi; inoltre migliora il tempo di esecuzione di decreasePriority
    private final BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo diretto
     * e pesato privo di pesi negativi.
     *
     * @param graph il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException     se il grafo passato &egrave; nullo
     * @throws IllegalArgumentException se il grafo passato &egrave; vuoto
     * @throws IllegalArgumentException se il grafo passato non &egrave; orientato
     * @throws IllegalArgumentException se il grafo passato non &egrave; pesato,
     *                                  cio&egrave; esiste almeno un arco il cui
     *                                  peso &egrave; {@code Double.NaN}
     * @throws IllegalArgumentException se il grafo passato contiene almeno un peso
     *                                  negativo
     */
    public DijkstraShortestPathComputer(Graph<L> graph) {
        // Requisiti da soddifarre per creare un oggetto su cui operare; se non soddisfatti non crano l'oggetto
        // e viene lanciata un'eccezione a seconda del tipo di requisito non soddisfatto
        if (graph == null)
            throw new NullPointerException("Tentativo di passare un grafo nullo");
        if (!graph.isDirected())
            throw new IllegalArgumentException("Tentativo di passare un grafo non orientato");
        if (graph.isEmpty())
            throw new IllegalArgumentException("Tentativo di passare un grafo vuoto");
        // Vede se tutti gli archi del grafo hanno un peso positvo, altrimenti lancio le eccezioni
        Set<GraphEdge<L>> edges = graph.getEdges();
        for (GraphEdge<L> edge : edges) {
            if (!edge.hasWeight())
                throw new IllegalArgumentException(
                        "Tentativo di passare un grafo con almeno un arco con peso non settato");
            if (edge.getWeight() < 0)
                throw new IllegalArgumentException("Tentativo passare un grafo con almeno un arco con peso negativo");
        }
        // Inizializzazione delle variabili d'istanza
        this.g = graph;
        this.computed = false;
        this.lastSource = null;
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    /**
     * Inizializza le informazioni necessarie associate ai nodi del grafo associato
     * a questo calcolatore ed esegue un algoritmo per il calcolo dei cammini minimi
     * a partire da una sorgente data.
     *
     * @param sourceNode il nodo sorgente da cui calcolare i cammini minimi verso
     *                   tutti gli altri nodi del grafo
     * @throws NullPointerException     se il nodo passato &egrave; nullo
     * @throws IllegalArgumentException se il nodo passato non esiste nel grafo
     *                                  associato a questo calcolatore
     * @throws IllegalStateException    se il calcolo non pu&ograve; essere
     *                                  effettuato per via dei valori dei pesi del
     *                                  grafo, ad esempio se il grafo contiene cicli
     *                                  di peso negativo.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        // Verifica delle API del metodo: controlla se il nodo passato sia valido e che appartenga al grafo. Il grafo non
        // può contenere archi di peso negativo, quindi non c'è bisogno di controllare che non ci siano cicli negativi.
        if (sourceNode == null)
            throw new NullPointerException("Tentativo di passare un nodo nullo");
        Set<GraphNode<L>> nodes = this.g.getNodes();
        if (!nodes.contains(sourceNode))
            throw new IllegalArgumentException("Il nodo non esiste in questo grafo");
        // Per ogni nodo del grafo, eccetto sourceNode, associa il valore di +infinito come distanza dal nodo sorgente
        for (GraphNode<L> node : nodes)
            // Se il nodo è la radice allora mette la distanza a 0 e il predecessore null
            if (node.equals(sourceNode)) {
                node.setFloatingPointDistance(0);
                node.setPrevious(null);
            } else {
                // Se invece non è la radice mette la distanza infinita e il predecessore null; poi inserisce i nodi in coda
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
                node.setPrevious(null);
                this.queue.insert(node);
            }
        // Pone currrentNode = sourceNode e continua l'iterazione estraendo ogni volta il minimo
        GraphNode<L> currentNode = sourceNode;
        // do-while sui restanti nodi nella coda finchè non è vuota
        do {
            // Mette in un set tutti gli archi uscenti, in questo caso, del nodo corrente
            Set<GraphEdge<L>> outgoingEdges = this.g.getEdgesOf(currentNode);
            for (GraphEdge<L> e : outgoingEdges)
                // Rilassamento di tutti gli archi del nodo corrente
                relax(e);
            if (!queue.isEmpty())
                // Estrae il minimo e lo assegna al currentNode
                currentNode = (GraphNode<L>) queue.extractMinimum();
            else // Se non c'è nessun altro nodo nella coda, allora si ferma ed esce dal do-while
                break;
        } while (true);
        // Aggiorna i campi
        this.lastSource = sourceNode;
        this.computed = true;
    }

    @Override
    public boolean isComputed() {
        // Restituisce true se è stato eseguito il calcolo dei cammini minimi per un oggetto di tipo
        // DjikstraShortestPathComputer, false altrimenti
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
        return this.g;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        // Verifica delle API del metodo: controlla se è stato eseguito almeno una volta il calcolo dei cammini minimi
        // da una sorgente e che il nodo passato in input sia valido
        if (!computed)
            throw new IllegalStateException("Non è stato eseguito nemmeno una volta il calcolo dei cammini minimi");
        if (targetNode == null)
            throw new NullPointerException("Il nodo passato è nullo");
        if (!this.getGraph().containsNode(targetNode))
            throw new IllegalArgumentException("Il nodo passato non esiste");
        // Inizializza il percorso
        ArrayList<GraphEdge<L>> path = new ArrayList<GraphEdge<L>>();
        try {
            // Crea il percorso
            createPath(this.g.getNodeOf(targetNode.getLabel()), path);
        } catch (IllegalArgumentException e) {
            // Restituisce null se non è possibile raggiungere il nodo passato, partendo dalla sorgente
            return null;
        }
        // Restituisce l'insieme completo degli archi dal nodo sorgente al nodo target
        return path;
    }

    /*
     * Metodo privato: se passando per un certo nodo, la distanza tra la sorgente e un determinato nodo è minore
     * della distanza attuale associata al nodo, allora aggiorna la distanza(priorità)
     */
    private void relax(GraphEdge<L> edge) {
        // Mette in due variabili distinte la distanza corrente associata al nodo e la
        // nuova distanza possibile, che è data dalla distanza dal nodo sorgente più il peso di questo arco
        double currentDistance = edge.getNode2().getPriority();
        double newPossibleDistance = edge.getNode1().getPriority() + edge.getWeight();
        if (currentDistance > newPossibleDistance) {
            // Aggiorna la distanza del nodo di arrivo
            queue.decreasePriority(edge.getNode2(), newPossibleDistance);
            // Mette come nodo predecessore del nodo di arrivo il nodo di partenza
            edge.getNode2().setPrevious(edge.getNode1());
        }
    }

    /*
     * Metodo che permette di costruire il percorso tra la sorgente e il nodo target
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
        Set<GraphEdge<L>> edges = this.g.getIngoingEdgesOf(currentNode);
        // Scorre l'insieme fin quando non si ha che il nodo sorgente dell'arco (node1) è il previous del nodo corrente
        for (GraphEdge<L> e : edges) {
            if (e.getNode1().equals(currentNode.getPrevious()))
                // Una volta trovato, aggiunge l'arco al percorso
                path.add(e);
        }
    }
}

