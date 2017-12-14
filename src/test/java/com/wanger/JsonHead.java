package com.wanger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonHead {
    private Json head;

    public JsonHead() {
    }

    public Json getHead() {
        return this.head;
    }

    public void setHead(Json head) {
        this.head = head;
    }
    
    public static void main(String [] args){
    	String messString = "数据库连接异常，请联系接口提供者";
    	boolean flag = false;
    	String errorString = "2001";
    	Json json = new Json();
		json.setErrorCode(errorString);
		json.setMessage(messString);
		json.setSuccess(flag);
		JsonHead json1 = new JsonHead();
		json1.setHead(json);
		ObjectMapper mapper = new ObjectMapper();
		String sjson;
		try {
			sjson = mapper.writeValueAsString(json1);
			System.out.println(sjson);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //将对象转换成json  
		
    }
}
