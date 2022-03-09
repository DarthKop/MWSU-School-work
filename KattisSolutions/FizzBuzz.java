import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FizzBuzz {
    public static void main(String[] args)throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inputString[] = input.readLine().split(" ");
        int a = Integer.parseInt(inputString[0]);
        int b = Integer.parseInt(inputString[1]);
        int c = Integer.parseInt(inputString[2]);
        for(int i = 1;i <= c; i++){
            if(i % a == 0 && i % b == 0){
                System.out.println("FizzBuzz");
            }else if(i % a == 0) {
                System.out.println("Fizz");
            }else if(i % b == 0){
                System.out.println("Buzz");
            }else System.out.println(i);
        }
    }
}
