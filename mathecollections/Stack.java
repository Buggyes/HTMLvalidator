package mathecollections;

/**
 * <p>Tem a mesma funcionalidade de uma pilha genérica simples.
 */
public class Stack<T> {
    /**
     * A quantidade de espaços que são adicionados a pilha toda vez que ela atinge seu tamamho máximo.
     */
    private int growth;
    /**
     * A pilha. Onde está armazenado todos os seus elementos.
     */
    private Object[] stack;
    /**
     * Indica qual posição da pilha é considerada o topo.
     */
    private int top;

    public Stack(int initialSize, int growth){
        stack = new Object[initialSize];
        this.growth = growth;
        this.top = -1;
    }
    public Stack(int growth){
        stack = new Object[growth];
        this.growth = growth;
        this.top = -1;
    }
    public Stack(){
        stack = new Object[10];
        this.growth = 10;
        this.top = -1;
    }

    /**
     * Adiciona um elemento na pilha.
     * @param obj - elemento a ser adicionado.
     */
    public void push(T obj){
        if (stack[stack.length-1] != null)
            grow();
            
        top++;
        stack[top] = obj;
    }

    /**
     * @return Valor <code>T</code> que está no topo da pilha.
     */
    @SuppressWarnings("unchecked")
    public T peek(){
        return (T)stack[top];
    }

    /**
     * Remove o valor que está no topo da pilha.
     * @return Valor <code>T</code> que está no topo da pilha.
     */
    @SuppressWarnings("unchecked")
    public T pop(){
        Object output = stack[top];
        stack[top] = null;
        top--;
        return (T)output;
    }

    /**
     * @return <code>true</code> se a pilha estiver vazia (todos os elementos como <code>null</code>), 
     * <code>false</code> caso contrário.
     */
    public boolean isEmpty(){
        return stack[0] == null;
    }

    /**
     * <p>Cria uma nova instância da pilha, removendo todos os elementos contidos nela e voltando para o seu tamanho original.
     */
    public void clear(){
        stack = new Object[growth];
    }

    /**
     * Aumenta o tamanho da pilha, transferindo todos os valores da pilha para uma pilha maior.
     */
    private void grow(){
        Object[] biggerStack = new Object[stack.length + growth];
        for (int i = 0; i < stack.length; i++)
            biggerStack[i] = stack[i];
        
        stack = biggerStack;
    }
}
