# Aquality Selenium

Aquality Selenium является надстройкой над инструментом работы с браузером посредством Selenium WebDriver. Работа с Selenium WebDriver требует определенных навыков и опыта. Aquality Selenium предлагает упрощенный, а главное более безопасный и стабильный способ работы с Selenium WebDriver.

 - <a href="#1-platform-support">1. PLATFORM SUPPORT</a>
 - <a href='#2-configurations'>2. CONFIGURATIONS</a>
    - <a href='#21-settings'>2.1. SETTINGS</a>
    - <a href='#22-browser'>2.2. BROWSER</a>
    - <a href='#23-driver-settings'>2.3. DRIVER SETTINGS</a>
    - <a href='#24-timeouts'>2.4. TIMEOUTS</a>
    - <a href='#25-retry-policy'>2.5. RETRY POLICY</a>
    - <a href='#26-logging'>2.6. LOGGING</a>
    - <a href='#27-cloud-usage'>2.7. CLOUD USAGE</a>
    - <a href='#28-actions-highlighting'>2.8. ACTIONS HIGHLIGHTING</a>
    - <a href='#29-access-from-the-code'>2.9. ACCESS FROM THE CODE</a>
 - <a href='#3-browser'>3. BROWSER</a>
    - <a href='#31-parallel-runs'>3.1. PARALLEL RUNS</a>
    - <a href='#32-browser-manager'>3.2. BROWSER MANAGER</a>
    - <a href='#33-browser-factory'>3.3. BROWSER FACTORY</a>
    - <a href='#34-driver-capabilities'>3.4. DRIVER CAPABILITIES</a>
    - <a href='#35-download-directory'>3.5. DOWNLOAD DIRECTORY</a>
    - <a href='#36-alerts'>3.6. ALERTS</a>
    - <a href='#37-screenshots'>3.7. SCREENSHOTS</a>
 - <a href='#4-elements'>4. ELEMENTS</a>
    - <a href='#41-element-factory'>4.1. ELEMENT FACTORY</a>
    - <a href='#42-custom-elements'>4.2. CUSTOM ELEMENTS</a>
    - <a href='#43-list-of-elements'>4.3. LIST OF ELEMENTS</a>
    - <a href='#44-states-of-elements'>4.4. STATES OF ELEMENTS</a>
 - <a href='#5-forms'>5. FORMS</a>
 - <a href='#6-javascript-execution'>6. JAVASCRIPT EXECUTION</a>
 - <a href='#7-json-file'>7. JSON FILE</a>

### 1. PLATFORM SUPPORT
В настоящее время Aquality Selenium позволяет автоматизировать веб тесты для Chrome, Firefox, Safari, IExplorer, Edge, а также других бразузеров, которые поддерживаются Selenium (подробнее [здесь](https://www.seleniumhq.org/about/platforms.jsp)).
При этом запуск тестов может выполняться на любой операционной системе с установленным на ней JDK версии 8 и выше.

### 2. CONFIGURATIONS

Aquality Selenium предоставляет пользователю гибкие возможности по конфигурации запусков путём предоставления к редактированию конфигурационного файла [settings.json](./src/main/resources/settings.json)
Большинство настроек понятны без дополнительных объяснений, но основные моменты обозначены ниже в данном разделе.
Также существует возможность использования Aquality Selenium реализовав собственные классы конфигурации.

### 2.1. SETTINGS

Работа с решением подразумевает использование [settings.json](./src/main/resources/settings.json) или его измененных копий для запуска тестов.
Если вы работаете в директории /src/`test`, то для начала работы скопируйте файл [settings.json](./src/main/resources/settings.json) в директорию `/src/test/resources`.

По умолчанию у вас есть возможность вносить любые желаемые изменения в settings файл.
Но также можно создать несколько копий settings файла для единовременного хранения нескольких конфигураций, отличающихся какими-либо параметрами.
Как правило это удобно, когда есть необходимость выполнять запуск тестов на различных операционных системах, машинах, браузерах и т.п.
Например, в настоящее время команда разработчиков Aquality Selenium использует две конфигурации [settings.json](./src/main/resources/settings.json) и [settings.local.json](./src/test/resources/settings.local.json) для выполнения запусков в локальном docker Circle CI и на персональной инфраструктуре.
Для того, чтобы удобно управлять тем, какой конфигурационный файл необходимо использовать можно установить переменную окружения с именем profile и присвоить ей желаемое значение (например, local).
В случае запуска при помощи Maven команда запуска может выглядеть так: `clean test -Dprofile=local`.
По умолчанию во время запусков используется [settings.json](./src/main/resources/settings.json).

Любой параметр [settings.json](./src/main/resources/settings.json) может быть также переопределен через переменную окружения.
Для этого необходимо указать jsonPath к параметру в json и его значение. Например:
`clean test -DdriverSettings.chrome.webDriverVersion=77.0.3865.10`

Settings файл содержит в себе несколько секций, назначение которых описывается ниже.

#### 2.2. BROWSER
`browserName` параметр определяет на каком браузере будет выполняться запуск. Например browser=chrome - означает, что запуск осуществиться в Google Chrome.

`isRemote` параметр определят будет ли запуск выполняться на той же машине, где выполняется java процесс или использовать удалённый сервер, указанный в параметре `remoteConnectionUrl`.

#### 2.3. DRIVER SETTINGS
`driverSettings` секция файла [settings.json](./src/main/resources/settings.json) предоставляет возможность устанавливать необходимые capabilities, options или start arguments для web driver.

Для получения допустимых аргументов и опций обратитесь к официальным источникам от разработчиков браузеров. Например, для chrome: [run-chromium-with-flags](https://www.chromium.org/developers/how-tos/run-chromium-with-flags)

Мы постарались [здесь](./README_InternetExplorer.md) описать особенности работы с IExplorer самостоятельно ввиду разрознености информации на этот счёт в интернете.

#### 2.4. TIMEOUTS

[settings.json](./src/main/resources/settings.json) содержит секцию `timeouts`, которая включает в себя набор параметров, связанных с различного рода таймаутами, используемыми в решении.

Все параметры данной конфигурации используются для инициализации объекта класса [TimeoutConfiguration](./src/main/java/aquality/selenium/configuration/TimeoutConfiguration.java), доступного впоследствии путем обращения `Configuration.getInstance().getTimeoutConfiguration()`.

Ниже приводится описание параметров из секции `timeouts` c их назначением:

- `timeoutImplicit` = 0 - значение [Selenium Implicit Wait](https://www.seleniumhq.org/docs/04_webdriver_advanced.jsp#implicit-waits). В рамках решения все ожидания элементов выполняются при помощи Excplicit Wait. Перед ожиданием элемента значение implicit wait будет установлено принудительно, независимо от того, что находится в конфигурации. Использование двух типов ожиданий не рекомендовано, так как может приводить к некорректному поведению.
- `timeoutCondition` = 15 - время ожидания событий в решении. К событиям относятся ожидание элементов или их состояния.
- `timeoutScript` = 10 - данное значение служит лимитом выполнения скриптов с использованием метода WebDriver **executeAsyncScript**
- `timeoutPageLoad` = 30 - время ожидания загрузки страницы
- `timeoutPollingInterval` = 300 - интервал опроса в при явных ожиданиях

#### 2.5 RETRY POLICY

Секция `retry` файла [settings.json](./src/main/resources/settings.json) отвечает за конфигурацию количества попыток выполнения операций над элементом.
Все операции над элементами (нажатия, ввод текста и т.п.) могут быть выполнены повторно в случае неудачи.
Данная логика заложена в классе [ElementActionRetrier](./src/main/java/aquality/selenium/utils/ElementActionRetrier.java) посредством которого выполняются любые операции.
Параметр `number` означает количество предпринимаемых попыток выполнить операцию прежде чем выбросить исключение.
Параметр `pollingInterval` означает интервал между попытками в миллисекудах.
[ElementActionRetrier](./src/main/java/aquality/selenium/utils/ElementActionRetrier.java) автоматически отлавливает исключения StaleElementReferenceException и ElementNotInteractableException) и повторяет попытку снова. 

#### 2.6. LOGGING
Решение поддерживает логирование выполняемых операций (взаимодействие с браузером, элементами страницы). Пример логирования:

2019-07-18 10:14:08 INFO  - Label &#39;First product&#39; :: Moving mouse to element

Логирование поддерживается на языках

- [en](./src/main/resources/localization/en.json) - английский
- [ru](./src/main/resources/localization/ru.json) - русский

Значение языка логирования устанавливается в параметре [logger.language](./src/main/resources/settings.json)

#### 2.7. CLOUD USAGE

Для того, чтобы настроить запуск на облачной платформе (например, BrowserStack, Saucelabs и т.д.) необходимо в конфигурационном файле [settings.json](./src/main/resources/settings.json) установить корректное значение URL для подключения к сервису в параметр `remoteConnectionUrl`, а также убедиться, что параметр `isRemote` равен **true**.
Например, для BrowserStack параметр может иметь вид [https://USERNAME:AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub](https://USERNAME:AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub)

#### 2.8. ACTIONS HIGHLIGHTING

`isElementHighlightEnabled` параметр отвечает за необходимость подсветки элементов веб страницы с которыми производится работа. Включение опции позволяет более явно наблюдать за действиями теста.

#### 2.9. ACCESS FROM THE CODE

Доступ к данным из конфигурационного файла обеспечивается посредством обращения к методам экземпляра класса [Configuration](./src/main/java/aquality/selenium/configuration/Configuration.java)
Например:  
```
Configuration.getInstance().getBrowserProfile().getBrowserName()
```
вернёт значение параметра "browser" из settings файла.

### **3. BROWSER**

Класс Browser, являющийся своего рода фасадом для WebDriver и содержит методы работы с окном браузера и непосредственно с WebDriver (например, navigate, maximize window и т.д.). Написание скрипта начинается с создания экземпляра `Browser` - подробнее об этом ниже.

#### 3.1. PARALLEL RUNS

Решение предполагает наличие единственного экземпляра класса `Browser` (содержит поле типа RemoteWebDriver) в рамках одного потока исполнения. Как правило тесты работают с одним экземпляром браузера и данный подход оптимален.

Если вы решаете задачу использования в рамках одного теста несколько браузеров, каждый браузер необходимо создавать в отдельном потоке. С примерами работы в многопоточном режиме можно ознакомиться здесь [BrowserConcurrencyTests.java](./src/test/java/tests/usecases/BrowserConcurrencyTests.java)

Если вы используете стандартные средства параллелизации, предоставляемые такими инструментами как TestNG, JUnit и т.п., для каждого потока будет автоматически создан свой экземпляр Browser.

Если вы хотите использовать parallel streams доступных в Java начиная с 8ой версии, необходимо использовать BrowserManager для установки текущей версии Browser, иначе parallel stream создаст по одному новому браузеру на каждый параллельный поток.
Пример использования parallel streams приводится в [testShouldBePossibleToUseParallelStreams](./src/test/java/tests/usecases/BrowserConcurrencyTests.java).

#### 3.2. BROWSER MANAGER

Существует несколько вариантов инициализации Browser. Основной способ базируется на использовании класса `BrowserManager` c набором статических методов для получения `Browser`. Ниже рассматриваются варианты работы с `BrowserManager`.

Если нам необходимо получить браузер с данными из конфигурационого settings файла то достаточно просто произвести вызов:

```
Browser browser = BrowserManager.getBrowser()
```

Первый вызов `getBrowser()` создаст необходимый экземпляр с WebDriver (откроется окно браузера, если только не задан headless режим). Все последующие обращения в рамках одного потока будут работать с созданным экземпляром.


#### 3.3. BROWSER FACTORY

Неявно для пользователя `BrowserManager` предоставляет `Browser` посредством обращения к фабрике браузеров. В решение существуют следующие реализации фабрик:

- [LocalBrowserFactory](./src/main/java/aquality/selenium/browser/LocalBrowserFactory.java) - для создания браузера в случае использования параметра `isRemote=false`
- [RemoteBrowserFactory](./src/main/java/aquality/selenium/browser/RemoteBrowserFactory.java) - для создания браузера в случае использования параметра `isRemote=true`

Каждая реализация фабрики реализует интерфейс `IBrowserFactory` с единственным методом `Browser` `getBrowser()`. Это предоставляет возможность реализовать свою фабрику.
Чтобы `getBrowser()` возвращал `Browser`, созданный вашей реализацией фабрики, необходимо до первого вызова `getBrowser()` установить в `BrowserManager` свою реализацию фабрики, 
используя метод `setFactory(IBrowserFactory browserFactory)`. 
Примеры использования собственной фабрики можно рассмотреть здесь [BrowserFactoryTests](./src/test/java/tests/usecases/BrowserFactoryTests.java)

Если по каким либо причинам вы решите отказаться от использования фабрик, у вас остается возможность создать экземпляр `Browser` самостоятельно и в дальнейшем установить его в `BrowserManager.setBrowser(Browser browser)`. 
Класс Browser содержит публичный конструктор со следующей сигнатурой `Browser(RemoteWebDriver remoteWebDriver, IСonfiguration configuration)`. При этом вы по прежнему доступно использовать уже имеющиеся реализации `IDriverSettings`, `ITimeoutConfiguration` и т.д..

#### 3.4. DRIVER CAPABILITIES

В процессе создания Browser и в частности WebDriver используются реализации интерфейса `IDriverSettings`. Реализация включает метод getCapabilities, которые впоследствии устанавливаются в WebDriver при его инициализации. 
Если вы пользуетесь `BrowserFactory` по умолчанию, список capabilities будет сформирован на основании информации в [settings.json](./src/main/resources/settings.json) файле.

Пример с использованием пользовательских capabilities представлен зедсь [testShouldBePossibleToSetFactory](./src/test/java/tests/usecases/BrowserFactoryTests.java)



#### 3.5. DOWNLOAD DIRECTORY

Не редким случаем является необходимость скачивать файлы в браузере и впоследствии производить с ними работу. Чтобы получить текущую директорию для загрузки можно воспользоваться методом `getDownloadDirectory()` экземпляра `Browser`.

Для поддержания этой функциональности интерфейс `IDriverSettings` обязывает реализовать метод String getDownloadDir(). Если вы используете одну из уже реализованных BrowserFactory, то директорию для загрузки файлов необходимо указать в файле [settings.json](./src/main/resources/settings.json).
Например: 

```
{
 "download.default_directory": ".//target//downloads//"
}
```

Обратите внимание, что ключ download.default\_directory отличается для разных браузеров. Изучить какие ключи актуальны для каких браузеров можно в соответствующих  классах

[ChromeSettings.java](./src/main/java/aquality/selenium/configuration/driversettings/ChromeSettings.java)

[FirefoxSettings.java](./src/main/java/aquality/selenium/configuration/driversettings/FirefoxSettings.java)

В настоящее время решение поддерживает загрузку файлов только в браузерах Chrome, Firefox, Safari.

#### 3.6. ALERTS

Класс `Browser` предоставляет методы работы с Alerts:

```
BrowserManager.getBrowser().handleAlert(AlertActions.ACCEPT);
```

Больше примеров использования можно найти здесь [AlertTests.java](./src/test/java/tests/integration/AlertTests.java)

#### 3.7. SCREENSHOTS

Для получения снимков экрана класс Browser предоставляет метод 
```
BrowserManager.getBrowser().getScreenshot()
```

Более подробный пример использования смотрите в тесте [testShouldBePossibleToMakeScreenshot](./src/test/java/tests/integration/BrowserTests.java)

### **4. ELEMENTS**

Когда Browser инициализирован и осуществлен переход на требуемую страницу можно начинать работу с элементами этой страницы.

#### 4.1. ELEMENT FACTORY

Решение включает класс [ElementFactory](./src/main/java/aquality/selenium/elements/ElementFactory.java), который отвечает за создание элемента необходимого типа. Ниже приводится пример получения ITextBox:

```
ElementFactory elementFactory = new ElementFactory();
ITextBox txbUsername = elementFactory.getTextBox(By.id("username"), "Username");
```
ElementFactory` способна создавать объекты любых классов, реализующих интерфейс `IElement`.
ElementFactory` содержит ряд методов, которые возвращают реализации `IElement`, имеющиеся по умолчанию в решении (`IButton`, `ITextBox`, `ICheckbox` и т.д.). Обратите внимание, что работа с элементами ведется через интерфейсы, чтобы пользователь обращал внимание только на функциональность, но не на реализацию.

#### 4.2. CUSTOM ELEMENTS

Пользователь имеет возможность создать свой элемент или расширить имеющийся по умолчанию. Для этих целей `ElementFactory` предоставляет метод \&lt;T extends IElement\&gt; T getCustomElement. Достаточно или реализовать `IElement` интерфейс или расширить имеющийся класс элемента. С примером расширения и последующего использования можно ознакомиться в классе [CustomElementTests](./src/test/java/tests/usecases/CustomElementTests.java)

#### 4.3. LIST OF ELEMENTS

Для получения списка элементов `ElementFactory` предоставляет метод `findElements`, использование которого демонстрируется ниже

```
List<ICheckBox> checkBoxes = elementFactory.findElements(By.xpath(checkboxLocator), ElementType.CHECKBOX);
```

С другими примерами работы с `ElementFactory` и элементами можно здесь [ElementTests.java](./src/test/java/tests/integration/ElementTests.java)


#### 4.4. STATES OF ELEMENTS

При работе с элементами страницы в зависимости от задачи как правило ожидается либо  только нахождение элемента который виден на странице (displayed), либо который хотя бы присутствует в верстке (exists in any state).

Для получения и последующей работы с данными двумя типами элементов `ElementFactory` предоставляет перегруженные методы получения элементов. Например,

```
elementFactory.getLink(By.id("redirect"), "Link", ElementState.DISPLAYED);
```

При работе с элементами частой является ситуация проверки состояния элемента или ожидание желаемого состояния.
Данная функциональность реализуется посредством класса [ElementStateProvider](./src/main/java/aquality/selenium/elements/ElementStateProvider.java)
Доступ к экземпляру этого класса можно получить посредством вызова метода `state()` у элемента:
```
getTxbInput().state().waitForEnabled();
getTxbInput().state().isDisplayed();
```
Больше примеров можно увидеть в классе [ElementStateTests](./src/test/java/tests/integration/ElementStateTests.java)

### **5. FORMS**

Основное назначение решения  - помощь в автоматизации тестирования Web приложений. Существует практика автоматизации с использованием подхода [Page Objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects). Для поддержания и расширения данного подхода решение предлагает к использованию класс [Form](./src/main/java/aquality/selenium/forms/Form.java), который может служить родительским классом для всех описываемых страниц и форм приложения. Пример использования:
```
public class SliderForm extends Form {
    public SliderForm() {
        super(By.id("slider_row"), "Slider");
    }
}
```
Здесь id = &quot;slider\_row&quot; устанавливает локатор, который будет использован при проверке открытия страницы/формы, используя метод `isFormDisplayed()` класса [Form](./src/main/java/aquality/selenium/forms/Form.java).

Пример теста с использованием Page Objects здесь [ShoppingCartTest.java](./src/test/java/tests/usecases/ShoppingCartTest.java)


### **6. JAVASCRIPT EXECUTION**

При необходимости выполнить какой либо JavaScript на открытой странице можно воспользоваться методом
```
browser.executeScript(final String script, Object... arguments).
```

Решение содержит достаточное количество наиболее используемых скриптов при выполнении автоматизации тестирования. Список скриптов представлен перечислением JavaScript. Сами скрипты расположены в директории ресурсов /src/main/resources/js

Примеры использования метода имеются в классе [BrowserTests](./src/test/java/tests/integration/BrowserTests.java)

### **7. JSON FILE**

Aquality Selenium использует для своей работы и предоставляет доступ к классу [JsonFile](./src/main/java/aquality/selenium/utils/JsonFile.java)
Данный класс предоставляет удобные методы для работы с JSON файлами вашего проекта.
Например, если вы захотите хранить URL сайта с которым вы работаете как параметр конфигурации вы сможете считывать значения из JSON при помощи указанного класса:
```
JsonFile environment = new JsonFile("settings.json")
String url = environment.getValue("/url").toString();
```
