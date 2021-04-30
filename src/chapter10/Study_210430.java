package chapter10;

/*
4/30일자 자바 공부 : 몸상태가 좀 많이 꽝으로 오늘은 최대한 가볍게 가봅니다.

Calendar 클래스 add(), roll() 메서드
add(int field(Calendar 상수), int amount) : 지정 필드의 값을 원하는 만큼 증가 or 감소
roll(int field, int amount) : 지정 필드 값 증감 > add와는 달리 다른 필드에 영향을 주지 않음
                                               > 예를 들어 add DATE가 31일이 넘어가면 MONTH도 1이 추가되나 roll은 유지
                                               > 물론 DATE가 말일일때 MONTH를 roll로 조정하면 DATE에 영향 미칠 수 있음(28/30/31일 월마다 다름)
                                               > 이는 add도 마찬가지 31일인데 2월로 변경하면 자동 28일로 DATE 필드 변환
                                               
이번달의 마지막 일을 알려면
1. getActualMaximum (Calendar.DATE)를 사용한다
2. 다음달의 1일에서 add로 하루를 뺀다.

JAVA의 정석에서 구현해본 날짜계산을 위한 몇가지 메서드 (아래 코드에서 확인)
1.boolean isLeapYear(int year) : 매개변수 year가 윤년이면 true 아님 false

2.int dayDiff(int y1, int m1, int d1, int y2, int m2, int d2) : 두 날짜간 차이를 일단위로 반환

3.String convertDayToDate(int day) : 일단위 값을 년월일 형태 문자열로 변환

4.int convertDateToDay(int year, int month, int day) : 년월일을 받아 일단위로 변환

 */

import java.util.Calendar;
import java.util.Scanner;

public class Study_210430 {
    public static void main(String args[]) {
        System.out.println("0. 우선 오랜만에 간단히 로또 프로그램 한번 만들어봄ㅎ");
        System.out.println("1. 달력 그리기 Calendar add 메서드 이용");
        System.out.print("호출할 메서드 입력 : ");
        Scanner sc = new Scanner(System.in);

        switch (sc.nextInt()) {
            case 0:
                lotto();
                break;
            case 1:
                makeCalendar();
                break;
        }
    }

    public static void lotto() {
        int[] lotto = new int[6];
        int[] many = new int[45];
        int cnt = 1;

        while(cnt <= 777) {
            for(int i=0; i<lotto.length; i++) {
                lotto[i] = (int)(Math.random() * 45) + 1;

                for(int j=0; j<i; j++) {
                    if(lotto[i] == lotto[j]) {
                        i--;
                        break;
                    }
                }
                many[lotto[i] - 1]++;
            }
            System.out.print(cnt + " 회차 번호 : ");
            for(int i=0; i< lotto.length; i++) {
                System.out.print(lotto[i] + " ");
            }
            cnt++;
            System.out.println();
        }
        System.out.println();
        System.out.println("=====많이 나온 수 집계=====");
        for(int i=0; i< many.length; i++) {
            if(many[i] != 0) {
                System.out.println((i+1) + "번째 수 나온 경우의 수 : " + many[i]);
            }
        }
    }
    
    public static void makeCalendar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input year : ");
        int year = sc.nextInt();
        System.out.print("Input month : ");
        int month = sc.nextInt();
        System.out.println();

        Calendar sDay = Calendar.getInstance(); // 시작일을 세팅할 참조변수
        Calendar eDay = Calendar.getInstance(); // 끝일을 세팅할 참조변수
        
        sDay.set(year, month - 1, 1); // Calendar에서 MONTH상수는 0~11이다
        eDay.set(year, month - 1, sDay.getActualMaximum(Calendar.DATE));
        
        sDay.add(Calendar.DATE, -sDay.get(Calendar.DAY_OF_WEEK) + 1); // 시작일 주의 일요일로 날짜 재 설정
        eDay.add(Calendar.DATE, 7 - eDay.get(Calendar.DAY_OF_WEEK)); // 말일이 속한 주의 토요일로 날짜 재 설정

        System.out.println("      " + year + "년 " + month + "월");
        System.out.println(" SU MO TU WE TH FR SA");
        
        for(int n=1; sDay.before(eDay) || sDay.equals(eDay); sDay.add(Calendar.DATE, 1)) {
            //1일씩 증가시키며 날짜 출력
            
            int day = sDay.get(Calendar.DATE);
            System.out.print((day < 10) ? "  " + day : " " + day );
            if(n++%7 == 0) System.out.println(); // 7일치를 찍을 때 줄을 바꿈
        }
    }

    public static int[] endOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static boolean isLeapYear (int year) {
        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }

    public static int getDayOfWeek(int year, int month, int day) {
        return convertDateToDay(year, month, day) % 7 + 1; // 1(일)~7(토)값 반환
    }

    public static int dayDiff (int y1, int m1, int d1, int y2, int m2, int d2) {
        return convertDateToDay(y1, m1, d1)  - convertDateToDay(y2, m2, d2);
    }

    public static int convertDateToDay(int year, int month, int day) { //서기1년1월1일부터 했지만 calendar는 1970년 1월 1일 기준
        int numOfLeapYear = 0; // 윤년의 수
        for(int i=1; i < year; i++) { // 전년도까지의 윤년 수
            if(isLeapYear(i)) numOfLeapYear++;
        }

        int toLastYearDaySum = (year-1) * 365 + numOfLeapYear; // 전년도까지 일수 (윤년은 1씩만 +하면 되므로)

        int thisYearDaySum = 0; // 올해의 현재 월까지의 일 수
        for(int i=0; i < month - 1; i++) {
            thisYearDaySum += endOfMonth[i];
        }
        if(month > 2 && isLeapYear(year)) thisYearDaySum++; // 윤년이고 2월 포함시 1일 추가

        thisYearDaySum += day;
        thisYearDaySum += toLastYearDaySum;

        return thisYearDaySum;
    }

    public static String converDayToDate(int day) {
        int year = 1;
        int month = 0;

        while(true) {
            int aYear = isLeapYear(year) ? 366 : 365;
            if(day > aYear) {
                day -= aYear;
                year++;

            } else {
                break;
            }
        }

        for(;;) {
            int endDay = endOfMonth[month];
            if(isLeapYear(year) && month == 1) endDay++; //윤년이고 윤달포함시 1일 더함

            if(day > endDay) {
                day -= endDay;
                month++;
            } else {
                break;
            }
        }

        return year+"-"+(month+1)+"-"+day;
    }
}
