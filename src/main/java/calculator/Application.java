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

        if (input.startsWith("//")) {
            int nl = input.indexOf("\\n");
            if (nl < 0) throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
            String header = input.substring(2, nl);
            if (header.length() != 1) {
                throw new IllegalArgumentException("커스텀 구분자는 한 글자여야 합니다.");
            }
            delimiter.add(header.charAt(0));
            start = nl + 1; // 본문 시작
            if (start >= input.length()) {
                return 0; // 빈 값 0 출력
            }
        }

        int sum = 0;
        int i = start;
        boolean hasNumber = false;
        int current = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            if (c >= '0' && c <= '9') {
                hasNumber = true;
                current = (current * 10) + (c - '0'); // 십진수 자리 밀기(×10) + 새 자리 더하기
            } else if (delimiter.contains(c)) {
                if (!hasNumber) {
                    throw new IllegalArgumentException("연속 구분자 또는 (맨앞, 맨뒤)구분자는 허용되지 않습니다.");
                }
                sum += current;
                current = 0;
                hasNumber = false;
            } else {
                throw new IllegalArgumentException("허용되지 않은 구분자: " + c);
            }
            i++;
        }

        if (!hasNumber) {
            throw new IllegalArgumentException("연속 구분자 또는 (맨앞, 맨뒤)구분자는 허용되지 않습니다.");
        }
        sum += current;
        return sum;
    }
}


