//package chpater11;

/*  Stack, Queue

    1. 자료구조 Stack, Queue 기본개념

    1-1. Stack  : 마지막에 저장한(push) 데이터를 가장 먼저 꺼내는(pop) LIFO >> 한쪽이 막힌 통 >> 넣은 순서와 꺼낸 순서가 뒤집힘

         Queue  : 처음에 저장한(offer) 데이터를 가장 먼저 꺼내는(poll) FIFO >> 파이프 구조


    1-2. Stack 구현 기반 : 순차 저장 및 (역)순차 삭제(배열 후미부터) 되므로 배열 기반 컬렉션클래스 구현이 적합
    
         Queue 구현 기반 : 처음 저장된 부분부터 삭제되므로 배열기반에서는 계속 빈공간을 채우기 위한 복사가 발생 >> LinkedList 등이 적합

    
    2. Java Stack, Queue 구현 및 메서드

    2-1. Stack : Vector를 상속받아(이미 예전부터 존재하는 자료구조) Stack 클래스가 구현 됨

    - boolean empty() : Stack이 비어있는지

    - T peek() : 맨 위(마지막 저장) 객체 반환 / pop과 달리 객체 삭제가 이뤄지지 않음 / 비었을 때는 EmptyStackException

    - T pop() : 맨 위 객체 '꺼냄' (스택에서는 삭제됨) / 비었을 때는 마찬가지로 EmptyStackException

    - T push(T item) : Stack에 객체 저장

    - int search(T t) : 지정된 객체 탐색해 위치 반환 (없으면 -1 반환 / 시작지점은 0이 아닌 1이다)


    2-2. Queue : 클래스로 구현된 Stack과 달리 Queue는 I/F로만 정의됨 >> API문서에서 All Known Implementing Class 항목에 적절한 구현 클래스를 선택해 쓰자
               : 사용은 Queue q = new LinkedList(); 와 같은 식으로 사용

    - boolean offer(T t) : 큐에 객체 저장

    - boolean add(T t) : 큐에 객체 저장 / 저장공간이 부족하면 IllegalStateException

    - T peek() : 처음 저장된 객체 반환 / Queue에서 삭제는 없음 / 비었을 때 null 반환

    - T element() : 처음 저장된 객체 반환 / peek과 달리 비었을 때는 NoSuchElementException

    - T poll() : 처음 저장된 객체 '꺼냄' / Queue에서는 해당 객체 삭제 / 비었을 때는 null 반환

    - T remove() : Queue에서 객체를 '꺼냄' / Queue에서는 해당 객체 삭제 / 비었을 때는 NoSuchException


    3. Stack, Queue 활용 예 (실제 프로그램에서 자주 사용되는 자료구조임)

    - Stack : undo/redo (워드나 포토샵 등) | 웹브라우저 뒤로/앞으로 | 괄호검사 | 수식 계산 등

    - Queue : 대기목록(최근 작업 문서 등도) | Buffer


    4. PriorityQueue (우선순위 큐)

    >> 저장순서에 상관없이 우선순위가 높은 데이터부터 추출함
    >> Integer와 같이 Number의 자손 클래스들은 자체적인 비교방법 정의 됨 / 그 밖의 객체들의 경우 비교방법을 제공해야한다

    >> null 저장시 NullPointException 발생

    >> Heap 자료구조(JVM의 heap 당근 다름)로 이뤄짐 | PriorityQueue 참조변수 출력시 나오는 배열내용이 저장순서가 다른 이유도 이것때문


    ※ Heap : 그래프의 이진트리(최대자손노드(트리구조의 각 정점/데이터가 저장됨)가 2개씩)구조 중 하나로 루트 노드에 최솟값이 저장됨 >> 우선순위 큐 구현에 사용

    - 노드 생성 순서 : 루트노드는 최솟값이며 + 위에서부터 + 왼쪽에서부터 채워짐 + 자식노드의 데이터는 반드시 부모보다 큰 수여야 한다

    - 노드 추가 시 : 위 생성순서의 가장 마지막부분에 우선 노드를 추가하여 >> for(부모와 비교해 자식이 작으면 자리를 교체)로 반복
    
    - 노드 추출 시 : 가장 위의 최솟값부터 빠진 뒤 >> 가장 마지막에 추가된 노드를 루트노드로 한 뒤 >> for(부모와 비교해 자식이 작으면 자리교체 + 양쪽 중 더 작은쪽과 교체)를 반복

    >> 데이터가 n개인 Heap의 소요시간은 최솟값 추출에는 O(1)의 시간 (당연 맨 위만 빼니까) | 노드 추가에 O(log2 n)(트리 깊이) | 추출후 트리 재정렬에 O(log2 n)
    
    
    5. Deque(Double Ended Queue)

    >> Queue의 변형 >> 한쪽은 추가 한쪽은 삭제만 가능한 파이프 구조에서 양쪽에서 추가 및 삭제가 가능한 구조
    >> 조상은 Queue이며 구현은 ArrayDeque와 LinkedList

    >> Stack과 Queue를 합친 것과 같다
    - offerLast() = 큐 offer() | 스택 push()

    - pollFirst() = 큐 poll()

    - pollLast() = 스택 pop()

    - peekFirst() = 큐 peek()

    - peekLast() = 스택 peek()
*/

import java.util.*;

public class Study_210519 {
    public static void main(String[] args) {

        Stack<Integer> st1 = new Stack<>();
        Queue<Integer> q1 = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            st1.push(i);
            q1.offer(i);
        }

        System.out.println("====Stack====");
        while (!st1.empty()) {
            System.out.println(st1.pop());
        }

        System.out.println();
        System.out.println("====Queue====");
        while (!q1.isEmpty()) { // LinkedList니 당연 있겠지
            System.out.println(q1.poll());
        }

        System.out.println();

        System.out.println("==============");
        System.out.println();

        goUrl("nate.com");
        goUrl("yahoo.com");
        goUrl("naver.com");
        goUrl("daum.net");

        printStatus();

        System.out.println("==뒤로가기 버튼 클릭==");
        goBack();
        printStatus();

        System.out.println("==뒤로가기 버튼 클릭==");
        goBack();
        printStatus();

        System.out.println("==앞으로 버튼 클릭==");
        goForward();
        printStatus();

        System.out.println("==새 주소 이동==");
        goUrl("google.com");
        printStatus();

        System.out.println("==============");
        System.out.println();

        Queue<Integer> pq = new PriorityQueue<Integer>();

        int[] pqInput = { 3, 1, 5, 2, 4 };
        for (int i = 0; i < pqInput.length; i++) {
            pq.offer(pqInput[i]);
        }
        System.out.println(pq);

        Object obj = null;
        while ((obj = pq.poll()) != null) {
            System.out.println(obj);
        }

        System.out.println("==============");
        System.out.println();

        System.out.println("help를 입력하면 도움말을 볼 수 있습니다");

        while (true) { // for(;;)
            System.out.print(">> ");

            try {
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine().trim();

                if ("".equals(input))
                    continue;

                if (input.equalsIgnoreCase("q")) {
                    sc.close();
                    System.exit(0); // equalsIgnoreCase : 대소문자 관계없이 체크

                } else if (input.equalsIgnoreCase("help")) {
                    System.out.println("help : 도움말을 보여줍니다");
                    System.out.println("q : 프로그램을 종료합니다");
                    System.out.println("history : 최근 입력한 명령어를 " + MAX_SIZE + "개 보여줍니다");

                } else if (input.equalsIgnoreCase("history")) {
                    int i = 0;
                    save(input);

                    LinkedList<String> tmp = (LinkedList<String>) historyBox;
                    ListIterator<String> iterator = tmp.listIterator();

                    while (iterator.hasNext()) {
                        System.out.println(++i + ". " + iterator.next());
                    }

                } else {
                    save(input);
                    System.out.println(input);
                }

            } catch (Exception e) {
                System.out.println("입력오류입니다");
            }
        }
    }

    // 두개의 스택을 구현해 뒤로가면 그 링크가 pop되어 다른 한 쪽으로 push
    public static Stack<String> back = new Stack<>();
    public static Stack<String> forward = new Stack<>();

    public static void goUrl(String url) {
        back.push(url); // back의 최상단 url은 현재페이지라고 생각 (history가 아님)
        if (!forward.empty()) {
            forward.clear();
        }
    }

    public static void goForward() {
        if (!forward.empty()) {
            back.push(forward.pop());
        }
    }

    public static void goBack() {
        if (!back.empty()) {
            forward.push(back.pop());
        }
    }

    public static void printStatus() {
        System.out.println("back : " + back);
        System.out.println("forward : " + forward);
        System.out.println("current page : " + back.peek());
        System.out.println();
    }

    /////////////////////////////////////////////////////////

    static Queue<String> historyBox = new LinkedList<>();
    static final int MAX_SIZE = 5;

    public static void save(String input) {
        if (!"".equals(input)) {
            historyBox.offer(input);
        }
        if (historyBox.size() > MAX_SIZE) { // history 목록 최대치를 넘으면 오래된 것부터 삭제
            historyBox.remove();
        }
    }

}
