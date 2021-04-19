package chapter9;

/*
자주 이용하는 java.util 패키지 클래스

java.util.Objects   : Object 클래스의 보조 클래스(얘는 Object's'다!) / Math처럼 모든 클래스가 static
                    : 객체 비교, 널체크(Object메서드랑 겹치는 경우 대부분 널처리 관련된 기능이 알아서 붙어있음)

                    : isNull(Object obj) : null이면 true <> nonNull()

                    : requireNonNull() 해당 객체가 널이 아닐때 사용 > null이면 NullPointerException발생시킴
                      > requireNonNull(Object obj, String message); message는 예외 문구 (Supplier<String>도 됨)
                      > if(obj == null){throw new NullPointerException(message);} else { this.obj = obj }를
                        this.obj = Objects.requireNonNull(obj, message);로 한방에 표현 가능

                    : Object는 등가비교의 equals()만 있다면 여기는 대소비교를 위한 compare(obj1, obj2, Comparator c) 추가
                    : Comparator는 두 객체 비교를 위한 비교기준 후에 Chapter11에서 다시 다룸

                    : Object와 달리 보조 Objects의 Objects.equals(a, b)는 매개변수의 null 검사를 하지 않아도 됨
                      ~ equals(Object a, Object b) { return (a==b) || a!=null && a.equals(b) } 로 내부 처리
                      > 양쪽 모두 null이면 참 한쪽만 null이면 거짓 a의 null검사가 알아서 내장됨

                    : deepEquals(obj1, obj2) : 객체를 재귀적으로 비교 > 다차원 배열 비교 가능
                    : 예를 들어 equals는 2차원배열 [][]을 비교시 반복문과 함께해야함 근데 얘는 바로 처리 가능

                    : toString의 경우 내부 널검사 기능 + 두번째 파라미터로 null일때 대신 사용할 String 값 지정 가능 기능 추가

                    : hashCode() 내부 널검사 이후 해당 Object의 hashCode()호출(String같은거) 만약 null이면 0반환

                    : 만약 Objects를 static import할 경우 겹치는 메소드는 compiler가 구분못해 충돌하므로 클래스 이름 붙임


java.util.Random    : 난수 생성 Math.random()도 사실 내부적으로 Random클래스 인스턴스 사용하는 것 (편한쪽으로 걍 가면 된다)
                    : Math.random() 이건 new Random().nextDouble()과 같은 효과 (얘는 static이 아님)
                    : 정수 1~6의 경우 (int)(Math.random() * 6) + 1; or new Random().nextInt(6) + 1; (얘도 0시작)

                    : Random의 가장 큰 특징은 Math.random()과 달리 종자값(seed) 설정 가능
                    : seed는 난수생성 공식에 사용되는 값 > seed가 같으면 같은 난수를 같은 순서로 반환
                    : Random() 생성자는 기본 System.currentTimeMillis()를 종자값으로 설정 (현재시간 1/1000초 단위로 반환)

                    : 관련 메서드
                    : Random() or Random(long seed)
                    : void setSeed(long seed) 종자값 변경

                    : boolean nextBoolean()
                    : double nextDouble(), float nextFloat() (0.0<=  <1.0)난수
                    : int nextInt() (걍 int 범위), nextInt(n) (0<= <n), long nextLong(걍 long 범위)

                    : void nextBytes(byte[] bytes) : bytes배열에 byte타입 난수를 채워서 반환 > 해당 참조변수 배열에 채워만 놓음
                    : double nextGaussian() 평균 0.0 표준편차 1.0의 가우시안 분포 double형 난수 (어따 쓰냐)


 */

import java.util.*;

public class Study_210419 {
    public static void main(String args[]) {
        System.out.println("1. Test Object's'");
        System.out.println("2. Test Random1 : 시드값에 따른 난수 동일성");
        System.out.println("3. Test Random2 : 재밌는? 랜덤에 따른 분포그래프 그리기");
        System.out.print("실행 메서드 선택 : ");
        Scanner sc = new Scanner(System.in);
        Study_210419 study = new Study_210419();

        switch (sc.nextInt()) {
            case 1:
                study.test_objects();
                break;
            case 2:
                study.test_random1();
                break;
            case 3:
                study.test_random2();
                break;
        }
    }

    public void test_objects() {
        String[][] str2D_1 = new String[][] {{"aaa", "bbb"}, {"AAA", "BBB"}};
        String[][] str2D_2 = new String[][] {{"aaa", "bbb"}, {"AAA", "BBB"}};

        System.out.print("str2D_1 = { ");
        for(String[] tmp: str2D_1) System.out.print(Arrays.toString(tmp));
        System.out.println(" }");

        System.out.print("str2D_2 = { ");
        for(String[] tmp: str2D_2) System.out.print(Arrays.toString(tmp));
        System.out.println(" }");

        System.out.println();
        System.out.println("Objects.equals(str2D_1, str2D_2) : " + Objects.equals(str2D_1, str2D_2));
        System.out.println("Objects.deepEquals(str2D_1, str2D_2) : " + Objects.deepEquals(str2D_1, str2D_2));

        System.out.println();
        System.out.println("Objects.isNull(null) : " + Objects.isNull(null));
        System.out.println("Objects.nonNull(null) : " + Objects.nonNull(null));

        System.out.println();
        System.out.println("Objects.hashCode(null) : " + Objects.hashCode(null));
        System.out.println("Objects.hashCode(new Object()) : " + Objects.hashCode(new Object()));
        System.out.println("Objects.hashCode(\"문자열\") : " + Objects.hashCode("문자열"));

        System.out.println();
        System.out.println("Objects.toString(null) : " + Objects.toString(null));
        System.out.println("Objects.toString(null, \"널\") : " + Objects.toString(null, "널"));

        Comparator c = String.CASE_INSENSITIVE_ORDER; // 대소문자 구분하지 않고 비교하기 위함
        System.out.println();
        System.out.println("Objects.compare(\"aa\", \"AA\", comparator) : " + Objects.compare("aa", "AA", c));
    }

    public void test_random1() {
        Random rand1 = new Random(1);
        Random rand2 = new Random(1);

        System.out.println("=== rand1 : 시드값 1 지정 ===");
        for(int i = 0; i <= 5; i++) System.out.println(i+1 + "회 : " + rand1.nextInt());

        System.out.println();
        System.out.println("=== rand2 : 시드값 1 지정 ===");
        for(int i = 0; i <= 5; i++) System.out.println(i+1 + "회 : " + rand2.nextInt());
    }
    
    public void test_random2() {
        Random rand = new Random();
        int[] number = new int[100];
        int[] counter = new int[10];

        System.out.println("=== 100번의 0~9 랜덤배열 ===");
        for(int i = 0; i < number.length; i++) {
            number[i] = rand.nextInt(10); // 0~9
            System.out.print(number[i]);
        }
        
        System.out.println();
        System.out.println("=== 그래프 ===");
        for(int i = 0; i < number.length; i++) {
            counter[number[i]]++;  //아 백준 그립타 해당 카운터에 ++하기
        }
        for(int i = 0; i < counter.length; i++) {
            System.out.println(i + "의 개수 : " + printGraph('ㅁ', counter[i]) + " " + counter[i] + "개");
        }
    }
    
    public static String printGraph (char ch, int value) {
        char[] bar = new char[value];
        
        for(int i =0; i <bar.length; i++) bar[i] = ch;
        
        return new String(bar);
    }

    // 책에서 제시한 유요할거 같은 random 메서드
    public int[] fillRand(int[] arr, int from, int to) { // from~to 범위 값들로 배열 채움
        for(int i=0;i<arr.length;i++) arr[i] = getRand(from, to);
        return arr;
    }

    public int[] fillRand(int[] arr, int[] data) { // arr을 data의 배열 내 임의의 값들로 채워넣음
        for(int i=0;i<arr.length;i++) arr[i] = data[getRand(0, data.length-1)];
        return arr;
    }

    public int getRand(int from, int to) { // from, to가 포함되는 범위 내 랜덤 int 반환
        return (int) (Math.random() * (Math.abs(to - from) + 1)) + Math.min(from, to);
        // 절대값으로 범위의 거리를 구한 뒤 최솟값 만큼 움직여서 범위를 알맞게 조정한다고 그래프적으로 생각!!
    }
}
