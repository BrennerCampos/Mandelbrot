public class Stack
{

    //public Coordinate top;
    // public Coordinate bottom;

    private static class Node       // class defining this particular stack of coordinates
    {
        public Coordinate crd;      // takes in a coordinate object with 4 coordinates, calling the link "crd"
        public Node bottom;         // establishes a bottom for the node object

        public Node(Coordinate crd)     //  element/node constructor taking in coord object and its coordinates and setting bottom to empty
        {
            this.crd = crd;     // top takes in the current coords
            bottom = null;      // and has no bottom yet
        }

    }


    public Node top;            // define a "top" for the element of the stack
    public Node bottom;         // define a "bottom" for element of the stack


    public Stack()      // constructor
    {
        top = null;         // initially setting both top and bottom to null (empty)
        bottom = null;
    }


    public void push(Coordinate crd)        // push function to insert into the stack
    {

        Node node = new Node(crd);  // create a new stack element "node" with given arguments
        node.bottom = top;          // makes the bottom now the top
        top = node;                 // and makes the top the new object with coordinates we are saving
    }


    public Coordinate pop()         // pop function to revert to previously saved coordinates on the stack
    {

       Node node = top; // checking top node on whether it is empty or not

        if(this.isEmpty()) {     //check if Stack is empty
            return null;        // if so, return null;
        }


        // how to only pop if not empty?

    //check if Stack has only 1 element

        if(top == top.bottom)       // if the top pointer is the same as the bottom, it's the only element in the stack
        {
            top = null;
            //top.bottom = null;
        }
        else
            {
            top = top.bottom;   // otherwise  do set the top to the bottom
            }


        return node.crd;        // return coords for top node

    }

    public boolean isEmpty()    // checks to see if the stack is empty
    {
        return top == null;     // returns true or false on whether the top is null (meaning empty stack)
    }
}