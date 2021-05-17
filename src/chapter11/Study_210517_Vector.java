//package chapter11

import java.util.*;

// 학습용 Vector 클래스의 기본 기능을 책에서 간단하게 구현

public class Study_210517_Vector implements List {

    Object[] data = null;
    int capacity = 0;
    int size = 0;

    public Study_210517_Vector(int capacity) {
        if (capacity < 10) {
            throw new IllegalArgumentException("유효하지 않은 값입니다. : " + capacity);
        }
        this.capacity = capacity;
        data = new Object[capacity];
    }

    public Study_210517_Vector() {
        this(10); // 용량를 지정하지 않은 경우 위 생성자를 10 파라매터로 호출
    }

    private void setCapacity(int capacity) {
        if (this.capacity == capacity)
            return; // 크기가 같으면 변경하지 않음

        Object[] tmp = new Object[capacity];
        System.arraycopy(data, 0, tmp, 0, size); // 복사할 것, 복사 대상 위치, 복사할 곳, 복사할 곳 위치, 복사범위크기
        data = tmp;
        this.capacity = capacity;

        // 해당 용량의 배열을 생성한 뒤 System.arraycopy()로 복사 후 멤버변수에 대입
    }

    public void ensureCapacity(int minCapacity) { // 최소 저장공관 확보 메서드
        if (minCapacity - data.length > 0) { // 현재 데이터 배열 용량보다 같거나 커야 세팅이 됨
            setCapacity(minCapacity);
        }
    }

    public boolean add(Object obj) {
        ensureCapacity(size + 1); // 새로운 객체를 저장하기 전에 저장공간 확보, 현재 용량이 size(실제 데이터 차지하는 공간)+1보다 작을 때만 적용
        data[size++] = obj; // 현재 실제 데이터 차지공간의 다음 지정
        return true;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("범위를 벗어났습니다");
        }
        return data[index];
    }

    public Object remove(int index) {
        Object oldObj = null;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("범위를 벗어났습니다");
        }

        oldObj = data[index]; // 삭제후 반환을 위해 복사해둠

        if (index != size - 1) { // 삭제하는 객체가 마지막 자리가 아닐 경우 땡겨줘야함 >> 새로운 복제 인스턴스를 참조시켜야함
            System.arraycopy(data, index + 1, data, index, size - index - 1);
            // 복사할 것, 복사 대상 위치, 복사할 곳, 복사할 곳 위치, 복사범위크기
            // 오리지널, 삭제객체 다음곳 부터, 덮어씌어질 오리지널, 삭제객체위치(거기서부터 땡겨져야하므로), 복사되어야할 범위 크기

            // ★★★ 중요한 점은 이런 중간 삭제, 수정은 이런식으로 arraycopy를 통한 배열 조정이 들어가므로 작업성능이 떨어진다는 점
        }
        data[size - 1] = null; // 복사해 땡겨 넣은 다음 덮어씌어지지 않은 마지막 부분을 null처리 배열은 0부터 시작하니 마지막 부분은 size-1 (capacity가
                               // 아니다)
        size--;

        return oldObj;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < size; i++) {
            if (obj.equals(data[i])) {
                remove(i); // 같은 데이터를 반복문으로 찾아내 발견한 경우 위의 remove(index)를 실행시킨후 true 리턴
                return true;
            }
        }
        return false;
    }

    public void trimToSize() {
        setCapacity(size);
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);

        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return capacity;
    }

    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray(Object[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsAll(Collection c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object set(int index, Object element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void add(int index, Object element) {
        // TODO Auto-generated method stub

    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        return null;
    }
}
