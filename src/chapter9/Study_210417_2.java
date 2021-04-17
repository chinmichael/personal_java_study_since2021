package chapter9;

import java.util.Scanner;

/*
wrapper 클래스

개념
JAVA가 완전한 객체지향 언어가 아니라 듣는 이유 : 8가지 기본형을 객체로 다루지 않기에 but 덕분에 성능향상이 가능함

대신 기본형(primitive type) 변수도 객체로 다뤄야하는 경우(null 등의 이유로 객체저장이 필요하거나 객체비교가 필요하는 등)에
기본형 값을 객체로 다룰 수 있게 한 클래스를 이야기함

Boolean, Character, Byte, Short, Integer, Long, Float, Double (생성자는 각각 대응하는 기본형 or 자료형에 알맞는 String)
                                                               >> 물론 자료형 안맞으면 NumberFormatException

각 래퍼클래스는 해당하는 기본형 타입의 private 멤버변수에 값을 보유함
+ equals(), toString()들이 오버라이딩되어 사용 가능
+ MAX_VALUE, MIN_VALUE, SIZE(비트 수), BYTES(바이트 수), TYPE(대응 기본형 타입) 등 static 상수를 갖고 있어 사용가능
+ 대신 객체이기 떄문에 비교연산자 사용 불가 so A.compareTo(B) 메서드 사용 (같으면 0, A가 작으면 음수, 크면 양수)

Number클래스(추상클래스)
내부적으로 숫자를 갖는 래퍼클래스(Character, Boolean은 아님)와 BigInteger, BigDecimal의 조상클래스
BigInteger, BigDecimal은 각각 long과 double의 범위를 넘는 수의 처리를 위한 클래스 (해당 파트의 연산자 역살하는 메서드 제공)

추상클래스다 보니 추상메서드가 있다는 건데,
살펴보면 각각의 int, long, float, double 반환타입의 추상메서드를 갖고 객체가 가진 값을 기본형으로 반환하게 처리하게 만든다.
(byte, short는 int 반환 추상메서드를 호출하고 (byte), (short)로 형변환시키는 메서드를 가짐 p492참조)

문자열을 숫자로 변환하는 여러가지 방법 (예시 : int / 각 숫자 래퍼클래스에 맞게 변용시키면 됨)
1. int i = Integer.parseInt("100"); 가장 흔함
2. Integer i = new Integer("100"); // int i = new Integer("100").intValue(); 위 Number클래스 추상 클래스
3. Integer i = Integer.valueOf("100"); 반환값이 래퍼클래스라는 점 주의

하단의 autoboxing기능으로 3의 경우처럼 반환값이 기본형과 래퍼클래스일 때 차이가 사라짐(호환됨) 단지 valueOf가 성능이 좀 더 떨어짐

parse나, valueOF 경우 두번째 파라미터로 진법(radix)를 입력해 여러 진법에 대응하여 변환처리를 할 수 있음
int i2 = Integer.parseInt("100", 2); >> 100(2) = 4
int i16 = Integer.parseInt("FF", 16); >> FF(16) = 255 // 물론 16진수 radix파라미터 설정 안하면 NumberFormatException


오토박싱 & 언박싱
컴파일러가 기본형 값을 자동으로 래퍼클래스 객체로 자동변환해(래퍼로 싸)주는 것(오토박싱) 반대로 기본형으로 변환하는 것이 언박싱

JDK 1.5 이전은 기본형과 참조형간 연산 불가로 기본형을 래퍼클래스로 객체화 시켰어야 했음
하지만 현재느 compiler가 자동으로 변환시키는 코드를 넣기 때문에 가능함
int i = 5; Integer i2 = new Integer(7); int sum = i + i2; (컴파일러가 알아서 i2를 intValue()를 추가해 변환처리)

내부적으로 객체배열을 가진 Vector나 ArrayList에 기본형을 넣거나 뺄때 알아서 형변환을 해주기도 함
Vector<Integer> list = new Vector<Integer>(); 지네릭스는 Integer임
list.add(10); 오토박싱처리  int value = list.get(0); 언박싱처리

개념이 헷갈리 수 있으므로 주의해서 사용
compile 전 >> compile 후
Integer intg = (Integer) i; >> Integer intg = Integer.valueOf(i);
Object obj = (Object) i; >> Object obj = (Object) Integer.valueOf(i); //본래 객체형변환이므로 객체끼리
Long lng = 100L; >> Long lng = new Long(100L);

>> 원래는 객체와 기본형을 함부로 넘나들지 못하는게 정상이다


 */
public class Study_210417_2 {
    public static void main(String args[]) {
        System.out.println("1. wrapper클래스 기본사항");
        System.out.print("실행할 메서드 선택 : ");
        Scanner sc = new Scanner(System.in);
        Study_210417_2 study = new Study_210417_2();

        switch(sc.nextInt()) {
            case 1:
                study.wrapperEx();
                break;
        }
    }

    public void wrapperEx() {
        Integer i1 = new Integer(100);
        Integer i2 = new Integer("100");

        System.out.println("i1 == i2 : " + (i1 == i2));
        System.out.println("i1.equals(i2) : " + i1.equals(i2));
        System.out.println("i1.compareTo(i2) : " + i1.compareTo(i2));
        System.out.println("i.toString() = " + i1.toString());
        System.out.println();

        System.out.println("MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("MIN_VALUE = " + Integer.MIN_VALUE);
        System.out.println("SIZE = " + Integer.SIZE + " bits");
        System.out.println("BYTES = " + Integer.BYTES + " bytes");
        System.out.println("TYPE = " + Integer.TYPE);
    }

}
