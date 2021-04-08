package chapter8;

import java.util.Scanner;

/*
    사용자 정의 예외 : 가능한 기존 예외클래스 사용이 좋지만, 필요에 따라 기존 예외클래스를 상속받아(주로 Exception, RuntimeException) 생성 가능

    기본 팁1 : Exception클래스는 생성 시 String값을 파라미터로 예외메시지 저장 가능
              NewException(String msg) { super(msg); }로 String매개변수 생성자를 상속 가능

    전에는 이런 checked예외 작성을 Exception을 상속 받아서 함 >> 불필요한 경우에도 try-catch처리 필요
    요즈엔 선택적 예외처리를 위해 RuntimeException을 상속 받아 처리하는 unchecked 경우가 많음 (JAVA 프로그래밍 환경 변화 : 필수처리 예외가 선택처리도 되는 경우 발생)

    예외 복합처리
    한 메소드에 여러 예외 : 몇 개는 try-catch로 자체처리 나머지는 throws로 호출메서드에 넘기기 가능
    예외 되던지기(exception re-throwing)  : 예외 처리 후 인위적으로 재발생시킴 >  한 예외에 대해 발생메서드와 호출메서드 양쪽 처리 가능
                                        : 발생가능 메서드 try-catch처리 후 catch에 throw로 해당 예외 발생 + 발생가능 메서드에 throws 선언
                                        : 반환값 있는경우 catch에도 return (물론 finally문에도 가능, try나 catch 후 return되며 최종적으로 finally return 반환)
                                          >> 만약 catch에서 예외되던지기 throw를 한 경우 return이 없어도 됨

    연결된 예외
    한 예외가 다른 예외를 발생시킬 수 있음 : 새로운 발생의 원인이 되는 예외 = 원인예외 cause exception
    원인예외 발생시키는 방법 : 발생예외 인스턴스 생성 후 initCause()로 예외원인(causeException ce) 등록 initCause(ce)
                           initCause()의 경우 Exception의 조상클래스인 Throwable에 정의 됨 (getCause()는 원인예외 반환)
                           RuntimeException의 경우 생성자의 파라매터로 등록 가능 (RuntimeException(Throwable cause)

    >사용이유   : 예외를 하나의 큰부류로 묶기 위해 if 상속을 통해 묶기에는 실제 발생한 예외가 무엇인지 모르고 관계가 꼬일 수 있음
               : checked예외에서 unchecked예외로 전환하기 위해(컴퓨터 환경 변화로 checked예외지만 처리가 안될 경우 무의미한 try-catch남발 가능)

*/
public class Study_210407 {
    public static void main(String args[]) {
        System.out.println("1 : newExceptionTest");
        System.out.println("2 : reThrowsMethod1");
        System.out.println("3 : chainedErrorTest");
        System.out.print("실행할 메소드 번호를 입력 : ");
        Scanner sc = new Scanner(System.in);

        Study_210407 study = new Study_210407();

        switch (sc.nextInt()) {
            case 1:
                study.newExceptionTest();
                break;
            case 2:
                study.reThrowsMethod1();
                break;
            case 3:
                study.chainedExceptionTest();
                break;
        }
    }

    public void chainedExceptionTest() {
        try {
            newInstall();
        } catch (InstallException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void newInstall() throws InstallException {
        try {
            startInstall(); copyFile();
        } catch (SpaceException se) {
            InstallException ie = new InstallException("설치중 예외 발생");
            ie.initCause(se); // throws 된 호출 메서드에서 위 에러 처리 이후 casued by로 se로그가 뜬다
            throw ie;
        } catch (MemoryException me) {
            InstallException ie = new InstallException("설치중 예외 발생");
            ie.initCause(me);
            throw ie;
        } finally {
            deleteTempFile();
        }
    }

    public void reThrowsMethod1() {
        try {
            reThrowsMethod2();
        } catch(Exception e) {
            System.out.println("reThrowsMethod1에서 남은 예외처리");
        }
    }
    static int reThrowsMethod2() throws Exception {
        try {
            throw new Exception(); // return 반환은 없지만 예외 보내기 가능
        } catch (Exception e) {
            System.out.println("reThrowsMethod2에서 일부 예외처리");
            throw e;
        }
    }

    public void newExceptionTest () {
        try {
            startInstall(); copyFile();
        } catch (SpaceException e) {
            System.out.println("에러 메시지 : " +e.getMessage());
            e.printStackTrace();
            System.out.println("공간 확보 후 재설치 권장");
        } catch (MemoryException e) {
            System.out.println("에러 메시지 : " +e.getMessage());
            e.printStackTrace();
            System.out.println("재설치 권장");
        } finally {
            deleteTempFile();
        }
    }

    static void startInstall () throws SpaceException, MemoryException {
        if(!enoughSpace()) throw new SpaceException("설치공간 부족");
        if(!enoughMemory()) {
            throw new MemoryException("메모리 부족");
            //throw new RuntimeException(new MemoryException("메모리부족"));
        }
    }
    static void copyFile() {} static void deleteTempFile() {}
    static boolean enoughSpace() {return false;} static boolean enoughMemory() {return true;}
}

class InstallException extends Exception{
    InstallException(String msg) {
        super(msg);
    }
}

class SpaceException extends Exception {
    SpaceException(String msg) {
        super(msg);
    }
}
class MemoryException extends Exception {
    MemoryException(String msg) {
        super(msg);
    }
}