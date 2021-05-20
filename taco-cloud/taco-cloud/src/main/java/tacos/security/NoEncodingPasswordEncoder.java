package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoEncodingPasswordEncoder implements PasswordEncoder {

    @Override
    // encode() : 로그인 대화상자에 입력된 비밀번호(rawPwd)를 암호화 하지 않고 String으로 반환
    public String encode(CharSequence rawPwd) {
        return rawPwd.toString();
    }

    @Override
    // matches() : encode()에서 반환된 비밀번호를 DB에서 가져온 비밀번호(encodedPwd)와 비교한다
    public boolean matches(CharSequence rawPwd, String encodedPwd) {
        return rawPwd.toString().equals(encodedPwd);
    }
}
