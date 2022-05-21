import java.util.Scanner; // needed for the main function (user input)

/**
 * value: data stored in highest position of the Stack
 * lower: contains Stack with all the rest of the data stored in the lower positions
 * @param <T>
 */
class MyStack<T> implements MyStackInterface<T>{
    private MyStack<T> lower;
    private T value;

    /** creates an empty stack */
    MyStack (){
    }

    /** Creates a Stack with value as first data point. */
    MyStack (T value){
        this.value = value;
    }

    /** Creates a Stack from the old stack astack with value as new data point. */
    MyStack (MyStack<T> astack, T value){
        this.lower = astack;
        this.value = value;
    }

    /**  */
    public boolean isEmpty(){
        return this.lower == null;
    }

    /** Adds a value to the Stack by letting the "lower" pointer point to the old stack and adding 
     * newvalue as the next data point.
     */
    public void push(T newvalue){
        this.lower = new MyStack (this.lower, this.value);
        this.value = newvalue;
    }

    /** Returns current value. */
    public T top(){
        return this.value;
    }

    /** removes current value if there is a stack (aka if it's not empty). Otherwise an Exception will be thrown. 
     * temp temporarily stores the current value in order to return it.
    */
    public T pop(){
        if (this.isEmpty()){
            return null;
        }

        T temp = this.value;

        this.value = this.lower.value;
        this.lower = this.lower.lower;

        return temp;
    }

    /** Test functionality of MyStack class: adding new values and taking them off again.
     * intStack is an example Stack.
     * @param args
     */
    public static void main (String args[]){
        MyStack intStack = new MyStack();

        System.out.println("Please enter integer number.");
        Scanner in = new Scanner(System.in);

        String s = in.nextLine();
        int input = 0;

        try{
            input = Integer.parseInt(s);
            System.out.println(input);
        } catch(NumberFormatException e){
            System.out.println("Please only enter Integer number! Quitting program.");
            System.exit(-1);
        }

        System.out.println(intStack.isEmpty());

        intStack.push(input);
        
        System.out.println(intStack.top());

        intStack.push(3);

        System.out.println(intStack.isEmpty());

        System.out.println(intStack.pop());
        intStack.pop();

        System.out.println(intStack.isEmpty());
    }
}