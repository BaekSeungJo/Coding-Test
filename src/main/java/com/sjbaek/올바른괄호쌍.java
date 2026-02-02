package com.sjbaek;

import java.util.Deque;

/**
 * 주어진 문자열 s는 '(',')','{','}','[',']'로만 이루어져 있습니다.
 * 아때, 문자열 s에서 올바른 괄호 쌍의 개수를 반환하는 solution 함수를 작성하세요.
 * 만약 문자열 s가 올바르지 않는 괄호로 구성되어 있다면 -1을 반환합니다.
 * <p>
 * 제약조건
 * 1. 1 <= s.length() <= 10^5
 * 2. s는 '(',')','{','}','[',']'로만 구성됩니다.
 */
public class 올바른괄호쌍 {

    /**
     * 문제분석
     * --------------------------------------------------------------------------------
     * 괄호 쌍의 개수 -> Stack ( LIFO )
     * 입력 : '(',')','{','}','[',']' 로만 이루어진 문자열
     * 출력 : 올바른 괄호 쌍의 개수, 올바른 괄호 쌍이 아니면 -1
     * 제약조건에 따른 고려사항
     * - O(n^2) 선택 시 O(10^10) 으로 O(10^8) 초과하므로 시간 초과, 따라서 최대 O(NlogN) 시간 복잡도 알고리즘 필요
     */

    /**
     * 문제풀이 1.
     * 알고리즘 : Stack ( LIFO )
     * <p>
     * InVariant
     * - 문자열을 왼쪽에서 오른쪽으로 처리하는 동안, 스택에는 아직 매칭되지 않은 여는 괄호 종료만 저장되어 있으며,
     * 스택의 크기는 현재 위치까지 등장한 여는 괄호의 개수에서 형식이 동일한 매칭된 닫는 괄호의 개수를 뺀 값과 항상 동일하다.
     * <p>
     * Proof Sketch
     * 1) 여는 괄호를 만나면
     * - 향후 닫아야 하므로 stack에 push한다.
     * 2) 닫는 괄호를 만나면
     * - 스택이 비어 있으면 매칭할 여는 괄호가 없으므로 즉시 -1 반환
     * - 스택 top pop 하고, 타입이 일치하지 않으면 중첩 규칙을 위반하므로 즉시 -1 반환
     * - 타입이 일치하면 하나의 올바른 괄호 쌍이 확정되므로 count를 갱신한다.
     * 3) 모든 문자를 처리한 후
     * - 스택이 비어 있지 않으면 닫히지 않는 여는 괄호가 남아 있으면 -1 반환
     * - 스택이 비어 있으면 모든 괄호가 정확히 매칭되므로 count 반환
     * <p>
     * 시간 복잡도 : O(N)
     */
    public static int solution(String s) {
        if((s.length() & 1) == 1) return -1;

        int answer = 0;
        Deque<Character> stack = new java.util.ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return -1;
                char top = stack.pop();
                if (
                        top == '(' && c == ')' || top == '{' && c == '}' || top == '[' && c == ']'
                ) {
                    answer++;
                } else {
                    return -1;
                }
            }
        }

        return stack.isEmpty() ? answer : -1;
    }

    public static int solution2(String s) {
        if((s.length() & 1) == 1) return -1;

        int answer = 0;
        Deque<Character> stack = new java.util.ArrayDeque<>();

        for(char c : s.toCharArray()) {
            if(c == '(') {
                stack.push(')');
            } else if(c == '[') {
                stack.push(']');
            } else if(c == '{') {
                stack.push('}');
            } else {
                if(stack.isEmpty() || stack.peek() != c) return -1;
                else if(stack.peek() == c) {
                    stack.pop();
                    answer++;
                }
            }
        }

        return stack.isEmpty() ? answer : -1;
    }
}
