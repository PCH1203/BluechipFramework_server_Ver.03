package com.framework.demo.jwt;

import com.framework.demo.domain.User;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.repository.user.UserRepository;
import com.framework.demo.service.security.impl.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 *  토큰을 생성하고 검증하는 클래스
 *  해당 컴포넌트는 필터클래스에서 사전 검증을 칩니다.
 */


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Value("${jwt.private.key}")
    private String secretKey;

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;
    // 리프레시 유효시간 24시간
    private long refreshTokenValidTime = 60 * 60 * 24 * 1000L;
//    private long refreshTokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;
    private final UserDetailServiceImpl userDetailServiceImpl;

    // 객체 초기화 secretKey를 Base64로 인코딩 한다.
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성 함수
//    public String createToken (String userPk, int roleId) {
//        System.out.println(">>>>> Access Token 생성");
//        Claims claims = Jwts.claims().setSubject(userPk); //JWT payload에 저장되는 정보단위, 보통 여기서 user 식별 값을 넣는다.
//        claims.put("roles", roleId); // 정보는 key / value 쌍으로 저장된다.
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 세팅
//                .compact();
//    }

    /**
     * Access Token 생성
     * @param userPk
     * @param serviceId
     * @return
     */
    public String createToken (String userPk, String serviceId, String role) {
        System.out.println(">>>>> Access Token 생성");
        System.out.println(">>>>> serviceId: " + serviceId);
        Claims claims = Jwts.claims().setSubject(userPk); //JWT payload에 저장되는 정보단위, 보통 여기서 user 식별 값을 넣는다.
        claims.put("serviceId", serviceId); // 정보는 key / value 쌍으로 저장된다.
        claims.put("role", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 세팅
                .compact();
    }


    /**
     * 리프레시 토큰은 user 정보를 담지 않는다.
     * 오직 Access Token 재발급에만 사용 된다.
     * @param
     * @return
     */
    //JWT 리프레시 토큰 생성
    public String createRefreshToken () {
//        Claims claims = Jwts.claims().setSubject(userPk); //JWT payload에 저장되는 정보단위, 보통 여기서 user 식별 값을 넣는다.
        Date now = new Date();
        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {

        token = BearerRemove(token); // JWT 토큰 접두사 bearer 제거

        System.out.println("Bearer 제거 확인 : " + token);

        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(this.getUserPk(token));

        System.out.println("userDetails: " + userDetails );

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        System.out.println("토큰에서 회원 정보 추출 token: " + token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 회원 정보 추출
    public User findUserInfoByRequest(HttpServletRequest request) {
        System.out.println("request Header -> token -> userEmail -> uid 추출: ");
        System.out.println("Authorization: " + request.getHeader("Authorization"));
        String token = resolveToken(request);
        String userPk = getUserPk(token);
        System.out.println(">>>>> userPk: " + userPk);


        return userRepository.findByUserEmail(userPk);
    }

    // Request의 Header에서 token 값을 가져온니다. "Authorization" : "Tokent 값"
    public String resolveToken(HttpServletRequest request) {

        if(request.getHeader("Authorization") != null) {
            String refreshToken = BearerRemove(request.getHeader("Authorization")); // Bearer 제거
            return refreshToken;
        } else {
            return null;
        }
    }

    // 토큰 유효성 검사
    public boolean validateToken(String jwtToken) {

        System.out.println("toekn 유효성 검사");
        
        jwtToken = BearerRemove(jwtToken); // Bearer 제거

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            System.out.println("claims: " + claims);
             return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("toekn 유효성 검사 실패");
            return false;
        }
    }

    //==토큰 앞 부분('Bearer') 제거 메소드==//
    private String BearerRemove(String token) {
        return token.replace("Bearer ","");

    }



}
