//package chpater11;

/*  LinkedList

    1. 배열(ArrayList는 내부 Object[] 이용)의 장단점
    
    1-1. 장점 : 자료구조가 간단함 / 사용이 쉬움 / 접근시간(데이터를 읽어오는 시간)이 가장 빠름

    1-2. 단점
    >> 크기변경 불가 : 용량 변경 시 새 배열 생성 후 복사해야함(처리성능 하락) if 이를 방지하기 위해 용량을 확보하면 메모리가 낭비
    >> 비순차적 데이터 추가 삭제(배열에 중간에 추가 혹은 중간 데이터 삭제) : 빈자리를 만들거나 빈자리를 땡기는데 복사가 이뤄짐 >> 성능 하락 (대신 순차적인 데이터 추가 or 삭제는 빠르다)


    2. LinkedList : 위의 배열 자료구조의 단점을 해결하기 위한 자료구조
    >> 배열처럼 순차적인 자료구조가 아닌 불연속적인 데이터들을 서로 link시킴

    by LinkedList의 각 요소(node)의 구성을 이용 : Node next;(다음 노드의 주소 저장) + Object obj;(노드의 데이터 저장)

    >> 이런 노드 자체에 다음 참조주소를 저장해 연결하는 방식을 취하여 중간 데이터의 삭제와 수정이 참조주소 변경만으로 끝나기에 처리속도가 빠름
    - 데이터 삭제 : 삭제하고자 하는 노드의 이전 노드의 참조주소를 > 삭제하고자 하는 노드의 다음 주소로 변경 / 연결이 끊긴 노드는 가비지 컬렉터가 알아서 날림
    - 데이터 수정 : 추가하고자 하는 위치의 이전 노드의 참조주소를 > 추가하고자 하는 노드의 주소로 변경 | + | 추가하는 노드의 참조주소를 다음 위치의 노드주소로 세팅

    2-1. LinkedList 종류
    
    - linked list : 위의 기본 linked list : 노드의 참조주소가 다음 노드로만 연결 >> 단방향 이동만 가능

    - doubly linked list : 위 단점을 해결하기 위해 노드에 이전 노드 참조주소를 추가 >> 양방향 이동 가능 (Node next; Node previous; Object obj;)
                         : 각 요소 접근, 이동이 수월하여 더 많이 사용 (대신 데이터 삭제 수정에서 참조주소 변경이 2배로(next랑 previous 동시 수정) 이뤄져야함 그래도 배열복사보다 낫다)
                         : LinkedList는 실제로 접근성을 높이기 위해 doubly linked list 채용

    - doubly circular linked list : 위 더블 링크드리스트에서 처음과 마지막 노드를 연결해 순환이 가능하도록 함


    3. ArrayList와 LinkedList 상황에 따른 성능 차이 (데이터 양이 적을때는 미묘하지만 커질수록 명확해진다)

    3-1. 추가 삭제 성능차이
    >> 순차적인 추가 / 삭제의 경우 (ArrayList의 용량이 충분하다는(새 인스턴스 생성 후 복제 발생X) 조건 하) >> ArrayList가 빠름 (대신 용량확보문제로 메모리 낭비가 생길 수 있음)
                    (순차삭제는 뒤에서부터 역순으로 삭제한다는것 뒤에서부터 걍 null화)

    <> 역으로 중간 데이터 추가 / 삭제는 당연 LinkedList(그걸 위해 생긴 자료구조) : LinkedList는 앞뒤 요소간 연결(참조주소)만 조정해주면 됨


    3-2. 접근시간 성능차이
    >> 접근시간(조회 속도) : ArrayList와 같은 배열이 빠름
    >> 배열의 경우 각 요소가 메모리에 연속적으로 존재 : 인덱스가 n인 데이터 주소 = 배열 주소 + n * 데이터타입크기 의 수식으로 간단하게 접근 가능
    <> LinkedList는 불연속적으로 존재하기에 순서대로 연결된 요소들을 읽어가야함 >> 데이터가 많아질수록 접근시간이 매우 느려진다

    3-3. 데이터 변경이 잦은 경우 LinkedList / 변경은 적은대신 조회가 잦은경우 ArrayList
         혹은 데이터 저장시 ArrayList 수정 삭제가 필요할때 작업처리는 LinkedList로 한뒤 다시 ArrayList저장
         >>컬렉션 클래스들은 생성자로 Collection c매개변수가 가능해 서로 변환이 대체로 간편하다 : ArrayList al = ~; LinkedList ll = new LinkedList(al);


    4. LinkedList 메서드 목록 (Collection과 List I/F의 메서드는 제외함 >> 후에 추가된 Queue와 Deque I/F 구현 메서드들이 추가됨)

    - LinkedList() / LinkedList(Collection c)


    >> Queue(Stack과 반대의 FIFO:선입선출 : 양쪽이 뚫린 파이프 구조 한쪽은 input(offer)만 반대쪽은 out(poll)만 가능한 구조) I/F 구현 메서드

    - T element() : 첫번째 요소 반환

    - T peek() : 위와 동일

    - T poll() : 첫번째 요소 반환 후 + 리스트에서 해당요소 제거

    - T remove() : 첫번째 요소 제거 + 해당 요소 반환.... 흠 실질적으로 poll이나 마찬가지인가...

    - boolean offer(T t) : 지정된 객체를 끝에 추가



    >> Deque(Queue와 같이 통로구조나 양쪽 입구에서 offer와 poll이 각각 가능 >> First Last로 메서드 구분) I/F 구현 메서드

    - T getFirst() / T getLast() : 첫번째/마지막 요소 반환

    - T peekFirst() / T peekLast() : 위와 동일

    - T pollFirst() / T pollLast() : 첫번째/마지막 요소 반환 + 리스트에서 제거

    - T removeFirst() / T removeLast() : 첫번째/마지막 요소 제거 + 성공하면 해당요소 반환...

    - T pop() = removeFirst()

    - void addFirst() / void addLast() : 리스트 맨앞/맨뒤에 객체 추가

    - boolean offerFirst(T t) / boolean offerLast(T t) : 리스트 맨앞/맨뒤에 객체 추가 + 성공여부 알림

    - void push(T t) = addFirst()


    - boolean removeFirstOccurrence(T t) : 리스트에서 첫번째로 일치하는 요소 제거

    - boolean removeLastOccurrence(T t) : 리스트에서 마지막으로 일치하는 요소 제거(뒤에서부터 첫번째)

    - Iterator descendingIterator() : 역순 조회를 위한 Iteraor 반환
*/

import java.util.*;

public class Study_210518 {
    public static void main(String[] args) {

        ArrayList al = new ArrayList(2000000); // 순차추가 삭제 비교를 위해 용량은 넉넉히
        LinkedList ll = new LinkedList();

        System.out.println("====순차 추가====");
        System.out.println("ArrayList : " + add1(al));
        System.out.println("LinkedList : " + add1(ll));
        System.out.println();

        System.out.println("====중간 추가====");
        System.out.println("ArrayList : " + add2(al));
        System.out.println("LinkedList : " + add2(ll));
        System.out.println();

        System.out.println("====중간 삭제====");
        System.out.println("ArrayList : " + remove2(al));
        System.out.println("LinkedList : " + remove2(ll));
        System.out.println();

        System.out.println("====순차 삭제====");
        System.out.println("ArrayList : " + remove1(al));
        System.out.println("LinkedList : " + remove1(ll));
        System.out.println();

        ArrayList<Integer> al2 = new ArrayList<>(1000000);
        LinkedList<Integer> ll2 = new LinkedList<>();

        add1(al2);
        add1(ll2);

        System.out.println("====접근시간====");
        System.out.println("ArrayList : " + access(al2));
        System.out.println("LinkedList : " + access(ll2));
        System.out.println();

    }

    public static long add1(List list) {
        long start = System.currentTimeMillis(); // 현재 시간을 밀리초 단위로
        for (int i = 0; i < 1000000; i++) {
            list.add(i + "");
        }
        long end = System.currentTimeMillis();

        return end - start; // 중간 반복문 끝난 후 - 반복문 시작하기 전
    }

    public static long add2(List list) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(500, "X");
        }
        long end = System.currentTimeMillis();

        return end - start;
    }

    public static long remove1(List list) {
        long start = System.currentTimeMillis();
        for (int i = list.size() - 1; i >= 0; i--) {
            list.remove(i); // 뒤에서부터 삭제해나감
        }
        long end = System.currentTimeMillis();

        return end - start;
    }

    public static long remove2(List list) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.remove(i); // 앞에서부터 삭제 >> 배열은 계속 땡기기 발생
        }
        long end = System.currentTimeMillis();

        return end - start;
    }

    public static long access(List list) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.get(i);
        }
        long end = System.currentTimeMillis();

        return end - start;
    }

}
