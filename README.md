# Publisher-Subscriber
В этой ветке находится более сложная версия задания. Light-версию см. в ветке light.

Unit-тесты не сделаны для экономии времени (в реальной работе лучше использовать TDD, конечно).

Так как таблицы две, то можно было бы сделать два DAO-класса, но тут действия с таблицами идентичны, поэтому у меня только один DAO.

Асинхронность отправки сообщений возникает благодаря методу Flux.flatMap.
Поэтому вручную создавать Thread-ы и отправлять оттуда сообщения особо смысла нет.

Схема БД:

![ссылка](schema.png)
