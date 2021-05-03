package chapter10;

/*
    형식화 클래스(java.text 패키지) : 숫자 / 날짜 / 텍스트 데이터를 일정한 형식에 맞게 표현하는 방법을 객체지향적으로 표준화함

    >> 정의된 사용자 패턴을 기준으로 > 데이터를 형식화(.format()) or 역으로 형식화된 데이터에서 원래 값 추출(.parse())
    >> 형식화 클래스는 패턴을 정의하는것이 거의 알파에서 오메가임

    DecimalFormat : 숫자를 형식화(정수, 부동소수점, 금액 등, 퍼센트나 퍼밀 등)

    >> 좀 주의할 패턴기호 : 0 vs # (10진수 표현) : 0은 값이 없는 구간에 0을 표현
                       : 소수점 이하의 0or#표현 범위 미만은 반올림됨
                       : 형식화클래스 이용하면 ','으로 단위표현도 쌉가능
                       : 1234.567 >> 00,000.00 vs ##,###.## >> 01,234.57 vs 1,234.57

                       : ';'를 통해 적용해야하는 패턴을 구분할 수 있음
                       : 1234.567 > #,###.##+;#,###.##- >> 1,234.57+(양수일때) 1,234.57-(음수일때)

                       : \u2030 (퍼밀 퍼센트 *10), \u00A4 (통화), '(Escape문자는 형식화에서 저게 공통)
                       : 1234.567 >> '''#'###,#\u2030 >> '#1234567퍼밀(기호)

    >> 사용방법 : 적용할 출력형식패턴을 작성해 이를 파라미터로 DecimalFormat 생성 초기화
                 > .format(number)인스턴스 메서드로 패턴에 맞는 결과값 반환 (반환타입 String)

                 > .parse(String source)를 이용하면 다시 적절한 숫자로 변환 가능 (반환타입 Number)
                   (래퍼클래스의 parse메서드와 다른 점은 ','등이 포함된 문자열도 변환이 가능하기 때문)

                   .parse는 DecimalFormat의 조상클래스인 NumberFormat에 아래와 같이 정의됨
                   public Number parse(String source) throws ParseException
                   >> 반환타입이 Number이기에 적절한 타입의 기본형Value() 메서드로 최종 변환
                   >> 또한 throws ParseException이 되어있기에 적절한 예외처리가 필요함

    SimpleDateFormat
    DateFormat(추상클래스)의 구현메서드 (DateFormat을 static인 getDateInstance()로 가져와도 SimpleDateFormat가져와짐)
    Date와 Calendar가 날짜를 계산하는 방법이라면 SimpleDateFormat은 사용자 정의 패턴에 맞춰 출력하는 방법

    >> 주요 패턴 기호
    G : 연대(BC, AC)
    y(년) / M(월) / d(일 : 월의 몇 번째) vs D(년의 몇 번째) / w(주 : 년의 몇 번째) vs W(월의 몇 번째) w,d 대소문자 순서가 반대
    F(월의 몇 번째 요일 > 몇째 주인지 1~5) E(요일)
    a : 오전 오후
    H(시간 0~23) / K(시간 0~11) / h(시간 1~12) / k(시간 1~24) /  << 먼가 헷갈리게 해놨네
    m(분 0~59) / s(초 0~59) / S(밀리초 0~999) / z, Z : 타임존

    >> 사용방법
    형식화 클래스의 공통적인 부분으로 위의 DecimalFormat처럼
    정의한 패턴을 파라미터로 인스턴스 생성

    Date타입을 파라미터로 .format(Date)메서드로 String 반환 (중요한건 Calendar는 .getTime()메서드로 Date로 변환한 뒤(반대는 .setTime(Date) 형식화해야함)
    역으로 .parse(String)으로 원래 Date타입을 반환 받을 수 있다 (DateFormat에 정의됨 마찬가지로 형식 미스매치에 적절한 예외처리가 필요하다)
    형식화 클래스를 사용하면 굳이 substring으로 번거롭게 년월일 등을 뽑아 재처리를 하는 수고를 줄일 수 있다.
 */

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Study_210503 {
    public static void main(String args[]) {
        System.out.println("1. DecimalFormat 연습");
        System.out.println("2. SimpleDateFormat 연습");
        System.out.print("실행할 메서드 선택 : ");
        Scanner sc = new Scanner(System.in);

        switch (sc.nextInt()) {
            case 1:
                decimalFormatEx();
                break;
            case 2:
                simpleDateFormatEx1();
                break;
        }
    }

    public static void decimalFormatEx () {
        double d1 = 123456.789;
        System.out.println("original : "+d1);

        DecimalFormat df1 = new DecimalFormat("#,###.#");

        String result1 = df1.format(d1);
        System.out.println("result1 : " + result1);

        try {
            Number result2 = df1.parse(result1);
            double d2 = result2.doubleValue();
            System.out.println("result2 : " + d2);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void simpleDateFormatEx1() {

        Calendar cal = Calendar.getInstance();
        Date day = cal.getTime();

        SimpleDateFormat df1, df2;
        df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a E요일");
        df2 = new SimpleDateFormat("yy-MM-dd 오늘은 올해 D번째 날(이 달의 d번째 날)");

        String result1 = df1.format(day);
        String result2 = df2.format(day);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println();

        try {
            DateFormat df3 = new SimpleDateFormat("yyyy년 MM월 dd일");
            // parse를 할 때는 그래도 이렇게 좀 제한되는 형식값이 있다.
            // 위 df1, df2로 parse하려면 못 읽고 ParseException 발생함
            
            String day2 = "2021년 5월 3일";
            Date result3 = df3.parse(day2);
            System.out.println(result3);

        } catch(ParseException pe) {
            pe.printStackTrace();
        }
    }

}
