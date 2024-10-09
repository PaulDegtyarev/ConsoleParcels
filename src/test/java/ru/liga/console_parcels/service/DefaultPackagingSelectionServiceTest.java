package ru.liga.console_parcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.liga.console_parcels.config.AppConfig;
import ru.liga.console_parcels.model.UserAlgorithmChoice;
import ru.liga.console_parcels.service.impl.DefaultPackagingSelectionService;
import ru.liga.console_parcels.service.impl.OptimizedPackagingService;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DefaultPackagingSelectionService.class, AppConfig.class})
@Import(DefaultPackagingSelectionService.class)
class DefaultPackagingSelectionServiceTest {
    @Autowired
    PackagingSelectionService packagingSelectionService;

    @MockBean
    Map<UserAlgorithmChoice, TruckPackageService> serviceMap;

    @Test
    void selectPackagingService_withValidInput_shouldReturnValidOutput() {
        UserAlgorithmChoice algorithmChoice = UserAlgorithmChoice.MAX_SPACE;
        TruckPackageService mockService = mock(TruckPackageService.class);

        when(serviceMap.get(any(UserAlgorithmChoice.class))).thenReturn(mockService);

        TruckPackageService actualResponse = packagingSelectionService.selectPackagingService(algorithmChoice);

        assertThat(mockService).isEqualTo(actualResponse);
    }
}
