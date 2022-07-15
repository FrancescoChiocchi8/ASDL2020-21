package totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implementazione dell'algoritmo di Floyd-Warshall per il calcolo di cammini
 * minimi tra tutte le coppie di nodi in un grafo pesato che pu&ograve; contenere anche
 * pesi negativi, ma non cicli di peso negativo.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class FloydWarshallAllPairsShortestPathComputer<L> {

    /*
     * Il grafo su cui opera questo calcolatore.
     */
    private final Graph<L> graph;

    /*
     * Matrice dei costi dei cammini minimi. L'elemento in posizione i,j
     * corrisponde al costo di un cammino minimo tra il nodo i e il nodo j, dove
     * i e j sono gli interi associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private final double[][] costMatrix;

    /*
     * Matrice dei predecessori. L'elemento in posizione i,j è -1 se non esiste
     * nessun cammino tra i e j oppure corrisponde all'indice di un nodo che
     * precede il nodo j in un qualche cammino minimo da i a j. Si intende che i
     * e j sono gli indici associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private final int[][] predecessorMatrix;

    /*
     * Variabile privata che determina se è stato calcolato il calcolo dei cammini minimi tra tutte
     * le coppie di nodi del grafo
     */
    private boolean computed;

    /**
     * Crea un calcolatore di cammini minimi fra tutte le coppie di nodi per un
     * grafo orientato e pesato. Non esegue il calcolo, che viene eseguito
     * invocando successivamente il metodo computeShortestPaths().
     *
     * @param graph il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException     se il grafo passato &egrave; nullo
     * @throws IllegalArgumentException se il grafo passato &egrave; vuoto
     * @throws IllegalArgumentException se il grafo passato non &egrave; orientato
     * @throws IllegalArgumentException se il grafo passato non &egrave; pesato,
     *                                  cio&egrave; esiste almeno un arco il cui
     *                                  peso &egrave; {@code Double.NaN}
     */
    public FloydWarshallAllPairsShortestPathComputer(Graph<L> g) {
        // Requisiti da soddifarre per creare un oggetto su cui operare; se non soddisfatti non crano l'oggetto
        // e viene lanciata un'eccezione a seconda del tipo di requisito non soddisfatto
        if (g == null)
            throw new NullPointerException("Il grafo passato è nullo");
        if (g.isEmpty())
            throw new IllegalArgumentException("Il grafo passato è vuoto");
        if (!g.isDirected())
            throw new IllegalArgumentException("Il grafo passato è non orientato");
        Set<GraphEdge<L>> edges = g.getEdges();
        for (GraphEdge<L> edge : edges)
            if (!edge.hasWeight())
                throw new IllegalArgumentException("Il grafo passato contiene almeno un arco senza peso");
        // Aggiorna le variabili d'istanza
        this.graph = g;
        this.computed = false;
        int n = g.nodeCount();
        // Inizializza la matrice dei costi e dei predecessori con il numero dei vertici del grafo
        costMatrix = new double[n][n];
        predecessorMatrix = new int[n][n];
    }

    /**
     * Esegue il calcolo per la matrice dei costi dei cammini minimi e per la
     * matrice dei predecessori cos&igrave; come specificato dall'algoritmo di
     * Floyd-Warshall.
     *
     * @throws IllegalStateException se il calcolo non pu&ograve; essere effettuato
     *                               per via dei valori dei pesi del grafo,
     *                               ad esempio se il grafo contiene cicli
     *                               di peso negativo.
     */
    public void computeShortestPaths() {
        // Mette il numero di node nel grafo in una variabile intera
        int n = this.graph.nodeCount();
        // Inizializzazione delle matrici
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = getWeight(i, j);
                // Se i è diverso da j allora mette come predecessore i, altrimenti sta cercando di ottenere il predecessore
                // per andare nello stesso nodo, quindi restituisce un valore negativo
                predecessorMatrix[i][j] = i != j ? i : -1;
            }
        }
        // k rappresenta un passo, i e j sono le celle. In ogni passo k si controlla ogni cella[i, j] della matrice.
        // Se la distanza fra i e j è maggiore della distanza fra la somma tra la distanza dal nodo i al nodo k più la
        // distanza tra il nodo k e j, allora aggiorna il costo di [i, j] e il predecessore di [i, j].
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (costMatrix[i][j] > costMatrix[i][k] + costMatrix[k][j]) {
                        costMatrix[i][j] = costMatrix[i][k] + costMatrix[k][j];
                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
                    }
                }
            }
        }
        // Controlla se il grafo ha cicli negativi
        for (int i = 0; i < n; i++) {
            if (costMatrix[i][i] < 0) {
                throw new IllegalStateException("Il grafo ha cicli negativi");
            }
        }
        // Aggiorna computed
        this.computed = true;
    }

    /**
     * Determina se &egrave; stata invocata la procedura di calcolo dei cammini minimi.
     *
     * @return true se i cammini minimi sono stati calcolati, false altrimenti
     */
    public boolean isComputed() {
        // Restituisce true se è stato eseguito il calcolo dei cammini per tutte le coppie di nodi per un oggetto di tipo
        // FloydWarshallAllPairsShortestParhComputer, false altrimenti
        return computed;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     *
     * @return il grafo su cui opera questo calcolatore
     */
    public Graph<L> getGraph() {
        // Restituisce il grafo su cui opera questo calcolatore
        return this.graph;
    }

    /**
     * Restituisce una lista di archi da un nodo sorgente a un nodo target. Tale
     * lista corrisponde a un cammino minimo tra i due nodi nel grafo gestito da
     * questo calcolatore.
     *
     * @param sourceNode il nodo di partenza del cammino minimo da
     *                   restituire
     * @param targetNode il nodo di arrivo del cammino minimo da restituire
     * @return la lista di archi corrispondente al cammino minimo; la lista &egrave;
     * vuota se il nodo sorgente &egrave; il nodo target. Viene restituito
     * {@code null} se il nodo target non &egrave; raggiungibile dal nodo
     * sorgente
     * @throws NullPointerException     se almeno uno dei nodi passati &egrave;
     *                                  nullo
     * @throws IllegalArgumentException se almeno uno dei nodi passati non
     *                                  esiste
     * @throws IllegalStateException    se non &egrave; stato eseguito il calcolo
     *                                  dei cammini minimi
     */
    public List<GraphEdge<L>> getShortestPath(GraphNode<L> sourceNode,
                                              GraphNode<L> targetNode) {
        // Verifica delle API del metodo: controlla se è stato eseguito almeno una volta il calcolo dei cammini minimi
        // da una sorgente e che i nodi passati in input siano validi
        if (!computed)
            throw new IllegalStateException(
                    "Non è stato eseguito nemmeno una volta il calcolo dei cammini minimi");
        if (sourceNode == null || targetNode == null)
            throw new NullPointerException("Almeno uno dei due nodi passati è nullo");
        if (!graph.containsNode(sourceNode))
            throw new IllegalArgumentException("Il nodo sorgente non esiste nel grafo");
        if (!graph.containsNode(targetNode))
            throw new IllegalArgumentException("Il nodo target non esiste nel grafo");
        // Inizializzo il percorso
        ArrayList<GraphEdge<L>> path = new ArrayList<GraphEdge<L>>();
        // Il percorso è vuoto se il nodo sorgente passato è lo stesso nodo target passato
        if (sourceNode.equals(targetNode))
            return path;
        // Prende gli indici dei nodi del grafo
        int i = this.graph.getNodeIndexOf(sourceNode.getLabel());
        int j = this.graph.getNodeIndexOf(targetNode.getLabel());
        // Restituisce null se dal nodo sorgente a quello target non è presente un percorso
        if (this.costMatrix[i][j] == Double.POSITIVE_INFINITY)
            return null;
        GraphNode<L> lastNode = this.graph.getNodeAtIndex(j);
        // Scorre finchè non trova nella matrice dei predecessori -1, ossia i e j uguali
        while (this.predecessorMatrix[i][j] != -1) {
            lastNode = this.graph.getNodeAtIndex(j);
            // Aggiorna il valore della j
            j = this.predecessorMatrix[i][j];
            // Aggiunge al percorso il nodo precedente di j
            path.add(this.graph.getEdge(this.graph.getNodeAtIndex(j), lastNode));
        }
        // Inverte tutti gli elementi della lista in modo tale da restituire un percorso ordinato dalla sorgente al nodo target
        Collections.reverse(path);
        // Restituisce il percorso
        return path;
    }

    /**
     * Restituisce il costo di un cammino minimo da un nodo sorgente a un nodo
     * target.
     *
     * @param sourceNode il nodo di partenza del cammino minimo
     * @param targetNode il nodo di arrivo del cammino minimo
     * @return il costo di un cammino minimo tra il nodo sorgente e il nodo
     * target. Viene restituito {@code Double.POSITIVE_INFINITY} se il
     * nodo target non &egrave; raggiungibile dal nodo sorgente, mentre viene
     * restituito zero se il nodo sorgente &egrave; il nodo target.
     * @throws NullPointerException     se almeno uno dei nodi passati &egrave;
     *                                  nullo
     * @throws IllegalArgumentException se almeno uno dei nodi passati non
     *                                  esiste
     * @throws IllegalStateException    se non &egrave; stato eseguito il calcolo
     *                                  dei cammini minimi
     */
    public double getShortestPathCost(GraphNode<L> sourceNode,
                                      GraphNode<L> targetNode) {
        // Verifica delle API del metodo: controlla se è stato eseguito almeno una volta il calcolo dei cammini minimi
        // da una sorgente e che i nodi passati in input siano validi
        if (!computed)
            throw new IllegalStateException(
                    "Non è stato eseguito nemmeno una volta il calcolo dei cammini minimi");
        if (sourceNode == null || targetNode == null)
            throw new NullPointerException("Almeno uno dei due nodi passati è nullo");
        if (!graph.containsNode(sourceNode))
            throw new IllegalArgumentException("Il nodo sorgente non esiste nel grafo");
        if (!graph.containsNode(targetNode))
            throw new IllegalArgumentException("Il nodo target non esiste nel grafo");
        if (sourceNode.equals(targetNode))
            return 0;
        // Prende gli indici dei nodi attraverso le etichette dei nodi passati in input
        int i = this.graph.getNodeIndexOf(sourceNode.getLabel());
        int j = this.graph.getNodeIndexOf(targetNode.getLabel());
        if (this.costMatrix[i][j] == Double.POSITIVE_INFINITY)
            return Double.POSITIVE_INFINITY;
        // Restituisce il costo del percorso dal nodo sorgente a quello target;
        // corrisponde a nient'altro che la posizione tra il nodo i-esimo e il nodo j-esimo
        return costMatrix[i][j];
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     *
     * @param path un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException se il cammino passato &egrave; nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

    /**
     * @return the costMatrix
     */
    public double[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * @return the predecessorMatrix
     */
    public int[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }

    /*
     * Metodo privato che restituisce il peso dell'arco tra le posizioni i e j dei nodi
     */
    private double getWeight(int i, int j) {
        if (i == j)
            return 0;
        if (i != j && this.graph.getEdgeAtNodeIndexes(i, j) != null)
            return this.graph.getEdgeAtNodeIndexes(i, j).getWeight();
        else
            return Double.POSITIVE_INFINITY;
    }
}
