package com.example.frontend_app;

import java.io.*;
import java.util.*;

public class TextReader {
    HashMap<String, String> owasp = new HashMap<String, String>();
    public String[] owaspTestNames = new String[10];
    public String[] owaspTestResults = new String[10];


    public void init(){
        owaspInit();

        for(int i = 0; i < 10; i++)
            owasp.put("a"+(i+1), "not found");

        try {
            findResults();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getOwasp();
    }

    void owaspInit(){
        owaspTestNames[0] = "A1 - Injection";
        owaspTestNames[1] = "A2 - Broken Authentication";
        owaspTestNames[2] = "A3 - Sensitive Data Exposure";
        owaspTestNames[3] = "A4 - XML External Entities (XXE)";
        owaspTestNames[4] = "A5 - Broken Access Control";
        owaspTestNames[5] = "A6 - Security Misconfiguration";
        owaspTestNames[6] = "A7 - Cross-Site Scripting (XSS)";
        owaspTestNames[7] = "A8 - Insecure Deserialization";
        owaspTestNames[8] = "A9 - Using Components with Known Vulnerabilities";
        owaspTestNames[9] = "A10 - Insufficient Logging & Monitoring";

        for(int i = 0; i < owaspTestResults.length; i++)
            owaspTestResults[i] = "NOT FOUND!";
    }

    void findResults() throws Exception{
        File file = new File("..\\resources\\data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String owaspTag = st.substring(0, 5);
            System.out.println(owaspTag);
            if(owaspTag.equals("owasp")){
                StringBuilder sb = new StringBuilder();
                sb.append(st.charAt(6));
                sb.append(st.charAt(7));
                if(st.charAt(8) != ' ')
                    sb.append(st.charAt(8));

                String key = sb.toString();
                key = key.toLowerCase();
                System.out.println(key);
                String value = " ";
                int charIndex = 9;
                while (value == " "){
                    if(st.charAt(charIndex) != ' ')
                        value = String.valueOf(st.charAt(charIndex));
                    charIndex++;
                }
                if(owasp.containsKey(key))
                    owasp.put(key, value.toUpperCase());
            }
        }
    }

     void getOwasp(){
        for(String key: owasp.keySet()){
            switch (key){
                case "a1":
                    owaspTestResults[0] = owasp.get(key);
                    break;
                case "a2":
                    owaspTestResults[1] = owasp.get(key);
                    break;
                case "a3":
                    owaspTestResults[2] = owasp.get(key);
                    break;
                case "a4":
                    owaspTestResults[3] = owasp.get(key);
                    break;
                case "a5":
                    owaspTestResults[4] = owasp.get(key);
                    break;
                case "a6":
                    owaspTestResults[5] = owasp.get(key);
                    break;
                case "a7":
                    owaspTestResults[6] = owasp.get(key);
                    break;
                case "a8":
                    owaspTestResults[7] = owasp.get(key);
                    break;
                case "a9":
                    owaspTestResults[8] = owasp.get(key);
                    break;
                case "a10":
                    owaspTestResults[9] = owasp.get(key);
                    break;
            }
        }
    }
}
