//package chapter12

/*  5/5 : 아직 10챕터 하는 중이었지만 Spring에서 JPA, API 공부하다 보니 몇몇 파트를 급히 공부해놓는게 좋을 것 같아 순서를 잠시 조정
    >> 12챕터 지네릭스와 14챕터 람다와 스트림 3파트를 먼저 공부하기로 함
    >> 이후 기존 일정에 대사 돌아오거나 or 필요에 따라 컬렉션타입이나 스레드를 가거나 할 예정 (그러다 수순이 다시 되면 공부한거 간단히 훑자)

    1. 지네릭스(Generics) : <T>
    다양한 타입의 객체를 다루는 / 메서드나 컬렉션 클래스에 / 컴파일 시 / 타입체크를 해주는 기능
    클래스와 메서드에 선언이 가능하다.

    >> 다룰 객체 타입을 미리 명시하여 타입 안정성을 제공(의도치않은 객체타입 저장 피함)하고 + 타입체크와 형변환을 줄여 코드를 간결하게 해준다

    >> class Box<T> {}에서
        - Box<T> : 지네릭 클래스 (T Box)
        - T : 타입 (매개)변수 >> 메서드의 매개변수와 유사
            > T에 타입을 지정하는 것 = 지네릭 타입 호출 | 지정된 타입 = 매개변수화된 타입
        - Box : 원시타입
            > 지네릭스는 컴파일 시 타입체크를 해주는 것 > 컴파일 후 지네릭타입이 제거되어 원시타입으로 바뀐다
            > Box<Integer>, Box<double>요소 내부 T가 매개변수화된 타입들로 지정되 인스턴스가 별개의 동작을 취해도 별개의 클래스를 의미하진 않음

            
    2-1. 지네릭 클래스 선언
    지네릭은 위에서도 클래스와 메서드에 선언 가능하다 함 > 이 중 지네릭이 선언된 클래스

    기존 다양한 종류의 타입을 다루는 매개변수나 리턴타입은 Object타입 참조변수를 지정하여 형변환을 처리
    class Box {
        Object item;
    }
    하지만 지네릭을 사용하면 필요에 따라 사용할 실제 데이터타입을 지정해주면 된다 : T(ype)는 말그대로 임의의 타입 '변수'
    class Box<T> {
        T item;
    }
    대신 사용시 실제타입(String 등) 지정을 반드시 해야한다. 물론 이전코드와 호환성을 위해 지네릭클래스에 지정을 안함 알아서 Object로 지네릭타입이 호출되긴 하는데 경고가 뜬다

    >> 지네릭 static 제한
        - 타입매개변수 T는 지네릭 타입 호출을 하기전에는 형태지정이 안되기에 인스턴스변수와 같이 간주됨
        - 따라서 모든 객체에 동일하게 동작하는 static 멤버에는 타입변수를 지정이 불가능함 (지네릭 메서드가 아니라 지네릭 클래스에서 지정한 타입변수)
        - class Box<T> { static T item; static int compare(T t1, T t2){};} 이렇게 대입되는 얘들이 안된다는 것 (지네릭 메서드랑 헷갈림 주의)

    >> 지네릭 배열 제한 : 클래스에 배열을 위한 참조변수를 선언하는건 가능하나 생성은 불가
        > T[] itemArr;은 가능 but T[] itemArr = new T[x]; 같은건 안됨
        > 이는 'new 연산자'나 instanceof와 같은 연산자들이 컴파일 시점에 타입을 정확히 알아야하기 때문 > 근데 지네릭은 컴파일 시 체크된다
        > 머 굳이 필요하면 Object배열을 생성해 복사해 형변환 하던가...

        
    2-2. 지네릭 클래스 객체 생성 및 사용 (아래 GenericsEx1 참조)
    - 객체 생성시는 참조변수와 생성자에 매개변수화된 타입이 일치하게 한다
        Box<Apple> appleBox = new Box<Apple>(); > 이렇게 지네릭스 타입호출(타입지정)을 실제 타입으로 동일하게 맞춤
        Box<Apple> appleBox = new Box<>(); JDK1.7부터 짜피 답정너의 경우 생략 가능
    
    - 위는 머 당연한 이야기 같은데 문제는 상속관계에 있다
        > 상속관계라 하더라도 지네릭스 타입호출은 동일하지 않으면 문제가 발생
            Box<Fruit> appleBox = new Box<Apple>(); > 보통 상속관계상 큰데다가 작은거 담는건 될거 같은데 안됨
        
        > 대신 매개변수화된 타입이 같으면 원시타입이 상속관계에 있는건 괜찮다.
            Box<Apple> applebox = new FruitBox<Apple>();
        > 또한 기존에 Object타입 넣고 했던 작업을 훨씬 스마트하게 한다는 시점에서 / 선언후 실제 운용에서 매개변수화된 타입의 자손 타입들은 사용되는건 가능하다
            Box<Fruit> fruitBox = new Box<Fruit>(); 면 한쪽에 Apple이 들어가는건 안되도
            내부에 void add(T item) >> void add(Fruit item)이 된 것이기에 매개변수로 Apple item을 받을 수는 있음

    2-3. 지네릭 클래스 타입매개변수 제한시키기 : 지네릭 타입 호출을 시켜 선언시점 전까지는 T만으로는 모든 타입이 들어가므로 >> T 자체를 제한시킨다 (선언 가능 타입은 여전히 하나) (아래 GenericsEx1 참조)
    - 타입변수 T 제한 방법은 extends를 사용하기 <T extends Fruit>
        > 지정된 클래스 타입 + 자손클래스만 대입할 수 있게 된다 (다형성에서 조상타입 참조변수로 자식타입 객체를 가리키는게 가능하것과 같음)

        > 만약 인터페이스 구현 제약일 경우도 마찬가지 제한을 걸 수 있다 > 이때 implements가 아니라 똑같이 extends를 써야함

        > 상속클래스 제한 + 인터페이스 구현 제약 함께 거는 경우 '&'로 연결 가능하다 <T extends Fruit & Eatable>

    3. 와일드카드 (아래 GenericsEx2 참조)
    - 다음과 같이 지네릭 클래스의 참조변수를 매개변수로 받는경우
        class Juicer {
            static Juice makeJuice(FruitBox<Fruit> box) {
                ...
                for(Fruit f : box.getList()) {...} >> 여기는 자손클래스타입 받을수 있어도 위 지네릭스에 걸리고 Object로하면 여기서 걸림 ㄷㄷ
            }
        }
        
        >> 곧바로 운용측면에 있어 3가지 정도 문제가 우선 발생한다.
        1) Juicer는 지네릭클래스가 아니어서 내부코드에 적용될 T를 지정 못함 + 애초에 static메서드에서는 인스턴스변수로 간주되는 T를 매개변수로 받지 못함
        2) 그래서 FruitBox<Fruit>로 지정할 경우 지네릭이 다른 FrutitBox<Apple>은 지네릭의 타입변수가 상속관계더라도 다르기에 매개변수타입으로 쓸수없음
        3) 그렇다고 오버로딩을 통해 static Juice makeJuice(FruitBox<Apple> box) {} 같은 개노가다도 안됨 >> 위에서처럼 지네릭타입음 컴파일 후 제거되기에 오버로딩이 성립되지 않고 메서드 중복 정의가 됨

        >> 그래서 이런 겁나 골때리는 상황을 위해 쓰이는 것이 와일드 카드 <?>
        >> 그리고 그냥 <?>을 쓰는건 기존의 Object쓰는거랑 별 차이가 없으니 <? extends T> <? super T>로 각각 상한과 하한을 지정
            - extends T : T와 그 자손만 vs super T : T와 그 조상만

            >> 그럼 이제 대충 위에서도 비슷하게 갔으니 상한은 알겠는데 하한은 언제 쓰는가 : 대표 케이스 Collections.sort() 메서드임
                - static <T> void sort(List<T> list, Comparator<? super T> c) 로 선언되어 있음
                
                - 반환 타입 앞에 있는 <T>는 위에서 언급한 지네릭스 2개중 하나인 지네릭메서드의 지네릭임
                    >> 담파트에 하겠는데 이 경우는 static<T> 지네릭클래스의 T와 별개인 지역변수개념으로 호출시 세팅이 가능하기에 static도 가능 List<T>는 지네릭메서드의 T라 가능
                
                    >> 같은 원리로 매개변수의 타입변수 T들은 지네릭메서드의 T로서 메서드 호출시 지정된다고 생각

                - 다시 이야기를 돌려 하한이 없어 Comparator<T>가 되었을때 Apple로 메서드를 호출하면 <Apple>sort(List<Apple> list, Comparator<Apple> c) 처럼 돌아간다고 할 수 있음
                    >> 근데 저 Comparator가 Apple의 부모인 Fruit의 weight요소를 가져와(super) 비교하는 것일 경우,
                       그냥 Fruit의 Comparator만 만들면 될것을 Apple, Grape 하위 요소마다 Comparator를 만들어주는 귀찮음을 벌여야함
                    >> 하지만 Comparator<? super T>이면 T가 Apple일 때 올 수 있는 값은 Apple, Fruit, Object가 되므로 Comparator<Fruit>하나 구현하고 종류마다 같다 넣음 됨
                    
                    >> 말그대로 super요소로 다 처리되는 얘들을 하위클래스타입으로 T지정 때문에 각각 하위클래스마다 구현할 필요가 없기 위한 한거임

*/

import java.util.ArrayList;

public class Study_210505 {

    public static void GenericsEx1() {
        Box<Fruit> fruitBox = new Box<Fruit>();
        Box<Apple> appleBox = new Box<Apple>();

        fruitBox.add(new Apple()); // 내부 메서드에 다형성 적용은 됨

        // Box<Fruit> appleBox2 = new Box<Apple>(); 원시타입 다형성은 되도 지네릭 다형성은 안됨
        Box<Apple> appleBox3 = new FruitBox<Apple>();

        FruitBox<Apple> appleBox4 = new FruitBox<Apple>();
        Box<Toy> toybox = new Box<Toy>();
        // FruitBox<Toy> toybox2 = new FruitBox<Toy>(); FruitBox 지네릭에 제한이 걸려 있으므로 Toy는
        // 안됨
    }

    static Juice makeJuice(FruitBox<? extends Fruit> box) {

        String tmp = "";
        for (Fruit f : box.getList()) {
            tmp += f + " ";
        }
        return new Juice(tmp);
    }

}

interface Eatable {
}

class Juice {
    String name;

    Juice(String fruitName) {
        this.name = fruitName + " Juice";
    }
}

class Fruit implements Eatable {
    String name;

    Fruit() {
        this.name = "FRUIT";
    }

    Fruit(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

class Apple extends Fruit {
    Apple() {
        super("Apple");
    }

    Apple(String name) {
        super(name);
    }
}

class Grape extends Fruit {
    Grape() {
        super("Grape");
    }

    Grape(String name) {
        super(name);
    }
}

class Toy {

}

class Box<T> {
    ArrayList<T> list = new ArrayList<T>();

    void add(T item) {
        list.add(item);
    }

    T get(int i) {
        return list.get(i);
    }

    ArrayList<T> getList() {
        return list;
    }

    public String toString() {
        return list.toString();
    }
}

class FruitBox<T extends Fruit & Eatable> extends Box<T> { // Fruit가 이미 Eatable을 구현함

}