package MatheCollections;

public class ArrayList<T> {
    private Object[] array;
    private int growth;
    private int initialSize;

    public ArrayList(int initialSize, int growth){
        this.initialSize = initialSize;
        array = new Object[initialSize];
        this.growth = growth;
    }
    public ArrayList(int growth){
        this.initialSize = growth;
        array = new Object[growth];
        this.growth = growth;
    }
    public ArrayList(){
        this.initialSize = 20;
        array = new Object[initialSize];
        this.growth = 20;
    }

    public void add(T obj){
        if (array[array.length-1] != null)
            grow();

        for (int i = 0; i < array.length; i++) {
            if(array[i] == null){
                array[i] = obj;
                break;
            }
        }
    }

    public void clear(){
        array = new Object[initialSize];
    }

    public void remove(T obj){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
                array[i] = null;
                break;
            }
        }
        organize();
    }

    private void organize(){
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] == null) {
                array[i] = array[i+1];
                array[i+1] = null;
            }
        }
    }

    private void grow(){
        Object[] biggerArray = new Object[array.length + growth];
        for (int i = 0; i < array.length; i++) {
            biggerArray[i] = array[i];
        }
        array = biggerArray;
    }
}
