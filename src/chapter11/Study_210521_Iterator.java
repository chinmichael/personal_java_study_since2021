//package chapter11;

import java.util.*;

public class Study_210521_Iterator extends Study_210517_Vector implements Iterator {

    int cursor = 0; // 앞으로 읽을 요소의 위치 저장
    int lastRet = -1; // 마지막으로 읽은 요소의 위치 저장

    public Iterator iterator() { // 불러들일때마다 커서를 초기화해서 반환해줌
        cursor = 0;
        lastRet = -1;
        return this;
    }

    @Override
    public boolean hasNext() {
        return cursor != size(); // 커서가 마지막 size(마지막 인덱스 + 1)인지
    }

    @Override
    public Object next() {
        Object next = get(cursor);
        lastRet = cursor++; // ++cursor가 아닌 cursor++이므로 lastRet에서 기존 cursor위치를 넣고 cursor+1
        return next;
    }

    @Override
    public void remove() { // 선택적 기능이기에 바로 fix implements로 띄우진 않는다
        if (lastRet == -1) {
            throw new IllegalStateException();
            // cursor는 다음에 읽을 요소의 위치임 즉 실제 next()로 읽은 뒤 > 그 요소의 위치는 lastRet에 저장됨
            // lastRet == -1 현재 읽었던 요소위치가 없는 것이므로 == 삭제할 것이 없는 것이므로 예외 발생

        } else {
            remove(lastRet); // 이건 상속한 Vector클래스의 remove
            cursor--;
            // Vector의 remove는 배열기반이기 때문에 remove후 다음요소들이 앞으로 땡겨진다
            // >> 다음 읽을 요소를 가리키는 cursor도 당연 -1로 땡겨져야 함
            lastRet = -1;
            // 읽은 요소가 삭제됨으로 -1로 초기화
            // (어디까지나 전에 읽었던 요소 위치임 전전까지 Iterator는 즉발성의 개념 히스토리가 남는 개념이 아니다)
        }
    }

}
