package org.BigBank;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;

public class CalculateEndPointAPIVerificationTest extends AbstractTest {

    /**
     * Test allowing 0.1% difference between server and local calculation
     */
    private final static double ERROR_TOLERANCE_RATE_BETWEEN_CALCULATOR = 0.1;

    private final static String DEFAULT_CURRENCY = "SEK";
    private final static String DEFAULT_PRODUCT_TYPE = "LOANSE02";
    private final static int DEFAULT_MONTHLY_PAYMENT_DAY = 27;
    private final static int DEFAULT_CONCLUSION_FEE = 0;
    private final static int DEFAULT_ADMINISTRATION_FEE = 40;
    private final static int DEFAULT_MATURITY = 83;
    private final static double DEFAULT_INTEREST_RATE = 10.95;
    private final static long DEFAULT_AMOUNT = 85_000;

    static Stream<Arguments> amountAndMaturityAndInterestRate() {
        return Stream.of(
                Arguments.of(85_000L, 83, 10.95),
                Arguments.of(45_000L, 23, 7.45),
                Arguments.of(600_000L, 12, 23.45),
                Arguments.of(125_000L, 40, 12.45)
        );
    }

    @ParameterizedTest
    @MethodSource("amountAndMaturityAndInterestRate")
    public void verify_interest_rate_calculator_result(long amount, int maturity, double interestRate) {
        final Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("currency", DEFAULT_CURRENCY);
        requestMap.put("productType", DEFAULT_PRODUCT_TYPE);
        requestMap.put("monthlyPaymentDay", DEFAULT_MONTHLY_PAYMENT_DAY);
        requestMap.put("conclusionFee", DEFAULT_CONCLUSION_FEE);
        requestMap.put("administrationFee", DEFAULT_ADMINISTRATION_FEE);
        requestMap.put("maturity", maturity);
        requestMap.put("amount", amount);
        requestMap.put("interestRate", interestRate);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestMap)
                .when().post("https://ansokan.bigbank.se/api/v1/loan/calculate")
                .then().contentType(ContentType.JSON)
                .extract().response();


        final double expectedMonthlyPayment = monthlyPaymentCalculator(amount, interestRate, maturity) + DEFAULT_ADMINISTRATION_FEE;
        final double expectedTotalAmountPayment = expectedMonthlyPayment * maturity + DEFAULT_CONCLUSION_FEE;


        final double expectedMonthlyPaymentDifference = expectedMonthlyPayment * ERROR_TOLERANCE_RATE_BETWEEN_CALCULATOR / 100;
        final double expectedTotalPaymentDifference = expectedTotalAmountPayment * ERROR_TOLERANCE_RATE_BETWEEN_CALCULATOR / 100;

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(expectedMonthlyPayment, response.jsonPath().getDouble("monthlyPayment"), expectedMonthlyPaymentDifference);
        Assert.assertEquals(expectedTotalAmountPayment, response.jsonPath().getDouble("totalRepayableAmount"), expectedTotalPaymentDifference);
    }

    @ParameterizedTest
    @ValueSource(longs = {6_000, DEFAULT_AMOUNT, 300_000})
    public void verify_amount_borders(long amount) {
        final Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("currency", DEFAULT_CURRENCY);
        requestMap.put("productType", DEFAULT_PRODUCT_TYPE);
        requestMap.put("monthlyPaymentDay", DEFAULT_MONTHLY_PAYMENT_DAY);
        requestMap.put("conclusionFee", DEFAULT_CONCLUSION_FEE);
        requestMap.put("administrationFee", DEFAULT_ADMINISTRATION_FEE);
        requestMap.put("maturity", DEFAULT_MATURITY);
        requestMap.put("amount", amount);
        requestMap.put("interestRate", DEFAULT_INTEREST_RATE);

        given()
                .contentType(ContentType.JSON)
                .body(requestMap)
                .when().post("https://ansokan.bigbank.se/api/v1/loan/calculate")
                .then().statusCode(amount >= 10_000 && amount <= 250_000 ? 200 : 400);

    }

    @ParameterizedTest
    @ValueSource(doubles = {3.5, DEFAULT_INTEREST_RATE, 26.78D})
    public void verify_interest_rate(double amount) {
        final Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("currency", DEFAULT_CURRENCY);
        requestMap.put("productType", DEFAULT_PRODUCT_TYPE);
        requestMap.put("monthlyPaymentDay", DEFAULT_MONTHLY_PAYMENT_DAY);
        requestMap.put("conclusionFee", DEFAULT_CONCLUSION_FEE);
        requestMap.put("administrationFee", DEFAULT_ADMINISTRATION_FEE);
        requestMap.put("maturity", DEFAULT_MATURITY);
        requestMap.put("amount", DEFAULT_AMOUNT);
        requestMap.put("interestRate", defaultParser);

        given()
                .contentType(ContentType.JSON)
                .body(requestMap)
                .when().post("https://ansokan.bigbank.se/api/v1/loan/calculate")
                .then().statusCode(amount >= 5.00 && amount <= 23.50 ? 200 : 400);

    }

    /**
     * It could be replaced by BigBank Calculator to verify the result.
     *
     * @see <a href= "https://www.calculatorsoup.com/calculators/financial/loan-calculator.php">Reference</a>
     */
    private double monthlyPaymentCalculator(
            final long amount,
            final double interestRate,
            final int durationInMonth
    ) {
        double monthlyRate = interestRate / 12 / 100;
        double x = Math.pow(1 + monthlyRate, durationInMonth);
        return amount * monthlyRate * x / (x - 1);
    }
}