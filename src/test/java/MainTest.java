import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class MessageTest {

    @AfterEach
    void print() {
        System.out.println();
    }

    @Mock
    GeoService geoServiceMock;
    @Mock
    LocalizationService localizationServiceMock;

    @Test
    @Order(1)
    @DisplayName("Проверка определения локации по ip (класс GeoServiceImpl)")
    void TestGeoServiceImpl() {
        GeoService geoService = new GeoServiceImpl();
        String NEW_YORK_IP = "96.44.183.149";
        Location locationExpected = new Location("New York", Country.USA, " 10th Avenue", 32);
        Location locationActual = geoService.byIp(NEW_YORK_IP);
        Assertions.assertEquals(locationExpected.getCity(), locationActual.getCity());
    }

    @Test
    @Order(2)
    @DisplayName("Проверка возвращаемого текста (класс LocalizationServiceImpl)")
    void TestLocalizationServiceImpl() {
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Assertions.assertEquals("Добро пожаловать", localizationServiceMock.locale(Country.RUSSIA));
    }

    @Test
    @Order(3)
    @DisplayName("Проверка по стране RUSSIA")
    void TestRussia() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Добро пожаловать", messageSender.send(Mockito.anyMap()));
    }

    @Test
    @Order(4)
    @DisplayName("Проверка по стране USA")
    void TestUSA() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.USA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Welcome", messageSender.send(Mockito.anyMap()));
    }

    @Test
    @Order(5)
    @DisplayName("Проверка по стране BRAZIL")
    void TestBRAZIL() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.BRAZIL, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.BRAZIL)).thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Welcome", messageSender.send(Mockito.anyMap()));
    }

    @Test
    @Order(6)
    @DisplayName("Проверка по стране GERMANY")
    void TestGERMANY() {
        Mockito.when(geoServiceMock.byIp(Mockito.any())).thenReturn(new Location(null, Country.GERMANY, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.GERMANY)).thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Assertions.assertEquals("Welcome", messageSender.send(Mockito.anyMap()));
    }
}