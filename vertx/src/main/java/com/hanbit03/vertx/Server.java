package com.hanbit03.vertx;

//161207

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import org.apache.commons.lang3.StringUtils;

public class Server extends AbstractVerticle{
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		
		vertx.deployVerticle(new Server());
	
	}
	
	@Override
	public void start() throws Exception {
		
		vertx.createHttpServer().requestHandler(req -> {
			
			/*

			System.out.println(req.absoluteURI());
			System.out.println(req.uri());
			
			req.absoluteURI();의 출력 결과는
			웹브라우저에 내가 입력한 http://localhost:8080/plus/4/5 이다.
			(자동으로 반환되는 http://localhost:8080/favicon.ico 는 무시하자)
			
			req.uri(); 의 출력 결과는
			/plus/4/5 이다.
			 
			즉, uri는  http://localhost:8080 뒤의 결과임을 알 수 있다.
			
			*/
			
			String uri = req.uri();
			String result = "Hello!";
				
			String[] uris = StringUtils.split(uri, "/");	//uri를 /를 구분자로 쪼개고
			
			if(uris.length == 3 && "plus".equals(uris[0])) {	//3개로 쪼개졌고, uris[0]의 내용이 plus이면
				int x = Integer.parseInt(uris[1]);
				int y = Integer.parseInt(uris[2]);
				
				result = "<b>Sum : </b><span style='color:red;'>" + (x + y) +"</span>";
			}
			
			//Sample Pictures 안에 있는 그림이름들 중 받은 이름의 그림 출력하는 경우
			else if(uris.length == 2 && "image".equals(uris[0])){	
				String imageName = uris[1];
				
				String path = "C:/Users/Public/Pictures/Sample Pictures/" + imageName + ".jpg";
				
				req.response()
				.putHeader("content-type", "image/jpeg")
				.sendFile(path);				//path경로에 있는 이미지 띄운다
				
				return;
			}
			
			
			/* Jellyfish.jpg 만 출력되는 경우.
			 * 
			else if("/image".equals(uri)){	//주소창에 /image 요청이 오면,
				String path = "C:/Users/Public/Pictures/Sample Pictures/Jellyfish.jpg";
				
				req.response()
				.putHeader("content-type", "image/jpeg")
				.sendFile(path);				//path경로에 있는 이미지 띄운다
				
				return;
			}*/
			
			else if("/html".equals(uri)) {
				String[] imgNames = {"Chrysanthemum", "Desert", "Hydrangeas", "Jellyfish", "Koala", "Lighthouse", "Penguins", "Tulips"};
				
				result = "<div>";
				
				for(String imageName : imgNames) {
					result += "<img src='/image/" + imageName + "' style='width:200px'><br>";
				}
				
				result += "</div>";

			}
	
			
			//result에 html 형식이므로 "text/html"... 그냥 텍스트 형식이면 "text/plain"
			req.response()
				.putHeader("content-type", "text/html")		
				.end(result);
			
		}).listen(8080);
	}
	
	
	/*
	 * Address : http://www.naver.com:80/hello/index.nhn?id=hanbit&no=123
	 * 
	 * URL : http://www.naver.com:80/hello/index.nhn
	 * 
	 * Protocol : http
	 * Host : www.naver.com
	 * Port : 80
	 * 
	 * URI : /hello/index.nhn
	 * 
	 * QueryString : id=hanbit&no=123
	 * Request Parameter Name : id, no
	 * Request Parameter Value : hanbit, 123
	 * 
	 */
	
}
