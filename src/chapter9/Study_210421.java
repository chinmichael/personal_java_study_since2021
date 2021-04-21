package chapter9;

/*
Scanner 클래스 (java.util.Scanner)
JDK 1.5부터 추가된 / 입력소스(화면, 파일, 문자열)로부터 문자데이터를 읽어오기 위한 클래스

다음과 같은 파라미터의 입력소스를 읽어올 수 있음
Scanner(String source)
Scanner(File source)
Scanner(Readable source)
Scanner(ReadableByteChannel source)
Scanner(Path source)
Scanner(InputStream source)

현재 JAVA 콘솔 화면으로부터 입력받는 방법 3가지
JDK 1.5이전
BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
String input = br.readLine();

JDK 1.5이후
Scanner sc = new Scanner(System.in);
String input = sc.nextLine();

JDK 1.6이후
Console console = System.console();  >> '화면' 입출력만 전담하는 java.io.Console 추가(안되는 IDE 존재)
String input = console.readLine();

Scanner는 정규식을 이용한 라인단위 검색 지원

Scanner useDelimiter(String pattern) or Scanner useDelimiter(Pattern pattern)
1. 딱봐도 구분자(','와 같은 delimiter)를 이용해 기준에 따라 각각에 데이터를 읽게 할 수 있음
2. Scanner는 정규식 검색 가능 >> delimiter에도 정규식 사용 가능(구분자가 복잡할 때도 사용 구분 가능)

String 입력 메서드 next() : 공백(\\s)를 기준으로 구분 입력, nextLine() : 1라인, 개행문자(\\n)기준 구분 입력
next() : 다음 토큰을 읽음
nextLine() : 다음 행을 읽음
hasNext() : 토큰이 있는지 확인, 공백은 무시 (공백이 아닌 문자가 있는지) hasNext() checks if there are any more non-whitespace characters available.
hasNextLine() : 행이 있는지 확인, \n도 읽기에 줄바꿈이 있어도 true / hasNextLine() checks to see if there is another line of text available.
밑에 3번째 메서드 확인

기본형 입력 메서드 next기본형() : 각각의 기본형에 맞게 입력문자열 자동변환 (데이터형식 미스매치시 : inputMismatchException)

아래 메서드에서 하는김에 예외처리도 함 확인 (입출력 관련에는 예외처리가 필요한 경우가 많다)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Study_210421 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //백준 등에서 성능문제 때문에 꽤 쓰므로 함 써봄
        System.out.println("1. Scanner 기본 입력 처리 : 공백처리 주의해서 볼것");
        System.out.println("2. Scanner file 읽기");
        System.out.println("3. Scanner useDelimiter 사용");
        System.out.print("사용할 메서드 번호를 입력 : ");

        Study_210421 study = new Study_210421();
        Integer input;
        try {
            input = Integer.parseInt(br.readLine());   //오우야 readLine은 예외처리 해줘야하네
            switch (input) {
                case 1:
                    study.scannerEx1();
                    break;
                case 2:
                    study.scannerEx2();
                    break;
                case 3:
                    study.scannerEx3();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scannerEx1() {
        Scanner sc = new Scanner(System.in);
        String[] argArr = null;

        while(true) {
            String prompt = ">>";
            System.out.print(prompt);

            String input = sc.nextLine(); // 라인단위 (개행단위)로 입력

            input.trim();
            argArr = input.split(" +"); // 공백을 구분자로 자름 >> " +"정규식은 하나 이상의 공백(공백문자가 여러개일수 있음)

            String command = argArr[0].trim(); // trim은 앞뒤 공백만 자르므로
            if("".equals(command)) continue; // 배열 사이(즉 원본 중간 공백)일 경우 담으로 넘김

            command = command.toLowerCase();

            if(command.equals("q")) {
                System.exit(0); // while true로 계속 입력을 받으므로 'q'입력시 현재 프로그램이 종료되게 만든다
            } else {
                for(int i=0; i< argArr.length; i++) {
                    System.out.println(argArr[i]);
                }
            }
        }
    }

    public void scannerEx2() throws IOException {
        // File 생성은 예외처리가 필요 짜피 main에 readLine하는 김에 try-catch를 Exception으로 했으니 거기서 처리하게 보냄

        Scanner sc = new Scanner(new File("C:\\Users\\chinm\\git\\personal_java_study_since2021\\reference\\chapter9\\scannerData1.txt"));
        // 경로 지정 : /루트 ./현재위치 ../상위디렉터리 근데 상대경로 계속 안먹혀서... 우선 절대경로로
        int sum = 0;
        int cnt = 0;

        while(sc.hasNext()) { // 입력이 있을 경우 true로 반환 hasNextInt()해도 됨
            sum += sc.nextInt();
            cnt++;
        }
        System.out.println("합계 : " + sum);
        System.out.println("평균 : " + (double)sum/cnt);
    }

    public void scannerEx3() throws Exception {
        Scanner sc = new Scanner(new File("C:\\Users\\chinm\\git\\personal_java_study_since2021\\reference\\chapter9\\scannerData2.txt"));
        int cnt = 0;
        int totalSum = 0;

        while(sc.hasNextLine()) { //줄마다 입력 있는지 확인
            String line = sc.nextLine(); // 줄마다 입력값 가져옴
            Scanner sc2 = new Scanner(line).useDelimiter(","); //구분자로 입력받은 걸 다시 나눠 입력
            int sum = 0;

            while(sc2.hasNextInt()) { // 얘는 Next이므로 공백으로 구분하여 입력값 있는지 확인
                sum += sc2.nextInt();
            }
            System.out.println(line + ", sum = " + sum);
            totalSum += sum;
            cnt++;
        }
        System.out.println("Line : " + cnt + ", Total : " + totalSum);
    }
}
