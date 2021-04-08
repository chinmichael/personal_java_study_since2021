package chapter8;

/*
오류 or 에러 == 프로그램 실행 중 오작동이나 비정상적인 종료의 원인
컴파일 에러 == 컴파일 시(소스코드의 기본적인 검사 후 컴파일 및 클래스 파일 생성 중) 발생
런타임 에러 == 실행도중 발생하는 에러(컴파일러는 실행 시 발생하는 잠재적 오류까지 검사 못함)
논리적 에러 == 의도와는 다르게 동작하는 것

자바의 런타임 에러 >> error와 exception으로 구분
error ==  OutOfMemoryError나 StackOverFlowError 같은 발생 시 복구 불가능한 심각한 오류
exception == 적절한 코드 작성에 따라 비정상적인 종료를 막고 수습 가능한 오류

자바에서 클래스로 정의된 발생가능 오류들 (Object의 자식클래스 Throwable의 자식클래스 Exception과 Error)
Exception의 최고 조상 Exception 클래스
Exception의 자식 클래스 RuntimeException의 하위 클래스 : 주로 프로그래머 실수에 의해 발생
>> 배열범위 벗어나거나 NullPointException 등
그 밖에 Exception의 자식 클래스 : 주로 클라이언트 동작이 원인>

exception handling(예외처리) : 어쩔 수 없는 에러와 달리 예외 대비 코드를 통해 비정상 종료 방지 정상 실행 유지하는 것
if 예외 처리를 하지 못하는 경우 비정상종료 + JVM의 예외처리기가 에러 원인을 출력
by try-catch구문

try(예외 발생 가능 코드)-catch구문(예외 발생 시 처리를 위한 코드 >> 처리한 경우 checked예외 아니면 unchecked)
하나의 메서드 안에 여러 try-catch 구문 사용 가능
try 혹은 catch블럭 안에 다른 try-catch 구문 사용 가능

try-catch구문에서는 try내 예외 발생 시 해당 catch구문으로 넘어가 실행(위에서 부터 차례로 검사됨),
오류 발생 시점 이후의 try 구문의 코드는 수행되지 않음

예외 발생 시 해당 예외의 클래스 인스턴스 생성 >> 인스턴스의 instanceof연산자를 이용 해당 참조변수의 catch()검색
>>위에서 부터 차례로 검사되기에 특정 Exception 처리 후 남은 걸 뒤에 catch(Exception e)로 몰빵처리 가능

catch블록에서만 사용 가능한 예외정보 메서드
printStackTrace() : 예외 발생 당시 call stack에 있던 메서드 정보와 예외 메시지를 화면에 출력
>>printStackTrace(PrintStream s) or printStackTrace(PrintWriter s) 예외 정보 파일 저장 가능
getMessage() : 발생한 예외 클래스 인스턴스의 저장된 메시지 get함 (String)

멀티 catch블럭 > 기호 | 이용(논리연산자 아님)
>catch (ExceptionA e) {} 와 catch (ExceptionB e) {} >> catch(ExceptionA | ExceptionB e) {} 동시 처리 가능
>두 예외 클래스는 조상과 자손 관계가 아니어야 함 (랄까 그러면 조상 클래스만 쓰면 되지)
>e가 둘 중 어디에 해당할지 모르므로 둘 공통분모인 조상클래스의 멤버와 메서드만 사용 가능(instancof로 확인하느니 걍 구분)

예외 발생 시키기 > new로 발생시키려는 예외의 객체 생성 후 > throw로 해당 참조변수를 불러와 고의적 예외 발생 가능

*/

public class Study_210405 {
    /*public static void main(String args[]) {
        int number = 100;
        int result = 0;

        for(int i = 0; i < 10; i++) {
            try {
                result = number / (int) (Math.random() * 10);
                System.out.println(result); // 만약 위에서 에러가 발생 시, 여기는 실행되지 않고 catch로 넘어감
            } catch (ArithmeticException e) { // Exception e로 하는 경우 모든 예외의 조상클래스라 instanceof연산결과가 모두 true로 잡힘
                System.out.println("divide 0 error"); // 없다면 0을 나누는 순간 프로그램 비정상 종료
                e.printStackTrace();
                System.out.println("예외 메시지 : " + e.getMessage());
            }
        }
    }*/

    public static void main(String args[]) {
        // throw new Exception("컴파일조차 안되는 에러발생");
        // throw new RuntimeException("실행은 되지만 비정상 종료되는 에러발생");

        try {
            Exception e = new Exception("고의 발생"); // getMessage()로 얻을 메시지 저장
            throw e; // throw new Exception("~");

        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("프로그램 정상 종료");
    }
}
