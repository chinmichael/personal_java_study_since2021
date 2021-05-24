//package chapter11

/*  Arrays 클래스
    
    >> 배열을 다루는데 유용한 staitc 메서드들을 정의함
    >> 기본형 배열과 참조형 배열로 각 타입에 따라 오버로딩되어 있음

    1. 배열 복사 : copyOf() / copyOfRange()
    ex) int[] arr = {0,1,2,3};
        int[] arr2 = Arrays.copyOf(arr, arr.length);
        int[] arr3 = Arrays.copyOf(arr, 0, arr.length);

    >> copyOf의 경우 대상의 첫번째 데이터부터 두번째 파라미터로 지정한 새 배열의 크기만큼 복사해옴
    >> copyOfRange의 경우 두번째 파라미터로 시작Index도 지정 가능

    >> 둘 모두 마지막 인덱스 데이터를 복사하려면 마지막 인덱스 + 1로 마지막 파라미터를 지정해야함
    >> 복사대상의 사이즈보다 크게 잡는 경우 뒷부분은 초기값이 세팅된다


    2. 배열 채우기 : fill() / setAll()
    ex) int[] arr = new int[5];
        Arrays.fill(arr, 9);
        Arrays.setAll(arr, i -> (int)(Math.random() * 5)+1);) >> 매개변수로 함수를 받기 위함

    >> 둘다 첫번째 파라미터 배열에 두번째 파라미터의 지정값을 채우지만
    >> setAll()의 경우 두번째 파라미터로 함수형 I/F를 받는다 >> 람다식이나 구현 객체를 넣어야 함


    3. 배열의 정렬과 검색 : sort() (정렬) / binarySearch()
    ex) int[] arr ={3,2,0,1}
        Arrays.sort(arr);
        int idx = Arrays.binarySearch(sort, 2)

    >> 글자그대로 sort()는 배열의 순차정렬을 / binarySearch(배열, 찾는값)는 이진탐색으로 찾는 값의 인덱스를 반환한다
    >> 이진탐색은 탐색범위를 반씩 줄여나가 O(long n) (로그 밑은 성능관점상 미미해 무시됨) 순차탐색 O(n)보다 훨씬 빠르지만
       그 탐색 특성 때문에 반드시 정렬이 된 배열에서 사용이 가능하다
       
       
    4. 문자열 비교, 출력 : equals() / toString() | deepEquals() / deepToString()

    Arrays.equals(arr1, arr2) / Arrays.toString(arr)
    
    >> 배열 내부 데이터를 비교 / 출력해주기 때문에 매우 편리하나
    >> 배열 내부 데이터가 다시 배열인 2차배열([][]) 이상의 다차원 배열인 경우에는 다시 참조주소값이 배교 / 출력 되기 때문에 문제가 있다

    Arrays.deepEquals(arr1, arr2) / Arrays.deepToString(arr)

    >> 다차원배열의 경우 모든 요소를 재귀적으로 접근(내부객체로 배열이 나옴 또 배열안을 파고듦)하는 deep~ 메서드를 사용하면 된다


    5. 배열 List 변환 : asList()
    ex) List list = Arrays.asList(new Integer[]{1,2,3,4,5});
        List list = Arrays.asList(arr)
        List list = Arrays.asList(1,2,3,4,5);

    >> 매개변수 배열을 List에 담아 반환이 가능 + 매개변수타입이 가변인수기 때문에 배열생성없이 넣을 데이터들을 나열하여 처리 가능

    >> 문제는 반환한 List의 크기를 변경할 수 없다 ( = 수정만 가능하고 추가 삭제가 불가)

    >> 크기를 변경하려면 List의 구현객체들의 생성자 매개변수로서 사용한다
    ex) List list = new ArrayList(Arrays.asList(1,2,3,4,5)); << 이런식으로 데이터들을 가지고 좀 편하게 리스트 객체를 생성
    >> LinkedList도 되는지 예제에서 확인해보겠음 >> 된다.


    6. 스트림 관련 : parallel~() / spliterator() / stream() >> 뒤 chpater14에서
    >> spliterator() : 여러 쓰레드가 처리할 수 있도록 한 작업을 여러작업으로 나눈다
    
    >> parallel~ () : 여러 쓰레드가 작업을 나누어서 처리하도록 함

    >> stream() : 컬렉션을 스트림으로 변환
*/

import java.util.*;

public class Study_210524 {
    public static void main(String[] args) {

        int[] arr = { 0, 1, 2, 3, 4 };
        int[][] arr2D = { { 11, 12, 13 }, { 21, 22, 23 } };

        System.out.println("arr Arrays.toString() : " + Arrays.toString(arr));
        System.out.println("arr2D Arrays.toString() : " + Arrays.toString(arr2D));
        System.out.println("arr2D Arrays.deepToString() : " + Arrays.deepToString(arr2D));
        System.out.println();

        int[] arr2 = Arrays.copyOf(arr, 3);
        int[] arr3 = Arrays.copyOf(arr, 7);
        int[] arr4 = Arrays.copyOfRange(arr, 2, 5);

        System.out.println("copyOf(arr, 3) : " + Arrays.toString(arr2));
        System.out.println("copyOf(arr, 7) : " + Arrays.toString(arr3));
        System.out.println("copyOfRange(arr, 2, 5) : " + Arrays.toString(arr4));
        System.out.println();

        int[] arr5 = new int[5];

        Arrays.fill(arr5, 9);
        System.out.println("fill(arr5, 9) : " + Arrays.toString(arr5));
        Arrays.setAll(arr5, i -> (int) (Math.random() * 6) + 1);
        System.out.println("setAll(arr5, 1~6 람다식) : " + Arrays.toString(arr5));
        System.out.println();

        int[][] arr2D_vs = { { 11, 12, 13 }, { 21, 22, 23 } };
        System.out.println("arr2D_vs : " + Arrays.deepToString(arr2D_vs));
        System.out.println("equals : " + Arrays.equals(arr2D, arr2D_vs));
        System.out.println("deepEquals : " + Arrays.deepEquals(arr2D, arr2D_vs));
        System.out.println();

        char[] chArr = { 'A', 'C', 'E', 'B', 'D' };
        System.out.println(Arrays.toString(chArr));
        System.out.println("binarySearch(chArr, 'B') before sort() : " + Arrays.binarySearch(chArr, 'B'));
        Arrays.sort(chArr);
        System.out.println("binarySearch(chArr, 'B') after sort() : " + Arrays.binarySearch(chArr, 'B'));
        System.out.println();

        List<Integer> test1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> test2 = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        test1.add(6);
        test2.add(6);
        System.out.println(test1);
        System.out.println(test2);

    }
}
