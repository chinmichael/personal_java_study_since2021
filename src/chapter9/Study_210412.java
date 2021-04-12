package chapter9;

/*
    문자 인코딩  : getBytes(String characterName) (예외처리 필요) > 문자열 문자 인코딩 변환 가능 (문자열 리터럴의 문자들은 OS인코딩 사용)

    String.format() : 형식화된 문자열 만들 때 사용 printf()와 사용법 같음 printf("%d %c %f \n", 3, a, 3.5) 머 이랬던거

    기본형 값 String 전환 : 숫자 + ""(빈문자열) >> 매우 간단( 알아서 참조변수가 가리키는 인스턴스 toString호출해서 변형)
                            vs valueOf() 사용 >> 성능은 이쪽이 더 좋음

    String을 기본형으로 전환    : 래퍼클래스.parse메서드(parseInt등)을 쓰거나 래퍼클래스.valuOf();를 사용
                                >> valueOf의 경우 기본형이 아니라 래퍼클래스 반환타입이나 auto-boxing이 알아서 자동변환시킴
                                >> 실제로 valueOf의 경우 내부에서 parse메서드 호출해서 사용하므로 같음
                                >> 정수나 실수 변환은 문자열 내 문자, 특히 공백등에 의해 NumberFormatException이 터질 경우 많으므로 trim() 습관화 좋음
                                >> 부호 의미하는 문자(+,-)나(jdk1.7) 소수점, float의 f 등 자료형 접미사는 자료형에 알맞을 경우 허용됨
                                   + parseInt(String str, int radix)의 radix(진수)가 16일 경우 대소문자 구분 없이 진수표현 위한 a,b,c,d,e,f 가능

    String.join(str, str, arr)  : 각각의 String을 하나로 합치거나 배열을 하나의 String으로 바꿔주는 메서드 > split같은거 할 때도 좋겠지
                                : .join(구분자, str, str)로 하여 합치는 사이 구분자를 넣을 수 있음
 */

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringJoiner;

public class Study_210412 {
    public static void main(String args[]) {
        System.out.println("1. encodingTest");
        System.out.println("2. parseTest");
        System.out.print("실행할 메소드 번호 선택 : ");
        Scanner sc = new Scanner(System.in);
        Study_210412 study = new Study_210412();

        switch (sc.nextInt()) {
            case 1:
                study.encodingTest();
                break;
            case 2:
                study.parseTest();
                break;
        }
    }

    public void encodingTest() {
        String str = "가";
        byte[] bArr; byte[] bArr2;

        try {
            bArr = str.getBytes("UTF-8");
            bArr2 = str.getBytes("CP949");

            System.out.println("UTF8(3바이트 표현) : " + joinByteArr(bArr));
            System.out.println("CP949(2바이트 표현) : " + joinByteArr(bArr2));

            System.out.println("new String UTF8 : " + new String(bArr, "UTF-8"));
            System.out.println("new String CP949 : " + new String(bArr2, "CP949"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String joinByteArr(byte[] arr) {
        StringJoiner sj = new StringJoiner(":", "[", "]");
        for(byte b : arr) {
            sj.add(String.format("%02X", b));
        }
        return sj.toString();
    }

    public void parseTest() {
        int intVal = 100;
        String strVal = String.valueOf(intVal);

        double doubleVal = 200.0;
        String strVal2 = String.valueOf(doubleVal);

        double sum = Integer.parseInt("+" + strVal) + Double.parseDouble(strVal2);
        double sum2 = Integer.valueOf(strVal) + Double.valueOf(strVal2);

        System.out.println(String.join("",strVal,"+",strVal2,"=") + sum);
        System.out.println(strVal + "+" + strVal2 + "=" + sum2);
        String[] strArr = {"java", "is", "fun"};
        System.out.println("String.join(-, arr) : " + String.join("-", strArr));
    }
}
