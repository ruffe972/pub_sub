Unit-тесты не сделаны для экономии времени (в реальной работе лучше использовать TDD, конечно).
Упор сделан на простоту кода, а не на эффективность в паре моментов:
* Сервер отвечает клиенту только после синхронной записи в БД.
* Клиент блокируется, пока не дождётся ответа сервера.

Хотел освоить реактивное программирование, поэтому здесь используется Spring Webflux вместо Spring MVC.