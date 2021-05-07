package tacos;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
public class Order {

    @NotBlank(message="Name is required")
    private String deliveryName;

    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    private String deliveryCity;

    @NotBlank(message="State is required")
    private String deliveryState;

    @NotBlank(message="Zip is required")
    private String deliveryZip;

    // @CreditCardNumber : 속성의 값이 Luhn 알고리즘 검사에 합격한 유요한 신용 카드 번호이어야 한다는 것을 선언
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    // Pattern 어노테이션에 정규 표현식을 지정하여 속성 값이 해당 형식을 따르는지 확인
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;

    // 입력값이 정확하게 세자리 숫자인지 검사
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;
}
