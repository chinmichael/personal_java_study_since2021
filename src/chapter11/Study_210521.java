//package chapter11;

/*  1. Iterator (Iterator, ListIterator, Enumeration)

    > 컬렉션 프레임워크가 지정한 / 컬렉션에서 요소들을 읽어오는 방법의 / 표준에서

      쓰이는 각 요소에 접근하는 기능의 인터페이스 = Iterator


    > Collection I/F(Map은 Iterator 안됨)에서는 위 I/F의 구현클래스 인스턴스인 Iterator를 반환하는 iterator()가 정의됨
    
      public interface Collectino {
          public Iterator iterator();
      }

      - Map의 경우 key-value 쌍으로 되어있기에
        대신 필요한 경우 keySet(), entrySet()과 같은 Set형태로 추출하는 메서드를 통해 iterator를 호출한다
        Map map = new HashMap();
        Iterator it = map.entrySet().iterator();

      - 참고로 위의 경우 entySet()의 반환타입인 Set이기에
        StringBuffer()에서 append() 반환타입이 StringBuffer이기에 append().append() 연속처리가 가능한것과 같은 원리 (반환타입이 void인 경우 append()호출이 안되니 당근 안됨)

    > Iterator I/F 메서드

     - boolean hasNext() : 다음에 읽어올 요소가 있는지 확인

     - T next() : 다음 요소를 읽어온다 | ★ 안전성을 위해 위 hasNext()로 먼저 남은 요소가 있는지 확인하고 읽는 것이 좋다

     - void remove() : 'next()로 읽어온' 요소를 삭제한다. (next()호출 다음에나 사용 가능) >> 메일같은거에서 그냥 읽어올 것인지 읽어오면서 삭제할것인지 등의 기능과 마찬가지
      

    > Collection I/F의 하위 List, Set의 구현 클래스들은 다음과 같이 요소추출하는 방법이 다음 예시와 같이 '표준화' 되어 있음

      List<T> list = new ArrayList<>();  // 다른 리스트 구현 클래스에서 이부분만 고치면 되게 (재사용성 높임)
      Iterator it = list.iterator();

      while(it.hasNext()) { // 주로 while 반복 쓰는 이유
            System.out.println(it.next());
      }

      - 이 코드의 경우 다른 List 구현 클래스에서도 바로 사용가능한데 이런 높은 재사용성이 가능한 이유는
        1. 참조변수 타입을 ArrayList가 아닌 List로 하였고
        2. Iterator란 공통 I/F을 통해 컬렉션을 읽는 방법을 '표준화'했기 때문

        (이런 코드 일관화를 통한 재사용성의 극대화는 OOP의 중요 목적 중 하나임)

        참고로 1의 경우 참조변수를 List타입으로 했기에 ArrayList에서만 정의된 메서드를 사용한게 아니게 된다.
        이렇게 구현 클래스만의 메서드를 사용하지 않은 경우 이렇게 상위클래스 혹은 인터페이스 타입으로 지정하는 것은
        굳이 검토를 하지 않아도 다른 구현 클래스들에서 바로 사용이 가능하게 하는 중요한 재사용성을 높이는 요소이다.
    

    > List와 Set의 Iterator 결과 차이 : 배경상 List와 달리 Set은 순서가 없으므로 Iterator로 읽어온 결과 역시 저장순서와 달리 읽혀진다
    
    
    2. Enumeration, ListIterator

    2-1. Enumeration : 컬렉션 프레임워크 이전 Iterator | 이전 버전과의 호환성을 위해 남은것

    - boolean hasMoreElements() = Iterator의 hasNext()

    - T nextElement() = Iterator의 next();


    2-2. ListIterator : List I/F 구현 클래스에서만 사용 가능
                      : Iterator를 상속받아 구현한 것으로 단방향 이동만 가능한 Iterator > 양방향 이동이 가능하도록 업그레이드 >> 마지막 요소에 다다러도 사용 가능

    - 추가된 메서드만 보이자면 기존 hasNext(), next(), remove()에서 previous로 이전이동이 추가된 정도의 느낌이다

    - boolean hasPrevious()

    - T previous()

    - int nextIndex() : 다음 요소의 index반환 (List 타입에만 해당되니 여기에 추가)

    - int previousIndex() : 이전 요소의 index 반환

    - void add(T t) : 컬렉션에 지정된 객체 추가

    - void set(T t) : next() 혹은 prvious()로 읽어 온 요소로르 지정된 객체로 변경한다 (remove처럼 반드시 next(), previous 호출 후에 사용 가능)

    ※ next()나 previous() 없이 remove()등을 사용하면 IllegalStateException 발생


    ※ 선택적 기능 : 위 Iterator의 void add(T t), void set(T t), void remove() 와 같은 케이스로
                  : 반드시 구현하지 않아도 되는 경우의 케이스들임
                    > 대신 I/F 상속의 메서드는 추상메서드이기에 body처리를 throw ~Exception으로 예외처리를 해줌으로서 구현되지 않은 기능임을 알려주는 것이 좋다

                  : Iterator에서 지원하지 않은(구현되지 않은) 메서드는 UnsupportedOperationException을 발생시킨다.
                    > 위 예외는 RuntimeException(실행은 가능하나 발생시 비정상종료)의 자손이기에 따로 메서드 선언부에 예외처리가 되진 않음
*/

import java.util.*;

public class Study_210521 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }

        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println();
        System.out.println("====================================");
        System.out.println();

        ListIterator<Integer> lit = list.listIterator();

        while (lit.hasNext()) {
            System.out.println(lit.next());
        }
        System.out.println();

        while (lit.hasPrevious()) { // 그냥 Iterator의 경우 단방향이기에 위에 다 돌았으면 더 이상 못쓰지만 ListIterator은 역으로 움직임 가능
            System.out.println(lit.previous());
        }

        System.out.println();
        System.out.println("====================================");
        System.out.println();

        List<Integer> original = new ArrayList<>();
        List<Integer> copy1 = new ArrayList<>(); // 그냥 Iterator로 읽으며 옮기는 경우
        List<Integer> copy2 = new ArrayList<>(); // Iterator로 읽고 삭제하며 옮기는 경우

        for (int i = 0; i < 10; i++) {
            original.add(i);
        }

        Iterator<Integer> origin_it = original.iterator();

        while (origin_it.hasNext()) {
            copy1.add(origin_it.next());
        }
        System.out.println("==Original에서 삭제없이 복사==");
        System.out.println("original : " + original);
        System.out.println("copy1 : " + copy1);
        System.out.println();

        origin_it = original.iterator(); // 단방향이동이므로 Iterator는 재사용이 안되 다시 얻어와야함

        while (origin_it.hasNext()) {
            copy2.add(origin_it.next());
            origin_it.remove();
        }

        System.out.println("==Original에서 next() 복사 후 remove()==");
        System.out.println("original : " + original);
        System.out.println("copy2 : " + copy2);
        System.out.println();

    }
}
