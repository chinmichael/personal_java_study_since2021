package chapter9;

/*
    java.math.BigDecimal
    double의 문제점 : 정밀도가 최대 13자리 / 10진 실수의 2진변환이 정확하지 않은 문제로 발생하는 오차 존재
    >> 정수 * 10-제곱의 형식으로 오차없이 저장

    >> final BigInteger intVal(정수파트) / final int scale(지수) / transient int precision(정밀도:정수자릿수)로 정의됨
    >> transient는 Serialize(직렬화)에서 제외처리, 데이터 전송에서 빼기위한 정도로 생각(나중에 다시)
    >> scale은 위에 -제곱이라 했듯 소수점을 기준으로 한다(당근 실수표현이니) 즉 scale 2면 실제 지수는 -2로 들어감
    >> precision은 정수파트의 전체 자리수
    ex 123.45는 intVal 12345 / scale은 2 / precision은 5로 저장됨

    BigDecimal 생성
    BigInteger때와 마찬가지로 기본형 리터럴로 생성자나 valueOf로 생성가능하나 표현한계로 보통 문자열로 생성
    특히 double타입 파라미터로 생성자를 통해 생성하면 오차가 발생할 수 있음
    new BigDecimal("0.1") >> 0.1 vs new BigDecimal(0.1) >> 0.1000...000555111...
    (그냥 문자열 쓰자...)

    다른 타입으로 변환
    기본형 변환은 BigInteger처럼 Number 상속으로 각각의 기본형 변환과 Exact변환을 가짐
    문자열 변환의 경우 toPlainString() (숫자로만 표현) toString (상황에 따라 지수형태 표현) 두가지가 있음
    BigDecimal초기화를 지수형태 파라미터로 했을 경우 toPlainString은 숫자형태로 toString은 지수형태로 표현

    BigDecimal 연산
    사칙연산 메서드의 형태는 기본 BigInteger와 동일 + 멤버변수가 final(애초에 하나는 BigInteger)이기에 결과값은 새로운 인스턴스로 생성

    사칙연산 결과에서 정수 지수 정밀도 처리를 유념해야함
    정수, 정밀도(말이 정밀도지 정수끼리 계산결과의 총길이)는 BigDecimal 변수저장 결과를 기준으로 정수파트의 계산결과로 생각
    123.45 * 1.0 은 BigDecimal기준 12345 * 10^-2와 10 * 10^-1를 계산하는 것 따라서 정수파트는 123450 정밀도는 6자리가 됨
    scale이 문제인데 +,-는 scale이 큰 쪽 / *는 scale을 더하고 / 나눗셈은 뺀다 (사칙연산 계산상 당연한 결과다 사실 이쪽도)

    나눗셈 >> 반올림 문제
    BigDecimal이 오차없는 실수를 저장해도 나눗셈에서는 오차가 발생하게 됨 + 반올림 문제 발생
    BigDecimal divide(BigDecimal divisor)은 추가 파라미터로 반올림처리방법과 scale을 더 정의가능
    BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode)
    BigDecimal divide(BigDecimal divisor, MathContext mc)
    BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) >> scale은 반올림해서 몇번째자리'까지' 표현하는가

    roundingMode는 int타입 지정도 되지만 BigDecimal에 정의된 열겨형 상수 RoundingMode.~를 사용하는 것이 좋다
    CEIILING (올림) / FLOOR (버림) / HALF_UP (5기준 우리가 아는 반올림) / UNNECESSARY (나머지 있음 예외발생)

    UP (크게만듦 > 양수일땐 올리고 음수일땐 버림) / DOWN (작게한다 > UP의 반대)
    HALF_DOWN (반올림 기준이 6이됨) / HALF_EVEN (반올림 자리 짝수 > HALF_UP / 홀수 > HALF_DOWN)

    UNNCESSARY가 아니어도 무한소수 발생 결과인데 반올림 모드 지정이 없음 예외발생 (BigDecimal저장 의의랑 구조상 당연한 결과)

    java.math.MathContext > 위에서 .divde()에 사용되는 부분 > precision(정밀도) + roundingMode(반올림모드)라 생각하면 됨
                                                                             > 그래서 위에 scale이 없었다
    주의할 점은 위에서 divide시 scale(소수점이하 자리수)과 RoundingMode를 지정해줬다면
    MathContext는 scale이 아닌 precision(계산결과 정수파트의 전체 자리수)
    bd1 = new BigDecimal("123.456")와 bd2 = new BigDecimal("1.0")을 divide한 경우
    bd1.divide(bd2, 2, RoundingMode.HALF_UP)은 scale이 2이기에 123.456에서 소수 2째자리까지 표현해 123.46가 되는 반면
    bd1.divide(bd2, new MathContext(2, RoundingMode.HALF_UP)은 precision이 2기준이기에 123456중 앞 두자리까지 표현(3번째자리서 반올림) 120 or 1.2E+2가 된다(E+2는 10^2)

    scale변경
    BigDecimal은 10단위의 곱하거나 나누는 것 = scale이 변경되는 것 > setScale로 대신할 수 있음
    BigDecimal setScale(int scale)
    BigDecimal setScale(scale, roundingMode) >> scale변ㄱ영은 10의 n제곱으로 나누는 처리나 마찬가지이므로 반올림지정을 해주어야함함
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Study_210427 {
    public static void main(String args[]) {
        BigDecimal bd1 = new BigDecimal("123.456");
        BigDecimal bd2 = new BigDecimal("1.0");
        BigDecimal result1 = bd1.divide(bd2, 2, RoundingMode.HALF_UP);
        BigDecimal result2 = bd1.divide(bd2, new MathContext(2, RoundingMode.HALF_UP));
        BigDecimal result3 = bd1.setScale(2, RoundingMode.HALF_UP);
        System.out.println("123.456 divide scale2 1.0 : " + result1);
        System.out.println("123.456 divide precision2 1.0 : " + result2);
        System.out.println(result2.toPlainString());
        System.out.println("123.456 setScale2 : " + result2);
        System.out.println();

        System.out.println("bd1 = "+bd1);
        System.out.println("unscaledValue (value) : "+bd1.unscaledValue());
        System.out.println("scale() : "+bd1.scale());
        System.out.println("precision() : "+bd1.precision());
        System.out.println();

        BigDecimal bd3 = bd1.multiply(bd2);
        System.out.println("bd3 = bd1.multi(new BigDecimal(1.0)) : " +bd3);
        System.out.println("unscaledValue (value) : "+bd3.unscaledValue());
        System.out.println("scale() : "+bd3.scale());
        System.out.println("precision() : "+bd3.precision());
        System.out.println();

    }
}
