package kitchenpos.application;

import kitchenpos.domain.OrderTable;
import kitchenpos.helper.OrderTableHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static kitchenpos.helper.NameHelper.NAME_OF_255_CHARACTERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class OrderTableServiceTest extends ApplicationTest {

    @Autowired
    private OrderTableService orderTableService;


    @DisplayName("새로운 테이블을 등록한다.")
    @Nested
    class CreateOrderTable {

        @DisplayName("상품명은 비어있을 수 없고, 255자를 초과할 수 없다.")
        @Nested
        class Policy1 {
            @DisplayName("상품명이 1자 이상 255자 이하인 경우 (성공)")
            @ParameterizedTest
            @ValueSource(strings = {"한", "a", "1", "상품명", "product name", "상품 A", NAME_OF_255_CHARACTERS})
            void success1(final String name) {
                // Given
                OrderTable orderTable = OrderTableHelper.create(name);

                // When
                OrderTable createdOrderTable = orderTableService.create(orderTable);

                // Then
                assertThat(createdOrderTable.getName()).isEqualTo(name);
            }

            @DisplayName("상품명이 null 인 경우 (실패)")
            @ParameterizedTest
            @NullSource
            void fail1(final String name) {
                // When
                OrderTable orderTable = OrderTableHelper.create(name);

                // Then
                assertThatThrownBy(() -> orderTableService.create(orderTable))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

}