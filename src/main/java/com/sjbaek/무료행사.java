package com.sjbaek;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 당신은 슈퍼마켓에서 진행 중인 할인 이벤트에 참여하고 있습니다.
 * 이 이벤트는 두 개의 상품을 선택해 그 가격의 합이 목표 금액(정수 target)과 일치하면 무료로 상품을 받을 수
 * 있는 이벤트 입니다. 매장에서 여러 상품이 있으며, 각 상품의 가격은 정수 배열 prices 로 주어집니다.
 * 두 가지 상품을 선택해서 목표 금액을 맞출 수 있는 경우의 수는 몇 개인지 반환하는 solution 함수를 작성해주세요.
 * 단, 같은 상품은 한번에 중복으로 선택할 수 없으며, 같은 가격을 가진 상품은 없습니다.
 * <p>
 * 제약 조건
 * 1. 2 <= prices의 길이 <= 10^5
 * 2. 1 <= prices[i] <= 10^9
 * 3. 2 <= target <= 10^9
 */
public class 무료행사 {

    /**
     * 문제 분석
     * --------------------------------------------------------------------------------
     * 두 개의 상품 -> 조합, 완전탐색
     * 입력 : 상품 배열 (정수 배열)
     * 출력 : 경우의 수 (정수)
     * 제약조건에 따른 고려사항
     * - O(n2) 선택 시 O(10^10) 으로 O(10^8) 초과하므로 시간 초과, 따라서 최대 O(NlogN) 시간 복잡도 알고리즘 필요
     * - price[i]는 최대 10^9 이고, -2^31 ~ 2^31-1 ( -21억 ~ 21억 => -2*10^9 ~ 2*10^9 ) 이므로
     *   int형 사용
     */

    /**
     * 문제풀이 1.
     * 알고리즘 : 완전 탐색
     * 시간 복잡도 : O(n^2)
     */
    public static int solution(int[] prices, int target) {
        int answer = 0;

        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[i] + prices[j] == target) {
                    answer++;
                }
            }
        }

        return answer;
    }

    /**
     * 알고리즘: 정렬 + 투 포인터
     * - 정렬 후 양 끝에서 시작해 합을 비교하며 범위를 좁힌다.
     * <p>
     * Invariant:
     * - 현재 (left, right) 구간 밖의 조합은 이미 조건에 따라 배제되었거나 카운트가 완료됨.
     * <p>
     * Proof Sketch:
     * - sum < target이면 left를 늘려 합을 키워야만 target에 도달 가능(정렬 때문에).
     * - sum > target이면 right를 줄여 합을 줄여야만 target에 도달 가능.
     * - sum == target이면 (prices[left], prices[right])는 유일한 한 쌍이므로 카운트 후
     * 중복/누락 없이 탐색을 계속하기 위해 left++, right--로 구간을 동시에 축소한다.
     * <p>
     * 시간복잡도: O(n log n) (정렬)
     * 공간복잡도: O(1)~O(log n) (정렬 구현체에 따라)
     */
    public static int solution2(int[] prices, int target) {
        int answer = 0;
        Arrays.sort(prices);    // logN

        int left = 0;
        int right = prices.length - 1;

        while (left < right) { // N
            if (prices[left] + prices[right] == target) {
                answer++;
                left++;
                right--;
            } else if (prices[left] + prices[right] < target) {
                left++;
            } else {
                right--;
            }
        }


        return answer;
    }

    /**
     * 알고리즘: HashSet 기반 One-pass
     *
     * Invariant:
     * - 루프의 각 단계에서 seen에는 현재 인덱스 이전의 가격들이 모두 저장되어 있다.
     *
     * Proof Sketch:
     * - 어떤 유효한 쌍 (a, b)가 존재하고 a가 먼저 등장한다면,
     *   b를 처리하는 시점에 a는 seen에 존재하므로 반드시 카운트된다.
     * - 각 쌍은 "나중에 등장한 값"을 처리할 때 한 번만 카운트되므로 중복이 없다.
     *
     * Edge Cases:
     * - 가능한 쌍이 없는 경우 -> 0 반환
     * - 값 범위가 커서 int overflow 가능 -> long 사용
     *
     *  Time: O(n) average
     * Space: O(n)
     */
    public static int solution3(int[] prices, int target) {
        int answer = 0;
        Set<Integer> seen = new HashSet<>();

        for (int price : prices) {
            int need = target - price;

            if(seen.contains(need)) {
                answer++;
            } else {
                seen.add(price);
            }

//            if (!seen.contains(target - price)) {
//                seen.add(price);
//            } else {
//                answer++;
//            }
        }

        return answer;
    }
}
