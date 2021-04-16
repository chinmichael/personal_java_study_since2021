package chapter9;

/*
Math 클래스 (기본적인 수리계산에 유용한 메서드 구성, random()이나 round()(반올림) 등)
    : 생성자 접근제어자가 private >> 다른 클래스에서 인스턴스 생성 불가능 // 멤버변수가 없어 필요 없음
                                                                    상수(PI:원주율, E:자연로그 밑)은 존재
                               >> 대신 모든 메서드가 static

    : round() : round(파라미터) 사용 반환 타입은 long
              : 무조건 소수 첫째자리에서 반올림 >> 특정 소수점까지 구하려면 10^n을 곱한 후 round()후 다시 10^n.0을 나눈다
              : 정수형 연산에서는 반올림이 이뤄지지 않으며 소수점 이하 버려짐 > 10^n.0로 필요한 소수점자리까지 구해내야 함

    : rint()  : round()처럼 소수점 첫째자리에서 반올림하나 반환 타입 double
              : 정가운데 값의 경우 가장 가까운 짝수 정수 반환 / -1.5는 round는 큰 값인 -1반환하나 rint는 -2반환

    : ceil(), floor() : 각각 올림과 버림 double반환
                      : floor의 경우 음수 버림을 할 경우 정수부분이 -1된다 / 왜냐하면 원래 숫자보다 작은 값을 리턴하기 때문

    : 예외를 발생시키는 메서드 : 연산명 + Exact를 붙임 : overflow감지함 (발생시 ArithmeticException 발생시킴)
                            (add/subtract/multiply/increment(++)/decrement/negate(-a)/toInt(int형변환)

                           : negate의 경우 int a 부호변환은 ~a + 1이므로 ~a가 int 최대값일 수 있음(0으로 한쪽은 한번 빠짐)
                           : Integer.MIN_VALUE(최소값) 100~000(-21~48)의 ~(비트전환연산)갑은 0111~111(21~47) int최대값
                           : 이 MAX_VALUE에서 + 1이 마저 처리되 부호변환이 이뤄지므로 오버플로우 발생
                           : Exact는 이런 경우 예외발생이 되므로 try-catch를 적용시켜 long으로 바꿔주는 등 처리 가능

 */

import java.util.Scanner;

public class Study_210416 {
    public static void main(String args[]) {

        System.out.println("1. mathRound()");
        System.out.println("2. mathExact");
        System.out.print("메소드 선택 : ");
        Scanner sc = new Scanner(System.in);
        Study_210416 study = new Study_210416();

        switch(sc.nextInt()) {
            case 1:
                study.mathRound();
                break;
            case 2:
                study.mathExact();
                break;
        }

    }

    public void mathRound() {
        double val = 90.7552;
        System.out.println("val = " + val);
        System.out.println("round(val) = " + Math.round(val));
        System.out.println();

        System.out.println("Math.round(val * 100) / 100 = " + Math.round(val * 100) / 100);
        System.out.println("Math.round(val * 100) / 100.0 = " + Math.round(val * 100) / 100.0);
        System.out.println();

        System.out.printf("ceil(%3.1f)=%3.1f%n", 1.1, Math.ceil(1.1)); // 3.1f 소수점포함 최소3자리 2번쨰자리서 반올림
        System.out.printf("floor(%3.1f)=%3.1f%n", 1.5, Math.floor(1.5));
        System.out.printf("round(%3.1f)=%d%n", 1.1, Math.round(1.1));
        System.out.printf("round(%3.1f)=%d%n", 1.5, Math.round(1.5));
        System.out.println();
        System.out.printf("rint(%3.1f)=%3.1f%n", 1.5, Math.rint(1.5));
        System.out.printf("rint(%3.1f)=%3.1f%n", -1.5, Math.rint(-1.5));
        System.out.printf("round(%3.1f)=%d1%n", -1.5, Math.round(-1.5));
        System.out.println();
        System.out.printf("ceil(%3.1f)=%3.1f%n", -1.1, Math.ceil(-1.1));
        System.out.printf("floor(%3.1f)=%3.1f%n", -1.1, Math.floor(-1.1));
    }

    public void mathExact() {
        int i = Integer.MIN_VALUE;

        System.out.println("i = " + i);
        System.out.println("-i = " + (-i));
        System.out.println("overflow!!");
        System.out.println();
        try {
            System.out.printf("negateExact(%d) = %d%n", i, Math.negateExact(i));

        } catch (ArithmeticException e) {
            System.out.printf("negateExact((long)%d) = (long)%d%n", (long)i, Math.negateExact((long)-i));
        }
    }
}
