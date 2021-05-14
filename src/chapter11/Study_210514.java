//package chapter11

/*  5/14 : 컬렉션 프레임워크 시작 (14챕터 스트림 이전에 들어가는게 맞는거 같다)

    1. 컬렉션 프레임워크
    >> 데이터 그룹(Collection)을 저장하는 클래스들을 표준화한 설계(아키텍처), 프로그래밍 방식(프레임워크)
    >> 표준화된 데이터 그룹(을 다루는) 클래스
    >> JDK 1.2이전 Vector, Hashtable, Properties들이 각자의 방식대로 처리되던 것을 표준화하고 새로운 컬렉션 클래스들을 추가함


    1-1 컬렉션 프레임워크 핵심 인터페이스 (Collection(>List / Set) / Map)

    >> 데이터 그룹의 3가지 타입 : List, Set, Map
    >> 이들을 각각의 인터페이스로 만들고 List와 Set의 공통부분으로 상위 인터페이스 Collection 추가 정의

    >> 컬렉션 프레임워크의 클래스는 List, Set, Map 중 하나를 구현하며 이름에 포함 (그 이전에 존재한 Vector, Hashtable, Properties는 명명법을 따르지 않음)

    cf) 추가된 Iterator I/F의 경우 Collection I/F가 상속 받음 이는 공통의 iterator()를 뽑아 중복을 제거하기 위함
    Tip) 객체지향적으로 잘 설계된 부분이라 나중에 실제 소스를 뜯어보는건 매우 도움이 됨

    >> 각 데이터 그룹 특징 (구현 클래스) : 개발 시에는 자신이 다루는 데이터가 어디 속하는지 파악하고 적절히 적용시켜야 한다.

        - List (ArrayList, LinkedList, Stack, Vector)
          순서가 있고 + 데이터 중복이 허용되는 집합

        - Set (HashSet, TreeSet)
          순서를 유지하지 않지만 + 데이터 중복이 허용되지 않는 집합 (양의 정수 집합 등)

        - Map (HashMap, TreeMap, Hashtable, Properties)
          Key와 Value의 쌍으로 이뤄진 집합 > Key는 중복이 허용되지 않으나 Value는 중복 허용


    1) Collection 인터페이스 (List, Set 조상)
    >> 컬렉션 클래스에 저장된 데이터의 읽기 / 추가 / 삭제 기본 메서드로 구성
    >> 람다 스트림에 추가된 파트는 14챕터에서

    - boolean add(T t) / boolean addAll(Collection c) : 지정 객체 혹은 Collection 객체들을 추가

    - void clear() : Collection 내의 모든 객체 삭제

    - boolean contains(T t) / boolean containsAll(Collection c) : 지정 객체 혹은 Collection 객체들이 포함되어 있는지 확인

    - boolean equals(Object o) : 동일한 Collection인지

    - int hashCode()

    - boolean isEmpty()

    - Iterator iterator() : Collection의 Iterator(컬렉션에 저장된 객체들에 접근하는 방법 제공)를 얻어 반환

    - boolean remove(T t) / boolean removeAll(Collection c) : 지정 객체 혹은 Collection 객체들 삭제

    - boolean retainAll(Collection c) : 지정 Collection의 객체들만 남기고 나머진 삭제 >> 작업으로 변화가 있으면 true

    - int size() : 저장된 객체 개수 반환

    - Object[] toArray() / Object[] toArray(Object[] a) : 저장된 객체를 객체배열로 반환 or 지정된 배열에 넣어 반환


    2) List 인터페이스 : 중복 허용 + 저장순서 유지 >> List는 key(Map)이나 value(Set)가 아니라 순서(index)로 구분된다고 생각
    >> ArrayList / LinkedList / Vector(>Stack)

    - void add(int index, T t) / void addAll(int index, Collection c) : 지정 위치(index : 0부터 시작)에 저장

    - T get(int index) : 지정 위치의 객체 반환

    - int indexOf(T t) / int lastIndexOf(T t) : 지정 객체의 위치 반환 >> 검색방향이 순방향 / 역방향 >> 데이터 중복이 허용되므로 검색순서에 영향 있다

    - ListIterator() / ListIterator(int index) : List객체 접근을 위한 Iterator 반환

    - T remove(int index) : 지정 위치의 객체 삭제 / 삭제 후 삭제된 객체 반환

    - T set(int index, T t) : 지정 위치에 객체 저장 (수정이라 표현하는게 나을듯)

    - void sort(Comparator c) : 지정된 비교자(Comparator)로 List 정렬

    - List subList(int fromIndex, int toIndex) : 지정범위 객체를 List로 반환 (toIndex부분은 넣지 않음)


    3) Set 인터페이스 : 중복 허용 X + 저장 순서 유지X >> value로 구분됨
    >> HashSet / SortedSet(>TreeSet)


    4) Map 인터페이스 : key-value의 쌍으로 저장 / key만 중복이 불가 >> key 중복시 해당 value가 덮어씌어진다.
    >> Key와 Value를 Mapping한다는 개념에서 Map임
    >> Hashtable / HashMap(>LinkedHashMap) / SortedMap(>TreeMap)

    - void clear()

    - boolean containsKey(K key) / boolean containsValue(V value) : 지정된 key / 혹은 value객체가 존재하는지

    - boolean equals(Object o) : 동일한 Map인지

    - V get(K key) : 지정 key에 대응하는 value 반환

    - int hashCode()

    - boolean isEmpty()

    - Set keySet() : 저장된 모든 key 객체 반환 (중복 허용 안되기에 Set)

    - Collection values() : 저장된 모든 value 객체 반환 (중복 허용이 되기에 Collection)

    - V put(K key, V value) : 지정 key에 value를 연결(mapping)해 추가 / 동일 key에 입력해 덮어쓰기가 될 때 기존 데이터가 반환됨 (Collection의 add는 저장되면 무조건 true)

    - void putAll(Map m) : 저장된 Map의 모든 key-value 추가

    - V remove(K key) : 지정 key에 일치하는 key-value 객체 삭제 / 삭제시 value 객체 반환

    - int size()

    - Set entrySet() : Map에 저장된 key-value 쌍을 Map.Entry타입의 객체로 저장한 Set으로 반환


    5) Map.Entry 인터페이스 : Map의 내부 인터페이스 (내부 클래스 같음 / 인터페이스 구현시 내부 인터페이스도 함께 구현해야함)
                           : Map에 저장되는 key-value를 다루기 위함 >> ★ Map의 Entry는 Key-Value 쌍의 객체
                           
    - boolean equals(Object o) : 동일한 Entry인지

    - K getKey() : Entry의 key반환

    - V getValue() : Entry의 value반환

    - int hashCode()

    - V setValue(V value) : 지정 value객체로 value바꿈 / 기존 value를 반환한다


    2. ArrayList
    >> 컬렉션 프레임워크에서 가장 많이 사용되는 컬렉션 클래스
    >> List인터페이스 구현 = 중복허용 + 데이터 저장순서 유지 >> List는 key(Map)이나 value(Set)가 아니라 순서(index)로 구분된다고 생각

    >> 기존의 Vector를 개선하여 구현원리와 기능이 거의 유사 / 되도록 ArrayList를 쓰자!

    >> 멤버변수로 elementData라는 이름의 transient(직렬화 제어자) Object[]을 이용해 데이터 저장 (Object는 처음 저장한 데이터에 따라 변화한다)
    >> Object[]에 데이터를 순차적으로 저장하며(add 메서드 쓰면 알아서 뒤에 추가),
       공간이 부족하면 더 큰 배열을 생성해 복사해서 넣는다 (자동적으로 확장이 되나 성능상 안좋으니 초기화 할때 미리 길이를 약간 여유있게 잘 잡자)

       public class ArrayList extends AbstractList
                              implements List, RandomAccess, Cloneable, java.io.Serailizable 
    
    
    2-1. ArrayList 추가 메서드 (Collection과 List의 공통인 부분은 제외)
    
    - ArrayList() / ArrayList(int capacity) / ArrayList(Collection c) : 지정이 없으면 크기가 10인 ArrayList생성 / 지정 초기용량으로 생성 / 주어진 Collection이 저장된 ArrayList 생성
    
    - Object clone() : ArrayList 복사 / Cloneable I/F 구현함 이게 있으니 자동확장도 되겠지...

    - void ensureCapacity(int minCapacity) : ArrayList용량이 최소한 minCapacity가 되도록 한다 (그니까 초기화 후에 확장할 때 쓴다)

    - void trimToSize() : 용량을 크기에 맞게 줄인다 (빈공간 삭제)

    2-2. ArrayList 주의점

    - 성능을 위해 초기화 시 크기를 적절히 조금 여유있게 잡는다

    - 반복문으로 삭제 처리시 뒤에서부터 해야할 때 (아래 예제코드 참조)
*/

import java.util.*;

public class Study_210514 {
    public static void main(String args[]) {
        ArrayList list1 = new ArrayList(10);
        // ArrayList<Integer>()로 할까 했지만 밑에 문자열도 집어넣네
        // ArrayList list1 = new ArrayList();도 된다 대신 들어갈 때 객체를 잘 잡아야하지만

        list1.add(5);
        list1.add(4);
        list1.add(2);
        list1.add(0);
        list1.add(1);
        list1.add(3);

        ArrayList list2 = new ArrayList(list1.subList(1, 4));

        arrPrint(list1, list2);

        Collections.sort(list1);
        Collections.sort(list2); // Collections 클래스의 sort메서드로 정렬 (I/F가 아니라 Class다 주의)

        arrPrint(list1, list2);

        System.out.println("list1.containsAll(list2) : " + list1.containsAll(list2));
        System.out.println();

        list2.add("B");
        list2.add("C");
        list2.add("A");
        list2.add("AA");
        arrPrint(list1, list2);

        System.out.println("list1.retainAll(list2) : " + list1.retainAll(list2));
        System.out.println();

        arrPrint(list1, list2);

        for (int i = list2.size() - 1; i >= 0; i--) {
            if (list1.contains(list2.get(i))) {
                list2.remove(i); // List는 키나 값이 아니라 순서(index)로 파악되는 구조라 생각
            }
        }
        // 만약 앞에서부터 삭제를 하면 요소가 삭제될때 빈공간을 채우기 위해 데이터가 자리이동을 하므로 전체 탐색에 문제가 생긴다
        // 따라서 이런식으로 list2에서 요소를 꺼내가며 비교체크를 할때는 list2의 마지막 부분부터 꺼내봐야한다.

        arrPrint(list1, list2);

        System.out.println();
        System.out.println("======================================================");
        System.out.println();

        final int LIMIT = 10;
        String source = "0123456789abcdefghijABCDEFGHIJ!@#$%^&*()ZZZ";
        int sourceLength = source.length();

        List list = new ArrayList<String>(sourceLength / LIMIT + 10); // 기본 여유공간(10)을 넘어가므로 좀 여유있게 잡는다

        for (int i = 0; i < sourceLength; i += LIMIT) { // 10문자씩 잘라(substring) 담아냄으로 i++가 아니다
            if (i + LIMIT < sourceLength) {
                list.add(source.substring(i, i + LIMIT));
            } else {
                list.add(source.substring(i));
            }
        }
        // List와 substring을 이용해 charAt for문보다 간결함

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    static void arrPrint(ArrayList list1, ArrayList list2) {
        System.out.println("list 1 : " + list1);
        System.out.println("list 2 : " + list2);
        System.out.println();
    }
}
