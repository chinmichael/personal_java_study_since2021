package chapter9;

/*
java.lang > 자바 프로그래밍 기본 클래스 담음(import없이도 사용가능, 예를 들어 String, System)

Object > 모든 클래스 최고조상 / 11개 메서드만 존재 / 모든 클래스에서 멤버 사용 가능
> equals(Object obj) : 객체의 참조변수를 받아 그 값의 같고 다름을 boolean 반환 >> 즉 주소값(주소위치)을 비교 (어디 힙 공간을 차지하는가)
                     : 객체생성 시 빈 메모리에 생성 so 같은 주소(힙공간)를 갖지는 않지만 같은 주소값을 참조(힙공간값을 참조) 할 수는 있음
                     : 하단 1,2메소드 참조

                     : String.equals(Stirng)의 경우는 equals를 오버라이딩 하여 인스턴스 주소값이 아닌 문자열 값을 비교하도록 함
                     : 따라서 String str=new String("A"); String str2=new String("A"); 시 str1 != str2지만 str1.equals(str2)는 true임

> hashCode()    : 해싱(데이터관리기법)에 사용되는 해시함수 구현 : 찾고자하는 값을 입력하면 값의 저장위치인 해시코드 반환
                : Object 클래스 hashCode()는 객체 주소값을 이용해 해시코드 반환 > 두 객체는 같은 해시코드 가질 수 없음

                : String의 경우 equals때처럼 동일 문자열 내용에 같은 해시코드를 반환하도록 hashCode()가 오버라이딩 되어있음
                : System.identityHashCode(Object obj)는 객체 주소값으로 해시코드 생성 > 위 String같은 경우에도 다른 객체임 확인 가능
                : hashCode()는 Object의 자식 클래스에서 오버라이딩 가능하기에 System.identityHashCode처럼 일단 해시코드 반환하는건 맞지만 클래스에 따라 String처럼 다를 수 있음
                : System.identityHashCode와 오버라이딩 되지않은 hashCode()은 주소값을 기준으로 하기에 프로그램 실행 시마다 할당되는 메모리 주소는 다를 수 있으므로 값이 바뀜

> toString  : 인스턴스 정보 문자열 제공 (정보출력용으로 자주 씀 / System.out.print()가 안에 문자열이 아닌 경우 해당 인스턴스의 toString 반환값을 출력)
            : 오버라이딩 되지 않은 toString은 객체 클래스 이름 + @ + 16진수 해시코드를 출력함
            : 위 코드 toString() { return getClass().getName() + "@" + Integer.toHexString(hashCode())}
              >> getClass, hashCode는 Object클래스 멤버라 인스턴스 생성 없이 호출 가능

            : 예를 들어 String, Date의 경우 각각 인스턴스가 가진 문자열(String), 날짜 시간(Date)의 문자열변환을 반환한다
            : 주의할 점 : 오버라이딩 시 접근자를 public으로 해야함(Object toString이 public이기 때문)
                         >> 조상클래스 메서드를 오버라이딩 시 접근제어자 범위가 같거나 더 넓어야 함

> clone : 자신을 복제하여 새로운 인스턴스 생성 (쉽게 말해 백업용)
        : Object의 clone은 단순히 인스턴스변수 값만 복제 so 참조타입 인스턴스 변수 복제는 완전하지 않음
          오히려 참조변수 주소값을 복사해버려 복제 인스턴스 작업이 원본 인스턴스에 영향을 줌 >> 오버라이딩 거의 필수 (새 배열 생성 + 배열내용 복사)
        : clone을 사용하려면 > Cloneable I/F를 implements(데이터보호차원)한 클래스에서 오버라이딩 + 접근제어자 protected > public(상속관계없는 곳에서도 호출) + 예외처리를 해줘야 함
        : 오버라이딩 방법은 하단 CloneTest class를 참조
        
        : 공변반환타입 : 오버라이딩 시 조상메서드 반환타입을 자손 클래스 타입으로 변경 허용
        : 아래 CloneTest 클래스에서 Object ~ { return obj를 >> CloneTest ~ { return (CloneTest) obj로 하여 메서드 호출 시 번거로운 형변환 줄일 수 있음

        : 배열도 객체 + 이미 Cloneable과 Serializable I/F가 구현되어 있으며 + clone이 public으로 오버라이딩 되었고 + 원본과 같은 타입으로 반환 됨
        : 따라서 System.arraycopy를 간단히 clone()으로 처리 가능
        : Vector, ArrayList, LinkedList, HashSet, TreeSet, HashMap, TreeMap, Date, Calendar도 이렇게 복제가 편히 되게 세팅되어있음

        : 얕은 복사 : clone()단순히 객체의 저장 값 복사 (참조하는 객체를 복사하는게 아님) > 단순배열은 ㄱㅊ but 객체배열은 객체 주소값들 복사 > 원본에 영향
                     > 객체 복사의 경우 같은 주소값을 참조하게 되어 원본, 복사본이 서로 영향을 주는 복사
        : 깊은 복사 : 원본이 참조하는 객체까지 복사 > 원본, 복사본이 서로 다른 객체 주소값 참조 > 서로 영향 없음
                     > 하단 CloneTest2 class의 swallow와 deep 참조 : 복제된 객체가 참조하는 객체를 새롭게 초기화하여 주소값을 달리하고 거기에 값을 복제해놓음
 */

import java.util.Arrays;
import java.util.Scanner;

public class Study_210408 {
    public static void main(String args[]) {
        System.out.println("1. checkEquals");
        System.out.println("2. checkOverrideEquals");
        System.out.println("3. checkHashCode");
        System.out.println("4. checkClone");
        System.out.println("5. checkArrayClone");
        System.out.println("6. checkClone2");
        System.out.print("실행할 메소드 번호 선택 : ");
        Scanner sc = new Scanner(System.in);

        Study_210408 study = new Study_210408();

        switch (sc.nextInt()) {
            case 1:
                study.checkEquals();
                break;
            case 2:
                study.checkOverrideEquals();
                break;
            case 3:
                study.checkHashCode();
                break;
            case 4:
                study.checkClone();
                break;
            case 5:
                study.checkArrayClone();
                break;
            case 6:
                study.checkClone2();
                break;
        }
    }

    public void checkEquals() {
        Value v1 = new Value(10); Value v2 = new Value(10);
        System.out.println("v1.value = " + v1.value); System.out.println("v2.value = " + v2.value);
        if(v1.equals(v2)) System.out.println("v1과 v2는 같은 힙 공간 값(주소값, 주소위치)을 가리킵니다.");
        else System.out.println("v1과 v2는 별개의 힙 공간 값(주소값, 주소위치)을 가리킵니다.");
        v2 = v1;
        System.out.println("v2 참조변수에 v1 참조변수 값 대입 : v2 = v1; v2가 가리키던 할당 메모리는 JVM이 냠냠");
        if(v1.equals(v2)) System.out.println("v1과 v2는 같은 힙 공간 값(주소값, 주소위치)을 가리킵니다.");
        else System.out.println("v1과 v2는 별개의 힙 공간 값(주소값, 주소위치)을 가리킵니다.");

    }

    public void checkOverrideEquals() {
        System.out.println("초기화시 파라미터에 등록한 멤버변수의 value 비교");
        ValueContent v1 = new ValueContent(10); ValueContent v2 = new ValueContent(10);
        if(v1.equals(v2)) System.out.println("두 참조변수가 가리키는 객체의 멤버변수 value 값은 동일");
        else System.out.println("두 참조변수가 가리키는 객체의 멤버변수 value 값은 다름");
        if(v1 == v2) System.out.println("v1 == v2 참조변수, 주소 값은 같음");
        else System.out.println("v1 != v2 참조변수, 주소 값은 다름");
    }

    public void checkHashCode() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        String str1 = new String("A");
        String str2 = new String("A");

        System.out.println("obj1의 hashCode()반환 값 : " + obj1.hashCode());
        System.out.println("obj2의 hashCode()반환 값 : " + obj2.hashCode());
        System.out.println("str1의 hashCode()반환 값 : " + str1.hashCode());
        System.out.println("str2의 hashCode()반환 값 : " + str2.hashCode());
        System.out.println();
        System.out.println("obj1의 System.identityHashCode 반환 값 : " + System.identityHashCode(obj1));
        System.out.println("obj2의 System.identityHashCode 반환 값 : " + System.identityHashCode(obj2));
        System.out.println("str1의 System.identityHashCode 반환 값 : " + System.identityHashCode(str1));
        System.out.println("str2의 System.identityHashCode 반환 값 : " + System.identityHashCode(str2));
    }

    public void checkClone() {
        CloneTest origin = new CloneTest(3, 5);
        CloneTest clone = (CloneTest) origin.clone(); // 공변반환타입을 해놨을 시 형변환 필요없음

        System.out.println("원본 인스턴스 toString : " + origin);
        System.out.println("복제 인스턴스 toString : " + clone);

    }

    public void checkArrayClone() {
        int[] arr = {1,2,3,4,5};
        int[] arrClone1 = arr.clone();

        int[] arrClone2 = new int[arr.length];
        System.arraycopy(arr, 0, arrClone2,0, arr.length);

        System.out.println("원본 배열 : " + Arrays.toString(arr));
        System.out.println("clone() : " + Arrays.toString(arrClone1));
        System.out.println("System.arraycopy : " + Arrays.toString(arrClone2));
        arrClone1[0] = 6;
        System.out.println("clone()의 첫째칸 숫자 6변경 : " + Arrays.toString(arrClone1));
    }

    public void checkClone2() {
        CloneTest2 origin = new CloneTest2(new Point(5, 10), 15);
        CloneTest2 swallowClone = origin.shallowClone();
        CloneTest2 deepClone = origin.deepClone();

        System.out.println("origin : " + origin);
        System.out.println("sallowClone : " + swallowClone);
        System.out.println("deepClone : " + deepClone);
        System.out.println();

        origin.p.x = 1; origin.p.y = 1;
        System.out.println("origin만 변경후 origin값 : "+origin);
        System.out.println("origin만 변경후 swallow값 : "+swallowClone);
        System.out.println("origin만 변경후 deep값 : "+deepClone);

    }
}

class Value {
    int value;
    Value(int value) {
        this.value = value;
    }
}

class ValueContent {
    int value;
    ValueContent (int value) {this.value = value;}
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueContent) return value == ((ValueContent)obj).value; // ValueContent의 정상비교를 위해 instanceof로 체크 후 형변환
        else return false;
    }
}

class CloneTest implements Cloneable {
    int x, y;

    CloneTest (int x, int y) { this.x = x; this.y = y; }

    public String toString() { return "(x = " + x + ", y = " + y + " )"; }

    public Object clone() { // 공변 반환타입 CloneTest
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj; // 공변 반환타입 return (CloneTest) obj; 가능
    }
}

class CloneTest2 implements Cloneable {
    Point p;
    int z;

    CloneTest2 (Point p, int z) { this.p = p; this.z =z; }
    public String toString() {
        return ("Point = " + p + ", 멤버 z = " + z);
    }

    public CloneTest2 shallowClone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (CloneTest2) obj;
    }

    public CloneTest2 deepClone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } // try-catch를 통해 object클래스의 clone메서드를 이용해 클론을 해준뒤

        CloneTest2 clone = (CloneTest2) obj; // 복제된 객체를 사용할 수 있도록 참조변수명을 만들어주고
        clone.p = new Point(this.p.x, this.p.y); // 그 참조변수에 들어간 복제객체가 참조하는 객체를 새로 초기화 한뒤 복제한 값을 넣어준다.

        return clone;
    }
}

class Point {
    int x; int y;
    Point(int x, int y) { this.x = x; this.y = y;}
    public String toString() { return "Point x = " + x + ", Point y = " + y; }
}
