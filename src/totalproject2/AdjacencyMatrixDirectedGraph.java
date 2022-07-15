package totalproject2;


import java.util.*;


/**
 * Classe che implementa un grafo orientato tramite matrice di adiacenza. Non
 * sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * <p>
 * I nodi sono indicizzati da 0 a nodeCount() - 1 seguendo l'ordine del loro
 * inserimento (0 &egrave; l'indice del primo nodo inserito, 1 del secondo e cos&igrave; via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non &egrave; rappresentata tramite array
 * ma tramite ArrayList.
 * <p>
 * Gli oggetti GraphNode<L>, cio&egrave; i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * <p>
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma &egrave; null se i nodi i e j non sono
 * collegati da un arco orientato e contiene un oggetto della classe
 * GraphEdge<L> se lo sono. Tale oggetto rappresenta l'arco.
 * <p>
 * Questa classe non supporta la cancellazione di nodi, ma supporta la
 * cancellazione di archi e tutti i metodi che usano indici, utilizzando
 * l'indice assegnato a ogni nodo in fase di inserimento.
 *
 * @author Template: Luca Tesei
 */
public class AdjacencyMatrixDirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /* Variabile istanza private che serve per contare gli archi. Ogni volta che viene aggiunto/rimosso un
     * arco viene incrementato/decrementato di 1/-1 il contatore.
     */
    private int countEdges;

    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixDirectedGraph() {
        // Inizializzazione delle variabili d'istanza
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
        this.countEdges = 0;
    }

    @Override
    public int nodeCount() {
        // Il dominio della mappa è l'insieme dei nodi
        return this.nodesIndex.keySet().size();
    }

    @Override
    public int edgeCount() {
        // Restituisce il numero degli archi, in questo caso la variabile d'istanza countEdges
        return this.countEdges;
    }

    @Override
    public void clear() {
        // Azzera tutte le variabili d'istanza
        this.matrix.clear();
        this.nodesIndex.clear();
        this.countEdges = 0;
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa un grafo orientato
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        // Il keySet restituisce un insieme delle chiavi della mappa(il dominio), in questo caso i nodi
        return this.nodesIndex.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e se non è già stato inserito in precedenza
        if (node == null)
            throw new NullPointerException("Tentativo di aggiungere un nodo null");
        // Controlla se il nodo è già stato inserito
        if (containsNode(node))
            return false;
        // Mette il nodo nel nuovo indice nella mappa; ad esempio se ci sono 6 nodi associo l'indice 6 a node
        this.nodesIndex.put(node, this.nodeCount()); // Dopo questa operazione nodeCount() è aumentato di 1
        // Aggiunge prima di tutto una riga alla matrice perchè deve esserne aumentata la dimensione 
        // -> nodeCount() * nodeCount()
        this.matrix.add(new ArrayList<GraphEdge<L>>());
        // Poi aggiunge null a tutte le colonne della nuova riga aggiunta
        for (int i = 0; i < this.nodeCount(); i++)
            this.matrix.get(this.nodeCount() - 1).add(null);
        // Aggiunge una casella alla fine di ogni riga, quindi nell'ultima colonna della matrice,
        // tranne per l'ultima riga perchè è già stato impostato null
        for (int i = 0; i < this.nodeCount() - 1; i++)
            this.matrix.get(i).add(null);
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Il nodo passato è null");
        throw new UnsupportedOperationException(
                "Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Tentativo di ricerca di nodo null");
        // Usa il metodo containsKey() dell'interfaccia Map; true se è già contenuto nel grafo, false altrimenti
        return this.nodesIndex.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        // Verifica delle API del metodo: controlla se l'etichetta passata è valida
        if (label == null)
            throw new NullPointerException("Tentativo di ricerca di un nodo con etichetta null");
        // Etichetta non-null; iterazione, prende l'insieme dei nodi e lo scorre
        for (GraphNode<L> node : this.nodesIndex.keySet())
            if (node.getLabel().equals(label))
                return node; // Ha trovato il nodo
        // Se si arriva fin qui terminando il for, non ha trovato il nodo e restituisce null per indicare
        // che non è presente il nodo con l'etichetta passata
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        // Verifica delle API del metodo: controlla se l'etichetta passata è valida e presente nel grafo
        if (label == null)
            throw new NullPointerException("Tentativo di ricerca di nodo con etichetta null");
        // Associa il nodo a n se è presente, altrimenti associa null a n se non è presente
        GraphNode<L> n = getNodeOf(label);
        if (n == null)
            throw new IllegalArgumentException("Un nodo con questa etichetta non esiste nel grafo");
        // Restituisce l'intero associato all'etichetta del nodo n
        return this.nodesIndex.get(n);
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        // Verifica delle API del metodo: controlla se l'indice passato è valido
        if (i >= this.nodeCount() || i < 0)
            throw new IndexOutOfBoundsException("L'indice che stai cercando non è nell'intervallo degli indici presenti");
        // Scorre le righe fin quando non trova che l'indice i è uguale all'intero associato a quella chiave nella riga
        for (HashMap.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()) {
            if (i == entry.getValue())
                // Restituisce il nodo
                return entry.getKey();
        }
        throw new IllegalStateException("Non è possibile arrivare in questo parte del metodo");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e che appartenga al grafo
        if (node == null)
            throw new NullPointerException("Tentativo di passare un nodo nullo");
        if (!this.containsNode(node))
            throw new IllegalArgumentException("Il nodo passato dal metodo non esiste");
        // Inizializza il risultato
        Set<GraphNode<L>> result = new HashSet<GraphNode<L>>();
        // Mette tutti gli archi uscenti del nodo 'node' nel set
        Set<GraphEdge<L>> outEdges = this.getEdgesOf(node);
        // Scorre la lista degli archi uscenti
        for (GraphEdge<L> edge : outEdges)
            // Aggiunge il nodo destinazione all'insieme risultato
            result.add(edge.getNode2());
        return result;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e che appartenga al grafo diretto
        if (node == null)
            throw new NullPointerException("Nodi null non supportati");
        if (!this.nodesIndex.containsKey(node))
            throw new IllegalArgumentException("Il nodo non è presente nel grafo");
        if (!this.isDirected())
            throw new UnsupportedOperationException("Grafo non orientato");
        // Inizializza il risultato 
        Set<GraphNode<L>> result = new HashSet<GraphNode<L>>();
        // Mette tutti gli archi entranti del nodo 'node' nel set 
        Set<GraphEdge<L>> inEdges = this.getIngoingEdgesOf(node);
        // Scorre la lista degli archi entranti
        for (GraphEdge<L> edge : inEdges)
            // Aggiunge il nodo sorgente all'insieme risultato
            result.add(edge.getNode1());
        return result;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        // Inizializza il risultato
        Set<GraphEdge<L>> result = new HashSet<GraphEdge<L>>();
        // Scorre tutta la matrice e finchè non termina il for, controlla se nella posizione (i, j) sia presente un arco
        // o meno. Se è presente lo aggiunge all'insieme.
        for (int i = 0; i < this.matrix.size(); i++)
            for (int j = 0; j < this.matrix.size(); j++)
                // Vedo se è presente un arco
                if (this.matrix.get(i).get(j) != null)
                    // Se true allora lo aggiunge al set
                    result.add(this.matrix.get(i).get(j));
        // Restituisce l'insieme degli archi del grafo
        return result;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla se l'arco passato è valido
        if (edge == null)
            throw new NullPointerException("Tentativo di inserimento di un arco null");
        if (!edge.isDirected())
            throw new IllegalArgumentException("Tentativo di inserimento di un arco "
                    + "non orientato in un grafo orientato");
        Integer s = this.nodesIndex.get(edge.getNode1());
        // Controlla se il primo nodo è null
        if (s == null)
            throw new IllegalArgumentException("Tentativo di inserimento di un arco con"
                    + " almeno il nodo sorgente null");
        Integer d = this.nodesIndex.get(edge.getNode2());
        // Controlla se il secondo nodo è null
        if (d == null)
            throw new IllegalArgumentException("Tentativo di inserimento di un arco con il nodo "
                    + "destinazione null");
        // Controlla se l'arco è già presente
        int indexSource = s.intValue();
        int indexDestination = d.intValue();
        if (this.matrix.get(indexSource).get(indexDestination) != null)
            // Significa che se è diverso da null l'arco in quella posizione è già presente -> restituisce false
            return false;
        // Inserisce l'arco nella posizione (s, d) 
        this.matrix.get(s).set(d, edge);
        // Se arriva fin qui aumenta il numero degli archi di questo grafo di un'unità
        this.countEdges++;
        // Arco inserito correttamente
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla se l'arco passato è valido
        if (edge == null)
            throw new NullPointerException("Tentativo di rimuovere un arco che non esiste");
        // Prende l'indice del nodo sorgente e del nodo destinazione dell'arco e lo mette in due oggetti interi;
        // controlla se i nodi dell'arco sorgente e destinazione sono validi
        Integer s = this.nodesIndex.get(edge.getNode1());
        if (s == null)
            throw new IllegalArgumentException("Tentativo di ricerca di un arco con la sorgente null");
        Integer d = this.nodesIndex.get(edge.getNode2());
        if (d == null)
            throw new IllegalArgumentException("Tentativo di ricerca di un arco con la destinazione null");
        // Rimuove l'arco nella posizione s
        boolean check = this.matrix.get(s).remove(edge);
        // Controlla se il check è positivo e in tal caso decrementa il numero degli archi presenti nel grafo di un'unità
        if (check)
            countEdges--;
        // Restituisce true se l'operazione è andata a buon fine, false altrimenti
        return check;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla se l'arco passato è valido
        if (edge == null)
            throw new NullPointerException(
                    "Tentativo di ricerca di un arco null");
        // Prende l'indice del nodo sorgente e del nodo destinazione dell'arco e lo mette in due oggetti interi;
        // controlla se i nodi dell'arco sorgente e destinazione sono validi
        Integer s = this.nodesIndex.get(edge.getNode1());
        if (s == null)
            throw new IllegalArgumentException(
                    "Tentativo di ricerca di un arco con la sorgente null");
        Integer d = this.nodesIndex.get(edge.getNode2());
        if (d == null)
            throw new IllegalArgumentException(
                    "Tentativo di ricerca di un arco con la destinazione null");
        // Restituisce true se è presente il nodo nella posizione (s, d) della matrice, false altrimenti
        return this.matrix.get(s).get(d) != null;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Richiesta degli archi connessi a un nodo null");
        // Cerca l'indice del nodo
        Integer i = this.nodesIndex.get(node);
        if (i == null)
            throw new IllegalArgumentException("Richiesta degli archi connessi a un nodo non esistente");
        // Inizializza il risultato
        Set<GraphEdge<L>> result = new HashSet<GraphEdge<L>>();
        // Mette tutti gli archi del grafo in un insieme
        Set<GraphEdge<L>> edges = this.getEdges();
        // Scorre l'insieme degli archi e confronta se il nodo sorgente dell'arco è uguale al nodo passato in input
        for (GraphEdge<L> edge : edges)
            if (edge.getNode1().equals(node))
                // Se l'esito è positivo allora lo aggiunge all'insieme risultato
                result.add(edge);
        // Restituisce l'insieme degli archi uscenti al nodo
        return result;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e se esiste nella mappa
        if (node == null)
            throw new NullPointerException(
                    "Tentativo di ottenere gli archi entranti in un nodo null");
        if (!this.nodesIndex.containsKey(node))
            throw new IllegalArgumentException(
                    "Richiesta degli archi entranti di un nodo non esistente");
        // Crea l'insieme risultato
        Set<GraphEdge<L>> result = new HashSet<GraphEdge<L>>();
        // Mette tutti gli archi del grafo in un insieme
        Set<GraphEdge<L>> edges = this.getEdges();
        // Scorre tutta la lista degli archi del grafo e cerca quali sono quei nodi che hanno come destinazione node
        for (GraphEdge<L> edge : edges)
            if (edge.getNode2().equals(node))
                // Se l'esito è positivo allora lo aggiunge all'insieme risultato
                result.add(edge);
        // Restituisce l'insieme degli archi entranti al nodo
        return result;
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // Verifica delle API del metodo: controlla se i nodo passati sono validi e se esiste nel grafo
        if (node1 == null || node2 == null)
            throw new NullPointerException("Almeno un nodo passato è null");
        if (!(this.containsNode(node1) && this.containsNode(node2)))
            throw new IllegalArgumentException("Almeno uno dei nodi non è presente nel grafo");
        // Crea un insieme per gli archi uscenti del nodo sorgente
        Set<GraphEdge<L>> outEdges = this.getEdgesOf(node1);
        // Crea un insieme degli archi entranti al nodo destinazione
        Set<GraphEdge<L>> inEdges = this.getIngoingEdgesOf(node2);
        // Scorre la lista degli archi uscenti 
        for (GraphEdge<L> edge : outEdges)
            // Se la lista degli archi entranti al nodo2 contiene un arco con lo stesso nome
            // allora esiste un arco che va dal nodo1 al nodo2, e quindi lo restituisce
            if (inEdges.contains(edge))
                return edge;
        // Altrimenti se esce dal for, significa che non esiste un arco che
        // collega i due nodi passati in input e restituisce null
        return null;
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        // Verifica delle API del metodo: controlla che l'indice sia compreso nell'intervallo,
        // altrimenti lancia un eccezione di tipo IndexOutOfBoundException
        if ((i < 0 || i >= this.nodeCount()) || (j < 0 || j >= this.nodeCount()))
            throw new IndexOutOfBoundsException(
                    "L'indice che stai cercando non è nell'intervallo degli indici presenti");
        // Vede se è presente un arco nella posizione i, j e che sia diverso da null
        if (this.matrix.get(i).get(j) != null)
            // Se l'esito è positivo allora restituisce l'arco
            return this.matrix.get(i).get(j);
        // Altrimenti restituisce null se non è presente un arco nella posizione i, j
        return null;
    }
}