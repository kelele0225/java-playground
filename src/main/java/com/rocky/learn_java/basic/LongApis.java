package com.rocky.learn_java.basic;

import static java.lang.System.out;

public class LongApis {
	
	private final String a = null;
	static String b;
	
	public static void main(String[] args) {
		out.println(System.getProperty("user.dir"));
		out.println(Long.valueOf("23"));
		LongApis la = new LongApis();
	
		la.myAtoi("42");
		
		out.println((301/60+1)*150);
	}
	
	public int myAtoi(String str) {
        if(str==null||str.length()==0) return 0;
        StringBuilder sb = new StringBuilder();
        boolean numberStarted = false;
        boolean numberEnded = false;
        char[] s = str.toCharArray();
        int i=0;
        int length = str.length();
        while(!numberStarted&&i<str.length()){
            if(s[i]==' ') i++;
            else if(s[i]=='-'||s[i]=='+'||(s[i]>='0'&&s[i]<='9')){
                sb.append(s[i]);
                i++;
                numberStarted = true;
            }else{
                //find non number chars at the beginning
                return 0;
            }
        }
        if(!numberStarted) return 0;
        while(!numberEnded&&i<length){
            if(s[i]>=0&&s[i]<=9){
                sb.append(s[i]);
                i++;
            } else {
                numberEnded = true;
            }
        }
        String numberS = sb.toString();
        try{
            return Integer.parseInt(numberS.toString());
            
        }catch(Exception e){
            char sign = numberS.charAt(0);
            if(sign=='+'||sign=='0') return Integer.MAX_VALUE;
            return Integer.MIN_VALUE;
        }
    }
}
