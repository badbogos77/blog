package com.cos.blog.controller;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfiles;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {	
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) {
		
		// POST 방식으로 key=value 데이터를 요청
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "c678d6faf21d5f6708d7499eb6c15a8e");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토근 : " + oauthToken.getAccess_token());
		
		// 사용자 정보 가져오기 POST 방식으로 key=value 데이터를 요청
		RestTemplate rt2 = new RestTemplate();
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");		
	
		HttpEntity<MultiValueMap<String, String>> kakaoProfilesRequest = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfilesRequest, String.class);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfiles kakaoProfiles = null;
		
		try {
			kakaoProfiles = objectMapper2.readValue(response2.getBody(), KakaoProfiles.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		String kakaousername = kakaoProfiles.getKakao_account().getEmail() + "_" + kakaoProfiles.getId();
		String kakaoemail = kakaoProfiles.getKakao_account().getEmail();
		String password = cosKey;
		
		System.out.println("카카오 정보가져오기 kakaousername" +kakaousername);
		System.out.println("카카오 정보 가져오기 kakaoemail " + kakaoemail);
		System.out.println("카카오 정보 가져오기 password " + password);
		
		User originUser = userService.회원확인(kakaousername); 
		// 회원 가입이 되어있는지 확인 후
		if (originUser.getUsername() == null) {
			System.out.println("회원가입 하기");
			//회원가입이 안되있으면 회원가입 
			User kakaoUser = User.builder().username(kakaousername).email(kakaoemail).password(cosKey).oauth("kakao").build();
			System.out.println("kakaoUser : " + kakaoUser);
			userService.회원가입(kakaoUser);
		}
		System.out.println("강제로그인하기");
		//로그인처리 		// 세션을 강제로 변경 시켜 줌
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaousername, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
		return "redirect:/ ";
	
	}
}
