package com.pyr.permission.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pyr.permission.exception.BizException;
import com.pyr.permission.model.SysUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 用户登录后，生成JWT, 拼接在url的参数里， 或者直接返回给客户端
 * 客户端收到服务器返回的 JWT，可以储存在 Cookie 里面，也可以储存在 localStorage
 * - 放在 Cookie 里面自动发送，但是这样不能跨域
 * - 更好的做法是放在 HTTP 请求的Header的`Authorization`字段里面
 * 此后，客户端每次与服务器通信，都要带上这个 JWT
 */
public class JWTUtil {

    // jwt加密使用的密钥
    private static final String CREDENTIAL_SECRET = "glkjSDGFKLJAHRTLKADGH2389675NALSKDFJG";

    // 加密使用的算法
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(CREDENTIAL_SECRET);

    // 7天过期
    public static final long EXPIRATION = 24 * 60 * 60 * 1000 * 7;
    public static final String USER_ID = "userId";

    /**
     * 生成token
     */
    public static String createToken(SysUser u) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION);

        return JWT.create()
                .withSubject("permission-sso-jwt")
                .withClaim(USER_ID, u.getId())
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(u.getPassward()));
    }

    /**
     * 验证并解析token
     */
    public static Map<String, Claim> verifyToken(String token) throws BizException {
        if (StringUtils.isEmpty(token)) {
            throw new BizException("token不能为空");
        }
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return decodedJWT.getClaims();
        } catch (TokenExpiredException e) {
            throw new BizException("token已经过期");
        } catch (Exception e) {
            throw new BizException("token验证失败");
        }
    }

    /**
     * 检查token是否需要更新
     */
    public static boolean isNeedUpdate(String token) {
        //获取token过期时间
        Date expiresAt;
        try {
            expiresAt = getDecodedJWT(token).getExpiresAt();
        } catch (TokenExpiredException e) {
            return true;
        } catch (Exception e) {
            throw new BizException("token验证失败");
        }
        //如果剩余过期时间少于过期时常的一般时 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (EXPIRATION >> 1);
    }

    private static DecodedJWT getDecodedJWT(String token) {
        return JWT.require(JWTUtil.ALGORITHM).build().verify(token);
    }
}