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
        } finally {
            Console.close();
        }
    }
}

class StringCalculator {
    public static int sum(String input) {
        if (input == null || input.isEmpty()) { // 빈 값 -> 0
            return 0;
        }

        int start = 0;
        Set<Character> delimiter = new HashSet<>();
        delimiter.add(','); // 기본 구분자
        delimiter.add(':');

        if (input.startsWith("//")) {
            int headerEnd = input.indexOf('\n');
            int consumed = 1; // '\n'은 1글자
            if (headerEnd < 0) {
                headerEnd = input.indexOf("\\n");
                if (headerEnd < 0) {
                    throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
                }
                consumed = 2; // "\\n"은 2글자
            }

            String header = input.substring(2, headerEnd).trim();
            if (header.length() != 1) {
                throw new IllegalArgumentException("커스텀 구분자는 한 글자여야 합니다.");
            }
            char custom = header.charAt(0);

            if (Character.isDigit(custom) ||
                    custom == ',' || custom == ':' || custom == '\\' || custom == '/') {
                throw new IllegalArgumentException("커스텀 구분자로 숫자와 ',', ':', '\\', '/' 는 사용할 수 없습니다.");
            }

            delimiter.add(custom);
            start = headerEnd + consumed;

            if (start >= input.length()) { // 본문 비어있음 -> 0
                return 0;
            }
        }

        int sum = 0;
        int current = 0;
        boolean hasNumber = false;

        for (int i = start; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c >= '0' && c <= '9') {
                hasNumber = true;
                current = current * 10 + (c - '0');
            } else if (delimiter.contains(c)) {
                if (!hasNumber) {
                    throw new IllegalArgumentException("연속 구분자 또는 (맨앞/맨뒤) 구분자는 허용되지 않습니다.");
                }
                if (current <= 0) { // 양수만 허용 (0, 음수 불가)
                    throw new IllegalArgumentException("양수만 입력할 수 있습니다.");
                }
                sum += current;
                current = 0;
                hasNumber = false;
            } else {
                throw new IllegalArgumentException("허용되지 않은 구분자: " + c);
            }
        }

        if (!hasNumber) {
            throw new IllegalArgumentException("연속 구분자 또는 (맨앞/맨뒤) 구분자는 허용되지 않습니다.");
        }
        if (current <= 0) {
            throw new IllegalArgumentException("양수만 입력할 수 있습니다.");
        }
        sum += current;

        return sum;
    }
}
