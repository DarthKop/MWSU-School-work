import java.util.*;
/*
Thomas Kopcho
CSC 285
Parcer Program


---------------------------
This program utilizes stacks to compute mathematical problems
The ArrayLists are used to Push and Pop values onto the stack.

character arrays are used to assign values. An integer array goes along with each char array to assign the char a value

A generic ArrayList Manager manages the array list, and allows for easier pushing and popping onto the stacks.

finally an operator object is created for an expression's operators.

*/

public class Stack1 {

    //main class
    public static void main(String[] args){
        //initializing some values
        int i,number, charValue;
        int j;
        //Variable tables
        int [][] matrixA = {{1,2},{3,4}};
        char [] varTable ={'A', 'B', 'C', 'D', 'E', 'F', '1', '2', '3' , '4' , '5', '6', '7', '8', '9', '0'};
        int[] iValue = {8, 12, 2, 3, 15, 4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        //Operator Table
        char [] opert={'@', '%', '*', '/', '+', '-', ')', '(', '#'};
        int [] opertValue={3,2,2,2,1,1,99,-99,-100};

        //creating the stacks
        //variable stack
        StackManager<Integer> operand= new StackManager<Integer>();
        //operator stack
        StackManager<opObject> operator = new StackManager<opObject>();
        //first expression
        String exp1 = "A@(2*(A-C*D))+(9*B/(2*C+1)-B*3)+E%(A-F)#";
        //Second Expression
        String exp2 = "B*(3@(A-D)%(B-C@D))+4@D*2#";
        //converts string to char array
        char[] CharExp1 = exp1.toCharArray();
        char[] CharExp2 = exp2.toCharArray();
        //prints out charexp1 & 2
        System.out.println(CharExp1);
        System.out.println(CharExp2);
        System.out.println("----------Pushing Operator # onto stack with priority -100----------");
        opObject pnode1= new opObject('#', -100);
        operator.pushNode(pnode1);int oprior, exValue;
        i=0;
        //loops through all until it finds # which signals the end of the expression
        while(CharExp1[i] !='#'){
            //prints out which char it is parsing
            System.out.println("Parsing: " +CharExp1[i]);
            //if current parse is a variable or number it recognizes it as an operand
            if(((CharExp1[i]>='0') &&(CharExp1[i] <='9'))||((CharExp1[i]>= 'A') && (CharExp1[i]<='Z'))){
                System.out.println(CharExp1[i] + " Is an Operand!");
                //finds the value using the variable array and integer array that goes with it
                charValue=findVal(CharExp1[i], varTable, iValue, 15);
                if(charValue==-99)System.out.println("No Value in Table For: " + CharExp1[i]);
                System.out.println("Pushing it onto the Stack");
                //pushes onto stack
                operand.pushNode(charValue);
            }else {
                //if its an operator
                System.out.println("This is an Operator!");
                //pushing the left parenthesis onto the stack
                if(CharExp1[i] =='('){
                    System.out.println("Pushing Left Parenthesis to Stack");
                    //least priority
                    opObject pnodeo=new opObject(CharExp1[i], -99);
                    operator.pushNode(pnodeo);
                }
                //if its the right parenthesis then it calls PushPop to compute
                else if(CharExp1[i] ==')'){
                    while((operator.peekNode()).operator!='('){
                        PushPop(operator, operand);
                    }
                    operator.popNode();
                }else{
                    oprior=findVal(CharExp1[i],opert, opertValue, 8);
                    System.out.println("Peeking at Top of Stack: "+(operator.peekNode().priority));
                    while(oprior<=(operator.peekNode().priority))PushPop(operator,operand);
                        System.out.println("Pushing: "+ CharExp1[i]+" With Priority: " +oprior);
                        opObject pnodeo = new opObject(CharExp1[i], oprior);
                        operator.pushNode(pnodeo);
                    }
                }
                //increment counter
                i++;
            }
            while(operator.peekNode().operator!='#'){
                PushPop(operator, operand);
            }
            //getting value of first expression
            exValue=operand.popNode();
            System.out.println("The Value For Expression 1 is: "+ exValue);

            //Calculating 2nd expression Everything is the same as the first one.
        i=0;
        while(CharExp2[i] !='#'){
            System.out.println("Parsing: " +CharExp2[i]);
            if(((CharExp2[i]>='0') &&(CharExp2[i] <='9'))||((CharExp2[i]>= 'A') && (CharExp2[i]<='Z'))){
                System.out.println(CharExp2[i] + " Is an Operand!");
                charValue=findVal(CharExp2[i], varTable, iValue, 15);
                if(charValue==-99)System.out.println("No Value in Table For: " + CharExp2[i]);
                System.out.println("Pushing it onto the Stack");
                operand.pushNode(charValue);
            }else {
                System.out.println("This is an Operator!");
                if(CharExp2[i] =='('){
                    System.out.println("Pushing Left Parenthesis to Stack");
                    opObject pnodeo=new opObject(CharExp2[i], -99);
                    operator.pushNode(pnodeo);
                }else if(CharExp2[i] ==')'){
                    while((operator.peekNode()).operator!='('){
                        PushPop(operator, operand);
                    }
                    operator.popNode();
                }else{
                    oprior=findVal(CharExp2[i],opert, opertValue, 8);
                    System.out.println("Peeking at Top of Stack: "+(operator.peekNode().priority));
                    while(oprior<=(operator.peekNode().priority))PushPop(operator,operand);
                    System.out.println("Pushing: "+ CharExp2[i]+" With Priority: " +oprior);
                    opObject pnodeo = new opObject(CharExp2[i], oprior);
                    operator.pushNode(pnodeo);
                }
            }
            i++;
        }
        while(operator.peekNode().operator!='#'){
            PushPop(operator, operand);
        }
        //value of 2nd expression
        exValue=operand.popNode();
        System.out.println("The Value For Expression 2 is: "+ exValue);
    }//end main
    //Tells the operators what to do.

    public static int IntEval(int opnd1, char oper, int opnd2){
        int result;
        switch(oper){
            //exponent
            case '@': result= (int) Math.pow(opnd1,opnd2);
            System.out.println("***Eval " +opnd1+oper+opnd2+"*result* " + result);
            return result;
            //modulus
            case '%': result=opnd1%opnd2;
                System.out.println("***Eval " +opnd1+oper+opnd2+"*result* " + result);
                return result;
            //multiplication
            case '*': result= opnd1*opnd2;
                System.out.println("***Eval " +opnd1+oper+opnd2+"*result* " + result);
                return result;
            //division
            //tells you if there is a division by 0 attempted
            case '/': if(opnd2 !=0) {
                result = opnd1 / opnd2;
                System.out.println("***Eval " + opnd1 + oper + opnd2 + "*result* " + result);
                return result;
            }else System.out.println("Attempted Divide By 0 Not Allowed");
            return -99;
            //addition
            case '+': result = opnd1+opnd2;
            return result;
            //subraction
            case '-': result = opnd1-opnd2;
            return result;
            //Bad operator default
            default: System.out.println("Bad Operator " + oper);
            return -99;
        }//end Switch
    }//end IntEval
    //this gets the actual value of the variable or operator.
    //The value of the operator is its priority
    public static int findVal(char x, char[] vtab, int [] valtb, int last){
        int i, vreturn= -99;
        for(i=0;i<=last; i++)
            if(vtab[i] ==x) vreturn= valtb[i];
            System.out.println("Found Char: " + x + ", The value is: " + vreturn);

        return vreturn;
    }//end findVal

    //pushes and pops to the stack
    public static void PushPop(StackManager<opObject> x, StackManager<Integer> y){
        int a,b,c;
        char operandX;
        operandX=(x.popNode()).getOperator();
        a=y.popNode();
        b=y.popNode();
        System.out.println("in PushPop " +b+operandX+a);
        c = IntEval(b, operandX, a);
        y.pushNode(c);
        return;
    }
}//end Main Class; Stack1
//the manager for the arraylist AKA stacks
class StackManager<T>{
    protected ArrayList<T> stack;
    //counter
    protected int num;
    public StackManager(){
        num=0;
        stack = new ArrayList<T>(100);
    }//end StackManager
    //allows you to get the number of items in array
    public int getNum(){
        return num;
    }//end getnum
    //adds to the array list
    public int pushNode(T x){
        System.out.println("In pushNode " + num + "X is " + x);
        stack.add(num,x);
        num++;
        System.out.println("Leaving pushNode");
        return num;
    }//end pushNode
    //removes from array list
    public T popNode(){
        T nodeVal;
        nodeVal = stack.get(num-1);
        stack.remove(num-1);
        num--;
        return nodeVal;
    }//end popNode
    //allows you to see what ever node you like
    public T peekNode(){
        T nodeVal;
        nodeVal= stack.get(num-1);
        return nodeVal;
    }//end peekNode
    //Sees if stack is empty or not
    boolean EmptyStack(){
        if(num==0) return true;
        else return false;
    }//end EmptyStack
}//end StackManager Class
//operator object class
class opObject{
    protected char operator;
    protected int priority;
    public opObject(char opert, int pri){
        operator = opert;
        priority=pri;
    }//end opObject
    public int getPriority(){
        return priority;
    }//end getPriority
    public char getOperator(){
        return operator;
    }//end getOperator
}//end opObject Class
