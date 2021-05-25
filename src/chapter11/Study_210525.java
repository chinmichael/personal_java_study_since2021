//package chapter11;

/*  1. Comparable & Comparator

    >> 컬렉션을 정렬하는데 필요한 메서드를 정의한 인터페이스

    >> 이전 Arrays.sort()의 경우에도 클래스에 구현된 위 두 I/F의 메서드를 이용한 것

    1-1. Comparable
    보통 같은 타입의 인스턴스끼리 비교 가능한 클래스들(wrapper클래스/String/Date/File 등)에 구현된 I/F >> 이 구현 클래스들이 기본적으로 정렬 기능 사용이 가능한 이유
    기본적으로 오름차순으로 정렬되어 있음

    위와 같은 Comparable 구현 클래스들의 기본 정렬기준 이용할 때 사용한다

    1-2. Comparator
    위 Comparable이 정의되어 있지 않거나
    새로운 사용자 정의 정렬 기준을 사용하고자 할때 구현 클래스를 정의하여 사용한다 (아래 예제 참조)


    2. Comparable (compareTo) & Comparator (compare) 메서드

    >> 다음과 같이 정의되어 있음
       
       public interface Comparable {
           public int compareTo(Object o);
       }
       
       public interface Comparator {
           int compare(Object o1, Object o2);
           
           boolean equals(Object o);
       }

    >> compare과 compareTo의 반환타입이 둘다 int인 것은
       비교하는 값보다 크면 1 / 같으면 0 / 작으면 -1 을 반환하기 때문

    >> Comparator는 기본적으로 compare() 추상메서드만 구현하며 되며,
       equals()의 경우 Object에 유래된 공통 메서드이기에 필요한 경우만 오버라이딩하면 된다.

       compare(Object o1, Object o2)의 구현에서 참조변수의 클래스 타입이
       Comparable을 구현한 경우는 이들의 compareTo를 사용하면 좋은 경우가 있으므로
       o1 instanceof Comparable 을 통해 구현된 클래스 타입인지(형변환이 가능한지) 확인 후 사용하면 좋다 (예제 참조)


    ex Integer 클래스 Comparable 구현
       public final class Integer extends Number implements Comparable {
           ...
           public int compareTo(Object o) { // 오버라이딩은 매개변수 형변환을 해서 오버로딩한 메서드로 전달만
               return compareTo((Integer) o);
           }
           public int compareTo(Integer anotherInteger) { // 오버로딩해서 구현
                int thisVla = this.value;
                int anotherVal = anotherInteger.value;

                return (thisVal<anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
           }
           ...
       }


       3. Arrays.sort()

       3-1. static void sort(Object[] a) | static void sort(Object[] a, Comparator c)
       >> 두번째 파라미터로 사용자 정의 정렬 기준을 사용가능 (없으면 배열 객체에 구현된 기준을 가지고)

       3-2. 참고로 문자열 배열의 경우 기본적으로 Comparable이 사전순으로 되어 있다
       >> 정확히는 오름차순 정렬이 공백 > 숫자 > 대문자 > 소문자 순으로 되어있는데 이는 문자의 유니코드의 오름차순이다

       >> 이때 두번째 파라미터로 String 클래스에 정의된 Comparator 상수인 CASE_INSENSITIVE_ORDER을 사용하면 (String.CASE_INSENSITIVE_ORDER)
          대소문자를 구분하지 않고 정렬이 가능하다.
    
*/

import java.util.*;

public class Study_210525 {
    public static void main(String[] args) {

        String[] strArr = { "cat", "Dog", "lion", "tiger" };

        Arrays.sort(strArr);
        System.out.println("strArr : " + Arrays.toString(strArr));
        System.out.println();

        Arrays.sort(strArr, String.CASE_INSENSITIVE_ORDER);
        System.out.println("strArr : " + Arrays.toString(strArr));
        System.out.println();

        Arrays.sort(strArr, new Descending()); // Comparator 객체를 생성해 넣는다
        System.out.println("strArr : " + Arrays.toString(strArr));

    }
}

class Descending<T> implements Comparator<T> { // Comparator 구현 클래스

    @Override
    public int compare(T o1, T o2) {

        if (o1 instanceof Comparable) { // instanceof는 지네릭 못쓰니...
            Comparable<T> c1 = (Comparable<T>) o1;

            return c1.compareTo(o2) * -1; // == c2.compareTo(c1)
                                          // 클래스를 들어가보면 Comparable<T>의 compare(T o)로 되어있다
        }

        return -1;
    }

    // @Override
    // public int compare(Object o1, Object o2) {

    // if (o1 instanceof Comparable && o2 instanceof Comparable) {
    // Comparable c1 = (Comparable) o1;
    // Comparable c2 = (Comparable) o2;

    // return c1.compareTo(c2) * -1; // == c2.compareTo(c1)
    // }

    // return -1;
    // }

    // 이렇게 내림차순으로 한다는 등의 기존 Comparable을 이용하는 경우는 instanceof로 파라미터의 객체가 Comparable을
    // 구현한 클래스 타입인지(형변환이 가능한지) 체크한 후 compareTo를 이용 가능 >> compareTo * -1를 한다거나 비교 순서를
    // 바꾸면 결과가 반전될테니

}
