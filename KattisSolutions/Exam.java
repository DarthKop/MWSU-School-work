import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Exam {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int numCorrect = Integer.parseInt(input.readLine());
        String myAnswers = input.readLine();
        String friendAnswers = input.readLine();
        int highScore = 0, possibleCorrect = 0;

        char[] myAnswersArr = new char[myAnswers.length()];
        char[] friendAnswersArr = new char[friendAnswers.length()];
        for(int i = 0; i < myAnswers.length(); i++){
            myAnswersArr[i] = myAnswers.charAt(i);
            friendAnswersArr[i] = friendAnswers.charAt(i);

            if(myAnswersArr[i] == friendAnswersArr[i] && numCorrect >0){
                highScore++;
                numCorrect--;
            }else if(myAnswersArr[i] == friendAnswersArr[i] && numCorrect <= 0){
                numCorrect--;
            }else{
                possibleCorrect++;
            }
        }
        if(numCorrect>0)possibleCorrect -= numCorrect;

        System.out.println(highScore+possibleCorrect);
    }
}
