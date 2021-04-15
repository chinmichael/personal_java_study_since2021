package chapter9;

/*
String : 내부 문자배열 변경 불가 > 인스턴스 생성시 지정된 문자열 변경 안됨 (변경마다 새로운 인스턴스 생성 및 주소 참조)

StringBuffer    : 내부적으로 문자열 편집을 위한 buffer 가짐 > 인스턴스 생성 시 buffer크기 지정
                : 이 때, 편집하는 문자열이 버퍼를 넘어가면 버퍼 길이 추가 작업이 수행되므로 미리 충분히 잡아주는 편이 효율적
                : String과 마찬가지로 내부 char[] 멤버변수 value를 참조

                : 인스턴스 생성 시 생성자 파라미터 int length에 따른 사이즈의 배열 value 생성 > 저장, 편집 공간(buffer) 역할
                : 버퍼 크기 미지정 시 기본 크기는 16 / 문자열 파라미터로 초기화 시 문자열 길이 + 16으로 버퍼 사이즈 생성
                
                : 문자열 추가편집인 append()인 경우 value배열에 추가시킨 뒤 자기 자신 반환 > 동일 인스턴스임
                : 따라서 StringBuffer sb = new StringBuffer()에서 sb.append().append(); 가능
                : append()파라미터는 기본형 모두와, Object 가능 > 파라미터 입력값을 문자열로 변환 후 현 문자열 뒤에 덧붙임
                
                : 문자열 편집 시, 작업 문자열이 버퍼보다 큰 경우 내부적으로 버퍼 크기 증가 작업 수행
                    >> 편집대상은 문자배열인데 배열 크기는 수정 불가 >> 새 배열 생성 후 System.arraycopy로 value내용 복사 + 다시참조

                : StringBuffer는 equals()를 오버라이딩 하지 않음 >> '=='와 같은 결과를 얻음
                : toString()은 뭐 그래도 당빠 오버라이딩 되어 있음 >> toString()으로 String인스턴스로 호출 후 equals로 비교해야 함

                /////////////////////////////////////////////////////////////////////////////////////////////////

                그 밖의 StringBuffer 메서드
                : int capacity() : 버퍼 크기를 알려줌 (length()는 버퍼 내 문자열 길이 알려줌)

                : char charAt(int index) : String과 마찬가지
                : StringBuffer delete(int start, int end) : start에서 end(end위치는 뺌) 문자 제거
                : StringBuffer deleteCharAt(int index) : 지정위치 문자제거

                : StringBuffer insert(int pos, 기본형 or Object) : pos위치(당빠 0부터)에 파라미터 문자열변환 값 박는다

                : StringBuffer replace(int start, int end, String str) 범위 내 문자열을 지정 문자열로 바꿈

                : void setCharAt(int index, char ch) : 지정 위치 문자를 변경

                : String substring(int start, int end) : 지정 범위 문자열만 뽑아 String반환 (end 지정 안하면 끝까지)

                : void setLength(int length) : 지정 길이로 문자열 길이 변경 (늘리는 경우 빈공간을 널문자로 채움)

                : StringBuffer reverse() : 문자열 거꾸로 배열시켜림

 */

public class Study_210415 {
    public static void main(String[] args) {

        StringBuffer sb1 = new StringBuffer("01");
        StringBuffer sb2 = sb1.append(23);
        sb1.append('4').append(56);

        StringBuffer sb3 = sb1.append(78);
        sb3.append(9.0);
        StringBuffer sb4 = new StringBuffer(sb3.toString());

        System.out.println("sb1 = " + sb1);
        System.out.println("sb2 = " + sb2);
        System.out.println("sb3 = " + sb3);  // sb1.append()로 대상한 시점에서 sb1의 인스턴스를 반환하므로 다 같음 중요!!
        System.out.println("sb4 = " + sb4);
        System.out.println("sb3.equals(sb4) = " + sb3.equals(sb4));
        System.out.println(("sb3.toString().equals(sb4.toString()) = " + sb3.toString().equals(sb4.toString())));
        System.out.println();

        System.out.println("sb1.deleteCharAt(10) = " + sb1.deleteCharAt(10));
        System.out.println("sb1.delete(3,6) = " + sb1.delete(3, 6));
        System.out.println("sb1.insert(3, 'abc') = " + sb1.insert(3, "abc"));
        System.out.println("sb1.replace(6, sb1.length(), 'END') = " + sb1.replace(6, sb1.length(), "END"));
        System.out.println();

        System.out.println("capacity = " + sb1.capacity());
        System.out.println("length = " + sb1.length());
    }
}
