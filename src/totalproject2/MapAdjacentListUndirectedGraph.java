package totalproject2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * non orientato. Non sono accettate etichette dei nodi null e non sono
 * accettate etichette duplicate nei nodi (che in quel caso sono lo stesso
 * nodo).
 * <p>
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati pi&ugrave; efficienti per quanto riguarda la complessit&agrave; in tempo
 * della ricerca se un nodo &egrave; presente (pseudocostante, con tabella hash) e se
 * un arco &egrave; presente (pseudocostante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risulta tuttavia pi&ugrave; grande di quello che servirebbe con
 * la rappresentazione standard.
 * <p>
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa &egrave; l'insieme dei nodi, su cui &egrave;
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cio&egrave; ad ogni nodo del grafo, non &egrave;
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi collegati al nodo: in
 * questo modo la rappresentazione riesce a contenere anche l'eventuale peso
 * dell'arco (memorizzato nell'oggetto della classe GraphEdge<L>). Per
 * controllare se un arco &egrave; presente basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 * <p>
 * Questa classe non supporta le operazioni indicizzate di ricerca di nodi e
 * archi.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class MapAdjacentListUndirectedGraph<L> extends Graph<L> {

    /*
     * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
     * associato con l'insieme degli archi collegati. Nel caso in cui un nodo
     * non abbia archi collegati è associato con un insieme vuoto. La variabile
     * istanza è protected solo per scopi di test JUnit.
     */
    protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

    /*
     * Variabile d'istanza private per contare il numero degli archi
     */
    private int countEdges;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public MapAdjacentListUndirectedGraph() {
        // Inizializzazione delle variabili d'istanza
        this.adjacentLists = new HashMap<GraphNode<L>, Set<GraphEdge<L>>>();
        countEdges = 0;
    }

    @Override
    public int nodeCount() {
        // Restituisce la dimensione del dominio (keySet) della mappa
        return this.adjacentLists.keySet().size();
    }

    @Override
    public int edgeCount() {
        // Restituisce il numero degli archi, in questo caso la variabile d'istanza countEdges
        return this.countEdges;
    }

    @Override
    public void clear() {
        // Azzera tutte le variabili d'istanza
        this.adjacentLists.clear();
        countEdges = 0;
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa grafi non orientati
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        // Il keySet restituisce un insieme delle chiavi della mappa(il dominio), in questo caso i nodi
        return this.adjacentLists.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Tentativo di aggiungere un nuovo nodo perchè è null");
        // Associa il nuovo nodo ad un insieme vuoto di archi; viene poi confrontato con null e restituirà true se è andato
        // tutto a buon fine, false altrimenti
        boolean checkAdd = this.adjacentLists.putIfAbsent(node, new HashSet<GraphEdge<L>>()) == null;
        return checkAdd;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Il nodo passato è nullo");
        // Rimuove il nodo dal dominio della mappa
        return this.adjacentLists.keySet().remove(node);
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido
        if (node == null)
            throw new NullPointerException("Tentativo di ricercare un nodo null");
        // Usa il metodo containsKey() dell'interfaccia Map; true se è già contenuto nel grafo, false altrimenti
        return this.adjacentLists.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        // Verifica delle API del metodo: controlla se l'etichetta passata è valida
        if (label == null)
            throw new NullPointerException("Nodi null non supportati");
        // Etichetta non-null; iterazione, prende l'insieme dei nodi e lo scorre
        for (GraphNode<L> nodo : this.adjacentLists.keySet()) {
            // Vede se è presente un nodo con l'etichetta passata, se l'esito è positivo restituisce il nodo
            if (nodo.getLabel().equals(label))
                return nodo;
        }
        // Altrimenti non è presente nessun nodo che si identifica con l'etichetta passata, quindi restituisce null
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        // Verifica delle API del metodo
        if (label == null)
            throw new NullPointerException("Tentativo di ricercare un nodo con etichetta null");
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        // Verifica delle API del metodo
        throw new UnsupportedOperationException("Ricerca dei nodi con indice non supportata");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e presente nel grafo
        if (node == null)
            throw new NullPointerException(
                    "Tentativo di ottenere i nodi adiacenti di un nodo null");
        if (!this.containsNode(node))
            throw new IllegalArgumentException(
                    "Tentativo di ottenere i nodi adiacenti di un nodo non esistente");
        // Inizializza il risultato
        Set<GraphNode<L>> result = new HashSet<GraphNode<L>>();
        // Mette gli archi del nodo passato in input in un insieme
        Set<GraphEdge<L>> edges = this.getEdgesOf(node);
        // Scorre la lista degli archi del nodo
        for (GraphEdge<L> e : edges) {
            // Controlla se il nodo2 dell'arco è il nodo passato; in tal caso aggiunge il node1 all'insieme dei nodi adiacenti
            // Se è presente un cappio considera ugualmente il nodo come nodo adiacente
            if (e.getNode2().equals(node))
                result.add(e.getNode1());
            else
                // Se il nodo2 è diverso dal nodo passato in input, allora significa che il nodo1 è uguale al nodo passato,
                // quindi lo aggiunge nell'insieme dei nodi adiacenti
                result.add(e.getNode2());
        }
        // Restituisce l'insieme dei nodi adiacenti del nodo passato in input
        return result;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        // Verifica delle API del metodo
        throw new UnsupportedOperationException(
                "Ricerca dei nodi predecessori non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        // Inizializza il risultato
        Set<GraphEdge<L>> edges = new HashSet<GraphEdge<L>>();
        // Mette tutti i nodi del grafo in un insieme
        Set<GraphNode<L>> nodes = this.adjacentLists.keySet();
        // Scorre tutti i nodi e li aggiunge nell'insieme edges
        for (GraphNode<L> nodo : nodes)
            edges.addAll(this.adjacentLists.get(nodo));
        // Restituisce l'insieme di tutti gli archi del grafo
        return edges;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla che l'arco passato sia valido, che il grafo sia non orientato,
        // che i nodi sorgente e destinazione dell'arco appartengano al dominio della mappa
        if (edge == null)
            throw new NullPointerException("Tentativo di inserire arco nullo");
        if (edge.isDirected())
            throw new IllegalArgumentException("Tentativo di inserire un arco orientato in un grafo non orientato");
        if (!this.adjacentLists.containsKey(edge.getNode1()) || !this.adjacentLists.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Inserimento di un arco con almeno uno dei due nodi non esistente nel grafo");
        // Eccezione in più per controllare se un cappio è guà stato inserito
        if (edge.getNode1() == edge.getNode2()) {
            if (this.containsEdge(edge))
                throw new IllegalArgumentException("Cappio già inserito");
            this.countEdges++;
        }
        // Inserisce l'arco nella lista di adiacenza del nodo1 e del nodo2 perchè è non orientato
        boolean check = this.adjacentLists.get(edge.getNode1()).add(edge);
        // Il metodo add restituisce true se l'elemento non è presente e viene aggiunto correttamente, false altrimenti 
        check = check && this.adjacentLists.get(edge.getNode2()).add(edge);
        // Se il check è positivo allora aumenta il numero degli archi e restituisce true; 
        // altrimenti il numero degli archi resta invariato e restituisce false
        if (check)
            countEdges++;
        return check;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla che l'arco passato sia valido e che i nodi sorgente e destinazione
        // dell'arco appartengano al dominio della mappa
        if (edge == null)
            throw new NullPointerException("Tentativo di inserire arco nullo");
        if (!this.adjacentLists.containsKey(edge.getNode1()) || !this.adjacentLists.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Inserimento di un arco con almeno uno dei due nodi non esistentio nel grafo");
        // Rimuove l'arco nella lista di adiacenza nelle posizioni con chiave i due nodi
        boolean check = this.adjacentLists.get(edge.getNode1()).remove(edge);
        check = this.adjacentLists.get(edge.getNode2()).remove(edge);
        // Se il check restituisce true allora decrementa il numero degli archi e restituisce true;
        // altrimenti il numero degli archi resta invariato e restituisce false 
        if (check)
            countEdges--;
        return check;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        // Verifica delle API del metodo: controlla se l'arco passato è valido e che i nodi sorgente e destinazione
        // dell'arco appartengano al dominio della mappa
        if (edge == null)
            throw new NullPointerException("Tentativo di inserire arco nullo");
        if (!this.adjacentLists.containsKey(edge.getNode1()) || !this.adjacentLists.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Inserimento di un arco con almeno uno dei due nodi non esistentio nel grafo");
        // Cerca l'arco nella lista di adiacenza nelle posizioni con chiave i due nodi
        boolean check = this.adjacentLists.get(edge.getNode1()).contains(edge);
        check = check || this.adjacentLists.get(edge.getNode2()).contains(edge);
        // Se il check è vero allora restituisce true, altrimenti false
        return check;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        // Verifica delle API del metodo: controlla se il nodo passato è valido e se un nodo esiste nel grafo
        if (node == null)
            throw new NullPointerException("Tentativo di ottenere gli archi da un nodo null");
        // Inizializza l'insieme degli archi
        Set<GraphEdge<L>> edges = new HashSet<GraphEdge<L>>();
        // Mette tutti gli archi del grafo, se esistono, nell'insieme edges
        edges = this.adjacentLists.get(node);
        if (edges == null)
            throw new IllegalArgumentException("Richiesta degli archi di un nodo non esistente");
        // Restituisce l'insieme degli archi entranti e uscenti al nodo poichè è un grafo non orientato
        return edges;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        // Verifica delle API del metodo
        throw new UnsupportedOperationException(
                "Ricerca degli archi entranti non supportata in un grafo non orientato");
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // Verifica delle API del metodo: controlla se i nodi passati in input sono validi e che appartengono al grafo
        if (node1 == null || node2 == null)
            throw new NullPointerException("Almeno un nodo passato è null");
        if (!(this.containsNode(node1) && this.containsNode(node2)))
            throw new IllegalArgumentException("Almeno uno dei nodi non è presente in questa lista di adiacenza");
        // Insieme contenente tutti gli archi del nodo1
        Set<GraphEdge<L>> a = this.adjacentLists.get(node1);
        // Insieme contenente tutti gli archi del nodo2
        Set<GraphEdge<L>> b = this.adjacentLists.get(node2);
        // Scorre l'insieme degli archi del nodo1, se un oggetto e è presente nel set del nodo2,  allora significa
        // che è presente un collegamento tra nodo1 e nodo2  e quindi restituisce l'arco e
        for (GraphEdge<L> e : a)
            if (b.contains(e))
                return e;
        // Se arriva fin qua significa che non esiste un arco che collega i due nodi passati in input e quindi restituisce null
        return null;
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        // Verifica delle API del metodo
        throw new UnsupportedOperationException(
                "Operazioni con indici non supportate");
    }

}
