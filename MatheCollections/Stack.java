package MatheCollections;

public class Stack<T> {
    private int growth;
    private Object[] stack;
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

    public void push(T obj){
        if (stack[stack.length-1] != null)
            grow();
            
        top++;
        stack[top] = obj;
    }

    @SuppressWarnings("unchecked")
    public T peek(){
        return (T)stack[top];
    }

    @SuppressWarnings("unchecked")
    public T pop(){
        Object output = stack[top];
        stack[top] = null;
        top--;
        return (T)output;
    }

    public boolean isEmpty(){
        return stack[0] == null;
    }

    public void clear(){
        stack = new Object[growth];
    }

    private void grow(){
        Object[] biggerStack = new Object[stack.length + growth];
        for (int i = 0; i < stack.length; i++)
            biggerStack[i] = stack[i];
        
        stack = biggerStack;
    }
}
