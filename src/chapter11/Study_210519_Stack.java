//package chapter11;

import java.util.*;

// Vector 상속 Stack 간단 구현
public class Study_210519_Stack extends Vector {

    public Object push(Object item) {
        addElement(item);
        return item;
    }

    public Object peek() {
        int len = size();

        if (len == 0) {
            throw new EmptyStackException();
        }
        // 배열은 0부터 시작하므로 마지막 요소 index는 size-1
        return elementAt(len - 1);
    }

    public Object pop() {
        Object obj = peek(); // 만약 스택이 비어있으면 peek에서 알아서 Exception은 발생시킨다
        removeElementAt(size() - 1);
        return obj;
    }

    public boolean empty() {
        return size() == 0;
    }

    public int search(Object obj) {
        int i = lastIndexOf(obj); // 끝에서부터 찾아야한다

        if (i >= 0) {
            return size() - i; // Stack은 맨위(맨마지막에 저장된) 객체 index가 1부터 시작하기에
        }
        return -1;
    }
}
