//package chapter11

/*  5/17 : 주말 간 상태가 안좋은게 좀 이어졌으므로 오늘은 간단히 Vector 클래스 공부

    ArrayList도 마찬가지인 Vector의 공간처리부분

    아래 예제코드와 함께 주석함
*/

import java.util.*;

public class Study_210517 {
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>(5);

        v.add(1);
        v.add(2);
        v.add(3);
        print(v);

        v.trimToSize();
        System.out.println("====After trimToSize()====");
        print(v);
        // trimToSize는 빈공간(null)을 없애 size==capacity로 한다 >> 배열은 크기변경이 안되기에 새 배열을 생성해
        // 참조변수에 새로이 주소를 할당하고 기존 배열은 가비지컬렉터가 제거

        v.ensureCapacity(6);
        System.out.println("====After ensureCapacity(6)====");
        print(v);
        // 최소 용량를 6으로 잡는다. 만약 기존 용량이 6이상이면 아무런 영향도 없으나 아니라면 위와 마찬가지로 새 배열 인스턴스에 복사하여 새롭게
        // 주소값을 할당한다.

        v.setSize(7);
        System.out.println("====After setSize(7)====");
        print(v);
        // size는 변경시 용량의 이하라면 새 인스턴스 생성이 없으나 아니라면 기존 용량의 2배의 크기로 새 배열 인스턴스를 생성한다

        // ★★★ capacity에 변동이 생기는 경우 이런식으로 새 인스턴스 생성 + 복제가 일어나기에 성능이 매우 떨어진다.
        // ★★★ 따라서 ArrayList, Vector의 생성 추가에서는 용량 확보에 매우 신경을 미리써야한다.

        v.clear();
        System.out.println("====After clear()====");
        print(v);
        // 데이터들을 삭제하기에 size만 변경 capacity는 유지된다
    }

    public static <T> void print(Vector<T> v) {
        System.out.println(v);
        System.out.println("size : " + v.size());
        System.out.println("capacity : " + v.capacity());
    }
}
