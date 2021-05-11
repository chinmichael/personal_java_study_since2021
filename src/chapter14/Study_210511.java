//package chapter14

/*  5/11 : 이래저래 생각해본 결과 오늘정도의 페이스도 나쁘지 않은듯 조금씩 꾸준히 골고루 3박자!

    1. java.util.function 패키지 (함수형 인터페이스를 미리 정의해 람다식 유지보수성 강화)

    >> 자주 쓰이는 형식의 메서드(매개변수 개수 + 반환 값 유무 (타입은 지네릭으로 해결))를 함수형 인터페이스로 정의한 것

    >> 람다식을 사용함에 있어 미리 정의된 이름으로 통일 >> 유지보수성 강화

    
    1-1. 기본 function패키지(매개변수 하나 / 매개변수와 반환타입 따로 지정) 함수형 인터페이스 (인터페이스이름 + 추상메서드 이 둘의 이름을 같이 알아둬야함)

    - java.lang.Runnable | void run() : 매개변수, 반환값 둘 모두 없는 경우

    - Supplier<T> | T get() : T타입의 반환값만 있는 경우

    - Consumer<T> | void accept(T t) : T타입의 매개변수 하나만 있는 경우

    - Function<T, R> | R apply(T t) : T타입의 매개변수를 하나 받아 R타입의 반환값을 내는 경우

    - Predicate<T> | boolean test(T t) : 반환값이 boolean타입인 조건람다식을 위한 Function

    cf) Predicate : 수학에서 true, false로 반환하는 함수를 말함


    1-2. 매개변수가 두 개인(3개 이상은 알아서 만드삼) function패키지 인터페이스 (I/F명에 Bi가 붙음 + 반환값이 2개일 수는 없으니 Supplier와 Runnable은 없다) 
    
    - BiConsumer<T, U> | void accept(T t, U r)

    - BiFunction<T, U, R> | R apply(T t, U u)

    - BiPredicate<T, U> | boolean test(T t, U u)


    1-3. 매개변수와 반환 타입이 모두 일치하는(지네릭에 타입변수가 하나임) function패키지 인터페이스

    - UnaryOperator<T> | T apply(T t) : Function의 자손

    - BinaryOperator<T> | T apply(T t1, T t2) : BiFunction의 자손


    1-4. 매개변수타입이나 반환타입으로 기본형이 가능한 경우 function패키지 인터페이스  (지네릭 타입이면 래퍼클래스로 처리해야 되므로 성능면에서 비효율적임)

    >> 매개변수타입 혹은 반환타입이 기본형 int인 경우

    - IntSupplier | int getAsInt()

    - IntConsumer | void accept(int i)

    - ObjIntConsumer<T> | void accpet(T t, int i) : 지네릭도 함께 사용가능

    - IntPredicate | boolean test(int i)

    >> Function의 경우 : 반환타입이 기본형인 경우 위 방식 앞에 To를 붙인다

    - DoubleToIntFunction | int applyAsInt(double d) : To~인 경우 반환타입명시를 위해 apply뒤에 As~가 붙는다

    - ToIntFunction<T> | int applyAsInt(T t)

    - IntFunction<T> | T apply(int i)

    >> IntIntFunction은 없다 하지만 IntFunction<Integer>는 오토박싱 언박싱 처리가 들어감 >> 이럴땐 IntUnaryOperator를 적극 사용하자 (애초에 얘가 있어서 없는거)


    2. 컬렉션 프레임워크와 함수형 인터페이스 : 컬렉션 프레임워 I/F에 추가된 디폴트 메서드 중 function패키지의 함수형 I/F를 사용하는 경우

    >> 얘는 실제로 예제(3번)이나 사용을 해보고 짚어봐야함

    >> 아래 지네릭타입은 사실 와일드 카드를 사용 근데 책에서 걍 생략

    >> Collection I/F

        - boolean removeIf(Predicate<T> filter) : 람다조건식에 맞는 요소를 삭제

    >> List I/F

        - void replaceAll(UnaryOperator<T> operator) : 모든 요소를 람다식으로 변환처리하여 대체

    >> Iterable I/F

        - void forEach(Consumer<T> action) : 모든 요소에 람다식 작업을 수행시킬 때 (주로 배열 출력을 할 때 등)

    >> Map : Map이니 Key Value 설정을 해야겠지

        - V compute(K Key, BiFunction<K, V, V> f) : 지정된 키값'에' 작업 f를 수행 (그 키가 있는 필드에 작업수행) (compute : 맵의 value를 변환하는 작업 수행)

        - V computeIfPresent(K Key, BiFunction<K, V, V> f) : 지정된 키가 있을 때 작업 f 수행 (그 키가 있으면 작업 수행)

        - V computeIfAbsent(K key, Function<K, V> f) : 키가 없으면 작업 f를 수행 후 추가함 (말그대로 키가 없으면 작업 수행한 뒤 덧붙이는거)

        =======>> compute메서드는 이해가 솔직히 정확히는 안됐으므로 나중에 사용하고 설명을 추가

        - V merge(K key, V value, BiFunction<V, V, V> f) : 모든 요소에 병합작업 f 수행

        - void forEach(BiConsumer<K, V> action) : 모든 요소에 action 수행

        - void replaceAll(BiFunction<K, V, V> f) : 모든 요소에 치환작업 수행


*/

import java.util.*;
import java.util.function.*;

public class Study_210511 {

    static <T> List<T> doSomething(Function<T, T> f, List<T> list) {
        List<T> newList = new ArrayList<T>(list.size());

        for (T t : list) {
            newList.add(f.apply(t)); // 매개변수로 받은 람다식을 적용한 함수인터페이스의 메서드 실행 지점
        }

        return newList;
    }

    static int[] doSomething(IntUnaryOperator op, int[] arr) { // 기본형 처리를 위해 오버로딩
        int[] newArr = new int[arr.length];

        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = op.applyAsInt(arr[i]);
        }

        return newArr;
    }

    static <T> void printEvenNum(Predicate<T> p, Consumer<T> c, List<T> list) {
        System.out.print("[ ");
        for (T t : list) { // 리스트에서 T타입 애들 순서대로 끝까지 꺼내쓴다는거 vue에서 for in 문법
            if (p.test(t)) {
                c.accept(t);
            }
        }
        System.out.println(" ]");
    }

    static void printEvenNum(IntPredicate p, IntConsumer c, int[] arr) {
        System.out.print("[ ");
        for (int i : arr) {
            if (p.test(i)) {
                c.accept(i);
            }
        }
        System.out.println(" ]");
    }

    static <T> void makeRandomList(Supplier<T> s, List<T> list) {
        for (int i = 0; i < 10; i++) {
            list.add(s.get());
        }
    }

    static void makeRandomList(IntSupplier s, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = s.getAsInt();
        }
    }

    public static void main(String[] args) {

        Supplier<Integer> s1 = () -> (int) (Math.random() * 100);
        Consumer<Integer> c1 = i -> System.out.print(i + ", ");
        Predicate<Integer> p1 = i -> i % 2 == 0;
        Function<Integer, Integer> f = i -> i / 10 * 10; // 일의 자리 제거

        IntSupplier s2 = () -> (int) (Math.random() * 100);
        IntConsumer c2 = i -> System.out.print(i + ", ");
        IntPredicate p2 = i -> i % 2 == 0;
        IntUnaryOperator o = i -> i / 10 * 10;

        System.out.println("===========================================");
        System.out.println();

        List<Integer> list = new ArrayList<>();
        makeRandomList(s1, list);
        System.out.println(list);
        printEvenNum(p1, c1, list);
        List<Integer> newList = doSomething(f, list);
        System.out.println(newList);

        System.out.println();
        System.out.println("===========================================");
        System.out.println();

        int[] arr = new int[10];
        makeRandomList(s2, arr);
        System.out.println(Arrays.toString(arr));
        printEvenNum(p2, c2, arr);
        int[] newArr = doSomething(o, arr);
        System.out.println(Arrays.toString(newArr));

        System.out.println();
        System.out.println("===========================================");
        System.out.println();

        ArrayList<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list2.add(i);
        }

        list2.forEach(i -> System.out.print(i + ", "));
        System.out.println();

        list2.removeIf(x -> x % 2 == 0 || x % 3 == 0);
        System.out.println(list2);

        list2.replaceAll(i -> i * 10);
        System.out.println(list2);

        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.forEach((k, v) -> System.out.print("{ " + k + " : " + v + " }, "));
    }
}
