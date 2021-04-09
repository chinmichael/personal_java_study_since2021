package chapter9;

import java.util.Scanner;

/*

getClass()  : 자신이 속한 클래스의 'Class객체(이름이 'Class'인 클래스 객체)' 반환
            : Class객체 : 클래스의 모든 정보를 담은 / 클래스 당 1개만 존재(final) / ClassLoader에 클래스 파일 올라갈 때 자동 생성

            : ClassLoader   : 실행 시 필요 클래스르 동적으로 메모리 로드하는 역할
                            : 메모리 클래스 있음>객체참조반환 / 없음>classpath 지정경로에서 파일 찾아 Class객체 반환>그래도 없음 ClassNotFoundException
                            : 클래스 파일을 읽어 Class클래스에 정의된 형식으로 변환

            >> 클래스 객체 = 클래스 파일을 읽기 편한 형태로 저장한 것

            : Class cObj = new Object().getClass(); or Object.class;(리터럴) or Class.forName("Object");
            : cObj.getName() : 패키지.클래스 / cObj.toGenericString() : (final) class까지 보여줌 / toString() : class까지


String 클래스

basic   : 기존 다른 언어에서 문자열 = 문자열 배열 but 자바는 String 클래스 제공 > 문자열 저장 및 문자열 다루기 위한 메서드 제공

immutable=변경불가 클래스 : 생성자 매개변수를 멤버변수 private char[] value;에 저장하며 수정이 불가능함!!
                          >> 따라서 문자열을 변경해서 넣거나 +를 이용해 문자열을 더하는 것은 그때마다 새 인스턴스가 생성되 메모리 차지
                          >> 문자열 결합 추출 작업이 많을 때는 StringBuffer클래스를 이용(저장된 문자열 변경 가능)

                        + final class이기에 부모 클래스가 될 수 없음 (final메서드는 오버라이딩 안됨 / final변수는 값 고정)

문자열 생성 및 비교 : 문자열 생성은 문자열 리터럴 지정(String str = "abc";) or 생성자 이용 (String str = new String("abc");)
                 : 문자열 리터럴은 클래스가 메모리에 로드될 때 자동으로 미리 생성된다 > 같은 문자열 리터럴을 지정한 String참조변수는 동일한 대상을 가리킴 = 같은 주소값 가짐
                 : 생성자를 이용할 경우는 new 연산자에 의해 새롭게 메모리 할당이 이뤄진다

                 >> 따라서 equals를 썼을때 문자열 내용이 같으면 생성자와 리터럴 결과는 같지만 ==연산자는 다름 (생성자는 false 리터럴은 true)
                 
                 : 컴파일 시 소스코드에 포함된 모든 문자열 리터럴(String 인스턴스)은 같은 내용의 경우 한번만 저장됨 > 동일 문자열 리터럴 지정은 동일 주소값 저장
                 : 로드시 JVM내 상수 저장소에 저장
                 

 */
public class Study_210409 {
    public static void main(String args[]) {
        System.out.println("1. classTest");
        System.out.println("2. createStringTest");
        System.out.print("사용할 메서드 번호 선택 : ");
        Scanner sc = new Scanner(System.in);
        Study_210409 study = new Study_210409();

        switch (sc.nextInt()) {
            case 1:
                study.classTest();
                break;
            case 2:
                study.createStringTest();
                break;
        }
    }

    public void classTest() {
        Card c1 = new Card("Heart", 3);
        System.out.println("new연산자 이용 : " + c1);
        Card c2;
        try {
            c2 = Card.class.newInstance(); // 이 방법으로 객체 생성할 경우는 예외처리 필요
            System.out.println("Object.class.newInstance 이용 : " + c2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class cObj = c1.getClass();
        System.out.println("cObj.getName() : " + cObj.getName());
        System.out.println("cObj.toGenericString() : " + cObj.toGenericString());
        System.out.println("cObj.toString() : " + cObj.toString());
    }

    public void createStringTest() {
        String str1 = new String("abc");
        String str2 = new String("abc");

        String str3 = "abc";
        String str4 = "abc";

        System.out.println("String str1 = new String(\"abc\")");
        System.out.println("String str2 = new String(\"abc\")");
        System.out.println("str1.equals(str2) : " + str1.equals(str2));
        System.out.println("str1 == str2 : " + (str1 == str2)); // 연산자 우선순위에 따른 괄호 주의

        System.out.println();
        System.out.println("String str3 = \"abc\"");
        System.out.println("String str4 = \"abc\"");
        System.out.println("str3.equals(str4) : " + str3.equals(str4));
        System.out.println("str3 == str4 : " + (str3 == str4));
    }
}

final class Card {
    String kind; int num;
    Card() { this("Spade", 1); }
    Card(String kind, int num) { this.kind = kind; this.num = num; }
    public String toString() { return kind + " : " + num; }
}
