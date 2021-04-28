package chapter10;

/*
1. 날짜와 시간 part1

Date와 Calendar    : 날짜와 시간을 다루기 위한 클래스 (java.uitl패키지)
                     > Date(JDK1.0부터) : 너무 빈약 >> Calendar(JDK1.1부터) : 몇몇 단점 존재
                     > java.time패키지 등장(기존 단점 개선) : 하지만 기존 Calendar와 Date를 계속 사용해왔기에 기본적인 부분 알아야함

Calendar 생성
Calendar는 추상클래스 >> 직접 객체생성 불가 >> 메서드(getInstance())를 통해 구현클래스 인스턴스를 생성해야 함

Calendar cal = Calendar.getInstance();를 하면 시스템의 국가, 지역설정을 확인하여
태국인 경우는 BuddhistCalendar, 그 외는 GregorianCalendar클래스의 인스턴스를 반환함

이렇게 메서드를 통해 자동으로 알맞은 타입의 특정 인스턴스를 반환 생성하게 함으로서 다른 역법이 추가되거나 사용될 경우
객체를 호출하는 클래스 자체를 수정하지 않아도 됨(의존성을 낮춤, 스프링적으로 생각이 좀 되네 / getInstance()만 수정되면 사용하는 클래스들 전체 수정 필요X)

getInstance()는 static 메서드(클래스 메서드) >> 따라서 Calendar가 추상클래스라 인스턴스 생성이 불가해도 사용 가능함
                                             + 대신 메서드 내에서 인스턴스 변수, 인스턴스 메서드 호출 불가

========================================================================================================================
★ 클래스(static) 메서드, 인스턴스 vs 인스턴스 메서드 (이는 앞에 Chapter6에서 복습하면서 다시 할거 지금 하고 복붙해야징)

-기본적으로 Class는 멤버변수(데이터)와 이와 관련된 메서드들의 집합임

-멤버변수 : static변수(클래스가 메모리에 로드 될 시에 함께 올라감) + 인스턴스변수(인스턴스 생성 시 메모리 할당) 의 통칭이다
-변수 : 멤버변수(static변수 + 인스턴스변수) + 지역변수(변수 선언문 수행시에 할당됨)

-static 메서드는 얘도 Class 로드시 같이 올라가기에 위에서처럼 인스턴스 변수를 작업에 사용하지 않는 메서드이다.
 (인스턴스 생성 전에는 있지도 않은 애들을 호출하면 당연 말이 안되겠지) / 클래스변수처럼 클래스.메서드 로 호출가능

-인스턴스 메서드의 경우 작업에서 객체 생성이 필요로 한 인스턴스 변수 등을 사용하는 케이스에 정의된다.

☆ 위와 같은 의미상 멤버변수, 메서드에서 static 고려사항

1. static 변수 : 클래스를 설계할 때 이를 토대로 만든 모든 인스턴스에서 공통으로 사용(데이터 사용이 공유)되야 하는 자료 (중간프로젝트할때 flg로 자주 써먹었지_
>> 인스턴스 생성마다 별개의 데이터 주소를 갖는 인스턴스변수와 달리 클래스로드시 함께 할당되어 전체공유가 되는 자료니 변경사항이 공유된다

2. static 메서드는 인스턴스 변수 사용 불가능 : 위에 쓴 것처럼 당빠 클래스로드되면 바로 사용가능 해야하는데 생성도 안된 데이터를 어케 씀
                                          > 물론 어차피 호출시 함께 받아오게 되는 파라미터 변수 같은 경우는 노상관이다

3. 위와 반대로 인스턴스 변수를 사용하지 않을 때는 static메서드를 고려함 : 해당 인스턴스의 해당 메서드 이런 호출 수순이 없어 훨씬 호출이 빠름(성능 좋음)

------------------------------------------------------------------------------------------------------------------------

위와 함께 알아둬야 할 JVM의 메모리 구조(당빠 Chapter6때 복붙할거임)

JAVA응용프로그램 실행시 >> 시스템은 JVM에 프로그램 수행을 위한 메모리 할당 >> JVM은 용도에 따라 3주요영역을 나눔

1. method area : 프로그램 실행 중 클래스 사용시 해당 클래스파일 정보를 로드하는 곳 (당연 클래스 변수 데이터도 함께 로드됨)

2. heap : 인스턴스 생성 공간 (인스턴스 변수들이 할당되는 공간)

3. callstack(호출스택) : 메서드 작업에 필요한 메모리 공간 제공(작업 종료 후 반환)
                        >> 메서드 호출시 작업종료까지 지역변수(매개변수포함), 연산 중간결과 등이 저장

                        >> 메서드 작업공간은 각 호출 메서드마다 메모리상에서 서로 구별됨
                        >> 그리고 stack이라 부르는 것처럼 구조가 FILO구조다(가장 아래가 처음 호출된 메서드 가장 위가 마지막에 호출된 메서드)

                        >> 호출 메서드가 작업 수행중 다른 메서드를 호출하면 작업이 중단되고 그 메서드가 스택에 할당되어 작업함
                            > 현재 작업중인 메서드가 종료되면 메모리 반환 및 스택제거가 이뤄지고 다시 자신을 호출한 밑에 있는 메서드가 뒤이어 실행
                            > 만약 반환값이 있었다면 호출했던 caller한테 넘겨준다.
========================================================================================================================

Date와 Calendar간 변환 (대부분읜 Date메서드는 deprecated된게 많지만 Date를 필요로 하는 메서드들이 있으므로)

1. Calendar >> Date
Calendar cal = Calendar.getInstance();
Date d = new Date(cal.getTimeInMillis()); // Date(Long date) >> Date 생성자에 수로 넣는다

2. Date >> Calendar
Date d = new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(d) // 시간을 지정하는 setTime() 메서드에 Date를 넣는다

3. Calendar 인스턴스의 get() 메서드를 통한 필요파트 가져오기 (메서드1참조 : 멤버상수표현)
4. Calendar 인스턴스 생성 후 set(year, month-1, day)로 날짜 세팅

void set(int field, int value)
void set(int year, int month, int date, int hourOfDay, int minute, int second) >> hourOfDay, minute, second는 옵션
void clear(int field) 모든 필드 값을 기본값으로 초기화(기본값은 구글링)

5. Calendar 날짜간 차이는 밀리초로 전환(getTimeInMillis()) 후 계산 : long 반환임
   시간상의 전후는 두 차이의 양수 음수를 판별하거나 boolean after(Object when), boolean before(Object when) 이용
 */

import java.util.Calendar;
import java.util.Scanner;

public class Study_210428 {
    public static void main(String args[]) {
        System.out.println("1. Calendar get메서드의 파리미터로 정의된 기본 static 상수 표현들 (코드에서 멤버상수 확인할 것)");
        System.out.println("2. Calendar set(y, m-1, d)로 날짜 설정 + 밀리초 변환 후 날짜간 차이 계산");
        System.out.print("메서드 번호 선택 : ");
        Scanner sc = new Scanner(System.in);

        switch(sc.nextInt()) {
            case 1:
                calEx1();
                break;
            case 2:
                calEx2();
                break;
        }
    }

    public static void calEx1() {

        Calendar current = Calendar.getInstance();

        System.out.println("해당 년도 : " + current.get(Calendar.YEAR));
        System.out.println("월(주의 !! >> 0~11이므로 +1처리를 함) : " + (current.get(Calendar.MONTH) + 1));
        System.out.println("해당 년의 몇 째 주 : " + current.get(Calendar.WEEK_OF_YEAR));
        System.out.println("해당 월의 몇 째 주 : " + current.get(Calendar.WEEK_OF_MONTH));
        System.out.println();

        System.out.println("이 달의 몇 일(Date) : " + current.get(Calendar.DATE));
        System.out.println("이 달의 몇 일(Day of Month) : " + current.get(Calendar.DAY_OF_MONTH));
        System.out.println("이 해의 몇 일 : " + current.get(Calendar.DAY_OF_YEAR));
        System.out.println();
        
        System.out.println("요일 (월~일 = 1~7) : " + current.get(Calendar.DAY_OF_WEEK));
        System.out.println("이 달의 몇 째 요일 : " + current.get(Calendar.DAY_OF_WEEK_IN_MONTH)); // DAY_OF_WEEK + IN_MONTH
        System.out.println("오전(0) vs 오후(1) : " + current.get(Calendar.AM_PM));
        System.out.println();

        System.out.println("시간(0~11) : " + current.get(Calendar.HOUR));
        System.out.println("시간(0~23) : " + current.get(Calendar.HOUR_OF_DAY));
        System.out.println("분(0~59) : " + current.get(Calendar.MINUTE));
        System.out.println("초(0~59) : " + current.get(Calendar.SECOND));
        System.out.println("밀리초(1/1000초 : 0~999) : " + current.get(Calendar.MILLISECOND));
        System.out.println();

        System.out.println("TimeZone(-12~12) : " + current.get(Calendar.ZONE_OFFSET) / (60*60*1000));
        // TimeZone은 밀리초로 표현되므로 시간으로 표현하기 위해 60*60*1000을 나눔
        System.out.println("해당 월의 마지막 날 : " + current.getActualMaximum(Calendar.DATE)); // 얜 ActualMaximun으로 최대
    }

    public static void calEx2() {
        final String[] DAY_OF_WEEK = {"월","화","수","목","금","토","일"}; // 요일은 1~7이므로 index 0처리를 위해 밑에서 -1

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal2.set(2021, Calendar.AUGUST, 15); // 각 월은 -1이 되므로 cal1.set(2021, 7, 15)와 같다

        /*
        만약 위를 set(int field, int value)형식으로 세팅한다면

        cal2.set(Calendar.YEAR, 2021);
        cal2.set(Calendar.MONTH, Calendar.AUGUST);
        cal2.set(Calendar.DATE, 15);
        */

        System.out.println("cal1(지금) : " + toString(cal1) + " " + DAY_OF_WEEK[cal1.get(Calendar.DAY_OF_WEEK) - 1] + "요일");
        System.out.println("cal2 : " + toString(cal2) + " " + DAY_OF_WEEK[cal2.get(Calendar.DAY_OF_WEEK) - 1] + "요일");
        System.out.println();

        System.out.println("날짜간 차이 계산을 위해서는 밀리초 단위로 바꿔야함");
        long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis(); //반환타입은 long임
        System.out.println("지금에서 그날까지 " + diff/1000 + "초가 남았습니다.");
        System.out.println("일자로 계산하면 " + diff/(24*60*60*1000) + "일 남았습니다.");
        System.out.println();

        System.out.println("위 차이를 시분초로 표현하면");
        final int[] TIME_UNIT = {3600, 60, 1};
        final String[] TIME_UNIT_NAME = {"시간 ", "분 ", "초 "};
        String tmp = "";
        for(int i=0; i < TIME_UNIT.length; i++) {
            tmp += (diff/1000) / TIME_UNIT[i] + TIME_UNIT_NAME[i];
            diff %= TIME_UNIT[i]; // 가장 큰 단위부터 나누어 남은 나머지(분이나 초와 같은 다음 단위)를 순차적으로 넘긴다.
        }
        System.out.println(tmp + "입니다.");
    }

    public static String toString (Calendar cal) {
        return cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DATE) + "일 ";
    }
}
