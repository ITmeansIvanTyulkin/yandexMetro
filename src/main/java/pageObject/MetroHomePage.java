package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MetroHomePage {
    // поле с экземпляром веб драйвера
    private final WebDriver driver;

    public MetroHomePage(WebDriver driver) {
        this.driver = driver;
    }


    // локатор кнопки выпадающего списка городов по имени класса
    private final By selectCityButton = By.className("select_metro__button");

    // локатор поля ввода «Откуда» по XPATH, поиск по плейсхолдеру
    private final By fieldFrom = By.xpath(".//input[@placeholder='Откуда']");

    // локатор поля ввода «Куда» по XPATH, поиск по плейсхолдеру
    private final By fieldTo = By.xpath(".//input[@placeholder='Куда']");

    // локатор коллекций станций «Откуда» и «Куда» маршрута по имени класса
    private final By routeStationFromTo = By.className("route-details-block__terminal-station");
    private final By cityName = By.xpath("//div[8]/div/ul/li[1]/span");
    private final By getRouteLink = By.xpath("//div[3]/div/div[3]/div[2]/div/span");


    // метод ожидания загрузки страницы: проверили видимость станции «Театральная»
    public void waitForLoadHomePage() {
        // подожди 8 секунд, пока появится веб-элемент с нужным текстом
        new WebDriverWait(driver, 8)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Театральная']")));
    }

    // метод выбора города по переданному названию
    public void chooseCity(String cityName) {
        // кликни по выпадающему списку городов
        driver.findElement(selectCityButton).click();
        // выбери город, переданный в параметре метода
        driver.findElement(By.xpath(String.format("//*[text()='%s']", cityName))).click();
    }

    // метод ввода названия станции в поле «Откуда»
    public void setStationFrom(String stationFrom) {
        // введи название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбери его в выпадающем списке саджеста
        driver.findElement(fieldFrom).sendKeys(stationFrom, Keys.DOWN, Keys.ENTER);
    }

    // метод ввода названия станции в поле «Куда»
    public void setStationTo(String stationTo) {
        // введи название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбери его в выпадающем списке саджеста
        driver.findElement(fieldTo).sendKeys(stationTo, Keys.DOWN, Keys.ENTER);
    }

    // метод ожидания построения маршрута: проверяется видимость кнопки «Получить ссылку на маршрут»
    public void waitForLoadRoute() {
        // подожди 3 секунды, чтобы элемент с нужным текстом стал видимым
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Получить ссылку на маршрут']")));
    }

    // метод построения маршрута
    public void buildRoute(String fieldFrom, String fieldTo) {
        // указание станции «Откуда»
        setStationFrom(fieldFrom);
        // указание станции «Куда»
        setStationTo(fieldTo);
        // ожидание построения маршрута
        waitForLoadRoute();
    }

    // метод получения имени станции «Откуда» для построенного маршрута
    public String getRouteStationFrom() {
        // возвращается текст первого элемента коллекции — станции «Откуда» и «Куда»
        return driver.findElements(routeStationFromTo).get(0).getText();
    }

    // метод получения имени станции «Куда» построенного маршрута
    public String getRouteStationTo() {
        // возвращается текст второго элемента коллекции — станции «Откуда» и «Куда»
        return driver.findElements(routeStationFromTo).get(1).getText();
    }

    // метод получения примерного времени маршрута
    public String getApproximateRouteTime(int routeNumber) {
        // возвращается текст из требуемого по номеру элемента из коллекции времен всех маршрутов
        return driver.findElements(By.className("route-list-item__time")).get(routeNumber).getText();
    }

    // метод проверки с ожиданием видимости станции метро
    public void waitForStationVisibility(String stationName) {
        // ждем видимости элемента с нужным текстом из параметра в течение 8 секунд
        new WebDriverWait(driver, 8).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//*[text()='%s']", stationName))));
    }
}