package totalproject2;

import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Implementazione di una coda con priorit&agrave; tramite heap binario. Gli oggetti
 * inseriti in coda implementano l'interface PriorityQueueElement che permette
 * di gestire la priorit&agrave; e una handle dell'elemento. La handle &egrave; fondamentale
 * per realizzare in tempo logaritmico l'operazione di decreasePriority che,
 * senza la handle, dovrebbe cercare l'elemento all'interno dello heap e poi
 * aggiornare la sua posizione. Nel caso di heap binario rappresentato con una
 * ArrayList la handle &egrave; semplicemente l'indice dove si trova l'elemento
 * nell'ArrayList. Tale campo naturalmente va tenuto aggiornato se l'elemento
 * viene spostato in un'altra posizione.
 *
 * @param <E> il tipo degli elementi che vengono inseriti in coda.
 * @author Template: Luca Tesei
 */
public class BinaryHeapMinPriorityQueue {

    /*
     * ArrayList per la rappresentazione dello heap. Vengono usate tutte le
     * posizioni (la radice dello heap è quindi in posizione 0).
     */
    private final ArrayList<PriorityQueueElement> heap;

    /**
     * Crea una coda con priorit&agrave; vuota.
     */
    public BinaryHeapMinPriorityQueue() {
        // Inizializzazione della variabile d'istanza
        this.heap = new ArrayList<PriorityQueueElement>();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the heap. The handle of the element will also be set
     * accordingly.
     *
     * @param element the new element to add
     * @throws NullPointerException if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        // Verifica delle API del metodo: controlla se l'elemento passato è valido
        if (element == null)
            throw new NullPointerException("L'elemento passato è nullo ");
        // Se la lista è vuota inserisce l'elemento nella posizione 0
        if (this.heap.isEmpty()) {
            this.heap.add(element);
            updateHandles(this.heap);
        } else {
            // Aggiunge alla fine della lista l'elemento passato
            this.heap.add(element);
            // Scarica tutto lo scambio al metodo moveUp
            moveUp(this.heap.size() - 1);
        }
    }


    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the heap.
     *
     * @return the current minimum element of this min-priority queue
     * @throws NoSuchElementException if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        // Verifica delle API del metodo: controlla se la coda sia non vuota, altrimenti lancia un'eccezione
        if (this.isEmpty())
            throw new NoSuchElementException("La coda di min-priorita' e' vuota");
        // Restituisce il primo elemento nella posizione 0 della lista
        return this.heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * binary heap will be updated accordingly.
     *
     * @return the current minimum element
     * @throws NoSuchElementException if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        // Verifica delle API del metodo: controlla se la coda sia non vuota, altrimenti lancia un'eccezione
        if (this.isEmpty())
            throw new NoSuchElementException("La coda di min-priorita' e' vuota");
        // Memorizza la radice nella variabile di tipo PriorityQueueElement
        PriorityQueueElement root = this.heap.get(0);
        // Se la lista ha un solo elemento cancella tutta la lista
        if (this.heap.size() == 1)
            this.heap.clear();
        else {
            // Mette l'ultima foglia dell'albero nella radice, rimuove l'ultimo elemento(perchè copiato nella radice)
            // e poi chiama il metodo heapify sulla radice per soddisfare la proprietà di MinHeap
            this.heap.set(0, this.heap.get(this.heap.size() - 1));
            this.heap.remove(this.heap.size() - 1);
            this.minHeapify(0);
        }
        // Restituisce la radice
        return root;
    }

    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     *
     * @param element     the element whose priority will be decreased, it
     *                    must currently be inside this min-priority queue
     * @param newPriority the new priority to assign to the element
     * @throws NoSuchElementException   if the element is not currently
     *                                  present in this min-priority queue
     * @throws IllegalArgumentException if the specified newPriority is not
     *                                  strictly less than the current
     *                                  priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
                                 double newPriority) {
        // Verifica delle API del metodo: controlla se l'elemento è presente nella coda e se il valore passato sia valido
        if (this.heap.size() == 0 || !this.heap.contains(element))
            throw new NoSuchElementException("L'elemento non è nella coda di priorità minima");
        if (element.getPriority() <= newPriority)
            throw new IllegalArgumentException("Tentativo di decremento non valido della chiave");
        // Se la handle è l'indice, e se l'elemento è in posizione 0, ossia la radice,
        // allora aggiorna la priorità senza chiamare il metodo moveUp
        if (this.heap.size() == 1 || element.getPriority() == 0) {
            element.setPriority(newPriority);
            return;
        }
        // Altrimenti, set della nuova priorità
        element.setPriority(newPriority);
        // Chiama il metodo moveUp dall'indice corrente dell'elemento
        moveUp(element.getHandle());
    }

    /**
     * Determines if this priority queue is empty.
     *
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        // Restituisce true se il confronto sotto è vero, false altrimenti
        return this.heap.size() == 0;
    }

    /**
     * Return the current size of this queue.
     *
     * @return the number of elements currently in this queue.
     */
    public int size() {
        // Restituisce il numero di elementi nella coda
        return this.heap.size();
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        // Cancella tutti gli elementi della coda
        this.heap.clear();
    }

    /*
     * Metodi privati per scopi di implementazione codice
     */
    private boolean hasRight(int i) {
        // Restituisce true se esiste il nodo figlio destro di i, false altrimenti
        return rightIndex(i) < this.heap.size();
    }

    private boolean hasLeft(int i) {
        // Restituisce true se esiste il nodo figlio sinsitro di i, false altrimenti
        return leftIndex(i) < this.heap.size();
    }

    private int rightIndex(int i) {
        // Restituisce l'indice del nodo figlio destro di i
        return 2 * i + 2;
    }

    private int leftIndex(int i) {
        // Restituisce l'indice del nodo figlio sinistro di i
        return 2 * i + 1;
    }

    private int parent(int i) {
        // Controlla se l'indice passato è la radice
        if (i == 0)
            return -1;
        // Restituisce l'indice del nodo del figlio i
        return (i - 1) / 2;
    }

    /*
     * Metodo privato che viene chiamato dal metodo extractMinimum() e poi richiamato ricorsivamente
     * per soddisfarre la proprietà di Min-Heap (tutti i nodi padri devono avere priorità minore dei nodi figli)
     */
    private void minHeapify(int i) {
        // Aggiorna la handle ogni volta che viene richiamato il metodo
        updateHandles(this.heap);
        // Se non ha un nodo figlio sinistro (quindi è una foglia) termina
        if (!hasLeft(i))
            return;
        // Vede se c'è bisogno di spostare qualche figlio
        int min = i;
        if (this.heap.get(min).getPriority() > this.heap.get(leftIndex(i)).getPriority())
            min = leftIndex(i);
        if (hasRight(i) && this.heap.get(min).getPriority() > this.heap.get(rightIndex(i)).getPriority())
            min = rightIndex(i);
        if (min == i)
            return; // Ha finito
        // Scambia i con min e richiama la funzione ricorsiva sull'indice del nodo figlio
        PriorityQueueElement app = this.heap.get(i);
        this.heap.set(i, this.heap.get(min));
        this.heap.set(min, app);
        minHeapify(min);
    }

    /*
     * Metodo dichiarato protected solo per fini di controllo JUnit5
     */
    protected ArrayList<PriorityQueueElement> getBinaryHeap() {
        // Resituisce la lista degli elementi dell'heap
        return this.heap;
    }

    /*
     * Metodo che permette ai metodi decreasePriority(PriorityQueueElement, double) e insert(PriorityQueueElement)
     * di mettere nella posizione che gli compete il nodo inserito/decrementato in base alla priorità
     */
    private void moveUp(int i) {
        while (i > 0 && this.heap.get(i).getPriority() < this.heap.get(parent(i)).getPriority()) {
            // Scambio, crea una variabile d'appoggio per memorizzare il nodo da scambiare in modo tale che non
            // vada perso
            PriorityQueueElement appoggio = this.heap.get(i);
            // Copia l'elemento padre nell'indice passato
            this.heap.set(i, this.heap.get(parent(i)));
            // Copia l'appoggio nel nodo padre
            this.heap.set(parent(i), appoggio);
            // Aggiorna la i
            i = parent(i);
        }
        // Aggiorna la handle di ogni elemento
        updateHandles(this.heap);
    }

    /*
     * Ogni volta che viene chiamato questo metodo si aggiorna la handle di ogni elemento dello heap corrente
     */
    private void updateHandles(ArrayList<PriorityQueueElement> heap) {
        // Indice che verrà poi incrementato ogni volta che continuo l'iterazione
        int index = 0;
        // Per ogni elemento dello heap gli associa index(progressivamente) come handle
        for (PriorityQueueElement element : heap) {
            element.setHandle(index);
            index++;
        }
    }
}
