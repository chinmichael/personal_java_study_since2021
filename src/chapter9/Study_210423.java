package chapter9;

import java.util.Scanner;
import java.util.StringTokenizer;

/*
java.util.StringTokenizer : 문자열을 지정된 구분자(delimiter)을 통해 token이란 문자열로 잘라냄

기존 방법
String[] result = "~".split("구분자");
Scanner sc = new Scanner("~").useDelimiter("구분자");
>> 기존 방법의 경우는 정규식 표현을 구분자로 써야 했음 so 정규식이 익숙하지 않으면 StringTokenizer가 간편하고 명확
>> 대신 StringTokenizer는 구분자가 하나의 문자 >> 복잡한 형태의 구분자가 필요한 경우 위의 방법으로 정규식을 써야함

>> 그 밖에 split의 경우 결과를 배열에 넣어야하기에 성능이 StringTokenizer보다 떨어짐
>> 또 split의 경우는 빈문자열도 토큰으로 인식하지만 StringTokenizer는 무시한다

StringTokenizer token = new StringTokenizer (String str, String delim); delim에 문자만 온다는 것이 아님, delim문자열의 문자 하나하나를 각각의 구분자로 인식
StringTokenizer token = new StringTokenizer (String str, String delim, true); 3번째 파라미터를 true로 하면 구분자도 토큰으로 간주
int countTokens() : 전체 토큰 수 반환
String nextToken() : 다음 토큰 반환
boolean hasMoreTokens() : 토큰이 남았는지 확인

 */
public class Study_210423 {
    public static void main(String[] args) {
        System.out.println("1. StringTokenizer 기본 사용");
        System.out.println("2. 두 종류의 구분자를 범주를 나눠 구분하기");
        System.out.println("3. StringTokenizer를 이용한 한글 숫자 전환");
        System.out.print("메서드 입력 : ");
        Scanner sc = new Scanner(System.in);
        Study_210423 study = new Study_210423();

        switch (sc.nextInt()) {
            case 1:
                study.tokenEx1();
                break;
            case 2:
                study.tokenEx2();
                break;
            case 3:
                study.tokenEx3("삼십만삼천백십오");
                break;
        }
    }

    public void tokenEx1 () {
        String source = "100,200,300,400,500";
        StringTokenizer st = new StringTokenizer(source, ",");
        System.out.println("source : " + source);
        System.out.println("구분자 토큰에 미포함");
        while(st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
        System.out.println();

        StringTokenizer st2 = new StringTokenizer(source, ",", true);
        System.out.println("구분자 포함");
        while(st2.hasMoreTokens()) {
            System.out.println(st2.nextToken());
        }
        System.out.println();

        String source2 = "x=100*(200+300)/2";
        String delim = "+=*/=()";
        System.out.println("source2 : "+ source2);
        System.out.println("source2's delim : " + delim);
        StringTokenizer st3 = new StringTokenizer(source2, delim);
        System.out.println("구분자 미포함");
        while(st3.hasMoreTokens()) {
            System.out.println(st3.nextToken());
        }
        System.out.println();

        StringTokenizer st4 = new StringTokenizer(source2, delim, true);
        System.out.println("구분자 포함");
        while(st4.hasMoreTokens()) {
            System.out.println(st4.nextToken());
        }
    }

    public void tokenEx2() {
        String source = "1.김천재,100,100,100|박수재,200,200,200|3,이자바,300,300,300";
        StringTokenizer st = new StringTokenizer(source, "|");
        System.out.println("source : " + source);
        System.out.println("구분자 적용 순서 : | >> ,");

        while(st.hasMoreTokens()) {
            String token = st.nextToken();

            StringTokenizer st2 = new StringTokenizer(token, ",");
            while(st2.hasMoreTokens()) {
                System.out.println(st2.nextToken());
            }

            System.out.println("----------");
        }
    }

    public void tokenEx3(String input) {
        System.out.println("default 입력값 : " + input);

        long result = 0;
        long tmpResult = 0; //십백천 단위 저장
        long num = 0;

        final String NUMBER = "영일이삼사오육칠팔구";
        final String UNIT = "십백천만억";
        final long[] UNIT_NUM = {10, 100, 1000, 10000, 100000000};

        StringTokenizer st = new StringTokenizer(input, UNIT, true);

        while(st.hasMoreTokens()) {

            String token = st.nextToken();

            int check = NUMBER.indexOf(token); // 토큰이 숫자인지

            if(check==-1) { //단위인 경우

                if("만억조".indexOf(token) == -1) {
                    tmpResult += (num != 0 ? num : 1) * UNIT_NUM[UNIT.indexOf(token)]; // 이전 숫자 num

                } else {
                    tmpResult += num; // 십백천 처리를 끝낸 뒤 만,억 계산
                    result += (tmpResult != 0 ? tmpResult : 1) * UNIT_NUM[UNIT.indexOf(token)];
                    tmpResult = 0;
                }
                num = 0; // 단위처리를 했으므로 0으로

            } else {
                num = check; // 1의 단위 or 다음 단위처리를 위해 넘김
            }
        }
        System.out.println(result + tmpResult + num);
    }
}
