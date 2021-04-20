package chapter9;

/*
java.util.regex (정규식 Regular Expression 패키지)

정규식 : 텍스트 데이터 중 원하는 조건(패턴)과 일치하는 문자열을 찾기 위해(+체크하기 위해) 사용하는 것
      : 미리 정의된 기호와 문자로 이뤄진 문자열
      : 워낙 양이 방대하기에 자주쓰이는 작성 예를 보고 응용할 수 있는 범위로 우선 가자

      : Pattern pattern = Pattern.compile(regex); > 정규식 regex 정의함
        (정규식 regex를 매개변수로 / Pattern클래스 static 메서드 compile을 호출해 / Pattern 인스턴스 생성)
        Matcher : 정규식 패턴과 데이터를 비교하는 역할 Matcher match = pattern.matcher(CharSequence pattern);
        (정규식 비교대상 데이터를 매개변수로 / Pattern클래스 Matcher타입 matcher메서드 호출해 Matcher인스턴스 생성)
        match.matches()로 판별

        + CharSequence는 I/F CharBuffer, String, StringBuffer가 이들의 구현 클래스

자주 쓰이는 정규식 패턴 (보고 밑에 2번 실행해보기)]
c[a-z]* : c로 시작하는 영단어
c[a-z]  : c로 시작하는 두자리 영단어
c[a-zA-Z] : c로 시작하는 두자리 영단어 (뒤는 대소문자 구분 안함 위 2케이스는 뒤에 소문자만)
c[a-zA-Z0-9] (=c\w) : c로 시작하고 숫자, 영어로 구성된 두 글자

.* : 모든 문자열
c. : c로 시작하는 두자리 문자열
c.* : c로 시작하는 모든 문자열
c\. : c.과 일치하는 문자열 : '.'는 패턴작성에 사용되므로 escape문자 '\'를 사용해야함

c.*t : c로 시작하고 t로 끝나는 모든 문자열
.*a.* : a를 포함하는 모든 문자열
.*a.+ : a를 포함하는 모든 문자열 // + : *와 달리 하나 이상의 문자가 포함되야 하므로 a로 끝나는 경우는 여기서 해당하지 않음

c[0-9] (=c\d) : c와 숫자로 구성된 두자리 문자열

[b|c].* = [bc].* = [b-c].* : b 또는 c로 시작하는 문자열
[^b|c].* = [^bc].* = [^b-c].* : b 또는 c로 시작하지 않는 문자열
[b|c].{2} : b 또는 c로 시작하는 3자리 문자열({2}2자리 의미)


정규식 그룹화 : ()로 묶어 그룹화 가능(3번째 메서드 참조)
예를 들어 전화번호의 경우 : (0\d{1,2})-(\d{3,4})-(\d{4}) : 2~3자리 정수문자열 - 3~4자리 정수문자열 - 4자리 정수문자열
또한 묶인 그룹 정규식을 따로 사용 가능
Matcher match >> match.group() or match.group(0) : 전체 / match.group(int i) i번째 그룹 정규식 적용

match.find() : 주어진 data에서 패턴과 일치하는 부분이 있으면 true반환
             : 다시 find()를 호출하면 이전에 탐색하고 반환한 그 다음 시점부터 다시 탐색

             : 매칭하는 곳을 찾으면 start()와 end()로 매칭 위치를 알 수 있음

             : appendReplacement(StringBuffer sb, String replacement)을 이용해 원하는 문자열로 치환할 수 있음
               >> 첫번째 or 이전 탐색 지점의 'end'부터 이번 탐색지점의 'start'부분 + 변경 문자열로 버퍼에 저장
             : appendTail(StringBuffer sb) : 마지막으로 치환된 이후 부분을 버퍼에 저장

             : 이 시퀀스는 밑에 4번째 메서드 참조

 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Study_210420 {
    public static void main(String args[]) {
        System.out.println("실행할 메서드 선택");
        System.out.println("1. 정규식 연습 (1)");
        System.out.println("2. 자주 쓰이는 패턴 리스트");
        System.out.println("3. 정규식 그룹화 및 find()");
        System.out.println("4. find() 후 위치 탐색 및 매칭 문자열 변경");
        System.out.print("실행할 메서드 입력 : ");
        Scanner sc = new Scanner(System.in);
        Study_210420 study = new Study_210420();

        switch (sc.nextInt()) {
            case 1:
                study.regexEx1();
                break;
            case 2:
                study.regexEx2();
                break;
            case 3:
                study.regexEx3();
                break;
            case 4:
                study.regexEx4();
                break;
        }
    }

    public void regexEx1() {
        String[] data = {"bat", "baby", "bonus", "cA", "ca", "co", "c.", "c0", "car", "combat", "count", "date", "disc"};

        Pattern pattern = Pattern.compile("c[a-z]*"); //c로 시작하는 소문자 영단어

        for(int i=0; i<data.length; i++) {
            Matcher match = pattern.matcher(data[i]);
            if(match.matches()) System.out.print(data[i] + " / ");
        }
    }

    public void regexEx2() {
        String[] data = {"bat", "baby", "bonus", "cA", "ca", "co", "c.", "c0", "c#",
                "car", "combat", "count", "date", "disc" };

        String[] regex = {".*", "c[a-z]*", "c[a-z]", "c[a-zA-Z]", "c[a-zA-Z0-9]",
                "c.", "c.*", "c\\.", "c\\w", "c\\d",
                "c.*t", "[b|c].*", ".*a.*", ".*a.+", "[b|c].{2}" };

        for (int i = 0; i < regex.length; i++) {
            Pattern pattern = Pattern.compile(regex[i]);
            System.out.println("Pattern : " + regex[i] + " 의 결과 :");
            for (int j = 0; j < data.length; j++) {
                Matcher match = pattern.matcher(data[j]);
                if (match.matches()) {
                    System.out.print(data[j] + " / ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    public void regexEx3() {
        String source = "HP : 011-1111-1111, HOME : 02-999-9999";
        String regex = "(0\\d{1,2})-(\\d{3,4})-(\\d{4})";

        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(source);

        int i = 0;
        while(match.find()) {
            System.out.println(++i + " : " + match.group() + " >> " + match.group(1) + " / "
                                                                    + match.group(2) + " / "
                                                                    + match.group(3));
        }
    }

    public void regexEx4() {
        String source = "A broken hand works, but not a broken heart.";
        String regex = "broken";
        StringBuffer sb = new StringBuffer();

        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(source);
        System.out.println("source : " + source);

        int i = 0;

        while(match.find()) {
            System.out.println(++i + "번째 매칭");
            System.out.println("start() ~ end() : " + match.start() + " ~ " + match.end());
            match.appendReplacement(sb, "drunken");
            // 첫번째 or 이전 탐색 지점의 end부터 이번 탐색지점의 start부분 + 변경 문자열로 버퍼에 저장
            System.out.println();
        }

        match.appendTail(sb); // 마지막으로 치환된 이후 부분을 버퍼에 저장
        System.out.println("Replace Count = i : " + i);
        System.out.println("Result sb : " + sb.toString());
    }
}
