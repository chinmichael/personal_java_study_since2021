package chapter9;

/*
삼각함수 계산
1. 피타고라스를 이용한 좌표 사이 거리 : sqrt(x, n) (n제곱근 계산)와 pow(x, n) (n제곱 계산) 메소드 이용

2.  직각삼각형 a(높이)b(밑변)c(빗변)이라 할 때 a = sinθ, b = cosθ (θ은 밑변과 빗변 사이각, 끼인각)
    JAVA 삼각함수 메서드 sin(rad), cos(rad) 매개변수 단위는 degree가 아니라 radian / 180 degree = PI radian
    따라서 a = c * sin(rad) 혹은 a = c * sin(PI / 180 * degree) (반환값은 double)

    atan2은 직각삼각형의 밑변과 높이로 끼인각을 구함 / 반환값은 역시 double, radian
    atan2의 결과값을 degree로 하고 싶다면 180/PI를 곱하던가 toDegrees(rad)를(반환값 double)이용

    degree : 우리가 일상에서 흔히 사용하는 한바퀴 360도 체계
    radian : 수학에서 주로 사용하는 각도체계 / 호의 길이와 반지름이 같게되는 각 = 1rad(이래뵈도 절대값) / 180degree = PI rad

지수, 로그 : 2^n은 10^x이라면 n * log10(2) = x로 상용로그 함수로 계산가능 (n자리 2진수를 10진수로 x자리까지 표현가능한지 확인가능)
         > 24 * log10(2) = 7.xx, 53 * log10(2) = 15.xx > float(가수 23자리 + 1)와 double(가수 52자리 + 1) 정밀도 관련


StrictMath클래스   : Math클래스는 성능을 위해 JVM이 설치된 OS의 메서드를 호출해 사용(OS의존적) > 부동소수점 등 계산에는 OS마다 dif
                    > 성능을 다소 포기하고 이런 OS마다의 차이를 없애기 위해 새로 작성한 클래스

그 밖에 Math 클래스 메서드
double, float, int, long 다 해당되는 경우 all이라 잠시 표현함

all abs(all a)  : 파라미터의 절대값 반환

all max(all a, all b), all min(all a, all b) : 두 값 중 큰 쪽 / 작은 쪽을 반환한다 (물론 두 파라미터 타입은 통일)

double random() : 0 ~ 1.0(1.0은 포함 안 함) 사이 double 값을 반환함 (그래서 0~9 구할 때 (int) Math.random() * 10 하는거)

double rint(double a) : 파라미터의 값에 가장 가까운 정수를 double로 표현 (단 1.5, -1.5등 정 가운데에서는 가까운 짝수를 반환)

 */

import static java.lang.Math.*;
import static java.lang.System.*;  // 두 클래스의 메서드를 인스턴스 호출없이 사용하기 위함

public class Study_210417 {
    public static void main(String args[]) {
        int x1 = 1, y1 = 1;
        int x2 = 2, y2 = 2;

        double c = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2)); // 두 좌표 사이 거리(피타고라스)

        double a = c * sin(PI / 4); // PI / 4 rad = 45 degree 위 좌표상 그려지는 직각삼각형은 이등변 삼각형
        double b1 = c * cos(PI / 4);
        double b2 = c * cos(toRadians(45));

        out.printf("a = %f%n", a);
        out.printf("b1 = %f%n", b1);
        out.printf("b2 = %f%n", b2);
        out.printf("c = %f%n", c);
        out.println();

        out.printf("angle = %f rad(atan2(a,b1))%n", atan2(a,b1));
        out.printf("angle = %f degree(atan2(a,b1) * 180 / PI)%n", atan2(a,b1) * 180 / PI);
        out.printf("angle = %f degree(toDegrees(atan2(a,b1)))%n", toDegrees(atan2(a,b1)));
        out.println();

        out.printf("24 * log10(2) = %f%n", 24 * log10(2)); // 24자리 2진수는 10진수로 7자리를 표현 가능
        out.printf("53 * log10(2) = %f%n", 53 * log10(2));

    }
}
