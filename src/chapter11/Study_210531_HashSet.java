// package chapter11

/*  5/31 : 공부 방식을 좀 더 달리할 예정
    기존 자바의 정석 요약 + 개인적인 설명이었지만 조금 더 파트의 핵심내용 위주로 가능한 뽑아볼까 함

    HashSet : Set I/F의 대표적인 컬렉션
            : Set이기에 중복 저장이 안되며 (add(), addAll()에서 false반환) 순서가 없음(저장순서도 유지하지 않음)('저장순서' '유지'가 필요하면 LinkedHashSet)
            : 정확히는 저장순서를 보장 않고 자체적인 저장방식으로 순서처리 (물론 순서 유지는 안되나 반복실행시 같은 데이터가 비슷한 위치에 갈 수는 있음)
            : 내부적으로 HashMap을 이용해 만들어진 >> Hashing 기법 사용해 중복 확인

    Hashing : 데이터를 고정길이의 불규칙한 값으로 바꿈
            : 일방향적이며 / 같은 데이터에는 항상 같은 결과 / 결과값으로 데이터 추론이 불가
            : 딸기주스를 다시 딸기로 만들지 못함 / 딸기를 믹서기에 넣음 항상 딸기주스가 나옴 / 딸기주스를 보고 딸기가 어떻게 생겼는지 모름

    HashSet의 중복처리
    1. 중복으로 간주한 데이터는 저장되지 않기에 중복저장을 막는 자료구조를 만들 때 유효
    2. 들어간 객체의 타입이 다르면 ("11", 11)과 같은 경우 서로 다른 객체로 판별해 저장 가능

    3. 중요한 것은 사용자 데이터 타입을 만들 때 중복확인을 위해 해당 객체의 equals()와 hashCode() 메서드를 오버라이딩하여 HashSet이 중복 판별을 가능하게 해야함
        > 특히 Object의 hashCode()는 객체의 주소값을 대상으로 해시값을 반환하기에 해싱을 이용해 객체판별을 하는경우 대부분 오버라이딩이 필요

        > hashCode()를 오버라이딩하는 간단한 방법은
          String클래스의 hashCode()를 이용하여 파라미터인 String 데이터 값으로 판별시키거나
          JDK 1.8이후의 Objects.hash(파라미터, [파라미터], ...)를 사용하는 것

          java.util.Objects의 hash()는 내부적으로 파라미터들을 배열처리하여
          Arrays.hashCode()를 사용한다.
          Arrays.hashCode()는 배열의 객체 내용들을 가지고 해쉬값을 반환하지만, 다차원 배열일 경우 중간에 데이터값이 아니라 주소값이 껴들어버리는 문제가 있음
          이때는 재귀적으로 내용을 파악하는 Arrays.deepHashCode()를 사용하자

    
    3-1. 오버라이딩된 equals()와 hashCode()는 다음 조건을 만족해야함

        > 실행 시마다 동일할 필요는 없으나 실행중일 동안은 같은 멤버변수에 대해 호출된 hashCode()의 반환값이 같아야함 (당연)

        > equals()로 true를 반환받는 두 객체는 hashCode()의 결과 값이 같아야함

        > equals()로 false를 반환받는 경우 같은 hashCode() 결과를 반환해도 괜찮지만 / 가능한 다른 결과를 반환해야함 >> hashing을 이용하는 컬렉션의 검색속도 향상을 위해(해시값 중복이 생길 수록 떨어짐)

    
    HashSet 메서드 (Collection I/F 중복 케이스 제외 + 집합관계 3개는 포함)

    - HashSet() / HashSet(Collection c) / HashSet(int capacity) / HashSet(int capacity, float loadFactor)
      > loadFactor은 저장공간 차기전에 미리 용량 확보 기준 (0.8은 80% / 기본값 0.75)

    - boolean addAll(Collection c) 두 HashSet에 대해 합집합 형성 가능

    - boolean removeAll(Collection c) 차집합 형성 가능

    - boolean retainAll(Collection c) 교집합 형성 가능

    - Object clone() : 얕은 복사이다 > 즉 객체의 경우 주소값을 복사해옴
*/

import java.util.*;

public class Study_210531_HashSet {
    public static void main(String[] args) {
        Object[] objArr = { "5", "1", new Integer(1), "2", "2" };
        Set set1 = new HashSet();

        for (int i = 0; i < objArr.length; i++) {
            set1.add(objArr[i]);
        }

        System.out.println(set1);

        System.out.println();
        System.out.println("=====================");
        System.out.println();

        Set<Integer> set2 = new HashSet<>();

        for (int i = 0; set2.size() < 6; i++) {
            int num = (int) ((Math.random() * 45) + 1);
            set2.add(num);
        }

        Iterator it = set2.iterator();

        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        List<Integer> list = new LinkedList<>(set2);
        Collections.sort(list);
        // Collection I/F가 아닌 Collections 클래스의 sort() static 메서드
        // 위 메서드는 List타입을 파라미터로 받음
        // 위 경우는 Integer 클래스의 정렬 기준이 사용됨

        System.out.println(list);

        System.out.println();
        System.out.println("=====================");
        System.out.println();

        HashSet<Person1> set3 = new HashSet<>();
        HashSet<Person2> set4 = new HashSet<>();

        set3.add(new Person1("David", 10));
        set3.add(new Person1("David", 10));
        set4.add(new Person2("David", 10));
        set4.add(new Person2("David", 10));

        System.out.println("Person1 HashSet : " + set3);
        System.out.println("Person2 HashSet : " + set4);
    }
}

class Person1 {

    private String name;
    private int age;

    Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return name + " : " + age;
    }
}

class Person2 {

    private String name;
    private int age;

    Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return name + " : " + age;
    }

    // HashSet 중복확인 처리를 위한 equals(), hashCode() 오버라이딩

    public boolean equals(Object obj) {
        if (obj instanceof Person2) {
            Person2 tmp = (Person2) obj;

            return name.equals(tmp.getName()) && age == tmp.getAge();
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(name, age); // String의 hashCode 사용시 return (name + age).hashCode();
    }
}