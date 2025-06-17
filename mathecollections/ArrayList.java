package mathecollections;

public class ArrayList<T> {
    /**
     * O array. Onde está armazenado todos os seus elementos.
     */
    private Object[] array;
    /**
     * Variável contadora que mantém o controle de quantos elementos estão no array.
     */
    private int count;
    /**
     * A quantidade de espaços que são adicionados ao array toda vez que ele atinge seu tamamho máximo.
     */
    private int growth;
    /**
     * Define o tamanho inicial do array toda vez que ele é instânciado.
     */
    private int initialSize;

    public ArrayList(int initialSize, int growth){
        count = 0;
        this.initialSize = initialSize;
        array = new Object[initialSize];
        this.growth = growth;
    }
    public ArrayList(int growth){
        count = 0;
        this.initialSize = growth;
        array = new Object[growth];
        this.growth = growth;
    }
    public ArrayList(){
        count = 0;
        this.initialSize = 20;
        array = new Object[initialSize];
        this.growth = 20;
    }

    /**
     * Adiciona um elemento no fim do <code>ArrayList</code>.
     * @param obj - Elemento a ser adicionado.
     */
    public void add(T obj){
        if (array[array.length-1] != null)
            grow();

        for (int i = 0; i < array.length; i++) {
            if(array[i] == null){
                count++;
                array[i] = obj;
                break;
            }
        }
    }

    /**
     * <p>Cria uma nova instância do <code>ArrayList</code>, removendo todos os elementos contidos nele, 
     * voltando para o seu tamanho inicial e definindo o <code>count</code> como 0.
     */
    public void clear(){
        count = 0;
        array = new Object[initialSize];
    }

    /**
     * Busca, e caso encontre, remove um elemento do <code>ArrayList</code>.
     * @param obj - Elemento a ser removido.
     */
    public void remove(T obj){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
                count--;
                array[i] = null;
                break;
            }
        }
        organize();
    }

    /**
     * @return <code>true</code> se o array estiver vazio (todos os elementos como <code>null</code>), 
     * <code>false</code> caso contrário.
     */
    public boolean isEmpty(){
        return array[0] == null;
    }

    /**
     * @return Quantos elementos não nulos estão no <code>ArrayList</code>
     */
    public int getCount(){
        return count;
    }

    /**
     * @param index - O índice do elemento a ser buscado.
     * @return O elemento que está localizado na posição passada por parâmetro, ou <code>null</code> caso contrário.
     */
    @SuppressWarnings("unchecked")
    public T get(int index){
        if (index >= 0 && index < count)
            return (T)array[index];
        return null;
    }

    /**
     * Define um elemento em um índice específico do <code>ArrayList</code>.
     * @param index - Posição a ser sobrescrita
     * @param obj - Elemento que irá sobrescrever
     */
    public void set(int index, T obj){
        if (index >= 0 && index < count)
            array[index] = obj;
    }

    /**
     * Organiza todos os elementos do <code>ArrayList</code>, orientados ao índice 0.
     */
    private void organize(){
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] == null) {
                array[i] = array[i+1];
                array[i+1] = null;
            }
        }
    }

    /**
     * Aumenta o tamanho do <code>ArrayList</code> de acordo com o valor definido em <code>growth</code>.
     * Copia todos os elementos do array antigo para um array maior.
     */
    private void grow(){
        Object[] biggerArray = new Object[array.length + growth];
        for (int i = 0; i < array.length; i++) {
            biggerArray[i] = array[i];
        }
        array = biggerArray;
    }
}
