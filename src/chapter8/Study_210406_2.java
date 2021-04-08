package chapter8;

/*
    finally > try-catch에서 선택적으로 사용가능 / 예외 발생여부 상관없이 실행할 코드를 포함
    예를 들어, 설치 프로그램에서 설치가 성공하던 실패하던 마지막에 임시파일을 삭제하는 처리 등

    try-with-resource문 > I/O관련 클래스를 사용할 때 유용함
    >>I/O클래스 중 사용 자원 반환을 위해 반드시 close를 해야하는 case의 경우
    >>try-catch시 finally구문에 .close()메소드로 처리 >> 문제는 close()가 예외를 발생시킬수 있음 >> finally에도 try-catch쓰게 됨(복잡)
    finally {
       try {
            if(dis != null) dis.close();
       } catch(IOException ie) {
            ie.printStackTrace();
       }
    }
    >> 가장 문제는 try와 finally 모두 예외 발생시 try의 예외는 무시됨

    >>try-with-catch의 괄호 안 객체 생성구문을 넣으면 따로 close()를 호출하지 않아도 try를 벗어날 때 자동 호출됨
      이를 위해서 각 클래스의 적절한 자원반환작업을 위한 AutoCloseable I/F를 구현한 클래스여야함

      >>만약 자동호출된 close()에서 Exception 발생할 경우
        기존 try-catch에서 finally의 close()의 예외만 처리되는 것과 달리,
        실제 발생한 예외와 함께 이에 따른 close()의 예외가 suppressed(억제된)예외로 함께 출력
        >> 두 예외가 동시 발생할 수 없기에 실제 발생 예외에 정보 저장 by Throwable의 addSuppressed메소드 

   ps. 괄호 안에 두 문장 이상을 넣을 경우 ;로 구분
 */
public class Study_210406_2 {
    /*public static void main(String args[]) {
        study_210406_2.method1();
        System.out.println("method1을 마치고 main으로 복귀");

    }

    static void method1() {
        try {
            System.out.println("method1이 종료됨");
            return; // return이 된 뒤에도 밑에 finally는 수행하고 넘어간다
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("method1의 finally 실행");
        }
    }*/

    public static void main(String args[]) {
        try (CloseableResource cr = new CloseableResource()) {
            cr.exceptionWork(false); // 예외 발생 안함
        } catch(WorkException e) {
            System.out.println(e.getMessage()); // IDE에서 로그 보여주는게 에러파트는 밑에 다 떠서 일부러 처리함
            e.printStackTrace();
        } catch(CloseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println();

        try (CloseableResource cr = new CloseableResource()) {
            cr.exceptionWork(true); //throw WorkException 실행
        } catch(WorkException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch(CloseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

class CloseableResource implements AutoCloseable { //내부 클래스를 이용하는 케이스 (I/F구현해야 하므로)

    public void exceptionWork(boolean exception) throws WorkException {
        System.out.println("exceptionWork("+exception+")가 호출됨");
        if(exception) throw new WorkException("WorkException 발생");
    }

    public void close() throws CloseException {
        System.out.println("close()가 호출됨");
        throw new CloseException("CloseException발생");
    }
}

class WorkException extends Exception { //내부 클래스를 이용하는 케이스 (클래스 상속)
    WorkException(String msg) {
        super(msg);
    }
}

class CloseException extends Exception {
    CloseException(String msg) {
        super(msg);
    }
}
