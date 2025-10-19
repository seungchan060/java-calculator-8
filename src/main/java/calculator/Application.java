package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        try {
            System.out.println("덧셈할 문자열을 입력해 주세요.");
            String input = Console.readLine();
            int result = StringCalculator.sum(input);
            System.out.println("결과 : " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } finally {
            Console.close();
        }
    }
}

class StringCalculator {
    public static int sum(String input) {
        if (input.isEmpty()) { // 빈 값이 들어온다면 0 출력
            return 0;
        }

        int start = 0;
        Set<Character> delimiter = new HashSet<>();
        delimiter.add(',');
        delimiter.add(':');

        return 0;
    }
}


