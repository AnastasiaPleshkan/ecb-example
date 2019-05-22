Сurrencies converter sample base on data from https://www.ecb.europa.eu/stats/html/index.en.html with Android Architecture Components
===========================================================
Euro foreign exchange reference rates
The reference rates are usually updated around 16:00 CET on every working day, except on TARGET closing days. They are based on a regular daily concertation procedure between central banks across Europe, which normally takes place at 14:15 CET. 

This is a sample app that uses Android Architecture Components with Dagger 2.

Introduction
-------------

### Functionality
The app is composed of 2 screens.
#### ExchangeRatesFragment
Download euro foreign exchange reference rates. Provides the ability to convert currencies.
#### AnimatedFragment
This fragment displays the font amimation from https://airbnb.io/lottie/#/.
### Building
You can open the project in Android studio and press run.
### Testing
The project uses both instrumentation tests that run on the device
and local unit tests that run on your computer.
##### Webservice Tests
The project uses [MockWebServer][mockwebserver] project to test REST api interactions.


### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Timber][timber] for logging
* [mockito][mockito] for mocking in tests
* [worker][worker] for executing tasks in background 


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[timber]: https://github.com/JakeWharton/timber
[mockito]: http://site.mockito.org
[worker]: https://developer.android.com/topic/libraries/architecture/workmanager/basics
