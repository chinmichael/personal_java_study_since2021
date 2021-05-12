//package chapter14

/*  1. java.util.function패키지 함수형 I/F static메서드, default메서드

    >> default 메서드의 경우 주로 두 람다식을 연결해 하나로 만들어주는 기능을 한다


    >> 하단 메서드들은 정의된걸 형태를 보는것보단 설명과 실제 적용을 보는편이 이해가 잘됨


    1-2. Function 인터페이스 default, static 메서드

    >> andThen() 메서드 (f.andThen(g)) : 두 람다식 f, g에서 f를 적용 후 g를 적용

        - default <V> Function<T, V> andThen (Function<? super R, ? extends V> after)

            > 이는 전체 Function<T, V> h 내부에서

              : T > Function<T, R> f의 apply > R > Function<R, V> g의 apply > V로 이어진다고 생각하면 된다

              : 예를 들어 16진법 문자열을 숫자로 변환하고 다시 2진 문자열로 변환하는 2개의 식을 하나로 연결해 만드려면

                Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
                Function<Integer, String> g = (i) -> Integer.toBinaryString(i);

                >> 이 둘을 andThen으로 합성하여 Function<String, String> h = f.andThen(g); 로 엮어낸 람다식 참조변수를 만들수 있다.

                >> 이때 일종의 tmp 치환식을 생각해주면 좋음 : 위에서 h의 T(a), V(c)인 String, String이 위 f, g의 최초진입 T, 최후반환 V임도 중요하나 사이에 연결해주는 Integer로 Z화살표가 나옴

                암튼 위에서 저렇게 람다식 참조변수 h를 설정하면 이제 "FF"를 2진문자열로 변환할 때 식 두개를 거치지 않고 h.apply("FF")로 마무리가 된다

    >> compose() 메서드 (f.compose(g)) : 그냥 위 andThen에서 적용 순서가 반대라고 생각하면 된다 (f >> g에서 g >> f로)

        - default <V> Function<V, R> compose(Function<? super V, ? extends T> before)

            > 위에서 T > R > V라면 여기는 V > T > R

    
    >> identity() : 항등함수(f(x) = x)인 람다식 x -> x를 메서드로 표현한 것 : 나중에 map()에서 변환없이 그대로 처리하는 등 가아끔 쓰인다한다

        - static <T> Function<T, T> identity()

        - Function<String, String> f = s -> s == Function.identity()


    
    1-3. Predicate default, static

    >> default메서드 and(), or(), negate() >> 조건식 논리연산자 &&, ||, ! 로 연결하는 것과 같다고 생각하면 됨 (하단 예제코드 참조)

        - default Predicate<T> and(Pedicate<? super T> other)

        - default Predicate<T> or(Predicate<? super T> other)

        - default Predicate<T> negate()

        >> 이런 메서드로 두 Predicate인터페이스 구현 람다식을 참조변수에 저장해 연결한뒤(ex h = f.and(g)) 최종 참조변수의 test()메서드로 사용한다 h.test(~)


    >> static 메서드 isEqual()

        - static <T> Predicate<T> isEqual(Object targetRef)

        >> boolean result = Predicate.isEqual(str1).test(str2);
           
           두 비교대상을 비교하는 Predicate를 만들때 사용하며
           1. isEqual() 매개변수로 비교대상을 지정하고 (Predicate<String> p = Predicate.isEqual(str1))
           2. 다른 비교대상은 위 p의 test()메서드의 매개변수로 지정한다


    2. 메서드 참조

    >> 람다식이 하나의 메서드만을 참조하는 경우 > 람다식을 더 간결하게 표현하는 방법

    >> 예를들어 Function<String, Integer> f = (String s) -> Integer.parseInt(s);를 | f = Integer::parseInt;로 표현할 수 있다 | static 메서드 참조 : (x)->ClassName.method(x) >> ClassName::method

       BiFunction<String, String, Boolean> f = (s1, s2) -> s1.equals(s2) 의 경우는 | f = String::equals | 인스턴스메서드 참조 (obj, x)->obj.method(x) >> ClassName::method

       이미 생성된 객체의 메서드를 사용하는 경우는 그 객체의 참조변수를 클래스명 자리에 적는다 | 특정객체의 인스턴스메서드 참조 (x)->obj.method(x) >> obj::method

    >> 이는 좌변 Function의 지네릭타입이나 우변의 메서드 선언문에서 생략된 요소들을 컴파일 시 추측가능하기 때문이다.
    
    >> 정리하면 하나의 메서드만 호출하는 람다식은
       클래스명::메서드명 (static메서드 혹은 인스턴스메서드 참조시) or 참조변수::메서드명 (특정 객체 인스턴스메서드 참조시)  으로 간단하게 바꿀 수 있다.


    2-2. 생성자(메서드) 참조

    Supplier<MyClass> s = () -> new MyClass()의 경우도 s = MyClass::new로 축약 가능

    만약 매개변수가 있거나 배열을 생성하는 경우는 함수형 인터페이스를 이용해 (지네릭을 통해) 축약을 시키면 된다
    
    BiFunction<Integer, String, MyClass> bf = MyClass::new (이때 지네릭 마지막에 생성자로 생성할 클래스 타입 쓴다)
    Function<Integer, int[]> f = x->new int[x] 를 생각해 f = int[]::new


*/

import java.util.function.*;

public class Study_210512 {
    public static void main(String[] args) {

        Function<String, Integer> f = s -> Integer.parseInt(s, 16);
        Function<Integer, String> g = i -> Integer.toBinaryString(i);

        Function<String, String> h1 = f.andThen(g);
        Function<Integer, Integer> h2 = f.compose(g); // andThen과 compose에서 위 f,g의 지네릭중 무엇이 연결다리를 하는 타입변수인지를 생각

        System.out.println(h1.apply("FF"));
        System.out.println(h2.apply(2));

        Function<String, String> id = Function.identity();
        System.out.println(id.apply("AAA"));

        System.out.println();

        Predicate<Integer> p = i -> i < 100;
        Predicate<Integer> q = i -> i < 200;
        Predicate<Integer> r = i -> i % 2 == 0;
        Predicate<Integer> notp = p.negate(); // i >= 100;

        Predicate<Integer> all = notp.and(q.or(r));
        System.out.println(all.test(300));

        String str1 = "abc";
        String str2 = "abc";

        Predicate<String> p2 = Predicate.isEqual(str1);
        boolean result = p2.test(str2);
        System.out.println(result);

        System.out.println();
        System.out.println("===============================================");
        System.out.println();

        Function<String, Integer> m1 = Integer::parseInt; // 지네릭과 클래스명 static메서드명을 통해 정보 추출
        BiFunction<String, String, Boolean> m2 = String::equals;

        System.out.println(m1.apply("11") + 12);
        System.out.println(m2.apply("abc", "cba"));

    }
}
