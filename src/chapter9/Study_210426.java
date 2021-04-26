package chapter9;

/*
    java.math.BigInteger클래스 : long(19자리 10진수)으로도 안되는 더 큰규모의 수 다룰때(보통 과학적게산 등 / 10의 60억제곱까지 가능)
                              : 내부적으로 int 배열을 통해 다름 (성능은 떨어지나 다루는 범위가 큼)
                              : final int signum;(부호: 1/0/-1) final int[] mag;
                              : String처럼 final처리된 배열 >> 값이 변하면 새로운 인스턴스를 생성해서 처리하는 것
                              : 부호처리는 signum을 기준으로 2의 보수법에 맞게 처리(머 정수형이 다 그렇다)

                              : 2의 보수법
                                2진수 음수를 단순히 부호 비트를 사용한 경우 대응하는 수를 더했을때 0이 되지 않고 / 2진음수가 증가할때 10진수로는 감소한다
                                n의보수는 더했을때 n이 되는 수
                                2진수로 2는 10이고 이는 자리올림 발생하고 0이 되는 수기에 2의 보수를 기준으로 하면 서로 더했을때 0이됨
                                2진수 2의 보수는 1의보수(1을0, 0을1) + 1로 계산한다

    BigInteger 생성 : 정수형 리터럴로는 값의 한계가 있어서 쓰는거다보니 (이것도 되긴하지만) 보통 문자열로 생성
                     new BigInteger("숫자", radix) 진수(radix)는 생략시 기본 10진수

    BigInteger 타입변환
    Number의 하위 클래스 > 기본형 int,long,float,double의 ~Value()와 타입 안 맞으면 예외 발생시키는 ~ValueExact()
    문자열 toString() toString(radix) 당빠 있고
    bytep[] toByteArray[]도 있음

    BigInteger 연산
    기본 정수 연산 : 해당 연산 이름의 메서드를 통해 BigInteger.~(BigInteger val)로 계산 > 물론 결과는 수치가 변하므로 String처럼 새로운 인스턴스가 생성되어 반환

    비트 연산 : 큰수를 다루기에 성능향상을 위한 비트 연산이 중요 (당빠 and, or, xor, not)은 물론 아래도 요긴하게 쓸 수 있음
    int bitCount() : 2진수 표현시 1의 개수(음수는 0의 개수) 반환
    int bitLength() : 2진수 표현시 값을 표현하는데 필요한 bit 수
    boolean testBit(int n) : 우측 n+1번째(컴터는 항상 0부터 세어나가므로) 1이면 true
    BigInteger setBit(int n) : 우측 n+1번째 비트 1로 변경
    BigInteger clearBit(int n) : 위의 반대
    BigInteger flipBit(int n) : 우측 n+1 비트 1이면 0, 0이면 1로 전환

    비트연산이 중요한 이유
    만약 그 변수의 값이 짝수인지 확인할 때(2로 나눈 나머지가 0인가)
    기본연산매서드르 사용하면 길고 복잡해짐 remainder로 BigInteger(2)로 나누고 equals로 BigInteger.ZERO랑 비교
    근데 짝수면 비트상 가장 오른쪽 값이 0일 것이므로 testBit(0) = false인지 확인하는게 훨씬 효율적이다

*/

import java.math.BigInteger;

public class Study_210426 {
    public static void main(String args[]) throws Exception {

        for(int i=1; i<100; i++) {
            System.out.printf("%d!=%s%n", i, calcFactorial(i));
            Thread.sleep(300); // 300밀리세컨드(0.3초 휴식을 줘서 텀을 주는 지연처리)
        }

    }

    static String calcFactorial(int n) {
        return factorial(BigInteger.valueOf(n)).toString();
    }

    static BigInteger factorial(BigInteger n) {
        if(n.equals(BigInteger.ZERO)) return BigInteger.ONE;
        else return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }
}
