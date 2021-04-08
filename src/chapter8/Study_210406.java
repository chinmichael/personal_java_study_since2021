package chapter8;

import java.io.File;

/*
메서드 선언부 throws 적용Exception : try-catch 구문을 사용하는 것 외의 예외를 메서드에 선언하는 방법
>> 메서드를 사용하기 위해 어떤 예외들이 처리되야 하는지 알리고 + 처리하도록 강요 가능
>> RuntimeException은 일반적으로 선언하지 않음 >> 아마 실행을 위해서는 반드시 예외처리를 해줘야하는 영역의 예외들을 명시함

>> 중요한 것은 throws는 정확히는 예외를 처리하는 것이 아닌 해당 메서드를 호출하는 메서드에서 예외를 처리하게(try-catch) 넘기는 것

ex java API문서의 wait메서드는 throws InterruptedException이 되어 있음 > '이 메서드를 호출하는 메서드에서'는 이에 대한 처리가 필요
   IllegalMonitorStateException은 RuntimeException의 자손클래스 이므로 wait메서드에 throws 선언 하지 않음

>>  메서드 자체에서 발생하는 예외가 처리가능한 경우는 try-catch를 사용
    호출한 메서드에서 넘겨받을 값 등을 다시 처리해 해결해야 하는 경우 throws로 넘김
 */
public class Study_210406 {

    /*public static void main(String[] args) {
        method1(); // 같은 클래스 내 static 멤버이므로
    }

    static void method1 () // throws Exception --> 이를 주석처리 하면 throws Exception처리된 method2의 Exception을 넘기지 못하므로 여기서 처리 필요
    {
        try {
            method2();
        } catch (Exception e) {
            System.out.println("method1에서 예외처리");
            e.printStackTrace(); // 예외가 method2에서 발생했으며(실행로그는 역순) method1에서 캐치됨을 알 수 있음
        }
    }

    static void method2 () throws Exception {
        throw new Exception(); // 예외 발생 지점 (throw)
    }*/

    public static void main(String[] args) {
        try {
            File f = createFile(args[0]);
            System.out.println(f.getName() + " 파일이 성공적으로 생성되었습니다");
        } catch (Exception e) {
            System.out.println(e.getMessage() + " 다시 입력해주세요");
        }
    }
    static File createFile(String fileName) throws Exception {
        if(fileName == null || fileName.isEmpty()) throw new Exception("파일명이 유효하지 않습니다");

        File f = new File(fileName);
        f.createNewFile();
        return f;
    }

}
