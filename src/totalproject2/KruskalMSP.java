package totalproject2;

import java.util.*;

/**
 * Classe singoletto che implementa l'algoritmo di Kruskal per trovare un
 * Minimum Spanning Tree di un grafo non orientato, pesato e con pesi non
 * negativi.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class KruskalMSP<L> {

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall'algoritmo di Kruskal.
     */
    private final ArrayList<HashSet<GraphNode<L>>> disjointSets;

    //comparatore tra archi
    private final ComparatorEdges comparatorEdges;

    /**
     * Costruisce un calcolatore di un albero di copertura minimo che usa
     * l'algoritmo di Kruskal su un grafo non orientato e pesato.
     */
    public KruskalMSP() {
        // Inizializzazione delle variabili di istanza
        this.disjointSets = new ArrayList<HashSet<GraphNode<L>>>();
        this.comparatorEdges = new ComparatorEdges();
    }

    /**
     * Utilizza l'algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L'albero restituito non &egrave; radicato, quindi &egrave; rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     *
     * @param g un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l'albero di
     * copertura minimo trovato
     * @throw NullPointerException se il grafo g &egrave; null
     * @throw IllegalArgumentException se il grafo g &egrave; orientato, non pesato o
     * con pesi negativi
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        // Verifica delle API del metodo: delega tutto il da farsi al metodo checkExceptions(Graph<L> g)
        checkExceptions(g);
        // Rimuove tutti gli elementi dell'ArrayList se presenti
        this.disjointSets.clear();
        // Prende tutti i nodi del grafo passato e li mette in un insieme
        Set<GraphNode<L>> nodes = g.getNodes();
        // Inizializzazione degli insiemi; per ogni insieme creato mette come unico elemento il nodo corrente del for
        for (GraphNode<L> n : nodes) {
            makeSet(n);
        }
        // Inizializzazione dell'insieme risultato
        Set<GraphEdge<L>> result = new HashSet<GraphEdge<L>>();
        // Crea una lista di archi per memorizzare tutti gli archi del grafo per poi ordinarli in ordine non decrescente
        List<GraphEdge<L>> edges = new ArrayList<GraphEdge<L>>();
        // Aggiunge alla lista creata tutti gli archi del grafo passato in input
        edges.addAll(g.getEdges());
        // Ordina la lista degli archi in ordine non decrescente
        Collections.sort(edges, this.comparatorEdges);
        // Scorre tutta la lista degli archi
        for (GraphEdge<L> e : edges) {
            int i = findSet(e.getNode1());
            int j = findSet(e.getNode2());
            // Controlla che non appartengono allo stesso albero; se appartengono allo stesso albero, si crea un ciclo, quindi 
            // non aggiunge l'arco e continuo il for
            if (i != j) {
                result.add(e);
                // Unisce gli insiemi i e j
                union(i, j);
            }
        }
        // Restituisce l'insieme risultato
        return result;
    }

    private void checkExceptions(Graph<L> g) {
        // Verifica delle API del metodo: controlla se il grafo passato è valido, che non sia orientato, che sia pesato
        // e che gli archi abbiano pesi non negativi
        if (g == null)
            throw new NullPointerException("Il grafo passato è nullo");
        if (g.isDirected())
            throw new IllegalArgumentException("Impossibilità della classe di gestire un grafo orientato");
        //ciclo for che scorre la lista degli archi del grafo passato
        for (GraphEdge<L> e : g.getEdges()) {
            //controllo se l'arco ha associato un peso, altrimenti lancio l'eccezione
            if (!e.hasWeight())
                throw new IllegalArgumentException("Il grafo contiene almeno un arco non pesato");
            //controllo se il peso dell'arco passato è positivo, altrimenti lancio l'eccezione
            if (e.getWeight() < 0)
                throw new IllegalArgumentException("Il grafo contiene almeno un arco con peso negativo");
        }
    }

    /*
     * Metodo privato che, per ogni nodo, crea insiemi inizializzandoli con un unico elemento che sarebbe il nodo
     * passato in input. Alla struttura dati viene aggiunto infine l'insieme creato.
     */
    private void makeSet(GraphNode<L> node) {
        HashSet<GraphNode<L>> h = new HashSet<GraphNode<L>>();
        h.add(node);
        // Aggiunge alla struttura dati l'insieme creato per ogni nodo, contenente il nodo stesso
        this.disjointSets.add(h);
    }

    /*
     * Metodo privato che cerca l'indice dell'insieme che contiene il nodo passato in input.
     */
    private int findSet(GraphNode<L> node) {
        // Scorre tutti gli insiemi disgiunti finchè non trova l'insieme che contiene il nodo passato. In caso positivo
        // restituisce l'indice, altrimenti -1 per indicare che il nodo passato non è in nessun insieme
        for (int i = 0; i < this.disjointSets.size(); i++) {
            if (this.disjointSets.get(i).contains(node))
                return i;
        }
        return -1;
    }

    /*
     * Metodo privato che unisce gli insiemi che contengono i e j.
     */
    private void union(int i, int j) {
        // Mette l'insieme della posizione j, nell'insieme della posizione con indice i
        this.disjointSets.get(i).addAll(this.disjointSets.get(j));
        // Rimuove l'insieme in posizione j
        this.disjointSets.remove(j);
    }

    /*
     * Classe privata che usa l'interfaccia Comparator per definire un ordinamento proprio.
     */
    private class ComparatorEdges implements Comparator<GraphEdge<L>> {
        // Ridefinizione del metodo compareTo(GraphEdge<L> e1, GraphEdge<L> e2)
        @Override
        public int compare(GraphEdge<L> e1, GraphEdge<L> e2) {
            // Vari controlli per restituire un intero a seconda del tipo di rapporto che intercorre tra i due archi
            if (e1.getWeight() == e2.getWeight())
                return 0;
            if (e1.getWeight() < e2.getWeight())
                return -1;
            // Se è arrivato fin qui significa che l'arco e1 ha peso maggiore dell'arco e2
            // e quindi restituisce 1
            return 1;
        }
    }
}
