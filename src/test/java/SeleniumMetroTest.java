import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MetroHomePage;

public class SeleniumMetroTest {

    // создание поля для драйвера и страницы
    private WebDriver driver;
    private MetroHomePage metroPage;


    // создание константы для тестовых данных
    private static final String CITY_SAINTP = "Санкт-Петербург";
    private static final String STATION_SPORTIVNAYA = "Спортивная";
    private static final String  STATION_LUBYANKA = "Лубянка";
    private static final String STATION_KRASNOGVARD = "Красногвардейская";


    // все предварительные действия вынесены в Before
    @Before
    public void setUp() {
        // открой браузер Chrome

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // переход на страницу тестового приложения
        driver.get("https://qa-metro.stand-2.praktikum-services.ru/");

        // создание объекта класса страницы стенда
        metroPage = new MetroHomePage(driver);

        // ожидание загрузки страницы
        metroPage.waitForLoadHomePage();
    }

    // проверка, как работает выбор города
    @Test
    public void checkChooseCityDropdown() {
        // выбираем Санкт-Петербург в списке городов
        metroPage.chooseCity(CITY_SAINTP);

        // проверка, что станция метро «Спортивная» видна через 8 секунд
        metroPage.waitForStationVisibility(STATION_SPORTIVNAYA);
    }

    // проверка отображения времени маршрута
    @Test
    public void checkRouteApproxTimeIsDisplayed() {
        // постройка маршрута от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA,STATION_KRASNOGVARD);

        // проверка, что у первого маршрута списка отображается нужное примерное время поездки
        Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
    }

    // проверка отображения станции «Откуда» в карточке маршрута
    @Test
    public void checkRouteStationFromIsCorrect() {
        // постройка маршрута от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA,STATION_KRASNOGVARD);
        // проверка, что отображается корректное название станции начала маршрута
        metroPage.getRouteStationFrom();
        Assert.assertEquals(STATION_LUBYANKA, metroPage.getRouteStationFrom());
    }

    // проверка отображения станции «Куда» в карточке маршрута
    @Test
    public void checkRouteStationToIsCorrect() {
        // постройка маршрута от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA,STATION_KRASNOGVARD);
        // проверка, что отображается корректное название станции конца маршрута
        Assert.assertEquals(STATION_KRASNOGVARD, metroPage.getRouteStationTo());
    }

    // добавление метода с аннотацией After для закрытия браузера
    @After
    public void tearDown() {
        // закрой браузер
       driver.quit();
    }
}