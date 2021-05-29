package wooteco.subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.path.domain.fare.age.BabyStrategy;
import wooteco.subway.path.domain.fare.age.ChildStrategy;
import wooteco.subway.path.domain.fare.age.DefaultStrategy;
import wooteco.subway.path.domain.fare.age.TeenagerStrategy;
import wooteco.subway.path.domain.fare.distance.OverFiftyStrategy;
import wooteco.subway.path.domain.fare.distance.TenToFiftyStrategy;
import wooteco.subway.path.domain.fare.distance.UnderTenStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.subway.line.LineFixture.이호선;

class FareTest {

    @DisplayName("요금 계산 - ~ 10km")
    @Test
    void calculateFare() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new UnderTenStrategy(), new DefaultStrategy());

        // when
        int totalFare = fare.calculate(10);

        // then
        assertThat(totalFare).isEqualTo(1250 + 1500);
    }

    @DisplayName("요금 계산 - 10km ~ 50km")
    @Test
    void calculateFare2() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new TenToFiftyStrategy(), new DefaultStrategy());

        // when
        int totalFare = fare.calculate(47);

        // then
        assertThat(totalFare).isEqualTo(2050 + 1500);
    }

    @DisplayName("요금 계산 - 50km ~")
    @Test
    void calculateFare3() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new OverFiftyStrategy(), new DefaultStrategy());

        // when
        int totalFare = fare.calculate(57);

        // then
        assertThat(totalFare).isEqualTo(2150 + 1500);
    }

    @DisplayName("요금 계산 - ~ 6세")
    @Test
    void calculateFareAge() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new UnderTenStrategy(), new BabyStrategy());

        // when
        int totalFare = fare.calculate(10);

        // then
        assertThat(totalFare).isEqualTo(0);
    }

    @DisplayName("요금 계산 - 6세 ~ 13세")
    @Test
    void calculateFareAge2() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new UnderTenStrategy(), new ChildStrategy());

        // when
        int totalFare = fare.calculate(10);

        // then
        assertThat(totalFare).isEqualTo(1200);
    }

    @DisplayName("요금 계산 - 13세 ~ 19세")
    @Test
    void calculateFareAge3() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new UnderTenStrategy(), new TeenagerStrategy());

        // when
        int totalFare = fare.calculate(10);

        // then
        assertThat(totalFare).isEqualTo(1920);
    }

    @DisplayName("요금 계산 - 19세 ~ & 비로그인 유저")
    @Test
    void calculateFareAge4() {
        // given
        Fare fare = new Fare(이호선.getExtraFare(), new UnderTenStrategy(), new DefaultStrategy());

        // when
        int totalFare = fare.calculate(10);

        // then
        assertThat(totalFare).isEqualTo(1250 + 1500);
    }
}