package totalproject2;

import java.util.List;

/**
 * Questa interface definisce oggetti che sono calcolatori di cammini minimi con
 * sorgente singola su un certo grafo orientato e pesato dato. Il grafo su cui
 * lavorare deve essere passato quando l'oggetto calcolatore viene costruito. Il
 * calcolatore implementa un algoritmo per il calcolo dei i cammini minimi con
 * sorgente singola utilizzando una coda con priorit&agrave; le cui funzionalit&agrave; sono
 * specificate nell'interfaccia {@code MinPriorityQueue<V>}. La coda pu&ograve; essere
 * usata per l'inserimento e l'estrazione dei nodi durante l'esecuzione
 * dell'algoritmo, nonch&egrave; per l'aggiornamento della loro priorit&agrave; tramite il
 * metodo decreasePriority.
 *
 * @param <L> il tipo delle etichette dei nodi del grafo
 * @author Luca Tesei
 */
public interface SingleSourceShortestPathComputer<L> {

    /**
     * Inizializza le informazioni necessarie associate ai nodi del grafo
     * associato a questo calcolatore ed esegue un algoritmo per il calcolo dei
     * cammini minimi a partire da una sorgente data.
     *
     * @param sourceNode il nodo sorgente da cui calcolare i cammini minimi
     *                   verso tutti gli altri nodi del grafo
     * @throws NullPointerException     se il nodo passato &egrave; nullo
     * @throws IllegalArgumentException se il nodo passato non esiste nel
     *                                  grafo associato a questo calcolatore
     * @throws IllegalStateException    se il calcolo non pu&ograve; essere
     *                                  effettuato per via dei valori dei
     *                                  pesi del grafo, ad esempio se il
     *                                  grafo contiene cicli di peso
     *                                  negativo.
     */
    void computeShortestPathsFrom(GraphNode<L> sourceNode);

    /**
     * Determina se &egrave; stata invocata almeno una volta la procedura di calcolo
     * dei cammini minimi a partire da un certo nodo sorgente specificato.
     *
     * @return true, se i cammini minimi da un certo nodo sorgente sono stati
     * calcolati almeno una volta da questo calcolatore
     */
    boolean isComputed();

    /**
     * Restituisce il nodo sorgente specificato nell'ultima chiamata effettuata
     * a {@code computeShortestPathsFrom(GraphNode<V>)}.
     *
     * @return il nodo sorgente specificato nell'ultimo calcolo dei cammini
     * minimi effettuato
     * @throws IllegalStateException se non &egrave; stato eseguito nemmeno una
     *                               volta il calcolo dei cammini minimi a
     *                               partire da un nodo sorgente
     */
    GraphNode<L> getLastSource();

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     *
     * @return il grafo su cui opera questo calcolatore
     */
    Graph<L> getGraph();

    /**
     * Restituisce una lista di archi dal nodo sorgente dell'ultimo calcolo di
     * cammini minimi al nodo passato. Tale lista corrisponde a un cammino
     * minimo tra il nodo sorgente e il nodo target passato.
     *
     * @param targetNode il nodo verso cui restituire il cammino minimo
     *                   dalla sorgente
     * @return la lista di archi corrispondente al cammino minimo; la lista è
     * vuota se il nodo passato &egrave; il nodo sorgente. Viene restituito
     * {@code null} se il nodo passato non &egrave; raggiungibile dalla
     * sorgente
     * @throws NullPointerException     se il nodo passato &egrave; nullo
     * @throws IllegalArgumentException se il nodo passato non esiste
     * @throws IllegalStateException    se non &egrave; stato eseguito nemmeno una
     *                                  volta il calcolo dei cammini minimi
     *                                  a partire da un nodo sorgente
     */
    List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode);

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     *
     * @param path un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException se il cammino passato &egrave; nullo
     */
    default String printPath(List<GraphEdge<L>> path) {
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

}
