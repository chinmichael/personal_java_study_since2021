//package chapter 12

/*  1. 지네릭 메서드
    Collection.sort()처럼 메서드 선언부에 지네릭 타입이 선언되 메서드 (반환타입 바로 앞에 위치)

    ex) static <T> void sort(List<T> list, Comparator<? super T> c)

    >> 주의할 부분 2가지
        - class Box<T> { <T> void gMethod() {} } 에서 지네릭클래스의 타입변수 T와 지네릭메서드의 타입변수 T는 서로 다른 것임
        
        - 일종의 지역매개변수처럼 생각하면 편하다. 즉 메서드 내에서만 적용되는 지역 타입변수로 생각
            >> 그렇기 때문에 위 sort메서드의 경우 static메서드임에도 사용이 가능한 이유다
            >> 인스턴스 초기화(시에 타입이 정해져야하는)가 필요한 인스턴스 변수같은 개념이 아닌 메서드 호출시 대입해서 메서드 내부에서만 사용하는 지역변수 개념이기에

        - (같은 이유로 내부클래스 타입변수도 T로 하지만 다르게 세팅 가능)

    >> 사용법 : 위에서처럼 일종의 지역 타입변수로 써 생각해 사용해주면 된다
        
        - static Juice makeJice (FruitBox<? extends Fruits> box) 를
            >> static<T extends Fruit> Juice makeJuice (FruitBox<T> box) 로 변경 가능 (굳이 와일드 카드를 쓸 일을 줄여줌)

            (cf.참고로 실제 사용시 <Fruit>makeJuice(fruitbox) 등으로 하지만 보통 매개변수들이 선언시 어떤 T가 들어가는지 컴파일러가 가늠이 되기에 생략되는 경우가 많다)

        - 근데 저런경우 그냥 와일드 카드를 써서 하면 되지 굳이 개념이 추가되나?
            >> 지네릭메서드의 타입변수는 일종의 지역변수로서 파라미터가 복잡해지는 등의 상황에서 매우 유용함

            static void printAll(ArrayList<? extends Product> list, ArrayList<? extends Product> list2, ArrayList<? extends Product> list3,)
                >> static<T extends Product> void printAll(ArrayList<T> list, ArrayList<T> list2, ArrayList<T> list3,)

            static <T extends Comparable<? super T>> void sort(List<T> list)
                >> T 타입을 매개변수화된 타입으로 하는 List를 파라미터로 받음
                >> 이 T는 Comparable I/F를 구현한 클래스여야 하며 + 이 Comparable 구현은 T자신 혹은 조상클래스의 타입을 비교하는 것이어야 한다.


    >> 5/10 추가 : 간단히 생각하면 좋음
        - 지네릭클래스의 타입변수는 인스턴스 생성시에 지정을 해줘야함 >> 따라서 와일드카드 같은 케이스가 아니면 static에 사용하지 못한다
        - 지네릭메서드의 타입변수는 메서드 호출 시 지정 + 지네릭 타입변수는 일종의 매개변수처럼 생각 가능 >> static메서드에서도 사용 가능



    2. 지네릭 타입 형변환

    >> 지네릭타입과 원시타입간 형변환 : 지네릭 타입과 넌지네릭간 형변환은 항상 가능 대신 경고가 뜬다

    >> 매개변수화된 타입이 다른 지네릭 타입간 형변환 : 한쪽이 설령 Object를 매개변수타입으로 해도 안됨 >> 머 그거야 상속관계 타입변수로 생성이 안될때부터 아는 이야기
        -Box<Object>와 Box<String>간 형변환 불가

    >> 와일드 카드를 사용한 경우 : 와일드 카드 내부 조건에 맞다면 형변환 가능 그렇기에 다형성이 적용된 것
        - Box<? extends Object> 라면 Box<Object>와 Box<String>간 형변환 가능

    ※ java.util.Optional클래스 예시
    public final class Optional<T> {
        private static final Optional<?> EMPTY = new Optional<>(); // 이때 여기서의 <>는 <?>가 아니라 Object를 가정한다 (미확인 타입 객체는 아무리 와일드카드라도 이론상 당연 불가능)
        private final T value;

        public static<T> Optional<T> empty() {
            Optional<T> t = (Optional<T>) EMPTY;
            // 비어있는 Optional 객체를 생성해 저장한 뒤 empty()로 호출 시 형변환하여 반환함
            // EMPTY의 지네릭은 위에서처럼 짜피 <>가 Object임에도 <Object>가 아닌 <?>로 선언한 이유는 그래야 Optinal<T>로 형변환이 가능하기 때문이다. 상속관계에 있어도 일반적인 지네릭은 타입 맞춰야한다
            return t;
        }
    }

    Optional<?> wopt = new Optional<Object>();
    Optional<Object> oopt = new Optional<Object>(); 에서

    Optional<String> sopt = (Optional<String>) wopt;는 가능해도 (Optional<String>) oopt; 는 불가능하다

    추가적으로 Optional의 경우 <Object>로 생성선언한 경우 <String>등 형변환이 직접 안되나 중간에 <?>형변환을 껴주는 것으로 경고는 뜨더라도 간접적으로 처리는 가능하다

    3. 컴파일시 기본적인 지네릭 타입 제거 과정 (지네릭 도입이전 호환성을 위해 처리되는 것)

        - 지네릭 타입 경계 제거 : <T>의 경우 Object로 치환된 후 제거 / <T extends Fruit>의 경우 Fruit로 치환된 후 제거

        - 제거한 뒤 타입불일치가 발생하면 형변환
            T get (int i ) { return list.get(i) } 일때 Fruit가 매개변수화된 타입이면 필요에 따라 Fruit get (int i) { return (Fruit) list.get(i)}
            특히 와일드 카드를 사용했을때 자동 형변환 기능이 많이 실행된다.
*/

public class Study_210507 {

}
