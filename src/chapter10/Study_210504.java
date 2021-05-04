//package chapter10

/*  오늘부터 그냥 VSCode로 달린다. (추천 플러그인 : Java Extension Pack)
    >>실행이 빠르기도하고 import같은 자동완성도 최소한해서 최대한 java개인 공부할 땐 손코딩하자

    ※ChoiceFormat : 특정 범위에 속하는 값(limits)을 문자열(value)로 치환해준다.

    >> 경우에 따라 if나 switch문이 복잡해질 경우 간단하고 직관적인 처리가 되는 방법으로 쓰일 수 있음

    >> 사용방법
    1. 경계값 배열(limits)와 치환될 문자열(value)를 지정하여 이 둘을 파라미터로 ChoiceFormat(limits, value)를 초기화
        - 이때 limits는 double 배열 + 반드시 오름차순 정렬
        - 치환 문자열의 경우 limits의 같은 사이즈의 배열이어야 함 >> 이 둘이 안 지켜지면 IllegalArgumentException
    2. 문자열로 패턴을 지정하여 이를 파라미터로 ChoicFormat(String pattern)을 초기화
        - 패턴 구분자는 #(경계값 포함), <(경계값 미포함), |(각 패턴구분)이용 >> 아래 코드에서 패턴 적용시 80점을 주의

    3. 이제까지와 마찬가지로 .format(data)를 호출하여 형식화를 함


    ※MessageFormat : 데이터를 정해진 양식에 맞게 처리되도록 처리
    
    >> 다수의 데이터를 같은 양식으로 출력하거나 or 하나의 데이터를 다수의 양식으로 전환할 때 매우 유용 (역으로 양식에서 데이터 추출하는 것 포함)
    >> 여러 사용처가 있지만 특히 csv 같은 파일을 가지고 DB SQL문을 작성한다는 등의 처리에 매우 유용 (아 진즉 알았음...)
    
    >> 사용방법
    1. .format() : 문자열 양식,패턴(pattern)과 양식에 들어갈 데이터(argument)를 지정하여 이 둘을 파라미터로 MessageFromat.format(pattern, argument) 메서드를 호출
        - 양식에 들어갈 데이터 argument는 객체배열(Object[])라 다양한 데이터 타입을 넣을 수 있다.
        - 양식 pattern에는 데이터 배열의 각 배열 index를 기준으로 데이터가 들어갈 자리를 {index}로 넣는다(한 데이터를 여러곳 넣을 수 있으므로 중복 가능) (코드 확인)
        - MessageFormat.format()의 반환타입은 String임

    2. .parse() : 문자열 패턴(pattern)을 파라미터로 MessageFormat 인스턴스를 생성 + 패턴에 맞게 기록된 문자열 데이터(source)을 파라미터로 parse.(source)를 실행한다.
        - 위와 역으로 당연히 .parse()의 반환타입은 Object[]이다. 
        - 예외처리가 필요하다
*/

import java.util.Scanner;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.io.File;

public class Study_210504 {
    public static void main(String args[]) {

        System.out.println("1. ChoiceFormat 연습");
        System.out.println("2. MessageFormat 연습1");
        System.out.println("3. MessageFormat 연습2 : DB 자료 처리시 유용해보이는 방법");
        System.out.println();
        System.out.print("실행할 메서드를 선택해 주세요 : ");
        Scanner sc = new Scanner(System.in);

        switch (sc.nextInt()) {
            case 1:
                choiceFormatEx();
                break;
            case 2:
                messageFormatEx1();
                break;
            case 3:
                try {
                    messageFormatEx2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        sc.close();
    }

    public static void choiceFormatEx() {
        double[] limits = { 60, 70, 80, 90 };
        String[] value = { "D", "C", "B", "A" };

        int[] score = { 100, 95, 80, 88, 70, 52, 60, 70 };

        ChoiceFormat form = new ChoiceFormat(limits, value);

        for (int i = 0; i < score.length; i++) {
            System.out.println(score[i] + " : " + form.format(score[i]));
        }
        System.out.println();

        String pattern = "60#D|70#C|80<B|90#A";
        ChoiceFormat format2 = new ChoiceFormat(pattern);

        for (int i = 0; i < score.length; i++) {
            if (score[i] == 80) {
                System.out.println(score[i] + " : " + format2.format(score[i]) + " (주의! 80<B라 경계값 포함 안됨)");
            } else {
                System.out.println(score[i] + " : " + format2.format(score[i]));
            }
        }
    }

    public static void messageFormatEx1() {
        String pattern = "Name: {0} \nTel: {1} \nAge: {2} \nBirthday: {3}";

        Object[] argument = { "이자바", "02-123-1234", 27, "07/29" };

        String result = MessageFormat.format(pattern, argument);

        System.out.println(result);
    }

    public static void messageFormatEx2() throws Exception { // File IO처리가 들어감
        String tableName = "custom_tbl";
        String pattern = "INSERT INTO " + tableName + " VALUES (''{0}'',''{1}'',{2},''{3}'');"; // '는 Escape문자이므로

        Object[][] arguments = { { "이자바", "02-123-1234", 27, "07/29" }, { "김프로", "032-333-1234", 33, "10/29" } };

        String[] data = new String[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            data[i] = MessageFormat.format(pattern, arguments[i]);
            System.out.println(data[i]);
        }

        MessageFormat mf = new MessageFormat(pattern);

        try {
            for (int i = 0; i < data.length; i++) {
                Object[] objs = mf.parse(data[i]);
                for (int j = 0; j < objs.length; j++) {
                    System.out.print(objs[j] + ", ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();

        String fileName = "src\\chapter10\\message_ref\\input.txt";

        Scanner sc = new Scanner(new File(fileName));

        String pattern2 = "{0},{1},{2},{3}";
        MessageFormat mf2 = new MessageFormat(pattern2);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Object[] obj = mf2.parse(line);
            System.out.println(MessageFormat.format(pattern, obj));
        }

        sc.close();
    }
}