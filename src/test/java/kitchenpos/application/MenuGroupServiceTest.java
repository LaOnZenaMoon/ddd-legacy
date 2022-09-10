package kitchenpos.application;

import kitchenpos.application.fake.FakeMenuGroupRepository;
import kitchenpos.application.support.TestFixture;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    private MenuGroupService menuGroupService;

    @BeforeEach
    void setup() {
        menuGroupService = new MenuGroupService(new FakeMenuGroupRepository());
    }

    @DisplayName("메뉴 그룹 생성")
    @ParameterizedTest
    @ValueSource(strings = {"menu Group", "proper name", "test name"})
    void create_menu_group(final String name) {
        MenuGroup menuGroup = TestFixture.createMenuGroupWithName(name);

        final MenuGroup result = menuGroupService.create(menuGroup);

        assertThat(result.getName()).isEqualTo(name);
    }

    @DisplayName("메뉴 그룹의 이름이 null이거나 비어있다면 IllegalArgumentException을 발생시킨다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_menu_group_whit_null_or_empty_name(final String name) {
        final MenuGroup menuGroup = TestFixture.createMenuGroupWithName(name);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> menuGroupService.create(menuGroup));
    }

    @DisplayName("생성된 메뉴 그룹은 조회가 가능하다")
    @Test
    void select_all_menu_group() {
        final MenuGroup firstMenuGroup = TestFixture.createMenuGroupWithName("첫메뉴");
        final MenuGroup secondMenuGroup = TestFixture.createMenuGroupWithName("두번째메뉴");

        menuGroupService.create(firstMenuGroup);
        menuGroupService.create(secondMenuGroup);

        final List<MenuGroup> menuGroupList = Arrays.asList(firstMenuGroup, secondMenuGroup);

        final List<MenuGroup> result = menuGroupService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(menuGroupList.size());
    }
}
