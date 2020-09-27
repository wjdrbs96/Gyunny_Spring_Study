package com.example.demo.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
    public static void main(String[] args) throws ParseException {

        String jsonData = "{\"Persons\":[{\"name\":\"고경태\",\"age\":\"30\",\"블로그\":\"ktko.tistory.com\",\"gender\":\"남자\"}, {\"name\":\"이홍준\",\"age\":\"31\",\"블로그\":\"없음\",\"gender\":\"남자\"}, {\"name\":\"서정윤\",\"age\":\"30\",\"블로그\":\"없음\",\"gender\":\"여자\"}], \"Books\":[{\"name\":\"javascript의모든것\",\"price\":\"10000\"},{\"name\":\"java의모든것\",\"price\":\"15000\"}]}";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);

        JSONArray jsonArray = (JSONArray)jsonObject.get("Persons");

        for (int i = 0; i < jsonArray.size(); ++i) {
            System.out.println("======== person : " + i + " ========");
            JSONObject bookObject = (JSONObject) jsonArray.get(i);
            System.out.println(bookObject.get("name"));
            System.out.println(bookObject.get("age"));

        }
    }
}
