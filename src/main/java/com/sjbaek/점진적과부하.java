package com.sjbaek;

import java.util.Deque;

/**
 * 스쿼트에 진심인 피트니스 매니아가 매일 최대 중량 기록을 측정하고 있습니다. 그의 목표는 더 무거운 무게를 드는 날을 지속적으로 만들어가는
 * 겁입니다.
 * 매일의 스쿼드 최대 중량 기록이 정수 배열 weights로 주어졌을 때, 각 날의 중량보다 더 높은 중량을 든 날까지 걸린 일수를 계산하여 배열로
 * 반환하는 solution 함수를 작성하세요.
 * 만약 이후에 더 높은 중량을 들지 못했으면 해당 값은 0으로 설정합니다.
 * <p>
 * 제약조건
 * 1. 1 <= weights.length <= 10^5
 * 2. 1 <= weights[1] <= 10^3
 *
 */
public class 점진적과부하 {

    /**
     * 문제분석
     * --------------------------------------------------------------------------------
     * 각 날의 중량보다 더 높은 중량을 든 날 -> 내림차순 단조스택(내림차순으로 정렬된 스택)
     * 입력 : 정수 배열 weights
     * 출력 : weights와 길이가 같은 정수 배열
     * 제약조건에 따른 고려사항
     * - O(n^2) 선택 시 O(10^10) 으로 O(10^8) 초과하므로 시간 초과, 따라서 최대 O(NlogN) 시간 복잡도 알고리즘 필요
     */

    /**
     * 문제풀이 1
     * 알고리즘 : 완전 탐색
     * 시간 복잡도 : O(n^2)
     */
    public static int[] solution1(int[] weights) {
        int[] answer = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            for (int j = i + 1; j < weights.length; j++) {
                if (weights[j] > weights[i]) {
                    answer[i] = j - i;  // 걸린 일수(거리)
                    break;              // 가장 가까운 날이므로 종료
                }
            }
        }

        return answer;
    }

    /**
     * 문제풀이 2
     * 알고리즘 : 단조스택
     * <p>
     * Invariant
     * - 스택에는 아직 '자신보다 더 큰 중량'을 만나지 못한 날들의 인덱스 또는 (인덱스,중량)이 저장되어 있다.
     * - 스택의 아래에서 위(top)으로 갈수록 weights[index]는 단조 감소한다. 즉, top이 가장 최근이면서 가장 작은(혹은 값은) 값 후보이다.
     * <p>
     * Proof Sketch
     * - 현재 날 cur 의 중량 w 를 본다.
     * - 스택 top 의 중량이 w 보다 작으면, top 인덱스 prev에 대해
     *   cur이 처음으로 weights[cur] > weights[prev] 를 만족하는 날이다.
     *   왜냐하면
     *      - prev 이후의 날들은 아직 답을 못 찾았기 때문에 스택에 남아 있었고
     *      - 그 중 가장 가까운 후보가 스택 top 이며
     *      - 단조 감소 구조 덕분에 w가 top보다 크면 그보다 더 위(더 최근) 후보들을 먼저 해결한다.
     * - 따라서 answer[prev] = cur - prev 를 기록하고 pop 한다.
     *   이 과정을 반복하면  w로 해결 가능한 모든 prev가 처리된다.
     * - 이후 cur을 스택에 push하면 단조 감소 불변식이 유지된다.
     * <p>
     * 시간 복잡도 : O(N)
     *
     */
    public static int[] solution2(int[] weights) {
        int length = weights.length;
        int[] answer = new int[length];
        Deque<int[]> stack = new java.util.ArrayDeque<>();

        for (int cur = 0; cur < length; cur++) {
            int weight = weights[cur];
            while(!stack.isEmpty() && stack.peek()[1] < weight) {
                int[] prevInfo = stack.pop();
                int prev = prevInfo[0];
                answer[prev] = cur - prev;
            }
            stack.push(new int[]{cur, weight});
        }

        return answer;
    }
}
