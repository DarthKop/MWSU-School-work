import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class TetrisGen {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(in.readLine());
        String[] ts = new String[count];
        for (int i = 0; i < count; i++)
            ts[i] = in.readLine();

        for (String string : ts)
            System.out.println(checkts(string));

    }

    public static int checkts(String t) {
        char[] chars = t.toCharArray();
        int index = 0;

        HashSet<Character> set = new HashSet<>();

        while (index<chars.length) { // Skip first set if incomplete
            if (!set.add(chars[index]))
                break;
            index++;
        }
        int start = index;
        set.clear();
        while (index<chars.length) { // Check groups of seven for duplicates
            if (!set.add(chars[index])) {
                if (start == 0)
                    return 0;
                else {
                    index = start-1;
                    set.clear();
                    start--;
                    continue;
                }
            }
            if (set.size() == 7)
                set.clear();
            index++;
        }
        return 1;
    }
}
