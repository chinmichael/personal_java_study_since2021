package chapter9;

/*
어제 String 계속    : C와 달리 JAVA는 길이가 0인 배열 생성 가능 > ""은 내부에 char[0]을 가지고 있음 >> ""로 초기화 가능
                                                          > 단 그렇다고 char은 ''로 지정 안되므로 ' '(공백)로 초기화 (기본값은 '\u0000' 빈문자 유니코드)

그 밖에 자주 쓰는 String 메서드
String(value) >> 생성자 파라미터로 문자열, char[], StringBuffer 가능
char charAt(int idx) : 지정 위치의 문자 알려줌(인덱스 값음 배열기준)
int indexOf(int ch), int indexOf(String str) : 주어진 문자, 문자열가 존재하는지 확인해 첫번째로 나온 배열위치를 알려줌, 없으면 -1
int lastIndexOf(int ch), int lastIndexOf(String str) : 위가 왼쪽부터 첫번째 나온위치를 센다면 위는 오른쪽부터 세서 처음나온 배열 위치 알려줌

indexOf 시리즈의 int ch는 오타가 아니라 문자는 맞지만 char(16비트 유니코드)로 다 표현 못하는 확장 유니코드(20비트)로 인해 String에서 지원은 하지만 char로 표현 못하는 케이스

String replace(char old, char new) String replace(CharSequence old, CharSequence new) : 지정된 전체 기존 문자, 문자열을 지정한 새 문자, 문자열로 교체
String replaceAll(String regex, String new) : 위와 마찬가지 단 정규식(regex)를 사용가능하여 더 범주가 넓게 적용 가능
String replaceFirst(String regex, String new) : 전체가 아닌 첫번째 지정 케이스만 바꿈

String[] split(String regex) : 문자열을 지정된 분리자(정규식이나 문자열)로 나눠 문자열배열로 나눠 담음
String[] split(String regex, int limit) : int limit는 얼마나 되는 문자열배열 크기로 나눠 담을지 (3개로 쪼개지는데 2개면 1개 1,1로 2개로 담김)

String substring(int begin) : begin(포함)지점에서부터 끝까지 문자열 반환
String substring(int begin, int end) : begin지점(포함)에서 end(비포함)까지 문자열 반환

String trim() : 문자열 양단의 공백(중간은 냅둠)을 제거함

String toLowCase() : 문자열을 죄다 소문자로
String toUpperCase() : 죄다 대문자로

String valueOf() : 파라미터 값을 문자열로 변경(boolean, char, int, long, float, double) 참조변수의 경우 toString값 반환

써본적은 없지만 책에 있던 메서드
int compareTo(String str) : 문자열의 사전순서를 반환 (앞 : 음수 / 동일 : 0 / 뒤 :양수)
String concat : 문자열 뒤에 덧붙임 그냥 + 생각
boolean contains(CharSequence str) : 지정 문자열이 포함되는지
boolean endWith(String suffix) : 지정 문자열로 끝나는지
boolean equalsIgnoreCase(String str) : 대소문자 구분없이 비교
String intern : 문자열을 상수풀에 등록 + 주소값을 반환(만약 이미 등록되어잇으면 등록된 주소값을 반환)

join, StringJoiner
join(결합자, 문자열배열) : 지정 구분자를 넣어 문자열배열을 결합 / split와 비슷하면서 하는건 반대라고 생각하면 됨
StringJoiner : StringJoiner클래스 이용해 문자열 결합도 가능 / 생성자로 (구분자, 맨앞에 붙일거, 뒤에붙일거) 지정 후 add()메서드로 추가
 */

import java.util.StringJoiner;

public class Study_210410 {
    public static void main(String args[]) {
        String animals = "dog,cat,bear";
        String[] arr = animals.split(",");

        System.out.println(String.join("-", arr));
        System.out.println();

        StringJoiner sj = new StringJoiner("/", "<", ">");
        for(String s : arr) {
            sj.add(s);
        }
        System.out.println(sj.toString());
    }
}
