//package chapter14

/*  1. 람다식(Lamda expression) : jdk1.8부터 도입된 객체지향언어인 JAVA에서 함수형 언어의 기능을 갖추기 위한 개념

    >> 메서드를 하나의 식(expression)으로 표현한 것 >> 메서드의 이름, 반환값이 없어지므로 '익명함수'라고도 함 (js es6의 arrow function 생각하면 됨)
        
        - 객체지향언어에서는 기존 function대신 method라는 객체의 행위와 동작을 의미하는 용어를 사용
        - 기능은 거의 같지만 method는 반드시 특정 클래스에 속하여야 함 >> 메서드 사용을 위해서는 클래스 및 객체생성이 필요하다.

        >> 람다식은 JAVA에서 코드 작성시 기존 함수처럼 식 자체로 독립적인 사용이 가능함

        ★ JS / JAVA 익명함수 (Arrow Function / Lamda Expression) 사용 이유
            - JS : this가 가리키는 객체(상위요소객체)를 명확히 하기 위함
            - JAVA : 메서드와는 다른 독립적인 함수식을 사용하기 위해 >> 간편성 / 식 자체를 매개변수로 전달 가능 / 식 자체를 반환값으로 사용 가능 >> '메서드(함수)를 변수처럼 사용이 가능'

    >> 작성 방법 : 메서드명과 반환타입을 제거하고 + 선언부와 몸통 사이 '->'를 추가 : () -> {}

                 > 생략가능한 부분과 조건을 좀 알아두면 좋다

                 - 추론이 가능하다면 매개변수 타입은 생략 가능 (반환타입이 생략되는것도 같은 이유) + 단 여러 매개변수 타입 중 일부만 생략되는건 불가능하다

                 - 매개변수 타입이 생략됨 + 매개변수가 하나일 경우 >> () 생략 가능

                 - 반환타입이 있는 경우 return문(statement) 대신 식(expression)으로 대체 가능 이 경우 ';'는 붙이지 않는다

                 - 몸통의 내용이 문장이 하나일 경우 + return문이 아닐 경우 >> {} 생략 가능 + 이 때 문장이어도 (System.out.println() 이런거) ';'는 붙이지 않는다

                 (int a) -> { return a>10 ? a : 10; } 이를 a -> a>10 ? a : 10 으로 생략 가능


    2. 함수형 인터페이스(Functional Interface)

    >> 람다식의 구현 및 사용은 함수와 같지만 JAVA에서 실질적인 동작은 익명 클래스와 동등하다. (기존 자바 규칙을 어기지 않으면서 구현하기 위함)

    ※ 익명 클래스(anonymous class) : 클래스의 선언과 객체의 생성을 동시에 하는 이름이 없는 내부 클래스 >> 선언 후 하나의 객체만 생성해 한번만 사용가능한 일회용 클래스
        - 상속받고자 하는 조상 클래스나 구현하는 인터페이스의 이름을 사용해 정의 
            >> 이름이 없으니 생성자도 없으며 (컴파일 시 생성되는 클래스 파일명 : 외부클래스명$숫자.class)
            >> 상속과 구현을 동시에 하거나 둘 이상의 구현을 할 수가 없음

    >> 따라서 실제로는 익명 객체이기에 구현대상으로서 람다식을 다루기 위한 인터페이스 생성 가능
       람다식을 참조변수(타입은 인터페이스로)에 담아 활용 가능
       >> aMethod(()->System.out.println()); 을 aMethod(Function f);처럼 처리 가능
       >> 물론 호출시에도 MyFunction f = () -> System.out.println();을 한 뒤 aMethod(f); 가 가능하다

       람다식을 다루기 위한 인터페이스 = 함수형 인터페이스

        (int a, int b) -> { a > b ? a : b }

        @FunctionalInterface // 이 어노테이션으로 컴파일 시 올바르게 함수형 인터페이스가 정의되었는지 확인하는 용도
        interface MyFunction {
            public abstract int max(int a, int b); // 메서드의 이름은 상관없다
        }

        MyFunction f = (int a, int b) -> a > b ? a : b; 가능 (마지막 ';'는 변수 선언문이기에 달게됨)

        함수형 인터페이스 제약사항 : 람다식과 1:1 매칭을 위해 추상메서드가 1개만 정의되어있어야함 (static과 default메서드는 상관없다)

        따라서 다음과 같은 처리도 가능하게 됨

        List<String> list = Arrays.asList{"abc","aaa","bbb"};

        Collections.sort(list, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });
        의 두번째 부분을

        Collections.sort(list, (s1, s2)->s2.compareTo(s1))


    2-1. 람다식 타입과 형변환 : 람다식은 익명객체 >> 타입이 없음 (컴파일시 임의로 이름 지정됨) +) 컴파일 시 클래스명(객체타입명) : 외부클래스이름$$Lamda$번호
         
         >> 따라서 함수형 인터페이스로 람다식을 참조하는 것은 실제로 타입이 일치하는 것이 아니라 구현된 클래스와(심지어 직접 구현한것도 아님) 동일하기에 형변환이 가능하고 + 생략이 가능해 저리 보이는 것 
         
         >> 참고로 객체지만 Object로 형변환은 불가능함 >> 따라서 toString()등을 사용하기 위해선 람다식을 함수형I/F로 형변환 한 뒤 Object로 형변환 해줘야 한다

    2-2. 람다식과 변수 (하단 Outer, Inner 참조)
        
        >> 람다식 내에서 참조되는 지역변수 : final을 붙이지 않아도 기본적으로 상수로 간주 (물론 매개변수도) >> 따라서 람다식 내 참조되면 내용 바꾸는게 불가

        >> 외부 선언 변수 접근방법 : 익명함수와 동일한 방법을 취함 / 즉 JS의 Arrow Function처럼 감싸는 요소(클래스)의 변수는 this.를 사용 (또 감싸는 요소가 있음 외외부.this. 이용)

*/

@FunctionalInterface
interface MyFunction1 {
    void run();

    default void runDefault() {
        System.out.println("default run");
    }

    static void runStatic() {
        System.out.println("stati run");
    }
}

@FunctionalInterface
interface MyFunction2 {
    void myMethod();
}

class Outer {
    int val = 10; // Outer.this.val (Inner 안의 람다식 기준)

    class Inner {
        int val = 20;

        void method(int i) {
            int val = 30;

            // i = 10; val = 20; >> 하단 람다식에서 참조되기에 final처리되어 변경 불가

            MyFunction2 f2_2 = () -> {
                System.out.println("i = " + i);
                System.out.println("val = " + val);
                System.out.println("this.val(Inner) = " + this.val);
                System.out.println("Outer.this.val = " + Outer.this.val);
            };

            f2_2.myMethod();
        }
    }
}

public class Study_210510 {

    static void execute(MyFunction1 f) { // 매개변수 타입이 MyFunction1인 경우
        f.run();
    }

    static MyFunction1 getMyFunction() { // 반환타입이 MyFunction1인 경우
        MyFunction1 f = () -> System.out.println("f1_3.run");
        return f;
    }

    public static void main(String args[]) {

        System.out.println();

        MyFunction1 f1_1 = () -> System.out.println("f1_1.run : 람다식으로 구현하여 참조변수에 담음");

        MyFunction1 f1_2 = new MyFunction1() {
            public void run() {
                System.out.println("f1_2.run : 익명클래스로 인터페이스 구현");
            }
        };

        MyFunction1 f1_3 = getMyFunction();

        f1_1.run(); // 람다식으로 구현한 뒤 사용
        f1_2.run();
        f1_3.run();

        execute(f1_1);
        execute(() -> System.out.println("매개변수로 넣는 곳에 직접 람다식으로 넣음"));

        System.out.println();
        System.out.println("==============================================================");
        System.out.println();

        MyFunction2 f2_1 = () -> {
        }; // (MyFunction2) 형변환 생략
        Object obj = (MyFunction2) (() -> {
        }); // (Object) 형변환 생략
        String str = ((Object) (MyFunction2) (() -> {
        })).toString();

        System.out.println(f2_1);
        System.out.println(obj);
        System.out.println(str);

        // System.out.println(()->{}); 람다식은 Object로 형변환 안되기에 에러뜸

        System.out.println();
        System.out.println("==============================================================");
        System.out.println();

        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.method(100);
    }
}
